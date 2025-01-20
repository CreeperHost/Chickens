package net.creeperhost.chickens.init;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.creeperhost.chickens.Chickens;
import net.creeperhost.chickens.ChickensPlatform;
import net.creeperhost.chickens.api.ChickenStats;
import net.creeperhost.chickens.api.ChickensRegistry;
import net.creeperhost.chickens.api.ChickensRegistryItem;
import net.creeperhost.chickens.item.ItemChicken;
import net.creeperhost.chickens.item.ItemChickenCatcher;
import net.creeperhost.chickens.item.ItemChickenEgg;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;
import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Chickens.MOD_ID, Registries.ITEM);
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Chickens.MOD_ID, Registries.CREATIVE_MODE_TAB);

    public static final RegistrySupplier<Item> CHICKEN_ITEM = ITEMS.register("chicken_item", item("chicken_item", ItemChicken::new, new Item.Properties().stacksTo(16)));
    public static final RegistrySupplier<Item> CATCHER_ITEM = ITEMS.register("catcher", item("catcher", ItemChickenCatcher::new));

    public static final RegistrySupplier<Item> CHICKEN_EGG = ITEMS.register("chicken_egg", item("chicken_egg", ItemChickenEgg::new));

    //ItemBlocks
    public static final RegistrySupplier<Item> BREEDER = ITEMS.register("breeder", item("breeder", props -> new BlockItem(ModBlocks.BREEDER.get(), props)));
    public static final RegistrySupplier<Item> INCUBATOR = ITEMS.register("incubator", item("incubator", props -> new BlockItem(ModBlocks.INCUBATOR.get(), props)));
    public static final RegistrySupplier<Item> EGG_CRACKER = ITEMS.register("egg_cracker", item("egg_cracker", props -> new BlockItem(ModBlocks.EGG_CRACKER.get(), props)));
    public static final RegistrySupplier<Item> OVOSCOPE = ITEMS.register("ovoscope", item("ovoscope", props -> new BlockItem(ModBlocks.OVOSCOPE.get(), props)));

    public static final RegistrySupplier<CreativeModeTab> CREATIVE_MODE_TAB = TABS.register("creative_tab", () -> CreativeTabRegistry.create(builder -> builder
                    .title(Component.translatable("itemGroup.chickens.creative_tab"))
                    .icon(() -> new ItemStack(ModBlocks.BREEDER.get()))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(BREEDER.get());
                        output.accept(INCUBATOR.get());
                        output.accept(EGG_CRACKER.get());
                        output.accept(OVOSCOPE.get());
                        output.accept(CATCHER_ITEM.get());

                        for (ChickensRegistryItem chicken : ChickensRegistry.getItems()) {
                            output.accept(ItemChicken.of(chicken));
                            //For testing
                            ItemStack stack = ItemChicken.of(chicken);
                            ChickenStats chickenStats = new ChickenStats(stack);
                            chickenStats.setGain(10);
                            chickenStats.setGrowth(10);
                            chickenStats.setStrength(10);
                            chickenStats.write(stack);
                            output.accept(stack);
                        }
                    }))
    );
    public static final RegistrySupplier<CreativeModeTab> CREATIVE_MODE_TAB_EGGS = TABS.register("creative_tab_eggs", () -> CreativeTabRegistry.create(builder -> builder
                    .title(Component.translatable("itemGroup.chickens.creative_tab_eggs"))
                    .icon(() -> new ItemStack(Items.EGG))
                    .displayItems((itemDisplayParameters, output) -> ChickensRegistry.getItems().forEach(e -> output.accept(ItemChickenEgg.of(e))))
            )
    );

    private static Supplier<Item> item(String name, Function<Item.Properties, Item> item, Item.Properties properties) {
        return () -> item.apply(properties.setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Chickens.MOD_ID, name))));
    }

    private static Supplier<Item> item(String name, Function<Item.Properties, Item> item) {
        return () -> item.apply(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Chickens.MOD_ID, name))));
    }
}
