package net.creeperhost.chickens.fabric;

import dev.architectury.platform.Platform;
import net.creeperhost.chickens.Chickens;
import net.creeperhost.chickens.client.ChickenGuiTextures;
import net.creeperhost.chickens.config.Config;
import net.creeperhost.chickens.data.ChickenDataManager;
import net.creeperhost.polylib.fabric.client.ResourceReloadListenerWrapper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class ChickensModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Chickens.init();
        if(Platform.getEnv() == EnvType.CLIENT) {
            FabricClient.init();
        }

        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new IdentifiableResourceReloadListener() {
            @Override
            public ResourceLocation getFabricId() {
                return ResourceLocation.fromNamespaceAndPath(Chickens.MOD_ID, "chicken_variants");
            }

            @Override
            public CompletableFuture<Void> reload(PreparationBarrier preparationBarrier, ResourceManager resourceManager, Executor executor, Executor executor2) {
                return ChickenDataManager.INSTANCE.reload(preparationBarrier, resourceManager, executor, executor2);
            }
        });

        for (Config.FabricSpawn spawn : Config.INSTANCE.fabricSpawns) {
            List<TagKey<Biome>> tags = spawn.biomeTags().stream().map(e -> TagKey.create(Registries.BIOME, ResourceLocation.parse(e))).toList();
            BiomeModifications.addSpawn(e -> tags.stream().anyMatch(e::hasTag), MobCategory.CREATURE, BuiltInRegistries.ENTITY_TYPE.getValue(ResourceLocation.parse(spawn.type())), spawn.weight(), spawn.minCluster(), spawn.maxCluster());
        }
    }
}

