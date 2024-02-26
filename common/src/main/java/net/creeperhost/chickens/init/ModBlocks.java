package net.creeperhost.chickens.init;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.creeperhost.chickens.Chickens;
import net.creeperhost.chickens.block.BlockBreeder;
import net.creeperhost.chickens.block.BlockEggCracker;
import net.creeperhost.chickens.block.IncubatorBlock;
import net.creeperhost.chickens.block.BlockOvoscope;
import net.creeperhost.chickens.blockentities.BlockEntityBreeder;
import net.creeperhost.chickens.blockentities.BlockEntityEggCracker;
import net.creeperhost.chickens.blockentities.IncubatorBlockEntity;
import net.creeperhost.chickens.blockentities.BlockEntityOvoscope;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlocks
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Chickens.MOD_ID, Registry.BLOCK_REGISTRY);
    public static final DeferredRegister<BlockEntityType<?>> TILES_ENTITIES = DeferredRegister.create(Chickens.MOD_ID, Registry.BLOCK_ENTITY_TYPE_REGISTRY);

    public static final RegistrySupplier<Block> INCUBATOR = BLOCKS.register("incubator", IncubatorBlock::new);

    public static final RegistrySupplier<Block> BREEDER = BLOCKS.register("breeder", BlockBreeder::new);

    public static final RegistrySupplier<Block> EGG_CRACKER = BLOCKS.register("egg_cracker", BlockEggCracker::new);

    public static final RegistrySupplier<Block> OVOSCOPE = BLOCKS.register("ovoscope", BlockOvoscope::new);


    public static final RegistrySupplier<BlockEntityType<BlockEntityBreeder>> BREEDER_TILE = TILES_ENTITIES.register("breeder", () -> BlockEntityType.Builder.of(BlockEntityBreeder::new, ModBlocks.BREEDER.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<IncubatorBlockEntity>> INCUBATOR_TILE = TILES_ENTITIES.register("incubator", () -> BlockEntityType.Builder.of(IncubatorBlockEntity::new, ModBlocks.INCUBATOR.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<BlockEntityEggCracker>> EGG_CRACKER_TILE = TILES_ENTITIES.register("egg_cracker", () -> BlockEntityType.Builder.of(BlockEntityEggCracker::new, ModBlocks.EGG_CRACKER.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<BlockEntityOvoscope>> OVOSCOPE_TILE = TILES_ENTITIES.register("ovoscope", () -> BlockEntityType.Builder.of(BlockEntityOvoscope::new, ModBlocks.OVOSCOPE.get()).build(null));

}
