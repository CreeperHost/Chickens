package net.creeperhost.chickens.client;

import net.creeperhost.chickens.entity.EntityChickensChicken;
import net.minecraft.client.model.ChickenModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.ChickenRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class RenderChickensChicken extends MobRenderer<EntityChickensChicken, RenderChickensChicken.CustomChickenState, ChickenModel> {
    public RenderChickensChicken(EntityRendererProvider.Context context) {
        super(context, new ChickenModel(context.bakeLayer(ModelLayers.CHICKEN)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(CustomChickenState state) {
        return state.texture;
    }

    @Override
    public CustomChickenState createRenderState() {
        return new CustomChickenState();
    }

    @Override
    public void extractRenderState(EntityChickensChicken chicken, CustomChickenState chickenRenderState, float f) {
        super.extractRenderState(chicken, chickenRenderState, f);
        chickenRenderState.flap = Mth.lerp(f, chicken.oFlap, chicken.flap);
        chickenRenderState.flapSpeed = Mth.lerp(f, chicken.oFlapSpeed, chicken.flapSpeed);
        chickenRenderState.texture = chicken.getTexture();
    }

    public static class CustomChickenState extends ChickenRenderState {
        public ResourceLocation texture;
    }
}
