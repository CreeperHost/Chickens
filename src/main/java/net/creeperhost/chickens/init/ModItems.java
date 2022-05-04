package net.creeperhost.chickens.init;

import net.creeperhost.chickens.ChickensMod;
import net.creeperhost.chickens.client.ChickensCreativeTabs;
import net.creeperhost.chickens.item.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ChickensMod.MODID);

    public static final RegistryObject<Item> SPAWN_EGG = ITEMS.register("spawn_egg", () -> new ItemSpawnEgg(new Item.Properties().tab(ChickensCreativeTabs.CHICKENS_SPAWN_EGGS)));
    public static final RegistryObject<Item> COLOURED_EGG = ITEMS.register("colored_egg", () -> new ItemColoredEgg(new Item.Properties().tab(ChickensCreativeTabs.CHICKENS_BLOCKS)));
    public static final RegistryObject<Item> LIQUID_EGG = ITEMS.register("liquid_egg", () -> new ItemLiquidEgg(new Item.Properties().tab(ChickensCreativeTabs.CHICKENS_BLOCKS)));
    public static final RegistryObject<Item> ANALYZER = ITEMS.register("analyzer", () -> new ItemAnalyzer(new Item.Properties().tab(ChickensCreativeTabs.CHICKENS_BLOCKS).stacksTo(1)));
    public static final RegistryObject<Item> CHICKEN_ITEM = ITEMS.register("chicken_item", () -> new ItemChicken(new Item.Properties().tab(ChickensCreativeTabs.CHICKENS_TAB).stacksTo(16)));
    public static final RegistryObject<Item> CATCHER_ITEM = ITEMS.register("catcher", () -> new ItemChickenCatcher(new Item.Properties().tab(ChickensCreativeTabs.CHICKENS_BLOCKS)));

    //ItemBlocks
    public static final RegistryObject<Item> HEN_HOUSE_ITEM = ITEMS.register("henhouse", () -> new BlockItem(ModBlocks.HEN_HOUSE.get(), new Item.Properties().tab(ChickensCreativeTabs.CHICKENS_BLOCKS)));
    public static final RegistryObject<Item> HEN_HOUSE_ACACIA_ITEM = ITEMS.register("henhouse_acacia", () -> new BlockItem(ModBlocks.HEN_HOUSE_ACACIA.get(), new Item.Properties().tab(ChickensCreativeTabs.CHICKENS_BLOCKS)));
    public static final RegistryObject<Item> HEN_HOUSE_BIRCH_ITEM = ITEMS.register("henhouse_birch", () -> new BlockItem(ModBlocks.HEN_HOUSE_BIRCH.get(), new Item.Properties().tab(ChickensCreativeTabs.CHICKENS_BLOCKS)));
    public static final RegistryObject<Item> HEN_HOUSE_DARK_OAK_ITEM = ITEMS.register("henhouse_dark_oak", () -> new BlockItem(ModBlocks.HEN_HOUSE_DARK_OAK.get(), new Item.Properties().tab(ChickensCreativeTabs.CHICKENS_BLOCKS)));
    public static final RegistryObject<Item> HEN_HOUSE_JUNGLE_ITEM = ITEMS.register("henhouse_jungle", () -> new BlockItem(ModBlocks.HEN_HOUSE_JUNGLE.get(), new Item.Properties().tab(ChickensCreativeTabs.CHICKENS_BLOCKS)));
    public static final RegistryObject<Item> HEN_HOUSE_SPRUCE_ITEM = ITEMS.register("henhouse_spruce", () -> new BlockItem(ModBlocks.HEN_HOUSE_SPRUCE.get(), new Item.Properties().tab(ChickensCreativeTabs.CHICKENS_BLOCKS)));

    public static final RegistryObject<Item> BREEDER = ITEMS.register("breeder", () -> new BlockItem(ModBlocks.BREEDER.get(), new Item.Properties().tab(ChickensCreativeTabs.CHICKENS_BLOCKS)));
    public static final RegistryObject<Item> ROOST = ITEMS.register("roost", () -> new BlockItem(ModBlocks.ROOST.get(), new Item.Properties().tab(ChickensCreativeTabs.CHICKENS_BLOCKS)));

}
