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
import net.minecraft.world.item.*;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Chickens.MOD_ID, Registries.ITEM);
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Chickens.MOD_ID, Registries.CREATIVE_MODE_TAB);

    public static final RegistrySupplier<Item> CHICKEN_ITEM = ITEMS.register("chicken_item", () -> ChickensPlatform.createNewChickenItem(new Item.Properties().stacksTo(16)));
    public static final RegistrySupplier<Item> CATCHER_ITEM = ITEMS.register("catcher", () -> new ItemChickenCatcher(new Item.Properties()));

    public static final RegistrySupplier<Item> CHICKEN_EGG = ITEMS.register("chicken_egg", ItemChickenEgg::new);

    //ItemBlocks
    public static final RegistrySupplier<Item> BREEDER = ITEMS.register("breeder", () -> new BlockItem(ModBlocks.BREEDER.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> INCUBATOR = ITEMS.register("incubator", () -> new BlockItem(ModBlocks.INCUBATOR.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> EGG_CRACKER = ITEMS.register("egg_cracker", () -> new BlockItem(ModBlocks.EGG_CRACKER.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> OVOSCOPE = ITEMS.register("ovoscope", () -> new BlockItem(ModBlocks.OVOSCOPE.get(), new Item.Properties()));

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
}
