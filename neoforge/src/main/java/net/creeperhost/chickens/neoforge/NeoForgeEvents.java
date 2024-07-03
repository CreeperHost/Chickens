package net.creeperhost.chickens.neoforge;

import dev.architectury.registry.client.level.entity.forge.EntityModelLayerRegistryImpl;
import net.creeperhost.chickens.Chickens;
import net.creeperhost.chickens.client.RenderChickensChicken;
import net.creeperhost.chickens.client.RenderRooster;
import net.creeperhost.chickens.client.RoosterModel;
import net.creeperhost.chickens.init.ModEntities;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = Chickens.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class NeoForgeEvents
{
    @SubscribeEvent
    public static void registerRenderEvent(EntityRenderersEvent.RegisterRenderers event)
    {
        ModEntities.CHICKENS.forEach((chickensRegistryItem, entityTypeSupplier) ->
        {
            Chickens.LOGGER.info("Registering render for " + entityTypeSupplier.get().getDescriptionId());
            event.registerEntityRenderer(entityTypeSupplier.get(), RenderChickensChicken::new);
        });

        event.registerEntityRenderer(ModEntities.ROOSTER.get(), RenderRooster::new);
    }

    @SubscribeEvent
    public static void registerModelLayerEvent(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
        event.registerLayerDefinition(RoosterModel.LAYER_LOCATION, RoosterModel::createBodyLayer);
    }
}
