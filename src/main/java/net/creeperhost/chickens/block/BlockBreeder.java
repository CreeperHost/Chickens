package net.creeperhost.chickens.block;

import net.creeperhost.chickens.blockentities.BlockEntityBreeder;
import net.creeperhost.chickens.blockentities.BlockEntityRoost;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockBreeder extends BaseEntityBlock
{
    public static final BooleanProperty IS_BREEDING = BooleanProperty.create("is_breeding");
    public static final BooleanProperty HAS_SEEDS = BooleanProperty.create("has_seeds");

    public BlockBreeder()
    {
        super(Properties.of(Material.WOOD).strength(2.0F).noOcclusion());
        this.registerDefaultState(getStateDefinition().any().setValue(IS_BREEDING, false).setValue(HAS_SEEDS, false));
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull InteractionResult use(@NotNull BlockState blockState, Level level, @NotNull BlockPos blockPos, @NotNull Player player, @NotNull InteractionHand interactionHand, BlockHitResult blockHitResult)
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
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type)
    {
        return (level1, blockPos, blockState, t) ->
        {
            if (t instanceof BlockEntityBreeder breeder)
            {
                breeder.tick();
            }
        };
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        return this.defaultBlockState().setValue(IS_BREEDING, false).setValue(HAS_SEEDS, false);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(IS_BREEDING, HAS_SEEDS);
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
        return new BlockEntityBreeder(blockPos, blockState);
    }

    @Override
    public void onRemove(BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, BlockState blockState1, boolean p_51542_)
    {
        if (!blockState.is(blockState1.getBlock()))
        {
            BlockEntity blockentity = level.getBlockEntity(blockPos);
            if (blockentity instanceof BlockEntityBreeder blockEntityBreeder)
            {
                for (int i = 0; i < blockEntityBreeder.inventory.getSlots(); i++)
                {
                    if(blockEntityBreeder.inventory.getStackInSlot(i).isEmpty()) continue;
                    ItemStack stack = blockEntityBreeder.inventory.getStackInSlot(i);
                    Containers.dropItemStack(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), stack);
                }
            }
            super.onRemove(blockState, level, blockPos, blockState1, p_51542_);
        }
    }
}
