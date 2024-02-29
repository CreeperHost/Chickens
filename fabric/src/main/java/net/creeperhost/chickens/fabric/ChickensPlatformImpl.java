package net.creeperhost.chickens.fabric;

import net.creeperhost.chickens.ChickensPlatform;
import net.creeperhost.chickens.item.ItemChicken;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;

import java.nio.file.Path;

public class ChickensPlatformImpl
{
    /**
     * This is our actual method to {@link ChickensPlatform#getConfigDirectory()}.
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

    public static int getBiomeTemperature(Holder<Biome> biome) {
        if (biome.is(ConventionalBiomeTags.SNOWY) || biome.is(ConventionalBiomeTags.ICY)) {
            return  -10;
        } else if (biome.is(ConventionalBiomeTags.CLIMATE_COLD)) {
            return  10;
        }else if (biome.is(ConventionalBiomeTags.CLIMATE_HOT)) {
            return 35;
        }
        return 20;
    }

    public static int getBiomeHumidity(Holder<Biome> biome) {
        if (biome.is(ConventionalBiomeTags.CLIMATE_WET)) {
            return 50;
        }else if (biome.is(ConventionalBiomeTags.CLIMATE_DRY)) {
            return  10;
        }
        return 30;
    }
}
