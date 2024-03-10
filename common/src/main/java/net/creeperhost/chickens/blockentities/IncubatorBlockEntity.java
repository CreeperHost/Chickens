package net.creeperhost.chickens.blockentities;

import dev.architectury.fluid.FluidStack;
import net.creeperhost.chickens.ChickensPlatform;
import net.creeperhost.chickens.api.ChickenStats;
import net.creeperhost.chickens.config.Config;
import net.creeperhost.chickens.containers.IncubatorMenu;
import net.creeperhost.chickens.init.ModBlocks;
import net.creeperhost.chickens.item.ItemChicken;
import net.creeperhost.chickens.item.ItemChickenEgg;
import net.creeperhost.polylib.blocks.PolyBlockEntity;
import net.creeperhost.polylib.blocks.RedstoneActivatedBlock;
import net.creeperhost.polylib.data.serializable.FloatData;
import net.creeperhost.polylib.data.serializable.IntData;
import net.creeperhost.polylib.helpers.MathUtil;
import net.creeperhost.polylib.inventory.fluid.*;
import net.creeperhost.polylib.inventory.item.ContainerAccessControl;
import net.creeperhost.polylib.inventory.item.ItemInventoryBlock;
import net.creeperhost.polylib.inventory.item.SerializableContainer;
import net.creeperhost.polylib.inventory.item.SimpleItemInventory;
import net.creeperhost.polylib.inventory.power.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

public class IncubatorBlockEntity extends PolyBlockEntity implements PolyFluidBlock, ItemInventoryBlock, MenuProvider, PolyEnergyBlock, RedstoneActivatedBlock {
    public static int HEAT_INCREMENT = 4; //TODO Does this need to be moved to a place like Constants?
    public static int TEMP_MAX = 40;
    public static int TEMP_MIN = 35;
    public static int MIN_HUMIDITY = 50;

    public final PolyTank tank = new PolyBlockTank(this, FluidManager.BUCKET * 16L, e -> e.getFluid().isSame(Fluids.WATER));
    public final SimpleItemInventory inventory = new SimpleItemInventory(this, 11)
            .setMaxStackSize(1)
            .setSlotValidator(9, stack -> FluidManager.isFluidItem(stack) && FluidManager.getHandler(stack).getFluidInTank(0).getFluid() == Fluids.WATER)
            .setSlotValidator(10, stack -> EnergyManager.isEnergyItem(stack) && EnergyManager.getHandler(stack).canExtract())
            .setStackValidator((slot, stack) -> stack.getItem() instanceof ItemChickenEgg);

    public final PolyEnergyStorage energy = new PolyBlockEnergyStorage(this, 128000);

    public final IntData temperature = register("temperature", new IntData(10), SAVE_BOTH);
    public final FloatData humidity = register("humidity", new FloatData(20), SAVE_BOTH);
    public final IntData heatSetting = (IntData) register("heat_setting", new IntData(0), SAVE_BOTH, CLIENT_CONTROL).setValidator(e -> e >= 0 && e <= 15);

    public IncubatorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlocks.INCUBATOR_TILE.get(), blockPos, blockState);
    }

    @Override
    public void tick() {
        super.tick();
        if (level.isClientSide()) return;

        updateResources();

        //Logic update occurs every 20 ticks
        if (!onInterval(20)) return;

        Holder<Biome> biome = level.getBiome(getBlockPos());
        int localTemperature = ChickensPlatform.getBiomeTemperature(biome);
        int localHumidity = ChickensPlatform.getBiomeHumidity(biome);

        updateHeat(localTemperature);
        updateHumidity(localTemperature, localHumidity);
        updateIncubation();
    }

    private void updateResources() {
        ItemStack bucketStack = inventory.getItem(9);
        if (!bucketStack.isEmpty()) {
            PolyFluidHandlerItem handler = FluidManager.getHandler(bucketStack);
            if (FluidManager.transferFluid(handler, tank).getAmount() > 0) {
                inventory.setItem(9, handler.getContainer());
            }
        }

        if (Config.INSTANCE.enableEnergy) {
            EnergyManager.transferEnergy(inventory.getItem(10), energy);
        }
    }

    private void updateHeat(int localTemperature) {
        int targetHeat = localTemperature + (heatSetting.get() * HEAT_INCREMENT);
        int energy = Math.max(temperature.get() - localTemperature, 0) * 10;

        if (localTemperature > temperature.get()) {
            temperature.inc();
        } else if (isTileEnabled() && consumeEnergy(energy)) {
            if (temperature.get() < targetHeat) {
                temperature.inc();
            } else if (temperature.get() > targetHeat && temperature.get() > localTemperature) {
                temperature.dec();
            }
        } else if (temperature.get() > localTemperature) {
            temperature.dec();
        }
    }

    private boolean consumeEnergy(int amount) {
        if (!Config.INSTANCE.enableEnergy) return true;
        return energy.extractEnergy(amount, false) == amount;
    }

    private void updateHumidity(int localTemperature, int localHumidity) {
        //Humidity Drain based on temperature
        float rate = ((temperature.get() - localTemperature) / 10F) * (humidity.get() / 100F);
        if (rate > 0) {
            humidity.subtract(rate);
            //Humidity will never go lower than the local value.
            humidity.set(Math.max(humidity.get(), localHumidity));
            if (humidity.get() < 0) humidity.set(0F);
        } else if (humidity.get() < localHumidity) {
            humidity.inc();
        } else if (humidity.get() > localHumidity) {
            humidity.dec();
        }


        //For simplicity, lets just say the incubator contains 1 cubic meter of air.
        //1 cubic meter of air at sea level weighs = 1.3kg.
        //Starting at 0% humidity, for a increase of 10% you would need to add *around* 3.7 grams of water.
        //So lets round that up to 4 grams, 1 gram of water = 1ml, lets say 1ml == 1mb.
        //That means 1mb of water results in a humidity increase of 2.5%
        //This is loosely based on a psychrometric chart.

        if (humidity.get() >= MIN_HUMIDITY || tank.isEmpty() || !isTileEnabled()) return;
        float needed = MIN_HUMIDITY - humidity.get();
        float needWater = needed / 2.5F; //Convert that to water
        int minExtract = (int) Math.ceil(needWater); //Round that up to the nearest int value

        FluidStack drained = tank.drain(FluidStack.create(Fluids.WATER, minExtract), false);
        if (!drained.isEmpty()) {
            humidity.add(drained.getAmount() * 2.5F);
        }
    }

    private void updateIncubation() {
        double hVal = MathUtil.clamp(1D - (humidity.get() / (double) MIN_HUMIDITY), 0, 1);
        //Death chance goes from 0 to 1% on an exponential curve as humidity reaches 0%
        //thats 1% chance per second, so on average 100 seconds form an egg to become non-viable at 0% humidity
        double deathChance = hVal * hVal * hVal * 0.01;

        RandomSource random = level.getRandom();

        for (int slot = 0; slot < 9; slot++) {
            ItemStack stack = inventory.getItem(slot);
            if (!(stack.getItem() instanceof ItemChickenEgg eggItem) || !eggItem.isViable(stack)) {
                continue;
            }

            if (deathChance > random.nextDouble()) {
                eggItem.setNotViable(stack);
                continue;
            }

            int progress = eggItem.getProgress(stack);
            if (isWithinHatchingTemp()) {
                if (progress < 100) {
                    //Previously we were updating a single random egg every second.
                    //This keeps some randomness without multiplying hatching time by the number of eggs, which seems unrealistic.
                    if (random.nextBoolean()) continue;
                    progress++;
                    eggItem.setProgress(stack, progress);
                } else {
                    //20% chance to hatch per second, adds some more randomness.
                    if (random.nextDouble() > 0.2) continue;
                    ItemStack chicken = ItemChicken.of(eggItem.getType(stack));
                    ChickenStats chickenStats = new ChickenStats(stack);
                    chickenStats.write(chicken);
                    if (!chicken.isEmpty()) {
                        inventory.setItem(slot, chicken);
                    }
                }
            } else if (progress > 0) {
                eggItem.incrementMissed(stack);
                int temp = temperature.get();
                if (temp > TEMP_MAX) {
                    double over = temp - TEMP_MAX;
                    if (eggItem.getMissedCycles(stack) > 1000 / over) {
                        eggItem.setNotViable(stack);
                    }
                } else if (temp < TEMP_MIN) {
                    //Too cold is not as dangerous as too hot, so we limit the min missed cycles to 200;
                    double over = Math.min(TEMP_MIN - temp, 5);
                    if (eggItem.getMissedCycles(stack) > 1000 / over) {
                        eggItem.setNotViable(stack);
                    }
                }
            }
        }
    }

    public boolean isWithinHatchingTemp() {
        return temperature.get() >= TEMP_MIN && temperature.get() <= TEMP_MAX;
    }

    @Override
    public void writeExtraData(CompoundTag nbt) {
        nbt.put("fluid_tank", tank.serialize(new CompoundTag()));
        inventory.serialize(nbt);
        energy.serialize(nbt);
    }

    @Override
    public void readExtraData(CompoundTag nbt) {
        tank.deserialize(nbt.getCompound("fluid_tank"));
        inventory.deserialize(nbt);
        energy.deserialize(nbt);
    }

    @Override
    public @Nullable PolyFluidHandler getFluidHandler(@Nullable Direction direction) {
        return tank;
    }

    @Override
    public SerializableContainer getContainer(@Nullable Direction side) {
        return new ContainerAccessControl(inventory, 0, Config.INSTANCE.enableEnergy ? 11 : 10)
                .containerRemoveCheck((slot, stack) -> {
                    if (slot <= 8) {
                        if (!(stack.getItem() instanceof ItemChickenEgg eggItem)) {
                            return true;
                        }
                        return !eggItem.isViable(stack);
                    } else if (slot == 9) {
                        PolyFluidHandlerItem handler = FluidManager.getHandler(stack);
                        if (handler == null) return true;
                        return handler.drain(FluidStack.create(Fluids.WATER, FluidManager.BUCKET), true).isEmpty();
                    } else if (slot == 10) {
                        IPolyEnergyStorage energy = EnergyManager.getHandler(stack);
                        return energy == null || !energy.canExtract() || energy.getEnergyStored() == 0;
                    }
                    return true;
                });
    }

    @Override
    public IPolyEnergyStorage getEnergyStorage(Direction side) {
        return Config.INSTANCE.enableEnergy ? energy : null;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new IncubatorMenu(i, inventory, this);
    }
}
