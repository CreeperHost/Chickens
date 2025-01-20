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
import net.fabricmc.fabric.mixin.lookup.BlockEntityTypeAccessor;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

public class ModBlocks
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Chickens.MOD_ID, Registries.BLOCK);
    public static final DeferredRegister<BlockEntityType<?>> TILES_ENTITIES = DeferredRegister.create(Chickens.MOD_ID, Registries.BLOCK_ENTITY_TYPE);

    public static final RegistrySupplier<Block> INCUBATOR = BLOCKS.register("incubator", block("incubator", IncubatorBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(2.0F).noOcclusion().lightLevel(value -> 15)));

    public static final RegistrySupplier<Block> BREEDER = BLOCKS.register("breeder", block("breeder", BreederBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).noOcclusion().strength(2.0F)));

    public static final RegistrySupplier<Block> EGG_CRACKER = BLOCKS.register("egg_cracker", block("egg_cracker", EggCrackerBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(2.0F).noOcclusion()));

    public static final RegistrySupplier<Block> OVOSCOPE = BLOCKS.register("ovoscope", block("ovoscope", OvoscopeBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.METAL).noOcclusion().strength(2.0F)));

    public static final RegistrySupplier<BlockEntityType<BreederBlockEntity>> BREEDER_TILE = TILES_ENTITIES.register("breeder", () -> new BlockEntityType<>(BreederBlockEntity::new, Set.of(ModBlocks.BREEDER.get())));
    public static final RegistrySupplier<BlockEntityType<IncubatorBlockEntity>> INCUBATOR_TILE = TILES_ENTITIES.register("incubator", () -> new BlockEntityType<>(IncubatorBlockEntity::new, Set.of(ModBlocks.INCUBATOR.get())));
    public static final RegistrySupplier<BlockEntityType<EggCrackerBlockEntity>> EGG_CRACKER_TILE = TILES_ENTITIES.register("egg_cracker", () -> new BlockEntityType<>(EggCrackerBlockEntity::new, Set.of(ModBlocks.EGG_CRACKER.get())));
    public static final RegistrySupplier<BlockEntityType<OvoscopeBlockEntity>> OVOSCOPE_TILE = TILES_ENTITIES.register("ovoscope", () -> new BlockEntityType<>(OvoscopeBlockEntity::new, Set.of(ModBlocks.OVOSCOPE.get())));


    private static Supplier<Block> block(String name, Function<BlockBehaviour.Properties, Block> block, BlockBehaviour.Properties properties) {
        return () -> block.apply(properties.setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(Chickens.MOD_ID, name))));
    }
}
