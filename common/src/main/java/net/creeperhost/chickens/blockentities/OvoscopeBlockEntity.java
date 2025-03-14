package net.creeperhost.chickens.blockentities;

import net.creeperhost.chickens.block.OvoscopeBlock;
import net.creeperhost.chickens.config.Config;
import net.creeperhost.chickens.containers.OvoscopeMenu;
import net.creeperhost.chickens.init.ModBlocks;
import net.creeperhost.chickens.item.ItemChickenEgg;
import net.creeperhost.polylib.blocks.PolyBlockEntity;
import net.creeperhost.polylib.blocks.RedstoneActivatedBlock;
import net.creeperhost.polylib.data.serializable.IntData;
import net.creeperhost.polylib.helpers.ContainerUtil;
import net.creeperhost.polylib.inventory.item.ContainerAccessControl;
import net.creeperhost.polylib.inventory.item.ItemInventoryBlock;
import net.creeperhost.polylib.inventory.item.SerializableContainer;
import net.creeperhost.polylib.inventory.item.SimpleItemInventory;
import net.creeperhost.polylib.inventory.power.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class OvoscopeBlockEntity extends PolyBlockEntity implements ItemInventoryBlock, MenuProvider, PolyEnergyBlock, RedstoneActivatedBlock {

    public final PolyEnergyStorage energy = new PolyBlockEnergyStorage(this, 128000);
    public final SimpleItemInventory inventory = new SimpleItemInventory(this, 4)
            .setMaxStackSize(1)
            .setSlotValidator(0, stack -> stack.getItem() instanceof ItemChickenEgg)
            .setSlotValidator(3, stack -> EnergyManager.isEnergyItem(stack) && EnergyManager.getHandler(stack).canExtract());

    public boolean scanning = false;
    public byte scanCount = 0;
    public final IntData progress = register("progress", new IntData(10), SAVE_BOTH);

    public OvoscopeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.OVOSCOPE_TILE.get(), pos, state);
        energy.setReceiveOnly();
    }

    @Override
    public void tick() {
        super.tick();
        if (level.isClientSide()) return;

        //Update Energy
        if (Config.INSTANCE.enableEnergy) {
            IPolyEnergyStorageItem storage = EnergyManager.getHandler(inventory.getItem(3));
            if (storage != null && EnergyManager.transferEnergy(storage, energy) > 0) {
                inventory.setItem(3, storage.getContainer());
            }
        }

        //Handle Outputs
        ItemStack viaStack = inventory.getItem(1);
        if (!viaStack.isEmpty() && level.getBlockEntity(getBlockPos().relative(viableFace())) instanceof Container target) {
            viaStack.setCount(ContainerUtil.insertStack(viaStack, target));
        }
        ItemStack nonViaStack = inventory.getItem(2);
        if (!nonViaStack.isEmpty() && level.getBlockEntity(getBlockPos().relative(nonViableFace())) instanceof Container target) {
            nonViaStack.setCount(ContainerUtil.insertStack(nonViaStack, target));
        }

        boolean outputObstructed = !inventory.getItem(1).isEmpty() || !inventory.getItem(2).isEmpty();
        ItemStack input = inventory.getItem(0);
        if (input.isEmpty() || !(input.getItem() instanceof ItemChickenEgg eggItem) || outputObstructed) {
            progress.set(0);
            scanning = false;
            return;
        }

        if (progress.get() < Config.INSTANCE.ovoscopeProcessTime) {
            if (isTileEnabled() && consumeEnergy()) {
                progress.inc();
                scanning = true;
            } else {
                scanning = false;
            }
            return;
        }

        if (eggItem.isViable(input)) {
            inventory.setItem(1, input);
        } else {
            inventory.setItem(2, input);
        }
        inventory.setItem(0, ItemStack.EMPTY);
        scanning = false;
        progress.set(0);
        scanCount++;
    }

    private Direction viableFace() {
        Direction facing = getBlockState().getValue(OvoscopeBlock.FACING);
        return facing.getCounterClockWise(Direction.Axis.Y);
    }

    private Direction nonViableFace() {
        Direction facing = getBlockState().getValue(OvoscopeBlock.FACING);
        return facing.getClockWise(Direction.Axis.Y);
    }

    private boolean consumeEnergy() {
        return !Config.INSTANCE.enableEnergy || energy.extractEnergy(Config.INSTANCE.ovoscopeEnergyRate, false) == Config.INSTANCE.ovoscopeEnergyRate;
    }

    @Override
    public SerializableContainer getContainer(@Nullable Direction side) {
        ContainerAccessControl ac = new ContainerAccessControl(inventory, 0, Config.INSTANCE.enableEnergy ? 4 : 3)
                .slotRemoveCheck(0, stack -> false)
                .slotInsertCheck(1, stack -> false)
                .slotInsertCheck(2, stack -> false)
                .slotRemoveCheck(1, stack -> side == viableFace()) //via
                .slotRemoveCheck(2, stack -> side == nonViableFace()); //nonVia

        if (Config.INSTANCE.enableEnergy) {
            ac.slotRemoveCheck(3, stack -> {
                IPolyEnergyStorage energy = EnergyManager.getHandler(stack);
                return energy == null || !energy.canExtract() || energy.getEnergyStored() == 0;
            });
        }
        return ac;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new OvoscopeMenu(i, inventory, this);
    }

    @Override
    public IPolyEnergyStorage getEnergyStorage(@Nullable Direction side) {
        return Config.INSTANCE.enableEnergy ? energy : null;
    }

    @Override
    public void writeExtraData(CompoundTag nbt) {
        inventory.serialize(nbt);
        energy.serialize(nbt);
    }

    @Override
    public void readExtraData(CompoundTag nbt) {
        inventory.deserialize(nbt);
        energy.deserialize(nbt);
    }
}
