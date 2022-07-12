package net.creeperhost.chickens.fabric;

import dev.architectury.platform.Platform;
import net.creeperhost.chickens.Chickens;
import net.creeperhost.chickens.client.RenderChickenItem;
import net.creeperhost.chickens.init.ModItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;

public class ChickensModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Chickens.init();
        if(Platform.getEnv() == EnvType.CLIENT)
        {
            BuiltinItemRendererRegistry.INSTANCE.register(ModItems.CHICKEN_ITEM.get(), new ChickenDynamicItemRenderer());
        }
    }
}
