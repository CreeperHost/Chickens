package net.creeperhost.chickens.init;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.creeperhost.chickens.Chickens;
import net.creeperhost.chickens.block.BlockBreeder;
import net.creeperhost.chickens.block.BlockIncubator;
import net.creeperhost.chickens.block.BlockRoost;
import net.creeperhost.chickens.blockentities.BlockEntityBreeder;
import net.creeperhost.chickens.blockentities.BlockEntityRoost;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlocks
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Chickens.MOD_ID, Registry.BLOCK_REGISTRY);
    public static final DeferredRegister<BlockEntityType<?>> TILES_ENTITIES = DeferredRegister.create(Chickens.MOD_ID, Registry.BLOCK_ENTITY_TYPE_REGISTRY);

    public static final RegistrySupplier<Block> INCUBATOR = BLOCKS.register("incubator", BlockIncubator::new);

    public static final RegistrySupplier<Block> BREEDER = BLOCKS.register("breeder", BlockBreeder::new);
    public static final RegistrySupplier<Block> ROOST = BLOCKS.register("roost", BlockRoost::new);

    public static final RegistrySupplier<BlockEntityType<BlockEntityBreeder>> BREEDER_TILE = TILES_ENTITIES.register("breeder", () -> BlockEntityType.Builder.of(BlockEntityBreeder::new, ModBlocks.BREEDER.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<BlockEntityRoost>> ROOST_TILE = TILES_ENTITIES.register("roost", () -> BlockEntityType.Builder.of(BlockEntityRoost::new, ModBlocks.ROOST.get()).build(null));


}
