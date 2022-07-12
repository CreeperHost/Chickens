package net.creeperhost.chickens.init;

import dev.architectury.platform.Platform;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.registry.level.biome.BiomeModifications;
import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import net.creeperhost.chickens.Chickens;
import net.creeperhost.chickens.api.ChickensRegistry;
import net.creeperhost.chickens.api.ChickensRegistryItem;
import net.creeperhost.chickens.client.RenderChickensChicken;
import net.creeperhost.chickens.entity.EntityChickensChicken;
import net.fabricmc.api.EnvType;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ModEntities
{
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Chickens.MOD_ID, Registry.ENTITY_TYPE_REGISTRY);

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
        //TODO
//        if(chickensRegistryItem.canSpawn())
//        {
//            BiomeModifications.addProperties(ModEntities::canSpawnBiome, (biomeContext, mutable) -> mutable.getSpawnProperties().addSpawn(MobCategory.MONSTER,
//                    new MobSpawnSettings.SpawnerData(entityType.get(), 10, 4, 4)));
//
//            SpawnPlacements.register(entityType.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
//                    (p_21781_, p_21782_, p_21783_, p_21784_, p_21785_) -> checkAnimalSpawnRules(p_21781_, p_21782_, p_21783_, p_21784_, p_21785_, chickensRegistryItem));
//        }
    }

    public static boolean canSpawnBiome(BiomeModifications.BiomeContext biomeContext)
    {
        if(biomeContext.hasTag(BiomeTags.IS_NETHER)) return false;
        if(biomeContext.hasTag(BiomeTags.IS_END)) return false;
        if(biomeContext.hasTag(BiomeTags.IS_OCEAN)) return false;
        return true;
    }


    public static boolean checkAnimalSpawnRules(EntityType<?> entityType, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource random, ChickensRegistryItem chickensRegistryItem)
    {
        return levelAccessor.getBlockState(blockPos.below()).is(BlockTags.ANIMALS_SPAWNABLE_ON) && isBrightEnoughToSpawn(levelAccessor, blockPos);
    }

    public static boolean isBrightEnoughToSpawn(BlockAndTintGetter blockAndTintGetter, BlockPos blockPos)
    {
        return blockAndTintGetter.getRawBrightness(blockPos, 0) > 8;
    }
}
