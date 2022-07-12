package net.creeperhost.chickens.forge;

import net.creeperhost.chickens.ChickensExpectPlatform;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public class ChickensExpectPlatformImpl
{
    /**
     * This is our actual method to {@link ChickensExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }

    public static Item createNewChickenItem(Item.Properties properties)
    {
        return new ItemChickenForge(properties);
    }
}
