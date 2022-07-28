package net.creeperhost.chickens.forge;

import net.creeperhost.chickens.Chickens;
import net.creeperhost.chickens.blockentities.BlockEntityBreeder;
import net.creeperhost.chickens.client.RenderChickensChicken;
import net.creeperhost.chickens.init.ModEntities;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;;

@Mod.EventBusSubscriber(modid = Chickens.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForgeEvents
{
    @SubscribeEvent
    public static void event(EntityRenderersEvent.RegisterRenderers event)
    {
        ModEntities.CHICKENS.forEach((chickensRegistryItem, entityTypeSupplier) ->
        {
            Chickens.LOGGER.info("Registering render for " + entityTypeSupplier.get().getDescriptionId());
            event.registerEntityRenderer(entityTypeSupplier.get(), RenderChickensChicken::new);
        });
    }
}
