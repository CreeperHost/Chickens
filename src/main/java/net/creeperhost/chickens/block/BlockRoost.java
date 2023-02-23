package net.creeperhost.chickens.block;

import net.creeperhost.chickens.blockentities.BlockEntityRoost;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockRoost extends BaseEntityBlock
{
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public BlockRoost()
    {
        super(Properties.of(Material.WOOD).strength(2.0F));
        this.registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult)
    {
        if (!level.isClientSide)
        {
            NetworkHooks.openScreen((ServerPlayer) player, (MenuProvider) level.getBlockEntity(blockPos), blockPos);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type)
    {
        return (level1, blockPos, blockState, t) ->
        {
            if (t instanceof BlockEntityRoost breeder)
            {
                breeder.tick();
            }
        };
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(FACING);
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState)
    {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState)
    {
        return new BlockEntityRoost(blockPos, blockState);
    }

    @Override
    public void onRemove(BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, BlockState blockState1, boolean p_51542_)
    {
        if (!blockState.is(blockState1.getBlock()))
        {
            BlockEntity blockentity = level.getBlockEntity(blockPos);
            if (blockentity instanceof BlockEntityRoost blockEntityRoost)
            {
                for (int i = 0; i < blockEntityRoost.inventory.getSlots(); i++)
                {
                    if(blockEntityRoost.inventory.getStackInSlot(i).isEmpty()) continue;
                    ItemStack stack = blockEntityRoost.inventory.getStackInSlot(i);
                    Containers.dropItemStack(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), stack);
                }
            }
            super.onRemove(blockState, level, blockPos, blockState1, p_51542_);
        }
    }
}
