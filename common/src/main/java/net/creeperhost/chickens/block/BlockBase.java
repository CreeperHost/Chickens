//package net.creeperhost.chickens.block;
//
//import net.minecraft.core.BlockPos;
//import net.minecraft.world.Container;
//import net.minecraft.world.Containers;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.block.BaseEntityBlock;
//import net.minecraft.world.level.block.RenderShape;
//import net.minecraft.world.level.block.entity.BlockEntity;
//import net.minecraft.world.level.block.state.BlockState;
//import org.jetbrains.annotations.NotNull;
//
//public abstract class BlockBase extends BaseEntityBlock
//{
//    public BlockBase(Properties properties)
//    {
//        super(properties);
//    }
//
//    @Override
//    public RenderShape getRenderShape(@NotNull BlockState blockState)
//    {
//        return RenderShape.MODEL;
//    }
//
//    @Override
//    public void onRemove(BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, BlockState blockState1, boolean p_51542_)
//    {
//        if (!blockState.is(blockState1.getBlock()))
//        {
//            BlockEntity blockentity = level.getBlockEntity(blockPos);
//            if (blockentity instanceof Container)
//            {
//                Containers.dropContents(level, blockPos, (Container) blockentity);
//                level.updateNeighbourForOutputSignal(blockPos, this);
//            }
//            super.onRemove(blockState, level, blockPos, blockState1, p_51542_);
//        }
//    }
//}
