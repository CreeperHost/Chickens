package net.creeperhost.chickens.init;

import dev.architectury.registry.registries.DeferredRegister;
import net.creeperhost.chickens.Chickens;
import net.creeperhost.chickens.api.ChickensRegistry;
import net.creeperhost.chickens.api.ChickensRegistryItem;
import net.creeperhost.chickens.entity.EntityChickensChicken;
import net.creeperhost.chickens.entity.EntityRooster;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Chickens.MOD_ID, Registries.ENTITY_TYPE);

    public static final Supplier<EntityType<EntityRooster>> ROOSTER = ENTITIES.register("rooster", () -> EntityType.Builder.of(EntityRooster::new, MobCategory.CREATURE)
            .sized(0.6F, 1.7F)
            .clientTrackingRange(8)
            .build(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Chickens.MOD_ID, "rooster"))));

    public static final Map<ChickensRegistryItem, Supplier<EntityType<EntityChickensChicken>>> CHICKENS = Util.make(new LinkedHashMap<>(), map ->
    {
        for (ChickensRegistryItem item : ChickensRegistry.getItems())
        {
            if(!item.equals(ModChickens.ROOSTER))
            {
                map.put(item, ENTITIES.register(item.getEntityName(), () -> EntityType.Builder.of(EntityChickensChicken::new, MobCategory.CREATURE)
                        .sized(0.6F, 1.7F)
                        .clientTrackingRange(8)
                        .build(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Chickens.MOD_ID, item.getEntityName())))
                ));
            }
        }
    });

    @Deprecated //TODO Neo now handles this neo side, still need to check if fabric works
    public static <T extends Animal> void registerSpawnFabric(EntityType<T> entityType, ChickensRegistryItem chickensRegistryItem) {
        if (chickensRegistryItem.isEnabled()) {
            SpawnPlacements.register(entityType, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModEntities::checkChickenSpawnRules);
        }
    }

    public static boolean checkChickenSpawnRules(EntityType<? extends Animal> entityType, LevelAccessor levelAccessor, EntitySpawnReason spawnReason, BlockPos blockPos, RandomSource randomSource) {
        if (!levelAccessor.getBiome(blockPos).is(BiomeTags.IS_OVERWORLD)) {
            return true;
        }
        return Animal.checkAnimalSpawnRules(entityType, levelAccessor, spawnReason, blockPos, randomSource);
    }
}
