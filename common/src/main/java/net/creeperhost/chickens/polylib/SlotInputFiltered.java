package net.creeperhost.chickens.polylib;

import net.creeperhost.polylib.containers.slots.SlotInput;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

public class SlotInputFiltered extends SlotInput
{
    private final ItemStack itemStack;

    public SlotInputFiltered(Container container, int i, int j, int k, ItemStack itemStack)
    {
        super(container, i, j, k);
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack()
    {
        return itemStack;
    }

    @Override
    public boolean mayPlace(ItemStack itemStack)
    {
        return itemStack.sameItem(getItemStack());
    }
}
