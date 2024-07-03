package net.creeperhost.chickens.client;

import net.creeperhost.chickens.Chickens;
import net.creeperhost.chickens.entity.EntityRooster;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class RenderRooster extends MobRenderer<EntityRooster, RoosterModel<EntityRooster>>
{
    public RenderRooster(EntityRendererProvider.Context context)
    {
        super(context, new RoosterModel<>(context.bakeLayer(RoosterModel.LAYER_LOCATION)), 0.5F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(EntityRooster entity)
    {
        return ResourceLocation.fromNamespaceAndPath(Chickens.MOD_ID, "textures/entity/rooster.png");
    }
}
