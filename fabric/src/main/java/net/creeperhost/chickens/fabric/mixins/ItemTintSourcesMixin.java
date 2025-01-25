package net.creeperhost.chickens.fabric.mixins;

import com.mojang.serialization.MapCodec;
import net.creeperhost.chickens.Chickens;
import net.creeperhost.chickens.client.ChickenEggTint;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.color.item.ItemTintSources;
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
@Mixin(ItemTintSources.class)
public class ItemTintSourcesMixin {

    @Shadow @Final public static ExtraCodecs.LateBoundIdMapper<ResourceLocation, MapCodec<? extends ItemTintSource>> ID_MAPPER;

    @Inject (
            method = "Lnet/minecraft/client/color/item/ItemTintSources;bootstrap()V",
            at = @At ("RETURN")
    )
    private static void onBootstrap(CallbackInfo ci) {
        ID_MAPPER.put(ResourceLocation.fromNamespaceAndPath(Chickens.MOD_ID, "egg_tint"), ChickenEggTint.MAP_CODEC);
    }

}
