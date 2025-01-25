package net.creeperhost.chickens.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

/**
 * Created by brandon3055 on 25/01/2025
 */
public class ChickenItemRender implements SpecialModelRenderer<ChickenItemRender.Data> {

    @Override
    public void render(@Nullable ChickenItemRender.Data data, ItemDisplayContext transformType, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay, boolean b) {
        if (data != null) {
            RenderChickenItem.renderByItem(data.stack, transformType, poseStack, bufferSource, light, overlay);
        }
    }

    @Nullable
    @Override
    public Data extractArgument(ItemStack stack) {
        return new Data(stack);
    }

    public static class Data {
        public ItemStack stack;
        public Data(ItemStack stack) {
            this.stack = stack;
        }
    }

    public record Unbaked() implements SpecialModelRenderer.Unbaked {
        public static final MapCodec<Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec((builder) -> builder.stable(new Unbaked()));

        public MapCodec<Unbaked> type() {
            return MAP_CODEC;
        }

        public SpecialModelRenderer<?> bake(EntityModelSet p_387681_) {
            return new ChickenItemRender();
        }
    }
}
