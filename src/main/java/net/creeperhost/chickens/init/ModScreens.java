package net.creeperhost.chickens.init;

import net.creeperhost.chickens.client.screens.GuiBreeder;
import net.creeperhost.chickens.client.screens.GuiHenhouse;
import net.creeperhost.chickens.client.screens.GuiRoost;
import net.minecraft.client.gui.screens.MenuScreens;

public class ModScreens
{
    public static void init()
    {
        MenuScreens.register(ModContainers.HENHOUSE_CONTAINER.get(), GuiHenhouse::new);
        MenuScreens.register(ModContainers.BREEDER_CONTAINER.get(), GuiBreeder::new);
        MenuScreens.register(ModContainers.ROOST_CONTAINER.get(), GuiRoost::new);
    }
}
