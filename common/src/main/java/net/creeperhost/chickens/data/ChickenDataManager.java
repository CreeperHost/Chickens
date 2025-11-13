package net.creeperhost.chickens.data;

import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.Map;

/**
 * Created by brandon3055 on 10/11/2025
 */
public class ChickenDataManager extends SimpleJsonResourceReloadListener<ChickenVariant> {

    protected ChickenDataManager(FileToIdConverter fileToIdConverter) {
        super(ChickenVariant.CODEC, fileToIdConverter);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    protected void apply(Map<ResourceLocation, ChickenVariant> variantMap, ResourceManager resourceManager, ProfilerFiller profilerFiller) {

    }
}
