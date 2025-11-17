package net.creeperhost.chickens.client;

import net.creeperhost.chickens.Chickens;
import net.creeperhost.chickens.data.ChickenVariant;
import net.creeperhost.chickens.entity.ChickensChicken;
import net.creeperhost.chickens.entity.EntityChickensChicken;
import net.creeperhost.chickens.entity.EntityRooster;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.ChickenRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Chicken;

public class RenderChickens extends MobRenderer<Chicken, RenderChickens.ChickensRenderState, ChickensModel> {
    public RenderChickens(EntityRendererProvider.Context context) {
        super(context, new ChickensModel(context.bakeLayer(ChickensModel.LAYER_LOCATION)), 0.5F);
    }

    @Override
    public ChickensRenderState createRenderState() {
        return new ChickensRenderState();
    }

    @Override
    public ResourceLocation getTextureLocation(ChickensRenderState state) {
        return state.texture;
    }

    @Override
    public void extractRenderState(Chicken entity, ChickensRenderState state, float f) {
        super.extractRenderState(entity, state, f);
        state.flap = Mth.lerp(f, entity.oFlap, entity.flap);
        state.flapSpeed = Mth.lerp(f, entity.oFlapSpeed, entity.flapSpeed);
        if (entity instanceof ChickensChicken chicken) {
            ChickenVariant variant = chicken.getChickenVariant();
            state.texture = variant.texture();
            state.isRooster = chicken.isRooster();
        } else {

            if (entity instanceof EntityChickensChicken chickensChicken) {
                state.texture = chickensChicken.getTexture();
            } else {
                //TODO, temporary, just until i get the rooster variants figured out.
                state.texture = ResourceLocation.fromNamespaceAndPath(Chickens.MOD_ID, "textures/entity/rooster.png");
            }
            state.isRooster = entity instanceof EntityRooster;
        }
    }

    public static class ChickensRenderState extends ChickenRenderState {
        public boolean isRooster = false;
        public ResourceLocation texture;
    }
}
