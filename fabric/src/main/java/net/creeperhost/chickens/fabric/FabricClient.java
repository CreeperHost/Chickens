package net.creeperhost.chickens.fabric;

import net.creeperhost.chickens.Chickens;
import net.creeperhost.chickens.client.ChickenGuiTextures;
import net.creeperhost.chickens.init.ModBlocks;
import net.creeperhost.polylib.fabric.client.ResourceReloadListenerWrapper;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;

/**
 * Created by brandon3055 on 27/04/2024
 */
public class FabricClient {

    public static void init() {

        //TODO need to do this via mixin for fabric
//        BuiltinItemRendererRegistry.INSTANCE.register(ModItems.CHICKEN_ITEM.get(), new ChickenDynamicItemRenderer());
        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new ResourceReloadListenerWrapper(ChickenGuiTextures::getAtlasHolder, ResourceLocation.fromNamespaceAndPath(Chickens.MOD_ID, "gui_atlas_reload")));
    }

}
