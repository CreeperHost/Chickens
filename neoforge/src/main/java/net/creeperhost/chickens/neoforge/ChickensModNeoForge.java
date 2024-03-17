package net.creeperhost.chickens.neoforge;

import net.creeperhost.chickens.Chickens;
import net.creeperhost.chickens.init.ModEntities;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.DistExecutor;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod (Chickens.MOD_ID)
public class ChickensModNeoForge
{
    public ChickensModNeoForge()
    {
        // Submit our event bus to let architectury register our content on the right time
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        Chickens.init();

        eventBus.addListener(this::commonLoaded);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientInit.init(eventBus));
    }

    private void commonLoaded(final FMLCommonSetupEvent event)
    {
        ModEntities.CHICKENS.forEach((chickensRegistryItem, entityTypeSupplier) -> ModEntities.registerSpawn(entityTypeSupplier.get(), chickensRegistryItem));
    }
}
