package net.creeperhost.chickens.handler;

import net.minecraft.world.item.ItemStack;

public interface IColorSource
{
    int getColorFromItemStack(ItemStack stack, int renderPass);
}
