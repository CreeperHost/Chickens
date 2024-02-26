package net.creeperhost.chickens.client;

import net.creeperhost.polylib.client.modulargui.sprite.Material;
import net.creeperhost.polylib.client.modulargui.sprite.SpriteUploader;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static net.creeperhost.chickens.Chickens.MOD_ID;

/**
 * Created by brandon3055 on 23/02/2024
 */
public class ChickenGuiTextures {

    public static final ResourceLocation ATLAS_LOCATION = new ResourceLocation(MOD_ID, "textures/atlas/gui.png");

    private static final Map<String, Material> MATERIAL_CACHE = new HashMap<>();
    private static final SpriteUploader SPRITE_UPLOADER = new SpriteUploader(new ResourceLocation(MOD_ID, "textures/gui"), ATLAS_LOCATION, "gui");

    public static SpriteUploader getUploader() {
        return SPRITE_UPLOADER;
    }

    public static void register(Map<ResourceLocation, Consumer<TextureAtlasSprite>> register, String location) {
        register.put(new ResourceLocation(MOD_ID, location), null);
    }

    public static Material get(String location) {
        return MATERIAL_CACHE.computeIfAbsent(MOD_ID + ":" + location, s -> getUncached(location));
    }

    public static Supplier<Material> getter(Supplier<String> texture) {
        return () -> get(texture.get());
    }

    public static Material getUncached(String texture) {
        return new Material(ATLAS_LOCATION, new ResourceLocation(MOD_ID, texture), SPRITE_UPLOADER::getSprite);
    }
}
