package net.creeperhost.chickens.events;

import net.creeperhost.chickens.ChickensMod;
import net.creeperhost.chickens.client.ChickenBakedItemModel;
import net.creeperhost.chickens.client.render.RenderChickensChicken;
import net.creeperhost.chickens.entity.EntityChickensChicken;
import net.creeperhost.chickens.init.ModEntities;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = ChickensMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents
{
    //TODO
//    @SubscribeEvent(priority = EventPriority.LOWEST)
//    public static void onRegister(RegistryEvent.Register<Block> event)
//    {
//        ModEntities.init();
//    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event)
    {
        for (RegistryObject<EntityType<?>> entry : ModEntities.ENTITIES.getEntries())
        {
            event.put((EntityType<? extends LivingEntity>) entry.get(), EntityChickensChicken.prepareAttributes().build());
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerRender(EntityRenderersEvent.RegisterRenderers event)
    {
        for (RegistryObject<EntityType<?>> entry : ModEntities.ENTITIES.getEntries())
        {
            EntityType<EntityChickensChicken> entityType = (EntityType<EntityChickensChicken>) entry.get();
            event.registerEntityRenderer(entityType, RenderChickensChicken::new);
        }

        event.registerEntityRenderer(ModEntities.EGG.get(), ThrownItemRenderer::new);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onModelBake(ModelBakeEvent e)
    {
        ResourceLocation location = new ModelResourceLocation(ChickensMod.MODID, "chicken_item", "inventory");
        BakedModel model = e.getModelRegistry().get(location);
        if(model != null)
        {
            e.getModelRegistry().put(location, new ChickenBakedItemModel(model));
        }
    }
}
