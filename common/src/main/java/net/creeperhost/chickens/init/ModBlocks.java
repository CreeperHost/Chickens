package net.creeperhost.chickens.init;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.creeperhost.chickens.Chickens;
import net.creeperhost.chickens.block.BreederBlock;
import net.creeperhost.chickens.block.EggCrackerBlock;
import net.creeperhost.chickens.block.IncubatorBlock;
import net.creeperhost.chickens.block.OvoscopeBlock;
import net.creeperhost.chickens.blockentities.BreederBlockEntity;
import net.creeperhost.chickens.blockentities.EggCrackerBlockEntity;
import net.creeperhost.chickens.blockentities.IncubatorBlockEntity;
import net.creeperhost.chickens.blockentities.OvoscopeBlockEntity;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlocks
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Chickens.MOD_ID, Registry.BLOCK_REGISTRY);
    public static final DeferredRegister<BlockEntityType<?>> TILES_ENTITIES = DeferredRegister.create(Chickens.MOD_ID, Registry.BLOCK_ENTITY_TYPE_REGISTRY);

    public static final RegistrySupplier<Block> INCUBATOR = BLOCKS.register("incubator", IncubatorBlock::new);

    public static final RegistrySupplier<Block> BREEDER = BLOCKS.register("breeder", BreederBlock::new);

    public static final RegistrySupplier<Block> EGG_CRACKER = BLOCKS.register("egg_cracker", EggCrackerBlock::new);

    public static final RegistrySupplier<Block> OVOSCOPE = BLOCKS.register("ovoscope", OvoscopeBlock::new);


    public static final RegistrySupplier<BlockEntityType<BreederBlockEntity>> BREEDER_TILE = TILES_ENTITIES.register("breeder", () -> BlockEntityType.Builder.of(BreederBlockEntity::new, ModBlocks.BREEDER.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<IncubatorBlockEntity>> INCUBATOR_TILE = TILES_ENTITIES.register("incubator", () -> BlockEntityType.Builder.of(IncubatorBlockEntity::new, ModBlocks.INCUBATOR.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<EggCrackerBlockEntity>> EGG_CRACKER_TILE = TILES_ENTITIES.register("egg_cracker", () -> BlockEntityType.Builder.of(EggCrackerBlockEntity::new, ModBlocks.EGG_CRACKER.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<OvoscopeBlockEntity>> OVOSCOPE_TILE = TILES_ENTITIES.register("ovoscope", () -> BlockEntityType.Builder.of(OvoscopeBlockEntity::new, ModBlocks.OVOSCOPE.get()).build(null));

}
