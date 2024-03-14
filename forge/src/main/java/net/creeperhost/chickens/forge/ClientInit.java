package net.creeperhost.chickens.forge;

import net.creeperhost.chickens.Chickens;
import net.creeperhost.chickens.client.ChickenGuiTextures;
import net.creeperhost.chickens.init.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * Created by brandon3055 on 23/02/2024
 */
public class ClientInit {

    public static void init(IEventBus modBus) {
        modBus.addListener(ClientInit::clientSetup);
        modBus.addListener(ClientInit::registerReloadListeners);
    }

    private static void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemProperties.register(ModItems.CHICKEN_ITEM.get(), new ResourceLocation(Chickens.MOD_ID, ""), ChickenBlockEntityWithoutLevelRender.getInstance());
        });
    }


    private static void registerReloadListeners(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(ChickenGuiTextures.getAtlasHolder());
    }
}
