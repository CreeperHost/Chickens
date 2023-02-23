package net.creeperhost.chickens.init;

import net.creeperhost.chickens.ChickensMod;
import net.creeperhost.chickens.entity.EntityChickensChicken;
import net.creeperhost.chickens.entity.EntityColoredEgg;
import net.creeperhost.chickens.handler.SpawnType;
import net.creeperhost.chickens.registry.ChickensRegistry;
import net.creeperhost.chickens.registry.ChickensRegistryItem;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.*;
import java.util.function.Supplier;

public class ModEntities
{
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registry.ENTITY_TYPE_REGISTRY, ChickensMod.MODID);
    public static final RegistryObject<EntityType<EntityColoredEgg>> EGG = ENTITIES.register("egg", () -> EntityType.Builder.<EntityColoredEgg>of(EntityColoredEgg::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build("egg"));

    public static final Map<ChickensRegistryItem, Supplier<EntityType<EntityChickensChicken>>> CHICKENS = Util.make(new LinkedHashMap<>(), map ->
    {
        for (ChickensRegistryItem item : ChickensRegistry.getItems())
        {
            map.put(item, ENTITIES.register(item.getEntityName(), () -> EntityType.Builder.of(EntityChickensChicken::new, MobCategory.CREATURE).sized(0.6F, 1.7F).clientTrackingRange(8).build(item.getEntityName())));
        }
    });

    public static void registerSpawn(Supplier<EntityType<EntityChickensChicken>> entityType, ChickensRegistryItem chickensRegistryItem)
    {
        ChickensMod.LOGGER.info("Registering spawn for " + entityType.get().getDescriptionId());
        SpawnPlacements.register(entityType.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModEntities::checkAnimalSpawnRules);
    }

    public static boolean checkAnimalSpawnRules(EntityType<? extends Entity> animal, LevelAccessor worldIn, MobSpawnType reason, BlockPos pos, RandomSource random)
    {
        return worldIn.getBlockState(pos.below()).is(Blocks.GRASS_BLOCK) && worldIn.getRawBrightness(pos, 0) > 8;
    }
}
