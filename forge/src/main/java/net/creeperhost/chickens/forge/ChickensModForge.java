package net.creeperhost.chickens.forge;

import dev.architectury.platform.forge.EventBuses;
import dev.architectury.registry.level.biome.BiomeModifications;
import net.creeperhost.chickens.Chickens;
import net.creeperhost.chickens.init.ModEntities;
import net.creeperhost.chickens.init.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Chickens.MOD_ID)
public class ChickensModForge
{
    public ChickensModForge()
    {
        // Submit our event bus to let architectury register our content on the right time
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        EventBuses.registerModEventBus(Chickens.MOD_ID, eventBus);
        Chickens.init();

        eventBus.addListener(this::commonLoaded);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientInit.init(eventBus));
    }

    private void commonLoaded(final FMLCommonSetupEvent event)
    {
        ModEntities.CHICKENS.forEach((chickensRegistryItem, entityTypeSupplier) -> ModEntities.registerSpawn(entityTypeSupplier.get(), chickensRegistryItem));
    }
}
