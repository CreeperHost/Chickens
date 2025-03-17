package net.creeperhost.chickens.blockentities;

import net.creeperhost.chickens.api.ChickenStats;
import net.creeperhost.chickens.api.ChickensRegistry;
import net.creeperhost.chickens.api.ChickensRegistryItem;
import net.creeperhost.chickens.block.BreederBlock;
import net.creeperhost.chickens.config.Config;
import net.creeperhost.chickens.containers.BreederMenu;
import net.creeperhost.chickens.init.ModBlocks;
import net.creeperhost.chickens.init.ModChickens;
import net.creeperhost.chickens.init.ModComponentTypes;
import net.creeperhost.chickens.init.ModItems;
import net.creeperhost.chickens.item.ItemChicken;
import net.creeperhost.chickens.item.ItemChickenEgg;
import net.creeperhost.chickens.polylib.CommonTags;
import net.creeperhost.polylib.blocks.PolyBlockEntity;
import net.creeperhost.polylib.data.serializable.FloatData;
import net.creeperhost.polylib.helpers.ContainerUtil;
import net.creeperhost.polylib.inventory.items.BlockInventory;
import net.creeperhost.polylib.inventory.items.ContainerAccessControl;
import net.creeperhost.polylib.inventory.items.PolyInventoryBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class BreederBlockEntity extends PolyBlockEntity implements PolyInventoryBlock, MenuProvider {

    public final BlockInventory inventory = new BlockInventory(this, 7)
            .setSlotValidator(0, CommonTags::isSeeds)
            .setSlotValidator(1, e -> e.is(ModItems.CHICKEN_ITEM.get()))
            .setSlotValidator(2, e -> e.is(ModItems.CHICKEN_ITEM.get()))
            .setSlotValidator(3, stack ->
            {
                ChickensRegistryItem chickensRegistryItem = ChickensRegistry.getByRegistryName(ItemChicken.getTypeFromStack(stack));
                if(chickensRegistryItem == null) return false;
                if(chickensRegistryItem.equals(ModChickens.ROOSTER)) return true;
                return false;
            });

    public final FloatData progress = register("progress", new FloatData(0), SAVE_BOTH);
    public final FloatData targetProgress = register("cycle_target", new FloatData(0), SAVE_BOTH);


    public BreederBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.BREEDER_TILE.get(), pos, state);
    }

    @Override
    public void tick() {
        super.tick();
        if (!(level instanceof ServerLevel serverLevel)) return;

        ItemStack seeds = inventory.getItem(0);
        ItemStack chicken1 = inventory.getItem(1);
        ItemStack chicken2 = inventory.getItem(2);
        ItemStack rooster = inventory.getItem(3);

        boolean canWork = chicken1.getItem() instanceof ItemChicken && chicken2.getItem() instanceof ItemChicken && CommonTags.isSeeds(seeds);
        setState(canWork);
        if (!canWork) {
            progress.set(0F);
            targetProgress.set(-1F);
            return;
        }

        ChickensRegistryItem regItem1 = ChickensRegistry.getByRegistryName(ItemChicken.getTypeFromStack(chicken1));
        ChickensRegistryItem regItem2 = ChickensRegistry.getByRegistryName(ItemChicken.getTypeFromStack(chicken2));
        float speedMultiplier = (regItem1 == null ? 1 : regItem1.breedSpeedMultiplier) * (regItem2 == null ? 1 : regItem2.breedSpeedMultiplier);

        ChickenStats chickenStats1 = new ChickenStats(inventory.getItem(1));
        ChickenStats chickenStats2 = new ChickenStats(inventory.getItem(2));

        if (targetProgress.get() < 0) {
            float t = Config.INSTANCE.breederMaxProcessTime / 0.75F / 2;
            int avgGain = (chickenStats1.getGain() + chickenStats2.getGain()) / 2;
            targetProgress.set((t + level.random.nextInt((int)t)) / (1 + (avgGain - 1F) / 9F));
        }

        if (progress.get() < targetProgress.get()) {
            progress.add(getProgressIncrement(chickenStats1, chickenStats2, speedMultiplier));
            return;
        }

        ChickensRegistryItem baby = ChickensRegistry.getRandomChild(regItem1, regItem2);
        if (baby == null) {
            progress.set(0F);
            targetProgress.set(-1F);
            return;
        }
        ItemStack chickenStack = ItemChickenEgg.of(baby, level.random.nextDouble() < Config.INSTANCE.onLaidViabilityChange);

        ChickenStats babyStats = increaseStats(chickenStack, chicken1, chicken2, level.random);
        babyStats.write(chickenStack);
        if(!rooster.isEmpty())
        {
            ChickensRegistryItem roosterItem = ChickensRegistry.getByRegistryName(ItemChicken.getTypeFromStack(chicken2));
            if(roosterItem == ModChickens.ROOSTER)
            {
                chickenStack.set(ModComponentTypes.EGG_VIABLE.get(), true);
            }
        }
        else
        {
            chickenStack.set(ModComponentTypes.EGG_VIABLE.get(), false);
        }

        ChickenStats chickenStats = new ChickenStats(chicken1);
        int count = Math.max(1, ((1 + chickenStats.getGain()) / 3));

        chickenStack.setCount(count);

        if (ContainerUtil.insertStack(chickenStack, inventory, true) == 0) {
            ContainerUtil.insertStack(chickenStack, inventory);
            int random = level.getRandom().nextInt(1, 5);
            if (random >= 4) {
                damageChicken(1);
                damageChicken(2);
            }
            level.playSound(null, getBlockPos(), SoundEvents.CHICKEN_EGG, SoundSource.NEUTRAL, 0.5F, 0.8F);
            serverLevel.sendParticles(ParticleTypes.HEART, getBlockPos().getX() + 0.5, getBlockPos().getY() + 1, getBlockPos().getZ() + 0.5, 8, 0.45, 0.45, 0.45, 0.0125);
            if (level.random.nextDouble() < Config.INSTANCE.breederFoodConsumptionChance) {
                seeds.shrink(1);
            }
            progress.set(0F);
            targetProgress.set(-1F);
        }
    }

    @Override
    public Container getContainer(@Nullable Direction side) {
        if (side != Direction.DOWN) {
            //Allows extraction from any slot from sides or top
            return new ContainerAccessControl(inventory, 0, 7)
                    .slotInsertCheck(1, stack -> stack.getCount() == 1 && inventory.getItem(1).isEmpty()) //TODO This limiting slot to 1 item can be done better with a custom SidedInvWrapper, though not sure about fabric...
                    .slotInsertCheck(2, stack -> stack.getCount() == 1 && inventory.getItem(2).isEmpty()) //TODO This limiting slot to 1 item can be done better with a custom SidedInvWrapper, though not sure about fabric...
                    .slotInsertCheck(3, stack -> stack.getCount() == 1 && inventory.getItem(3).isEmpty()) //TODO This limiting slot to 1 item can be done better with a custom SidedInvWrapper, though not sure about fabric...
                    .containerInsertCheck((slot, stack) -> slot <= 2);
        }
        //Only allow extraction of outputs from bottom (basic hopper compatibility)
        return new ContainerAccessControl(inventory, 0, 7)
                .slotInsertCheck(1, stack -> stack.getCount() == 1 && inventory.getItem(1).isEmpty()) //TODO This limiting slot to 1 item can be done better with a custom SidedInvWrapper, though not sure about fabric...
                .slotInsertCheck(2, stack -> stack.getCount() == 1 && inventory.getItem(2).isEmpty()) //TODO This limiting slot to 1 item can be done better with a custom SidedInvWrapper, though not sure about fabric...
                .slotInsertCheck(3, stack -> stack.getCount() == 1 && inventory.getItem(3).isEmpty()) //TODO This limiting slot to 1 item can be done better with a custom SidedInvWrapper, though not sure about fabric...
                .containerInsertCheck((slot, stack) -> slot <= 3)
                .containerRemoveCheck((slot, stack) -> slot > 3);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new BreederMenu(i, inventory, this);
    }

    @Override
    public void writeExtraData(HolderLookup.Provider provider, CompoundTag nbt) {
        super.writeExtraData(provider, nbt);
        inventory.serialize(provider, nbt);
    }

    @Override
    public void readExtraData(HolderLookup.Provider provider, CompoundTag nbt) {
        super.readExtraData(provider, nbt);
        inventory.deserialize(provider, nbt);
    }

    public void setState(boolean canWork) {
        boolean hasSeeds = !inventory.getItem(0).isEmpty();
        level.setBlock(getBlockPos(), getBlockState().setValue(BreederBlock.HAS_SEEDS, hasSeeds).setValue(BreederBlock.IS_BREEDING, canWork), 3);
    }

    public float getProgressIncrement(ChickenStats chickenStats1, ChickenStats chickenStats2, float speedMultiplier) {
        float progress = chickenStats1.getGain() + chickenStats2.getGain();
        if (progress > 50) progress = 50;
        return progress * speedMultiplier;
    }

    public void damageChicken(int slot) {
        if (!inventory.getItem(slot).isEmpty() && inventory.getItem(slot).getItem() instanceof ItemChicken) {
            ItemStack copy = inventory.getItem(slot).copy();

            ChickenStats chickenStats = new ChickenStats(copy);
            int life = chickenStats.getLifespan() - 1;
            if (life > 0) {
                chickenStats.setLifespan(life);
                chickenStats.write(copy);
                inventory.setItem(slot, copy);
            } else {
                inventory.setItem(slot, ItemStack.EMPTY);
            }
        }
    }

    private ChickenStats increaseStats(ItemStack baby, ItemStack parent1, ItemStack parent2, RandomSource rand) {
        ChickenStats babyStats = new ChickenStats(baby);
        ChickenStats parent1Stats = new ChickenStats(parent1);
        ChickenStats parent2Stats = new ChickenStats(parent2);

        babyStats.setGrowth(calculateNewStat(parent1Stats.getStrength(), parent2Stats.getStrength(), parent1Stats.getGrowth(), parent2Stats.getGrowth(), rand));
        babyStats.setGain(calculateNewStat(parent1Stats.getStrength(), parent2Stats.getStrength(), parent1Stats.getGain(), parent2Stats.getGain(), rand));
        babyStats.setStrength(calculateNewStat(parent1Stats.getStrength(), parent2Stats.getStrength(), parent1Stats.getStrength(), parent2Stats.getStrength(), rand));

        return babyStats;
    }

    private int calculateNewStat(int thisStrength, int mateStrength, int stat1, int stat2, RandomSource rand) {
        int mutation = rand.nextInt(2) + 1;
        int newStatValue = (stat1 * thisStrength + stat2 * mateStrength) / (thisStrength + mateStrength) + mutation;
        if (newStatValue <= 1) return 1;
        return Math.min(newStatValue, 10);
    }
}
