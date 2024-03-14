package net.creeperhost.chickens.client;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import net.creeperhost.chickens.entity.EntityChickensChicken;
import net.creeperhost.chickens.item.ItemChicken;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class RenderChickenItem
{
    public static void renderByItem(ItemStack itemStack, ItemDisplayContext transformType, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay)
    {
        Minecraft mc = Minecraft.getInstance();
        if (mc == null) return;
        if (mc.level == null) return;
        String s = ItemChicken.getTypeFromStack(itemStack);
        if(s == null || s.isEmpty()) return;

        EntityType<?> entityType = BuiltInRegistries.ENTITY_TYPE.get(new ResourceLocation(s));
        if(entityType == null) return;

        Entity entity = entityType.create(mc.level);
        if(entity == null) return;

        EntityChickensChicken chicken = (EntityChickensChicken) entity;
        if (chicken == null) return;
        //Force the head rot in position to stop it bouncing
        chicken.yHeadRot = 0;

        poseStack.pushPose();

        poseStack.translate(0.5F, 0.0F, 0.5F);

        if (transformType == ItemDisplayContext.GUI)
        {
            Lighting.setupForFlatItems();
        }

        if (mc.getEntityRenderDispatcher() != null)
        {
            EntityRenderDispatcher entityRenderDispatcher = mc.getEntityRenderDispatcher();

            MultiBufferSource.BufferSource irendertypebuffer$impl = mc.renderBuffers().bufferSource();
            entityRenderDispatcher.getRenderer(chicken).render(chicken, 0.0F, 0.0F, poseStack, irendertypebuffer$impl, combinedLight);
        }

        poseStack.popPose();
    }
}
