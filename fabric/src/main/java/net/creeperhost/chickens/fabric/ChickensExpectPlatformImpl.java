package net.creeperhost.chickens.fabric;

import net.creeperhost.chickens.ChickensExpectPlatform;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class ChickensExpectPlatformImpl
{
    /**
     * This is our actual method to {@link ChickensExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }
}
