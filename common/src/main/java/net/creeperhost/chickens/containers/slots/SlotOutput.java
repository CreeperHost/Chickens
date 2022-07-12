package net.creeperhost.chickens.containers.slots;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SlotOutput extends Slot
{
    public SlotOutput(Container container, int i, int j, int k)
    {
        super(container, i, j, k);
    }

    @Override
    public boolean mayPlace(ItemStack itemStack)
    {
        return false;
    }
}
