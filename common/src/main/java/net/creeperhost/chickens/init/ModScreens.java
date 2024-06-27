package net.creeperhost.chickens.init;

import dev.architectury.registry.menu.MenuRegistry;
import net.creeperhost.chickens.client.gui.IncubatorGui;
import net.creeperhost.chickens.client.gui.BreederGui;
import net.creeperhost.chickens.client.gui.EggCrackerGui;
import net.creeperhost.chickens.client.gui.OvoscopeGui;

public class ModScreens
{
    public static void init()
    {
        MenuRegistry.registerScreenFactory(ModContainers.BREEDER_CONTAINER.get(), BreederGui.Screen::new);
        MenuRegistry.registerScreenFactory(ModContainers.INCUBATOR.get(), IncubatorGui.Screen::new);
        MenuRegistry.registerScreenFactory(ModContainers.EGG_CRACKER.get(), EggCrackerGui.Screen::new);
        MenuRegistry.registerScreenFactory(ModContainers.OVOSCOPE.get(), OvoscopeGui.Screen::new);
    }
}
