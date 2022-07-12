package net.creeperhost.chickens.containers.slots;

import net.creeperhost.chickens.item.ItemChicken;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SlotChicken extends Slot
{
    public SlotChicken(Container container, int i, int j, int k)
    {
        super(container, i, j, k);
    }

    @Override
    public boolean mayPlace(ItemStack itemStack)
    {
        return itemStack.getItem() instanceof ItemChicken;
    }
}
