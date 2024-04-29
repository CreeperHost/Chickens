package net.creeperhost.chickens.fabric;

import dev.architectury.platform.Platform;
import net.creeperhost.chickens.Chickens;
import net.creeperhost.chickens.config.Config;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;

import java.util.List;

public class ChickensModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Chickens.init();
        if(Platform.getEnv() == EnvType.CLIENT) {
            FabricClient.init();
        }

        for (Config.FabricSpawn spawn : Config.INSTANCE.fabricSpawns) {
            List<TagKey<Biome>> tags = spawn.biomeTags().stream().map(e -> TagKey.create(Registries.BIOME, new ResourceLocation(e))).toList();
            BiomeModifications.addSpawn(e -> tags.stream().anyMatch(e::hasTag), MobCategory.CREATURE, BuiltInRegistries.ENTITY_TYPE.get(new ResourceLocation(spawn.type())), spawn.weight(), spawn.minCluster(), spawn.maxCluster());
        }
//
//        BiomeModifications.addSpawn(e -> e.hasTag(BiomeTags.IS_OVERWORLD), MobCategory.CREATURE, BuiltInRegistries.ENTITY_TYPE.get(new ResourceLocation("chickens:flint_chicken")), 10, 2, 4);
//        BiomeModifications.addSpawn(e -> e.hasTag(BiomeTags.IS_OVERWORLD), MobCategory.CREATURE, BuiltInRegistries.ENTITY_TYPE.get(new ResourceLocation("chickens:log_chicken")), 10, 2, 4);
//        BiomeModifications.addSpawn(e -> e.hasTag(BiomeTags.IS_OVERWORLD), MobCategory.CREATURE, BuiltInRegistries.ENTITY_TYPE.get(new ResourceLocation("chickens:sand_chicken")), 10, 2, 4);
//        //Add these to both creature and monster because creature spawn rate is ridiculously low in the nether.
//        BiomeModifications.addSpawn(e -> e.hasTag(BiomeTags.IS_NETHER), MobCategory.CREATURE, BuiltInRegistries.ENTITY_TYPE.get(new ResourceLocation("chickens:quartz_chicken")), 60, 12, 12);
//        BiomeModifications.addSpawn(e -> e.hasTag(BiomeTags.IS_NETHER), MobCategory.CREATURE, BuiltInRegistries.ENTITY_TYPE.get(new ResourceLocation("chickens:soulsand_chicken")), 60, 12, 12);
    }
}

