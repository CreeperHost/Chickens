package net.creeperhost.chickens;

import net.creeperhost.chickens.config.ConfigHandler;
import net.creeperhost.chickens.init.ModBlocks;
import net.creeperhost.chickens.init.ModChickens;
import net.creeperhost.chickens.init.ModEntities;
import net.creeperhost.chickens.init.ModItems;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Chickens
{
    public static final String MOD_ID = "chickens";
    public static final Logger LOGGER = LogManager.getLogger();
    
    public static void init()
    {
        ConfigHandler.LoadConfigs(ModChickens.generateDefaultChickens());
        ModBlocks.BLOCKS.register();
        ModEntities.ENTITIES.register();
        ModBlocks.TILES_ENTITIES.register();
        ModItems.ITEMS.register();
    }
}
