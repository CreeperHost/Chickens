package net.creeperhost.chickens.network.packets;

import dev.architectury.networking.NetworkManager;
import net.creeperhost.chickens.blockentities.BlockEntityIncubator;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class PacketIncubator
{
    BlockPos blockPos;
    boolean increase;

    public PacketIncubator(BlockPos blockPos, boolean increase)
    {
        this.blockPos = blockPos;
        this.increase = increase;
    }

    public PacketIncubator(FriendlyByteBuf friendlyByteBuf)
    {
        this.blockPos = friendlyByteBuf.readBlockPos();
        this.increase = friendlyByteBuf.readBoolean();
    }

    public void write(FriendlyByteBuf friendlyByteBuf)
    {
        friendlyByteBuf.writeBlockPos(blockPos);
        friendlyByteBuf.writeBoolean(increase);
    }

    public void handle(Supplier<NetworkManager.PacketContext> context)
    {
        context.get().queue(() ->
        {
            Level level = context.get().getPlayer().getLevel();
            if(level == null) return;

            if(level.getBlockEntity(blockPos) != null && level.getBlockEntity(blockPos) instanceof BlockEntityIncubator blockEntityIncubator)
            {
                int value = blockEntityIncubator.getLightLevel();
                if(increase)
                {
                    value++;
                }
                else
                {
                    value--;
                }
                if(value > 15) value = 15;
                if(value < 0) value = 0;
                blockEntityIncubator.setLightLevel(value);
            }
        });
    }
}
