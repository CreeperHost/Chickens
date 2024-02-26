package net.creeperhost.chickens.forge;

import net.creeperhost.chickens.Chickens;
import net.creeperhost.chickens.client.screen.IncubatorGui;
import net.creeperhost.polylib.PolyLib;
import net.creeperhost.polylib.forge.datagen.providers.DynamicTextureProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Created by brandon3055 on 23/02/2024
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenEventHandler {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();

        if (event.includeClient()) {
            DynamicTextureProvider textureProvider = new DynamicTextureProvider(gen, event.getExistingFileHelper(), Chickens.MOD_ID);
            gen.addProvider(true, textureProvider);

            textureProvider.addDynamicTexture(new ResourceLocation(PolyLib.MOD_ID, "textures/gui/dynamic/gui_vanilla"), new ResourceLocation(Chickens.MOD_ID, "textures/gui/incubator"), IncubatorGui.GUI_WIDTH, IncubatorGui.GUI_HEIGHT, 4, 4, 4, 4);
        }
    }
}
