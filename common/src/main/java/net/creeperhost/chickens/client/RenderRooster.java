package net.creeperhost.chickens.client;

import net.creeperhost.chickens.Chickens;
import net.creeperhost.chickens.entity.EntityRooster;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.ChickenRenderState;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class RenderRooster extends MobRenderer<EntityRooster, RenderRooster.RoosterRenderState, RoosterModel>
{
    public RenderRooster(EntityRendererProvider.Context context)
    {
        super(context, new RoosterModel(context.bakeLayer(RoosterModel.LAYER_LOCATION)), 0.5F);
    }

    @Override
    public RoosterRenderState createRenderState() {
        return new RoosterRenderState();
    }

    @Override
    public ResourceLocation getTextureLocation(RoosterRenderState livingEntityRenderState) {
        return ResourceLocation.fromNamespaceAndPath(Chickens.MOD_ID, "textures/entity/rooster.png");
    }

    public static class RoosterRenderState extends ChickenRenderState {

    }
}
