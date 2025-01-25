package net.creeperhost.chickens.fabric.mixins;

import com.mojang.serialization.MapCodec;
import net.creeperhost.chickens.client.ChickenItemRender;
import net.creeperhost.chickens.init.ModItems;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Created by brandon3055 on 20/01/2025
 */
@Mixin(SpecialModelRenderers.class)
public class SpecialModelRenderersMixin {

    @Shadow @Final public static ExtraCodecs.LateBoundIdMapper<ResourceLocation, MapCodec<? extends SpecialModelRenderer.Unbaked>> ID_MAPPER;

    @Inject (
            method = "Lnet/minecraft/client/renderer/special/SpecialModelRenderers;bootstrap()V",
            at = @At ("RETURN")
    )
    private static void onBootstrap(CallbackInfo ci) {
        ID_MAPPER.put(ModItems.CHICKEN_ITEM.getId(), ChickenItemRender.Unbaked.MAP_CODEC);
    }

}
