package net.creeperhost.chickens.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.architectury.platform.Platform;
import net.creeperhost.chickens.Chickens;
import net.creeperhost.chickens.api.ChickensRegistryItem;
import net.creeperhost.chickens.blockentities.BreederBlockEntity;
import net.creeperhost.chickens.blockentities.EggCrackerBlockEntity;
import net.creeperhost.chickens.blockentities.IncubatorBlockEntity;
import net.creeperhost.chickens.init.ModChickens;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.MobCategory;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Config
{
    public static Config INSTANCE;

    public boolean enableEnergy = true;
    public int crackerEnergyRate = 20;
    public int ovoscopeEnergyRate = 10;
    public double incubatorEnergyMultiplier = 1;

    public int breederMaxProcessTime = 1000;
    public int crackerProcessTime = 100;
    public int ovoscopeProcessTime = 100;

    public double onLaidViabilityChange = 0.97;
    public double incubateSuccessRate = 0.97;

    public List<ChickenConfig> chickens = new ArrayList<>();
    public List<FabricSpawn> fabricSpawns = new ArrayList<>();


    public Config()
    {
        for (ChickensRegistryItem chickensRegistryItem : ModChickens.generateDefaultChickens())
        {
            chickens.add(ChickenConfig.of(chickensRegistryItem));
        }
        if (Platform.isFabric()) {
            fabricSpawns.add(new FabricSpawn(Collections.singletonList(BiomeTags.IS_OVERWORLD.location().toString()), "chickens:flint_chicken", 10, 2, 4));
            fabricSpawns.add(new FabricSpawn(Collections.singletonList(BiomeTags.IS_OVERWORLD.location().toString()), "chickens:log_chicken", 10, 2, 4));
            fabricSpawns.add(new FabricSpawn(Collections.singletonList(BiomeTags.IS_OVERWORLD.location().toString()), "chickens:sand_chicken", 10, 2, 4));
            fabricSpawns.add(new FabricSpawn(Collections.singletonList(BiomeTags.IS_NETHER.location().toString()), "chickens:quartz_chicken", 60, 12, 12));
            fabricSpawns.add(new FabricSpawn(Collections.singletonList(BiomeTags.IS_NETHER.location().toString()), "chickens:soulsand_chicken", 60, 12, 12));
        }
    }

    public static void init(File file)
    {
        try
        {
            if(!Chickens.CHICKENS_CONFIG_DIR.exists())
            {
                Chickens.CHICKENS_CONFIG_DIR.mkdir();
                Chickens.LOGGER.info("Chickens config folder does not exist, creating...");
            }
            if (!file.exists())
            {
                Config.INSTANCE = new Config();
                FileWriter tileWriter = new FileWriter(file);
                tileWriter.write(saveConfig());
                tileWriter.close();
            }
            else
            {
                Config.loadFromFile(file);
            }
        } catch (Exception ignored) {}
    }

    public static void loadFromFile(File file)
    {
        Gson gson = new Gson();
        try
        {
            FileReader fileReader = new FileReader(file);
            INSTANCE = gson.fromJson(fileReader, Config.class);
        } catch (Exception ignored) {}
    }

    public static String saveConfig()
    {
        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        return gson.toJson(INSTANCE);
    }


    public static void saveConfigToFile(File file)
    {
        try (FileOutputStream configOut = new FileOutputStream(file))
        {
            IOUtils.write(Config.saveConfig(), configOut, Charset.defaultCharset());
        }
        catch (Throwable ignored) {}
    }

    public record FabricSpawn(List<String> biomeTags, String type, int weight, int minCluster, int maxCluster) {}
}
