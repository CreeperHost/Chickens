package net.creeperhost.chickens.init;

import net.creeperhost.chickens.ChickensMod;
import net.creeperhost.chickens.entity.EntityChickensChicken;
import net.creeperhost.chickens.entity.EntityColoredEgg;
import net.creeperhost.chickens.handler.SpawnType;
import net.creeperhost.chickens.registry.ChickensRegistry;
import net.creeperhost.chickens.registry.ChickensRegistryItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ModEntities
{
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registry.ENTITY_TYPE_REGISTRY, ChickensMod.MODID);
    public static final RegistryObject<EntityType<EntityColoredEgg>> EGG = ENTITIES.register("egg", () -> EntityType.Builder.<EntityColoredEgg>of(EntityColoredEgg::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build("egg"));
    public static final List<EntityType<?>> SPAWNABLE_CHICKENS = new ArrayList<>();

    public static void init()
    {
        for (ChickensRegistryItem item : ChickensRegistry.getItems())
        {
            EntityType<EntityChickensChicken> entityType = EntityType.Builder.of(EntityChickensChicken::new, MobCategory.CREATURE).sized(0.4F, 0.7F).clientTrackingRange(8).build(item.getEntityName());
            ENTITIES.register(item.getEntityName(), () -> entityType);
            if(item.canSpawn())
            {
                SPAWNABLE_CHICKENS.add(entityType);
                SpawnPlacements.register(entityType, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (p_21781_, p_21782_, p_21783_, p_21784_, p_21785_)
                        -> checkAnimalSpawnRules(p_21781_, p_21782_, p_21783_, p_21784_, p_21785_, item));
            }
        }
    }

    public static boolean checkAnimalSpawnRules(EntityType<? extends Animal> entityType, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, Random random, ChickensRegistryItem chickensRegistryItem)
    {
        if(!levelAccessor.getBlockState(blockPos.below()).is(BlockTags.ANIMALS_SPAWNABLE_ON)) return false;
        if(!isBrightEnoughToSpawn(levelAccessor, blockPos)) return false;
        if(!chickensRegistryItem.canSpawn()) return false;
        if(chickensRegistryItem.getSpawnType() == SpawnType.NORMAL)
        {
            return levelAccessor.dimensionType().natural();
        }
        if(chickensRegistryItem.getSpawnType() == SpawnType.HELL)
        {
            return levelAccessor.dimensionType().piglinSafe();
        }
        return true;
    }

    public static boolean isBrightEnoughToSpawn(BlockAndTintGetter blockAndTintGetter, BlockPos blockPos)
    {
        return blockAndTintGetter.getRawBrightness(blockPos, 0) > 8;
    }
}
