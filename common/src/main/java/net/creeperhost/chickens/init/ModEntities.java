package net.creeperhost.chickens.init;

import dev.architectury.registry.registries.DeferredRegister;
import net.creeperhost.chickens.Chickens;
import net.creeperhost.chickens.api.ChickensRegistry;
import net.creeperhost.chickens.api.ChickensRegistryItem;
import net.creeperhost.chickens.entity.EntityChickensChicken;
import net.creeperhost.polylib.registry.SpawnRegistry;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Chickens.MOD_ID, Registries.ENTITY_TYPE);

    public static final Map<ChickensRegistryItem, Supplier<EntityType<EntityChickensChicken>>> CHICKENS = Util.make(new LinkedHashMap<>(), map ->
    {
        for (ChickensRegistryItem item : ChickensRegistry.getItems()) {
            map.put(item, ENTITIES.register(item.getEntityName(), () -> EntityType.Builder.of(EntityChickensChicken::new, MobCategory.CREATURE)
                    .sized(0.6F, 1.7F)
                    .clientTrackingRange(8)
                    .build(item.getEntityName())
            ));
        }
    });

    public static <T extends Animal> void registerSpawn(EntityType<T> entityType, ChickensRegistryItem chickensRegistryItem) {
        if (chickensRegistryItem.isEnabled()) {
//            SpawnRegistry.registerSpawnPlacement(() -> entityType, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModEntities::checkAnimalSpawnRules);
            SpawnPlacements.register(entityType, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);



        }
    }
}
