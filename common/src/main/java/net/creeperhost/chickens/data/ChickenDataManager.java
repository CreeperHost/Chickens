package net.creeperhost.chickens.data;

import net.creeperhost.chickens.Chickens;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Created by brandon3055 on 10/11/2025
 */
public class ChickenDataManager extends SimpleJsonResourceReloadListener<ChickenVariant> {

    public static final ChickenDataManager INSTANCE = new ChickenDataManager();
    private final Map<String, ChickenVariant> variants = new HashMap<>();

    public ChickenDataManager() {
        super(ChickenVariant.CODEC, FileToIdConverter.json("variants"));
    }

    public static Set<String> getVariantIds() {
        return INSTANCE.variants.keySet();
    }

    public static Collection<ChickenVariant> getVariants() {
        return INSTANCE.variants.values();
    }

    @Nullable
    public static ChickenVariant getVariant(String variantId) {
        return INSTANCE.variants.get(variantId);
    }

    public static ChickenVariant getVariantOrMissing(String variantId) {
        return INSTANCE.variants.getOrDefault(variantId, ChickenVariant.MISSING);
    }

    @Override
    protected void apply(Map<ResourceLocation, ChickenVariant> variantMap, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        variants.clear();
        variantMap.forEach((location, chickenVariant) -> variants.put(chickenVariant.id(), chickenVariant));
        Chickens.LOGGER.info("Loaded {} entity chicken variants", variants.size());
    }
}
