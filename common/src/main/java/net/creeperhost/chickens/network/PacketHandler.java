package net.creeperhost.chickens.network;

import dev.architectury.networking.NetworkChannel;
import net.creeperhost.chickens.Chickens;
import net.minecraft.resources.ResourceLocation;

public class PacketHandler
{
    public static final NetworkChannel HANDLER = NetworkChannel.create(new ResourceLocation(Chickens.MOD_ID, "main_channel"));

    public static void init()
    {
        HANDLER.register(PacketIncubator.class, PacketIncubator::write, PacketIncubator::new, PacketIncubator::handle);
        HANDLER.register(PacketFluidSync.class, PacketFluidSync::write, PacketFluidSync::new, PacketFluidSync::handle);
    }
}
