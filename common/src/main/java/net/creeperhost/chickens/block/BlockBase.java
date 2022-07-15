package net.creeperhost.chickens.block;

import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public abstract class BlockBase extends BaseEntityBlock
{
    public BlockBase(Properties properties)
    {
        super(properties);
    }

    @Override
    public RenderShape getRenderShape(@NotNull BlockState blockState)
    {
        return RenderShape.MODEL;
    }
}
