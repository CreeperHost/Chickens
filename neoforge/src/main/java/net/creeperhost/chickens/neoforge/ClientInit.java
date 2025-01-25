package net.creeperhost.chickens.neoforge;

import net.creeperhost.chickens.Chickens;
import net.creeperhost.chickens.client.ChickenEggTint;
import net.creeperhost.chickens.client.ChickenGuiTextures;
import net.creeperhost.chickens.client.ChickenItemRender;
import net.creeperhost.chickens.init.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterSpecialModelRendererEvent;

/**
 * Created by brandon3055 on 23/02/2024
 */
public class ClientInit {

    public static void init(IEventBus modBus) {
        modBus.addListener(ClientInit::registerReloadListeners);
        modBus.addListener(ClientInit::registerItemExtensions);
        modBus.addListener(ClientInit::registerItemTintSources);
    }

    private static void registerReloadListeners(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(ChickenGuiTextures.getAtlasHolder());
    }

    public static void registerItemExtensions(RegisterSpecialModelRendererEvent event) {
        event.register(ModItems.CHICKEN_ITEM.getId(), ChickenItemRender.Unbaked.MAP_CODEC);
    }

    public static void registerItemTintSources(RegisterColorHandlersEvent.ItemTintSources event) {
        event.register(ResourceLocation.fromNamespaceAndPath(Chickens.MOD_ID, "egg_tint"), ChickenEggTint.MAP_CODEC);
    }
}
