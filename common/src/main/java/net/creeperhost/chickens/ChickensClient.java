package net.creeperhost.chickens;

import dev.architectury.platform.Platform;
import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import net.creeperhost.chickens.client.RenderIncubator;
import net.creeperhost.chickens.client.RenderChickens;
import net.creeperhost.chickens.client.ChickensModel;
import net.creeperhost.chickens.init.ModBlocks;
import net.creeperhost.chickens.init.ModEntities;
import net.creeperhost.chickens.init.ModScreens;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;

public class ChickensClient
{
    public static void clientSetup(Minecraft minecraft)
    {
        ModScreens.init();

        if (Platform.isFabric())
        {
            ModEntities.CHICKENS.forEach((chickensRegistryItem, entityTypeSupplier) -> EntityRendererRegistry.register(entityTypeSupplier, RenderChickens::new));
            EntityModelLayerRegistry.register(ChickensModel.LAYER_LOCATION, ChickensModel::createBodyLayer);
            EntityRendererRegistry.register(ModEntities.ROOSTER, RenderChickens::new);
        }

        BlockEntityRendererRegistry.register(ModBlocks.INCUBATOR_TILE.get(), context -> new RenderIncubator());

        ChickensPlatform.registerBlockRenderType(ModBlocks.INCUBATOR.get(), ChunkSectionLayer.TRANSLUCENT);
        ChickensPlatform.registerBlockRenderType(ModBlocks.OVOSCOPE.get(), ChunkSectionLayer.TRANSLUCENT);
    }
}
