package net.creeperhost.chickens.util;

import net.creeperhost.chickens.capability.SmartInventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;

import static net.minecraftforge.items.ItemHandlerHelper.canItemStacksStackRelaxed;
import static net.minecraftforge.items.ItemHandlerHelper.insertItem;

public class InventoryHelper
{
    @Nonnull
    public static ItemStack insertItemStacked(SmartInventory inventory, @Nonnull ItemStack stack, boolean simulate)
    {
        if (inventory == null || stack.isEmpty())
            return stack;

        // not stackable -> just insert into a new slot
        if (!stack.isStackable())
        {
            return insertItem(inventory, stack, simulate);
        }

        int sizeInventory = inventory.getSlots();

        // go through the inventory and try to fill up already existing items
        for (int i = 0; i < sizeInventory; i++)
        {
            ItemStack slot = inventory.getStackInSlot(i);
            if (canItemStacksStackRelaxed(slot, stack))
            {
                stack = inventory.insertItemInternal(i, stack, simulate);

                if (stack.isEmpty())
                {
                    break;
                }
            }
        }

        // insert remainder into empty slots
        if (!stack.isEmpty())
        {
            // find empty slot
            for (int i = 0; i < sizeInventory; i++)
            {
                if (inventory.getStackInSlot(i).isEmpty())
                {
                    stack = inventory.insertItemInternal(i, stack, simulate);
                    if (stack.isEmpty())
                    {
                        break;
                    }
                }
            }
        }

        return stack;
    }
}
