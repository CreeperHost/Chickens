package net.creeperhost.chickens.init;

import dev.architectury.registry.registries.DeferredRegister;
import net.creeperhost.chickens.Chickens;
import net.creeperhost.chickens.api.ChickensRegistry;
import net.creeperhost.chickens.api.ChickensRegistryItem;
import net.creeperhost.chickens.entity.EntityChickensChicken;
import net.creeperhost.polylib.entities.SpawnRegistry;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ModEntities
{
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Chickens.MOD_ID, Registry.ENTITY_TYPE_REGISTRY);

    public static final Map<ChickensRegistryItem, Supplier<EntityType<EntityChickensChicken>>> CHICKENS = Util.make(new LinkedHashMap<>(), map ->
    {
        for (ChickensRegistryItem item : ChickensRegistry.getItems())
        {
            map.put(item, ENTITIES.register(item.getEntityName(), () -> EntityType.Builder.of(EntityChickensChicken::new, MobCategory.CREATURE).sized(0.6F, 1.7F).clientTrackingRange(8).build(item.getEntityName())));
        }
    });

    public static void registerSpawn(EntityType<?> entityType, ChickensRegistryItem chickensRegistryItem)
    {
        if (chickensRegistryItem.canSpawn())
        {
            SpawnRegistry.registerSpawnPlacement(() -> entityType, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModEntities::checkAnimalSpawnRules);
        }
    }

    public static boolean checkAnimalSpawnRules(EntityType<? extends Entity> animal, LevelAccessor worldIn, MobSpawnType reason, BlockPos pos, RandomSource random)
    {
        return worldIn.getBlockState(pos.below()).is(Blocks.GRASS_BLOCK) && worldIn.getRawBrightness(pos, 0) > 8;
    }
}
