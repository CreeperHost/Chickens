package net.creeperhost.chickens.block;

import dev.architectury.registry.menu.MenuRegistry;
import net.creeperhost.chickens.init.ModBlocks;
import net.creeperhost.polylib.blocks.PolyEntityBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class BreederBlock extends PolyEntityBlock {
    public static final BooleanProperty IS_BREEDING = BooleanProperty.create("is_breeding");
    public static final BooleanProperty HAS_SEEDS = BooleanProperty.create("has_seeds");

    public BreederBlock() {
        super(Properties.of().mapColor(MapColor.WOOD).noOcclusion().strength(2.0F));
        this.registerDefaultState(getStateDefinition().any().setValue(IS_BREEDING, false).setValue(HAS_SEEDS, false));
        this.setBlockEntity(ModBlocks.BREEDER_TILE::get, true);
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
        if (!level.isClientSide) {
            BlockEntity blockEntity = level.getBlockEntity(blockPos);
            MenuRegistry.openExtendedMenu((ServerPlayer) player, (MenuProvider) blockEntity, packetBuffer -> packetBuffer.writeBlockPos(blockEntity.getBlockPos()));
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        return this.defaultBlockState().setValue(IS_BREEDING, false).setValue(HAS_SEEDS, false);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(IS_BREEDING, HAS_SEEDS);
    }

    @Override
    public boolean skipRendering(BlockState blockState, BlockState blockState2, Direction direction) {
        return false;
    }
}
