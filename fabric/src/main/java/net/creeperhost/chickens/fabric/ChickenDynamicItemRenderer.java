package net.creeperhost.chickens.fabric;

import com.mojang.blaze3d.vertex.PoseStack;
import net.creeperhost.chickens.client.RenderChickenItem;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.item.ItemStack;

public class ChickenDynamicItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer
{
    @Override
    public void render(ItemStack itemStack, ItemTransforms.TransformType mode, PoseStack poseStack, MultiBufferSource vertexConsumers, int light, int overlay)
    {
        RenderChickenItem.renderByItem(itemStack, mode, poseStack, vertexConsumers, light, overlay);
    }
}
