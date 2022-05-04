package net.creeperhost.chickens.handler;

import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ItemColorHandler implements ItemColor
{
    @Override
    public int getColor(ItemStack stack, int tintIndex)
    {
        return ((IColorSource) stack.getItem()).getColorFromItemStack(stack, tintIndex);
    }
}
