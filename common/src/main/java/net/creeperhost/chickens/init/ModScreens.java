package net.creeperhost.chickens.init;

import net.creeperhost.chickens.client.screen.ScreenBreeder;
import net.minecraft.client.gui.screens.MenuScreens;

public class ModScreens
{
    public static void init()
    {
        MenuScreens.register(ModContainers.BREEDER_CONTAINER.get(), ScreenBreeder::new);
    }
}
