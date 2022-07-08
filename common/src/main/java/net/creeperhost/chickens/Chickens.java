package net.creeperhost.chickens;

import net.creeperhost.chickens.init.ModBlocks;
import net.creeperhost.chickens.init.ModItems;

public class Chickens
{
    public static final String MOD_ID = "chickens";
    
    public static void init()
    {
        ModBlocks.BLOCKS.register();
        ModBlocks.TILES_ENTITIES.register();
        ModItems.ITEMS.register();
    }
}
