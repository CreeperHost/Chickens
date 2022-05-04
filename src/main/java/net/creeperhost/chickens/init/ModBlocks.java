package net.creeperhost.chickens.init;

import net.creeperhost.chickens.ChickensMod;
import net.creeperhost.chickens.block.BlockBreeder;
import net.creeperhost.chickens.block.BlockHenhouse;
import net.creeperhost.chickens.block.BlockRoost;
import net.creeperhost.chickens.blockentities.BlockEntityBreeder;
import net.creeperhost.chickens.blockentities.BlockEntityHenhouse;
import net.creeperhost.chickens.blockentities.BlockEntityRoost;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ChickensMod.MODID);
    public static final DeferredRegister<BlockEntityType<?>> TILES_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, ChickensMod.MODID);

    public static final RegistryObject<Block> HEN_HOUSE = BLOCKS.register("henhouse", BlockHenhouse::new);
    public static final RegistryObject<Block> HEN_HOUSE_ACACIA = BLOCKS.register("henhouse_acacia", BlockHenhouse::new);
    public static final RegistryObject<Block> HEN_HOUSE_BIRCH = BLOCKS.register("henhouse_birch", BlockHenhouse::new);
    public static final RegistryObject<Block> HEN_HOUSE_DARK_OAK = BLOCKS.register("henhouse_dark_oak", BlockHenhouse::new);
    public static final RegistryObject<Block> HEN_HOUSE_JUNGLE = BLOCKS.register("henhouse_jungle", BlockHenhouse::new);
    public static final RegistryObject<Block> HEN_HOUSE_SPRUCE = BLOCKS.register("henhouse_spruce", BlockHenhouse::new);

    public static final RegistryObject<Block> BREEDER = BLOCKS.register("breeder", BlockBreeder::new);
    public static final RegistryObject<Block> ROOST = BLOCKS.register("roost", BlockRoost::new);


    public static final RegistryObject<BlockEntityType<BlockEntityHenhouse>> HEN_HOUSE_TILE = TILES_ENTITIES.register("henhouse", () -> BlockEntityType.Builder.of(BlockEntityHenhouse::new, ModBlocks.HEN_HOUSE.get(), ModBlocks.HEN_HOUSE_ACACIA.get(), ModBlocks.HEN_HOUSE_BIRCH.get(), ModBlocks.HEN_HOUSE_DARK_OAK.get(), ModBlocks.HEN_HOUSE_JUNGLE.get(), ModBlocks.HEN_HOUSE_SPRUCE.get()).build(null));
    public static final RegistryObject<BlockEntityType<BlockEntityBreeder>> BREEDER_TILE = TILES_ENTITIES.register("breeder", () -> BlockEntityType.Builder.of(BlockEntityBreeder::new, ModBlocks.BREEDER.get()).build(null));
    public static final RegistryObject<BlockEntityType<BlockEntityRoost>> ROOST_TILE = TILES_ENTITIES.register("roost", () -> BlockEntityType.Builder.of(BlockEntityRoost::new, ModBlocks.ROOST.get()).build(null));


}
