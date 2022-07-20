package net.creeperhost.chickens.init;

import dev.architectury.fluid.FluidStack;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.creeperhost.chickens.Chickens;
import net.creeperhost.chickens.ChickensExpectPlatform;
import net.creeperhost.chickens.api.ChickensRegistry;
import net.creeperhost.chickens.api.ChickensRegistryItem;
import net.creeperhost.chickens.entity.EntityChickensChicken;
import net.creeperhost.chickens.item.*;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.*;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ModItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Chickens.MOD_ID, Registry.ITEM_REGISTRY);

    public static final CreativeModeTab CREATIVE_MODE_TAB = CreativeTabRegistry.create(new ResourceLocation(Chickens.MOD_ID, "creative_tab"), () -> new ItemStack(ModBlocks.ROOST.get()));
    public static final CreativeModeTab CREATIVE_MODE_TAB_EGGS = CreativeTabRegistry.create(new ResourceLocation(Chickens.MOD_ID, "creative_tab_eggs"), () -> new ItemStack(Items.EGG));

    @Deprecated
    public static final RegistrySupplier<Item> COLOURED_EGG = ITEMS.register("colored_egg", () -> new ItemColoredEgg(new Item.Properties().tab(CREATIVE_MODE_TAB)));
    @Deprecated
    public static final RegistrySupplier<Item> ANALYZER = ITEMS.register("analyzer", () -> new ItemAnalyzer(new Item.Properties().tab(CREATIVE_MODE_TAB).stacksTo(1)));
    public static final RegistrySupplier<Item> CHICKEN_ITEM = ITEMS.register("chicken_item", () -> ChickensExpectPlatform.createNewChickenItem(new Item.Properties().tab(CREATIVE_MODE_TAB).stacksTo(16)));
    public static final RegistrySupplier<Item> CATCHER_ITEM = ITEMS.register("catcher", () -> new ItemChickenCatcher(new Item.Properties().tab(CREATIVE_MODE_TAB)));

    @Deprecated
    public static final RegistrySupplier<Item> FLUID_EGG = ITEMS.register("fluid_egg", () -> new ItemFluidEgg());

    public static final RegistrySupplier<Item> CHICKEN_EGG = ITEMS.register("chicken_egg", () -> new ItemChickenEgg());

    //ItemBlocks
    public static final RegistrySupplier<Item> BREEDER = ITEMS.register("breeder", () -> new BlockItem(ModBlocks.BREEDER.get(), new Item.Properties().tab(CREATIVE_MODE_TAB)));
    public static final RegistrySupplier<Item> ROOST = ITEMS.register("roost", () -> new BlockItem(ModBlocks.ROOST.get(), new Item.Properties().tab(CREATIVE_MODE_TAB)));
    public static final RegistrySupplier<Item> INCUBATOR = ITEMS.register("incubator", () -> new BlockItem(ModBlocks.INCUBATOR.get(), new Item.Properties().tab(CREATIVE_MODE_TAB)));
    public static final RegistrySupplier<Item> EGG_CRACKER = ITEMS.register("egg_cracker", () -> new BlockItem(ModBlocks.EGG_CRACKER.get(), new Item.Properties().tab(CREATIVE_MODE_TAB)));

}
