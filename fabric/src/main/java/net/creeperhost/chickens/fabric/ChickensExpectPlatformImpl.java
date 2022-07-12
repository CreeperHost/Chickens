package net.creeperhost.chickens.fabric;

import net.creeperhost.chickens.ChickensExpectPlatform;
import net.creeperhost.chickens.item.ItemChicken;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.item.Item;

import java.nio.file.Path;

public class ChickensExpectPlatformImpl
{
    /**
     * This is our actual method to {@link ChickensExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }

    public static Class getChickenItemClass()
    {
        return ItemChicken.class;
    }

    public static Item createNewChickenItem(Item.Properties properties)
    {
        return new ItemChicken(properties);
    }
}
