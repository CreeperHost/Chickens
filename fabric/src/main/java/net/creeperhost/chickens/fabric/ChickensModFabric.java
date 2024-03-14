package net.creeperhost.chickens.fabric;

import dev.architectury.platform.Platform;
import net.creeperhost.chickens.Chickens;
import net.creeperhost.chickens.client.ChickenGuiTextures;
import net.creeperhost.chickens.init.ModItems;
import net.creeperhost.polylib.PolyLib;
import net.creeperhost.polylib.client.modulargui.sprite.PolyTextures;
import net.creeperhost.polylib.fabric.client.ResourceReloadListenerWrapper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;

public class ChickensModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Chickens.init();
        if(Platform.getEnv() == EnvType.CLIENT)
        {
            BuiltinItemRendererRegistry.INSTANCE.register(ModItems.CHICKEN_ITEM.get(), new ChickenDynamicItemRenderer());
            ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new ResourceReloadListenerWrapper(ChickenGuiTextures::getAtlasHolder, new ResourceLocation(Chickens.MOD_ID, "gui_atlas_reload")));
        }
    }
}
