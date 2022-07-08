package net.creeperhost.chickens.api;

import net.minecraft.world.item.ItemStack;

public interface IColorSource
{
    int getColorFromItemStack(ItemStack stack, int renderPass);
}
