package net.creeperhost.chickens.containers.slots;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.Tags;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class SlotSeed extends SlotItemHandler
{
    public SlotSeed(IItemHandler itemHandler, int index, int xPosition, int yPosition)
    {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean mayPickup(Player playerIn)
    {
        return true;
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack)
    {
        return stack.is(Tags.Items.SEEDS);
    }
}
