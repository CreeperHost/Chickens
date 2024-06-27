package net.creeperhost.chickens.network;

import dev.architectury.networking.NetworkChannel;
import net.creeperhost.chickens.Chickens;
import net.minecraft.resources.ResourceLocation;

public class PacketHandler {

    @SuppressWarnings("removal")
    public static final NetworkChannel HANDLER = NetworkChannel.create(ResourceLocation.fromNamespaceAndPath(Chickens.MOD_ID, "main_channel"));

    public static void init() {
    }
}
