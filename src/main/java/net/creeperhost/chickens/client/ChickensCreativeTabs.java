package net.creeperhost.chickens.client;

import net.creeperhost.chickens.init.ModItems;
import net.creeperhost.chickens.item.ItemChicken;
import net.creeperhost.chickens.registry.ChickensRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

/**
 * Created by setyc on 12.02.2016.
 */
public class ChickensCreativeTabs
{
    public static CreativeModeTab CHICKENS_TAB = new CreativeModeTab("chickens")
    {
        @Override
        public ItemStack makeIcon()
        {
            ItemStack itemstack = new ItemStack(ModItems.CHICKEN_ITEM.get());
            ItemChicken.applyEntityIdToItemStack(itemstack, ChickensRegistry.SMART_CHICKEN_ID);

            return itemstack;
        }
    };

    public static CreativeModeTab CHICKENS_SPAWN_EGGS = new CreativeModeTab("chickens_spawneggs")
    {
        @Override
        public ItemStack makeIcon()
        {
            return new ItemStack(ModItems.SPAWN_EGG.get());
        }
    };

    public static CreativeModeTab CHICKENS_BLOCKS = new CreativeModeTab("chickens_blocks")
    {
        @Override
        public ItemStack makeIcon()
        {
            return new ItemStack(ModItems.HEN_HOUSE_ITEM.get());
        }
    };
}
