package net.creeperhost.chickens.neoforge;

import dev.architectury.platform.Platform;
import net.creeperhost.chickens.Chickens;
import net.creeperhost.chickens.init.ModEntities;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod (Chickens.MOD_ID)
public class ChickensModNeoForge
{
    public ChickensModNeoForge(IEventBus iEventBus)
    {
        Chickens.init();

        if(Platform.getEnv().isClient())
        {
            ClientInit.init(iEventBus);
        }
    }
}
