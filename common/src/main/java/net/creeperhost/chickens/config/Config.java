package net.creeperhost.chickens.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.architectury.platform.Platform;
import net.creeperhost.chickens.Chickens;
import net.creeperhost.chickens.api.ChickensRegistryItem;
import net.creeperhost.chickens.init.ModChickens;
import net.minecraft.tags.BiomeTags;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Config {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    public static Config INSTANCE = new Config();

    public boolean enableEnergy = true;
    public int crackerEnergyRate = 20;
    public int ovoscopeEnergyRate = 10;
    public double incubatorEnergyMultiplier = 1;

    public int breederMaxProcessTime = 9000;
    public int crackerProcessTime = 100;
    public int ovoscopeProcessTime = 100;

    public int eggItemMaxTimeOnGround = 15;

    public double onLaidViabilityChange = 0.97;
    public double incubateSuccessRate = 0.97;

    public double breederFoodConsumptionChance = 0.50;

    public List<ChickenConfig> chickens = new ArrayList<>();
    public List<FabricSpawn> fabricSpawns = new ArrayList<>();

    public Config() {}

    private void generateDefaults() {
        chickens.clear();
        fabricSpawns.clear();
        for (ChickensRegistryItem chickensRegistryItem : ModChickens.generateDefaultChickens()) {
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

    public static void init() {
        if (!Chickens.CONFIG_FILE.exists()) {
            INSTANCE.generateDefaults();
            saveConfig();
        }
        loadConfig();
    }

    public static void loadConfig() {
        try (FileReader reader = new FileReader(Chickens.CONFIG_FILE)) {
            INSTANCE = GSON.fromJson(reader, Config.class);
        } catch (IOException e) {
            Chickens.LOGGER.error("Failed to load config file, Will restore default config", e);
            INSTANCE = new Config();
            INSTANCE.generateDefaults();
            saveConfig();
        }
    }

    public static void saveConfig() {
        if (!Chickens.CONFIG_FILE.getParentFile().exists() && !Chickens.CONFIG_FILE.getParentFile().mkdirs()) {
            Chickens.LOGGER.error("Failed to create chickens config directory! {}", Chickens.CONFIG_FILE.getParentFile());
        }

        try (FileWriter writer = new FileWriter(Chickens.CONFIG_FILE)) {
            GSON.toJson(INSTANCE, writer);
        } catch (IOException e) {
            Chickens.LOGGER.error("Failed to save config file!", e);
        }
    }

    public record FabricSpawn(List<String> biomeTags, String type, int weight, int minCluster, int maxCluster) {
    }
}
