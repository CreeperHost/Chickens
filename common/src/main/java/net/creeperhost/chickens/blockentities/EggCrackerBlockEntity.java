package net.creeperhost.chickens.blockentities;

import dev.architectury.fluid.FluidStack;
import net.creeperhost.chickens.api.ChickensRegistryItem;
import net.creeperhost.chickens.config.Config;
import net.creeperhost.chickens.containers.EggCrackerMenu;
import net.creeperhost.chickens.init.ModBlocks;
import net.creeperhost.chickens.item.ItemChickenEgg;
import net.creeperhost.polylib.blocks.PolyBlockEntity;
import net.creeperhost.polylib.blocks.RedstoneActivatedBlock;
import net.creeperhost.polylib.data.serializable.IntData;
import net.creeperhost.polylib.helpers.ContainerUtil;
import net.creeperhost.polylib.inventory.fluid.*;
import net.creeperhost.polylib.inventory.items.BlockInventory;
import net.creeperhost.polylib.inventory.items.ContainerAccessControl;
import net.creeperhost.polylib.inventory.items.PolyInventoryBlock;
import net.creeperhost.polylib.inventory.power.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EggCrackerBlockEntity extends PolyBlockEntity implements PolyInventoryBlock, MenuProvider, PolyFluidBlock, PolyEnergyBlock, RedstoneActivatedBlock {

    public final PolyTank tank = new PolyBlockTank(this, FluidManager.BUCKET * 16L);
    public final PolyEnergyStorage energy = new PolyBlockEnergyStorage(this, 128000);
    public final BlockInventory inventory = new BlockInventory(this, 9)
            .setSlotValidator(0, stack -> stack.getItem() instanceof ItemChickenEgg)
            .setSlotValidator(7, FluidManager::isFluidItem)
            .setSlotValidator(8, stack -> EnergyManager.isEnergyItem(stack) && EnergyManager.getHandler(stack).canExtract());

    public final IntData progress = register("progress", new IntData(10), SAVE_BOTH);

    public EggCrackerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.EGG_CRACKER_TILE.get(), pos, state);
    }

    @Override
    public void tick() {
        super.tick();
        if (level.isClientSide()) return;

        //Update Energy
        if (Config.INSTANCE.enableEnergy) {
            IPolyEnergyStorageItem storage = EnergyManager.getHandler(inventory.getItem(8));
            if (storage != null && EnergyManager.transferEnergy(storage, energy) > 0) {
                inventory.setItem(8, storage.getContainer());
            }
        }

        //Update Fluid Slot
        ItemStack fluidItem = inventory.getItem(7);
        PolyFluidHandlerItem itemFluidHandler = FluidManager.getHandler(fluidItem);
        if (!FluidManager.transferFluid(tank, itemFluidHandler).isEmpty()) {
            inventory.setItem(7, itemFluidHandler.getContainer());
        }

        //Can Run?
        ItemStack input = inventory.getItem(0);
        if (!(input.getItem() instanceof ItemChickenEgg eggItem) || eggItem.getType(input) == null) {
            progress.set(0);
            return;
        }

        //Update Progress
        if (progress.get() < Config.INSTANCE.crackerProcessTime) {
            if (isTileEnabled() && consumeEnergy()) {
                progress.inc();
            }
            return;
        }

        ChickensRegistryItem type = eggItem.getType(input);
        ItemStack drop = type.getLayItemHolder().getStack();
        if (!drop.isEmpty()) {
            if (eggItem.isViable(input)) {
                int remaining = ContainerUtil.insertStack(drop, inventory, true);
                if (remaining == 0) {
                    ContainerUtil.insertStack(drop, inventory);
                    input.shrink(1);
                    progress.set(0);
                }
            } else {
                input.shrink(1);
                progress.set(0);
            }
        } else {
            Fluid fluid = type.getLayItemHolder().getFluid();
            int amount = type.getLayItemHolder().getAmount();
            FluidStack fluidStack = FluidStack.create(fluid, amount);
            if (fluidStack.isEmpty()) return;

            if (tank.isEmpty() || tank.fill(fluidStack, true) == fluidStack.getAmount()) {
                tank.fill(fluidStack, false);
                input.shrink(1);
                progress.set(0);
            }
        }
    }

    private boolean consumeEnergy() {
        return !Config.INSTANCE.enableEnergy || energy.extractEnergy(Config.INSTANCE.crackerEnergyRate, false) == Config.INSTANCE.crackerEnergyRate;
    }

    @Override
    public void writeExtraData(HolderLookup.Provider provider, CompoundTag nbt) {
        tank.serialize(provider, nbt);
        inventory.serialize(provider, nbt);
        energy.serialize(provider, nbt);
    }

    @Override
    public void readExtraData(HolderLookup.Provider provider, CompoundTag nbt) {
        tank.deserialize(provider, nbt);
        inventory.deserialize(provider, nbt);
        energy.deserialize(provider, nbt);
    }

    @Override
    public Container getContainer(@Nullable Direction side) {
        ContainerAccessControl ac = new ContainerAccessControl(inventory, 0, Config.INSTANCE.enableEnergy ? 9 : 8)
                .containerInsertCheck((slot, stack) -> slot == 0 || slot > 6)
                .slotRemoveCheck(0, stack -> false)
                .slotInsertCheck(7, stack -> stack.getCount() == 1 && inventory.getItem(7).isEmpty()) //TODO This limiting slot to 1 item can be done better with a custom SidedInvWrapper, though not sure about fabric...
                .slotRemoveCheck(7, stack -> {
                    PolyFluidHandlerItem handler = FluidManager.getHandler(stack);
                    if (handler == null) return true;
                    if (handler.getTanks() == 1 && handler.getFluidInTank(0).getAmount() >= handler.getTankCapacity(0)) return true;
                    if (tank.isEmpty()) return false;
                    return handler.fill(FluidStack.create(tank.getFluid(), FluidManager.BUCKET), true) == 0;
                });

        if (Config.INSTANCE.enableEnergy) {
            ac.slotInsertCheck(8, stack -> stack.getCount() == 1 && inventory.getItem(8).isEmpty());
            ac.slotRemoveCheck(8, stack -> {
                IPolyEnergyStorage energy = EnergyManager.getHandler(stack);
                return energy == null || !energy.canExtract() || energy.getEnergyStored() == 0;
            });
        }
        return ac;
    }

    @Override
    public @Nullable PolyFluidHandler getFluidHandler(@Nullable Direction side) {
        return tank;
    }

    @Override
    public IPolyEnergyStorage getEnergyStorage(@Nullable Direction side) {
        return Config.INSTANCE.enableEnergy ? energy : null;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new EggCrackerMenu(i, inventory, this);
    }
}
