package net.creeperhost.chickens.forge;

import dev.architectury.platform.forge.EventBuses;
import net.creeperhost.chickens.Chickens;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Chickens.MOD_ID)
public class ChickensModForge
{
    public ChickensModForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(Chickens.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        Chickens.init();
    }
}
