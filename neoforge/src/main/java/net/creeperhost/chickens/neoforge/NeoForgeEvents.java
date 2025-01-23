package net.creeperhost.chickens.neoforge;

import net.creeperhost.chickens.Chickens;
import net.creeperhost.chickens.client.RenderChickensChicken;
import net.creeperhost.chickens.client.RenderRooster;
import net.creeperhost.chickens.client.RoosterModel;
import net.creeperhost.chickens.init.ModEntities;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

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

    @SubscribeEvent
    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        ModEntities.CHICKENS.forEach((item, typeSupplier) -> {
            if (item.isEnabled()) {
                event.register(typeSupplier.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModEntities::checkChickenSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
            }
        });
        event.register(ModEntities.ROOSTER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModEntities::checkChickenSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
    }
}
