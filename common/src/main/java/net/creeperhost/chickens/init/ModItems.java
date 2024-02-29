package net.creeperhost.chickens.init;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.creeperhost.chickens.Chickens;
import net.creeperhost.chickens.ChickensPlatform;
import net.creeperhost.chickens.item.ItemChickenCatcher;
import net.creeperhost.chickens.item.ItemChickenEgg;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;

public class ModItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Chickens.MOD_ID, Registry.ITEM_REGISTRY);

    public static final CreativeModeTab CREATIVE_MODE_TAB = CreativeTabRegistry.create(new ResourceLocation(Chickens.MOD_ID, "creative_tab"), () -> new ItemStack(ModBlocks.BREEDER.get()));
    public static final CreativeModeTab CREATIVE_MODE_TAB_EGGS = CreativeTabRegistry.create(new ResourceLocation(Chickens.MOD_ID, "creative_tab_eggs"), () -> new ItemStack(Items.EGG));

    public static final RegistrySupplier<Item> CHICKEN_ITEM = ITEMS.register("chicken_item", () -> ChickensPlatform.createNewChickenItem(new Item.Properties().tab(CREATIVE_MODE_TAB).stacksTo(16)));
    public static final RegistrySupplier<Item> CATCHER_ITEM = ITEMS.register("catcher", () -> new ItemChickenCatcher(new Item.Properties().tab(CREATIVE_MODE_TAB)));

    public static final RegistrySupplier<Item> CHICKEN_EGG = ITEMS.register("chicken_egg", () -> new ItemChickenEgg());

    //ItemBlocks
    public static final RegistrySupplier<Item> BREEDER = ITEMS.register("breeder", () -> new BlockItem(ModBlocks.BREEDER.get(), new Item.Properties().tab(CREATIVE_MODE_TAB)));
    public static final RegistrySupplier<Item> INCUBATOR = ITEMS.register("incubator", () -> new BlockItem(ModBlocks.INCUBATOR.get(), new Item.Properties().tab(CREATIVE_MODE_TAB)));
    public static final RegistrySupplier<Item> EGG_CRACKER = ITEMS.register("egg_cracker", () -> new BlockItem(ModBlocks.EGG_CRACKER.get(), new Item.Properties().tab(CREATIVE_MODE_TAB)));
    public static final RegistrySupplier<Item> OVOSCOPE = ITEMS.register("ovoscope", () -> new BlockItem(ModBlocks.OVOSCOPE.get(), new Item.Properties().tab(CREATIVE_MODE_TAB)));

}
