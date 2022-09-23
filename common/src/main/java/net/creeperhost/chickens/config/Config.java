package net.creeperhost.chickens.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.creeperhost.chickens.Chickens;
import net.creeperhost.chickens.Constants;
import net.creeperhost.chickens.api.ChickensRegistryItem;
import net.creeperhost.chickens.init.ModChickens;
import net.creeperhost.chickens.polylib.ItemHolderData;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class Config
{
    public static Config INSTANCE;

    public List<ChickenConfig> chickens = new ArrayList<>();

    public Config()
    {
        for (ChickensRegistryItem chickensRegistryItem : ModChickens.generateDefaultChickens())
        {
            chickens.add(ChickenConfig.of(chickensRegistryItem));
        }
    }

    public static void init(File file)
    {
        try
        {
            if(!Constants.CHICKENS_CONFIG_DIR.exists())
            {
                Constants.CHICKENS_CONFIG_DIR.mkdir();
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
}
