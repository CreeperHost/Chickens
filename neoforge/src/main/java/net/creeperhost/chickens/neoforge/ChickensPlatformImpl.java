package net.creeperhost.chickens.neoforge;

import net.creeperhost.chickens.ChickensPlatform;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.common.Tags;

import java.nio.file.Path;

public class ChickensPlatformImpl
{
    /**
     * This is our actual method to {@link ChickensPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }

//    public static Item createNewChickenItem(Item.Properties properties)
//    {
//        return new ItemChickenNeoForge(properties);
//    }

    public static int getBiomeTemperature(Holder<Biome> biome) {
        if (biome.is(Tags.Biomes.IS_SNOWY)) {
            return  -10;
        } else if (biome.is(Tags.Biomes.IS_COLD)) {
            return  10;
        }else if (biome.is(Tags.Biomes.IS_HOT)) {
            return  35;
        }
        return 20;
    }

    public static int getBiomeHumidity(Holder<Biome> biome) {
        if (biome.is(Tags.Biomes.IS_WET)) {
            return 50;
        }else if (biome.is(Tags.Biomes.IS_DRY)) {
            return  10;
        }
        return 30;
    }
}
