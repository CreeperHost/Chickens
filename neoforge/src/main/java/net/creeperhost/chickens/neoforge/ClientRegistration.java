package net.creeperhost.chickens.neoforge;

import net.creeperhost.chickens.Chickens;
import net.creeperhost.chickens.client.gui.BreederGui;
import net.creeperhost.chickens.client.gui.EggCrackerGui;
import net.creeperhost.chickens.client.gui.IncubatorGui;
import net.creeperhost.chickens.client.gui.OvoscopeGui;
import net.creeperhost.chickens.init.ModContainers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = Chickens.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ClientRegistration {

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event)
    {
        event.register(ModContainers.BREEDER_CONTAINER.get(), BreederGui.Screen::new);
        event.register(ModContainers.INCUBATOR.get(), IncubatorGui.Screen::new);
        event.register(ModContainers.EGG_CRACKER.get(), EggCrackerGui.Screen::new);
        event.register(ModContainers.OVOSCOPE.get(), OvoscopeGui.Screen::new);
    }
}
