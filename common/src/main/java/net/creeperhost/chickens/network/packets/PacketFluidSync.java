//package net.creeperhost.chickens.network.packets;
//
//import dev.architectury.fluid.FluidStack;
//import dev.architectury.hooks.fluid.FluidStackHooks;
//import dev.architectury.networking.NetworkManager;
//import net.creeperhost.chickens.blockentities.BlockEntityEggCracker;
//import net.creeperhost.chickens.blockentities.BlockEntityIncubator;
//import net.minecraft.core.BlockPos;
//import net.minecraft.network.FriendlyByteBuf;
//import net.minecraft.world.level.Level;
//
//import java.util.function.Supplier;
//
//public class PacketFluidSync
//{
//    BlockPos blockPos;
//    FluidStack fluidStack;
//
//    public PacketFluidSync(BlockPos blockPos, FluidStack fluidStack)
//    {
//        this.blockPos = blockPos;
//        this.fluidStack = fluidStack;
//    }
//
//    public PacketFluidSync(FriendlyByteBuf friendlyByteBuf)
//    {
//        this.blockPos = friendlyByteBuf.readBlockPos();
//        this.fluidStack = FluidStackHooks.read(friendlyByteBuf);
//    }
//
//    public void write(FriendlyByteBuf friendlyByteBuf)
//    {
//        friendlyByteBuf.writeBlockPos(blockPos);
//        FluidStackHooks.write(fluidStack, friendlyByteBuf);
//    }
//
//    public void handle(Supplier<NetworkManager.PacketContext> context)
//    {
//        context.get().queue(() ->
//        {
//            Level level = context.get().getPlayer().getLevel();
//            if(level == null) return;
//
//            if(level.getBlockEntity(blockPos) != null)
//            {
//                if(level.getBlockEntity(blockPos) instanceof BlockEntityIncubator blockEntityIncubator)
//                    blockEntityIncubator.temperature.setFluidStack(fluidStack);
//
//                if(level.getBlockEntity(blockPos) instanceof BlockEntityEggCracker blockEntityEggCracker)
//                    blockEntityEggCracker.fluidTank.setFluidStack(fluidStack);
//            }
//        });
//    }
//}
