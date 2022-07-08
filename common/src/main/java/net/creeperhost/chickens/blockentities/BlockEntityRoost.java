package net.creeperhost.chickens.blockentities;

import net.creeperhost.chickens.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BlockEntityRoost extends BaseContainerBlockEntity
{
    public BlockEntityRoost(BlockPos blockPos, BlockState blockState)
    {
        super(ModBlocks.ROOST_TILE.get(), blockPos, blockState);
    }

    public void tick()
    {
        
    }

    @Override
    protected Component getDefaultName()
    {
        return null;
    }

    @Override
    protected AbstractContainerMenu createMenu(int i, Inventory inventory)
    {
        return null;
    }

    @Override
    public int getContainerSize()
    {
        return 0;
    }

    @Override
    public boolean isEmpty()
    {
        return false;
    }

    @Override
    public ItemStack getItem(int i)
    {
        return null;
    }

    @Override
    public ItemStack removeItem(int i, int j)
    {
        return null;
    }

    @Override
    public ItemStack removeItemNoUpdate(int i)
    {
        return null;
    }

    @Override
    public void setItem(int i, ItemStack itemStack)
    {

    }

    @Override
    public boolean stillValid(Player player)
    {
        return false;
    }

    @Override
    public void clearContent()
    {

    }
}
