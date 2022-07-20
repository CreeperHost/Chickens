package net.creeperhost.chickens.blockentities;

import net.creeperhost.chickens.containers.ContainerEggCracker;
import net.creeperhost.chickens.init.ModBlocks;
import net.creeperhost.chickens.item.ItemChickenEgg;
import net.creeperhost.chickens.polylib.PolyInventory;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class BlockEntityEggCracker extends BaseContainerBlockEntity
{
    public PolyInventory inventory = new PolyInventory(10)
    {
        @Override
        public void setChanged()
        {
            BlockEntityEggCracker.this.setChanged();
        }
    };

    public void tick()
    {
        if(level == null) return;
        if(level.isClientSide) return;
        if(inventory == null) return;
        if(!inventory.getItem(0).isEmpty() && inventory.getItem(0).getItem() instanceof ItemChickenEgg itemChickenEgg)
        {
            ItemStack drop = itemChickenEgg.getType(inventory.getItem(0)).getDropItemHolder().getStack();
            if(!drop.isEmpty())
            {
                if(itemChickenEgg.isViable(inventory.getItem(0)))
                {
                    ItemStack stack = inventory.addItem(drop);
                    if(stack.isEmpty()) inventory.getItem(0).shrink(1);
                }
                else
                {
                    inventory.getItem(0).shrink(1);
                }
            }
        }
    }

    public BlockEntityEggCracker(BlockPos blockPos, BlockState blockState)
    {
        super(ModBlocks.EGG_CRACKER_TILE.get(), blockPos, blockState);
    }

    @Override
    protected Component getDefaultName()
    {
        return Component.literal("Egg Cracker");
    }

    @Override
    protected AbstractContainerMenu createMenu(int i, @NotNull Inventory inventory)
    {
        return new ContainerEggCracker(i, inventory, this, new SimpleContainerData(0));
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
        return inventory.stillValid(player);
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
