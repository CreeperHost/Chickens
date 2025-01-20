package net.creeperhost.chickens.client;

import com.google.common.collect.Iterables;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.creeperhost.chickens.api.ChickensRegistry;
import net.creeperhost.chickens.api.ChickensRegistryItem;
import net.creeperhost.chickens.item.ItemChicken;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class RenderChickenItem
{
    public static void renderByItem(ItemStack itemStack, ItemDisplayContext transformType, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay)
    {
        Minecraft mc = Minecraft.getInstance();
        if (mc == null || mc.level == null) {
            return;
        }
        String s = ItemChicken.getTypeFromStack(itemStack);
        if(s == null || s.isEmpty()) {
            ChickensRegistryItem item = Iterables.get(ChickensRegistry.getItems(), (int) ((System.currentTimeMillis() / 1000) % ChickensRegistry.getItems().size()));
            s = item.registryName.toString();
        }

        if(s.isEmpty()) {
            return;
        }

        EntityType<?> entityType = BuiltInRegistries.ENTITY_TYPE.getValue(ResourceLocation.parse(s));
        if(entityType == null) {
            return;
        }

        Entity entity = entityType.create(mc.level, EntitySpawnReason.SPAWNER);
        if(entity == null) return;

        if(entity instanceof Chicken chicken)
        {
            if (chicken == null) return;
            //Force the head rot in position to stop it bouncing
            chicken.yHeadRot = 0;

            poseStack.pushPose();

            poseStack.translate(0.5F, 0.0F, 0.5F);

            if (mc.getEntityRenderDispatcher() != null)
            {
                EntityRenderDispatcher entityRenderDispatcher = mc.getEntityRenderDispatcher();
                if (transformType == ItemDisplayContext.GUI)
                {
                    Lighting.setupForFlatItems();
                }
//                entityRenderDispatcher.getRenderer(chicken).render(chicken, 0, 0, poseStack, bufferSource, combinedOverlay);
                entityRenderDispatcher.render(entity, 0.0, 0.0, 0.0, mc.getDeltaTracker().getGameTimeDeltaPartialTick(false), poseStack, bufferSource, combinedLight);
            }

            poseStack.popPose();
        }
    }
}
