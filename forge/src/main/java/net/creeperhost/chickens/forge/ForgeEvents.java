package net.creeperhost.chickens.forge;

import net.creeperhost.chickens.Chickens;
import net.creeperhost.chickens.client.RenderChickensChicken;
import net.creeperhost.chickens.init.ModEntities;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.client.event.EntityRenderersEvent;;

@Mod.EventBusSubscriber(modid = Chickens.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForgeEvents
{
    @SubscribeEvent
    public static void event(EntityRenderersEvent.RegisterRenderers event)
    {
        ModEntities.CHICKENS.forEach((chickensRegistryItem, entityTypeSupplier) ->
        {
            Chickens.LOGGER.info("Registering render for " + entityTypeSupplier.get().getDescriptionId());
            event.registerEntityRenderer(entityTypeSupplier.get(), RenderChickensChicken::new);
        });

        event.registerEntityRenderer(ModEntities.EGG.get(), ThrownItemRenderer::new);
    }
}
