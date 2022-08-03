package net.creeperhost.chickens.blockentities;

import net.creeperhost.chickens.containers.ContainerEggCracker;
import net.creeperhost.chickens.init.ModBlocks;
import net.creeperhost.chickens.item.ItemChickenEgg;
import net.creeperhost.polylib.blockentity.BlockEntityInventory;
import net.creeperhost.polylib.inventory.PolyItemInventory;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class BlockEntityEggCracker extends BlockEntityInventory
{
    public BlockEntityEggCracker(BlockPos blockPos, BlockState blockState)
    {
        super(ModBlocks.EGG_CRACKER_TILE.get(), blockPos, blockState);
        setInventory(new PolyItemInventory(10));
        getInventoryOptional().ifPresent(polyItemInventory ->
        {
            this.addSlot(new Slot(polyItemInventory, 0, 26, 34));

            int i = 0;
            for (int l = 0; l < 3; ++l)
            {
                for (int k = 0; k < 3; ++k)
                {
                    i++;
                    this.addSlot(new Slot(polyItemInventory, i, 105 + k * 18, l * 18 + 17));
                }
            }
        });
    }

    public void tick()
    {
        if(level == null) return;
        if(level.isClientSide) return;
        if(!getItem(0).isEmpty() && getItem(0).getItem() instanceof ItemChickenEgg itemChickenEgg)
        {
            ItemStack drop = itemChickenEgg.getType(getItem(0)).getDropItemHolder().getStack();
            if(!drop.isEmpty())
            {
                if(itemChickenEgg.isViable(getItem(0)))
                {
                    ItemStack stack = getInventoryOptional().get().addItem(drop);
                    if(stack.isEmpty()) getItem(0).shrink(1);
                }
                else
                {
                    getItem(0).shrink(1);
                }
            }
        }
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
}
