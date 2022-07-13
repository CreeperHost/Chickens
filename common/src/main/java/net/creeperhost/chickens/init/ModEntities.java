package net.creeperhost.chickens.init;

import dev.architectury.registry.level.biome.BiomeModifications;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.creeperhost.chickens.Chickens;
import net.creeperhost.chickens.api.ChickensRegistry;
import net.creeperhost.chickens.api.ChickensRegistryItem;
import net.creeperhost.chickens.api.SpawnType;
import net.creeperhost.chickens.entity.EntityChickensChicken;
import net.creeperhost.chickens.entity.EntityColoredEgg;
import net.creeperhost.polylib.entities.SpawnRegistry;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.LevelAccessor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ModEntities
{
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Chickens.MOD_ID, Registry.ENTITY_TYPE_REGISTRY);
    public static final RegistrySupplier<EntityType<EntityColoredEgg>> EGG = ENTITIES.register("egg", () -> EntityType.Builder.<EntityColoredEgg>of(EntityColoredEgg::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build("egg"));

    @Deprecated
    public static final List<EntityType<?>> SPAWNABLE_CHICKENS = new ArrayList<>();

    public static final Map<ChickensRegistryItem, Supplier<EntityType<EntityChickensChicken>>> CHICKENS = Util.make(new LinkedHashMap<>(), map ->
    {
        for (ChickensRegistryItem item : ChickensRegistry.getItems())
        {
            map.put(item, ENTITIES.register(item.getEntityName(), () -> EntityType.Builder.of(EntityChickensChicken::new, MobCategory.CREATURE).sized(0.6F, 1.7F).clientTrackingRange(8).build(item.getEntityName())));
        }
    });

    public static void registerSpawn(Supplier<EntityType<EntityChickensChicken>> entityType, ChickensRegistryItem chickensRegistryItem)
    {
        if(chickensRegistryItem.canSpawn())
        {
            SpawnRegistry.registerSpawn(entityType::get, biomeContext -> canSpawnBiome(biomeContext, chickensRegistryItem), ModEntities::checkAnimalSpawnRules,1, 2, 25);
        }
    }

    public static boolean canSpawnBiome(BiomeModifications.BiomeContext biomeContext, ChickensRegistryItem chickensRegistryItem)
    {
        if(chickensRegistryItem.getSpawnType() == SpawnType.NONE) return false;

        if(chickensRegistryItem.getSpawnType() == SpawnType.HELL && biomeContext.hasTag(BiomeTags.IS_NETHER)) return true;
        if(chickensRegistryItem.getSpawnType() == SpawnType.SNOW && biomeContext.hasTag(BiomeTags.ONLY_ALLOWS_SNOW_AND_GOLD_RABBITS)) return true;
        if(chickensRegistryItem.getSpawnType() == SpawnType.NORMAL)
        {
            if(biomeContext.hasTag(BiomeTags.IS_NETHER)) return false;
            if(biomeContext.hasTag(BiomeTags.IS_END)) return false;
            if(biomeContext.hasTag(BiomeTags.ONLY_ALLOWS_SNOW_AND_GOLD_RABBITS)) return false;
            if(biomeContext.hasTag(BiomeTags.IS_OCEAN)) return false;

            return true;
        }
        return false;
    }


    public static boolean checkAnimalSpawnRules(EntityType<?> entityType, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource random)
    {
        if(!levelAccessor.getFluidState(blockPos.below()).isEmpty()) return false;
        if(levelAccessor.getBlockState(blockPos.below()).isAir()) return false;
        if(!levelAccessor.getBlockState(blockPos.below()).is(BlockTags.ANIMALS_SPAWNABLE_ON)) return false;
        if(!isBrightEnoughToSpawn(levelAccessor, blockPos)) return false;

        return true;
    }

    public static boolean isBrightEnoughToSpawn(BlockAndTintGetter blockAndTintGetter, BlockPos blockPos)
    {
        return blockAndTintGetter.getRawBrightness(blockPos, 0) > 8;
    }
}
