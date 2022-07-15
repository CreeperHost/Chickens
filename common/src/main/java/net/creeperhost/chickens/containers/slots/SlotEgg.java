package net.creeperhost.chickens.containers.slots;

import net.creeperhost.chickens.item.ItemChickenEgg;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SlotEgg extends Slot
{
    public SlotEgg(Container container, int i, int j, int k)
    {
        super(container, i, j, k);
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack itemStack)
    {
        return itemStack.getItem() instanceof ItemChickenEgg;
    }

    @Override
    public int getMaxStackSize(@NotNull ItemStack itemStack)
    {
        return 1;
    }

    @Override
    public int getMaxStackSize()
    {
        return 1;
    }
}
