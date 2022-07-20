package net.creeperhost.chickens.containers.slots;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class SlotWaterBucket extends Slot
{
    public SlotWaterBucket(Container container, int i, int j, int k)
    {
        super(container, i, j, k);
    }

    @Override
    public boolean mayPlace(ItemStack itemStack)
    {
        return itemStack.is(Items.WATER_BUCKET);
    }
}
