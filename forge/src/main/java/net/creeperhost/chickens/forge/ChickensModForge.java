package net.creeperhost.chickens.forge;

import dev.architectury.platform.Platform;
import dev.architectury.platform.forge.EventBuses;
import net.creeperhost.chickens.Chickens;
import net.creeperhost.chickens.blockentities.BlockEntityBreeder;
import net.creeperhost.chickens.init.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import java.util.function.Consumer;

@Mod(Chickens.MOD_ID)
public class ChickensModForge
{
    public ChickensModForge() {
        // Submit our event bus to let architectury register our content on the right time
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        EventBuses.registerModEventBus(Chickens.MOD_ID, eventBus);
        Chickens.init();

        eventBus.addListener(this::clientInit);
    }

    private void clientInit(final FMLClientSetupEvent event)
    {
        event.enqueueWork(() ->
        {
            ItemProperties.register(ModItems.CHICKEN_ITEM.get(), new ResourceLocation(Chickens.MOD_ID, ""), ChickenBlockEntityWithoutLevelRender.getInstance());
        });
    }
}
