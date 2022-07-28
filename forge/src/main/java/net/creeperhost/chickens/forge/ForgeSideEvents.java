package net.creeperhost.chickens.forge;

import net.creeperhost.chickens.Chickens;
import net.creeperhost.chickens.blockentities.BlockEntityBreeder;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

@Mod.EventBusSubscriber(modid = Chickens.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeSideEvents
{
    @SubscribeEvent
    public static void injectEvent(AttachCapabilitiesEvent<BlockEntity> event)
    {
        BlockEntity blockEntity = event.getObject();
        if(blockEntity instanceof BlockEntityBreeder breeder)
        {
            BasicCapabilityProvider<IItemHandler> capabilityProvider = new BasicCapabilityProvider<>(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, new SidedInvWrapper(breeder, Direction.UP));
            event.addCapability(new ResourceLocation(Chickens.MOD_ID, "inventorywrapper"), capabilityProvider);
            event.addListener(breeder::invalidateCaps);
        }
    }
}
