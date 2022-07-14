package net.creeperhost.chickens.client;

import net.creeperhost.chickens.entity.EntityChickensChicken;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ChickenModel;
import net.minecraft.client.model.MinecartModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class RenderChickensChicken extends MobRenderer<EntityChickensChicken, ChickenModel<EntityChickensChicken>>
{
    public RenderChickensChicken(EntityRendererProvider.Context context)
    {
        super(context, new ChickenModel<>(context.bakeLayer(ModelLayers.CHICKEN)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(EntityChickensChicken entity)
    {
        return entity.getTexture();
    }

    @Override
    protected float getBob(EntityChickensChicken p_114000_, float p_114001_)
    {
        float f = Mth.lerp(p_114001_, p_114000_.oFlap, p_114000_.flap);
        float f1 = Mth.lerp(p_114001_, p_114000_.oFlapSpeed, p_114000_.flapSpeed);
        return (Mth.sin(f) + 1.0F) * f1;
    }
}
