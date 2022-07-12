package net.creeperhost.chickens.containers.slots;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SlotSeed extends Slot
{
    public SlotSeed(Container container, int i, int j, int k)
    {
        super(container, i, j, k);
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack itemStack)
    {
        //TODO
        return true;
    }
}
