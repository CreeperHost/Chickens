package net.creeperhost.chickens.init;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.creeperhost.chickens.Chickens;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;

public class ModItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Chickens.MOD_ID, Registry.ITEM_REGISTRY);

    public static final CreativeModeTab CREATIVE_MODE_TAB = CreativeTabRegistry.create(new ResourceLocation(Chickens.MOD_ID, "creative_tab"), () -> new ItemStack(Items.EGG));

    //ItemBlocks
    public static final RegistrySupplier<Item> HEN_HOUSE_ITEM = ITEMS.register("henhouse", () -> new BlockItem(ModBlocks.HEN_HOUSE.get(), new Item.Properties().tab(CREATIVE_MODE_TAB)));
    public static final RegistrySupplier<Item> HEN_HOUSE_ACACIA_ITEM = ITEMS.register("henhouse_acacia", () -> new BlockItem(ModBlocks.HEN_HOUSE_ACACIA.get(), new Item.Properties().tab(CREATIVE_MODE_TAB)));
    public static final RegistrySupplier<Item> HEN_HOUSE_BIRCH_ITEM = ITEMS.register("henhouse_birch", () -> new BlockItem(ModBlocks.HEN_HOUSE_BIRCH.get(), new Item.Properties().tab(CREATIVE_MODE_TAB)));
    public static final RegistrySupplier<Item> HEN_HOUSE_DARK_OAK_ITEM = ITEMS.register("henhouse_dark_oak", () -> new BlockItem(ModBlocks.HEN_HOUSE_DARK_OAK.get(), new Item.Properties().tab(CREATIVE_MODE_TAB)));
    public static final RegistrySupplier<Item> HEN_HOUSE_JUNGLE_ITEM = ITEMS.register("henhouse_jungle", () -> new BlockItem(ModBlocks.HEN_HOUSE_JUNGLE.get(), new Item.Properties().tab(CREATIVE_MODE_TAB)));
    public static final RegistrySupplier<Item> HEN_HOUSE_SPRUCE_ITEM = ITEMS.register("henhouse_spruce", () -> new BlockItem(ModBlocks.HEN_HOUSE_SPRUCE.get(), new Item.Properties().tab(CREATIVE_MODE_TAB)));

    public static final RegistrySupplier<Item> BREEDER = ITEMS.register("breeder", () -> new BlockItem(ModBlocks.BREEDER.get(), new Item.Properties().tab(CREATIVE_MODE_TAB)));
    public static final RegistrySupplier<Item> ROOST = ITEMS.register("roost", () -> new BlockItem(ModBlocks.ROOST.get(), new Item.Properties().tab(CREATIVE_MODE_TAB)));

}
