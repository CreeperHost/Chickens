package net.creeperhost.chickens.containers.slots;

import net.creeperhost.chickens.item.ItemChickenEgg;
import net.creeperhost.polylib.containers.slots.SlotInput;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

@Deprecated
public class SlotEgg extends SlotInput
{
    int stackSize;
    public SlotEgg(Container container, int i, int j, int k, int stackSize)
    {
        super(container, i, j, k);
        this.stackSize = stackSize;
    }

    @Override
    public boolean mayPlace(ItemStack itemStack)
    {
        return itemStack.getItem() instanceof ItemChickenEgg;
    }

    @Override
    public int getMaxStackSize(@NotNull ItemStack itemStack)
    {
        return stackSize;
    }

    @Override
    public int getMaxStackSize()
    {
        return stackSize;
    }
}
