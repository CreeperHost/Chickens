package net.creeperhost.chickens.init;

import net.creeperhost.chickens.client.screen.IncubatorGui;
import net.creeperhost.chickens.client.screen.ScreenBreeder;
import net.creeperhost.chickens.client.screen.ScreenEggCracker;
import net.creeperhost.chickens.client.screen.ScreenOvoscope;
import net.minecraft.client.gui.screens.MenuScreens;

public class ModScreens
{
    public static void init()
    {
        MenuScreens.register(ModContainers.BREEDER_CONTAINER.get(), ScreenBreeder::new);
        MenuScreens.register(ModContainers.INCUBATOR.get(), IncubatorGui.Screen::new);
        MenuScreens.register(ModContainers.EGG_CRACKER.get(), ScreenEggCracker::new);
        MenuScreens.register(ModContainers.OVOSCOPE.get(), ScreenOvoscope::new);
    }
}
