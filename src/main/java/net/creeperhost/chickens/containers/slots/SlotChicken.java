package net.creeperhost.chickens.containers.slots;

import net.creeperhost.chickens.capability.SmartInventory;
import net.creeperhost.chickens.item.ItemChicken;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class SlotChicken extends SlotItemHandler
{
    int index;
    public SlotChicken(IItemHandler itemHandler, int index, int xPosition, int yPosition)
    {
        super(itemHandler, index, xPosition, yPosition);
        this.index = index;
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack)
    {
        return stack.getItem() instanceof ItemChicken;
    }

    @Override
    public boolean mayPickup(Player playerIn)
    {
        return true;
    }

    @NotNull
    @Override
    public ItemStack remove(int amount)
    {
        SmartInventory inventory = (SmartInventory) getItemHandler();
        return inventory.extractItemInternal(index, amount, false);
    }

    @Override
    public int getMaxStackSize()
    {
        return 16;
    }
}
