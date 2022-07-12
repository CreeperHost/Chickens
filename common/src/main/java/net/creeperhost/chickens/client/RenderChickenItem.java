package net.creeperhost.chickens.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.creeperhost.chickens.entity.EntityChickensChicken;
import net.creeperhost.chickens.item.ItemChicken;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;

public class RenderChickenItem
{
    public static void renderByItem(ItemStack itemStack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay)
    {
        Minecraft mc = Minecraft.getInstance();
        if(mc == null) return;
        if(mc.level == null) return;

        EntityType<?> entityType = Registry.ENTITY_TYPE.get(ResourceLocation.tryParse(ItemChicken.getTypeFromStack(itemStack)));

        EntityChickensChicken chicken = (EntityChickensChicken) entityType.create(mc.level);
        if(chicken == null) return;
        //Force the head rot in position to stop it bouncing
        chicken.yHeadRot = 0;

        poseStack.pushPose();
        poseStack.translate(0.5F, 0.0F, 0.5F);

        EntityRenderDispatcher entityRenderDispatcher = mc.getEntityRenderDispatcher();
        entityRenderDispatcher.setRenderShadow(false);
        entityRenderDispatcher.setRenderHitBoxes(false);
        MultiBufferSource.BufferSource irendertypebuffer$impl = mc.renderBuffers().bufferSource();
        RenderSystem.runAsFancy(() -> {
            entityRenderDispatcher.render(chicken, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, poseStack, irendertypebuffer$impl, 15728880);
        });

        poseStack.popPose();
    }
}
