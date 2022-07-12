package net.creeperhost.chickens.blockentities;

import net.creeperhost.chickens.containers.ContainerBreeder;
import net.creeperhost.chickens.init.ModBlocks;
import net.creeperhost.chickens.polylib.PolyInventory;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class BlockEntityBreeder extends BaseContainerBlockEntity
{
    public final PolyInventory inventory = new PolyInventory(6)
    {
        @Override
        public void setChanged()
        {
            super.setChanged();
            BlockEntityBreeder.this.setChanged();
        }
    };

    public final ContainerData containerData = new SimpleContainerData(1)
    {
        @Override
        public int get(int index)
        {
            if (index == 0)
            {
                return 0;
            }
            throw new IllegalArgumentException("Invalid index: " + index);
        }

        @Override
        public void set(int index, int value)
        {
            throw new IllegalStateException("Cannot set values through IIntArray");
        }

        @Override
        public int getCount()
        {
            return 1;
        }
    };

    public BlockEntityBreeder(BlockPos blockPos, BlockState blockState)
    {
        super(ModBlocks.BREEDER_TILE.get(), blockPos, blockState);
    }

    public void tick()
    {

    }

    @Override
    protected Component getDefaultName()
    {
        return Component.literal("Breeder");
    }

    @Override
    protected AbstractContainerMenu createMenu(int i, @NotNull Inventory inventory)
    {
        return new ContainerBreeder(i, inventory, this, containerData);
    }

    @Override
    public int getContainerSize()
    {
        return inventory.getContainerSize();
    }

    @Override
    public boolean isEmpty()
    {
        return inventory.isEmpty();
    }

    @Override
    public ItemStack getItem(int i)
    {
        return inventory.getItem(i);
    }

    @Override
    public ItemStack removeItem(int i, int j)
    {
        return inventory.removeItem(i, j);
    }

    @Override
    public ItemStack removeItemNoUpdate(int i)
    {
        return inventory.removeItemNoUpdate(i);
    }

    @Override
    public void setItem(int i, @NotNull ItemStack itemStack)
    {
        inventory.setItem(i, itemStack);
    }

    @Override
    public boolean stillValid(@NotNull Player player)
    {
        return true;
    }

    @Override
    public void clearContent()
    {
        inventory.clearContent();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag compoundTag)
    {
        super.saveAdditional(compoundTag);
        compoundTag.merge(inventory.serializeNBT());
    }

    @Override
    public void load(@NotNull CompoundTag compoundTag)
    {
        super.load(compoundTag);
        inventory.deserializeNBT(compoundTag);
    }
}
