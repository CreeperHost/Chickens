package net.creeperhost.chickens;

import net.creeperhost.chickens.client.render.RenderChickenItem;
import net.creeperhost.chickens.client.render.RenderRoost;
import net.creeperhost.chickens.compat.top.TheOneProbePlugin;
import net.creeperhost.chickens.config.ConfigHandler;
import net.creeperhost.chickens.handler.ItemColorHandler;
import net.creeperhost.chickens.init.*;
import net.creeperhost.chickens.registry.LiquidEggRegistry;
import net.creeperhost.chickens.registry.LiquidEggRegistryItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ChickensMod.MODID)
public class ChickensMod
{
    public static final String MODID = "chickens";
    public static final Logger LOGGER = LogManager.getLogger();

    public ChickensMod()
    {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModBlocks.BLOCKS.register(eventBus);
        ModItems.ITEMS.register(eventBus);
        ModBlocks.TILES_ENTITIES.register(eventBus);
        ModContainers.CONTAINERS.register(eventBus);
        ConfigHandler.LoadConfigs(ModChickens.generateDefaultChickens());
        ModEntities.ENTITIES.register(eventBus);
        registerLiquidEggs();
        eventBus.addListener(this::clientInit);

        if (ModList.get().isLoaded("theoneprobe")) {
            InterModComms.sendTo("theoneprobe", "getTheOneProbe", TheOneProbePlugin.GetTheOneProbe::new);
        }

        MinecraftForge.EVENT_BUS.register(this);
    }

    public void clientInit(FMLClientSetupEvent event)
    {
        ModScreens.init();
        Minecraft.getInstance().getItemColors().register(new ItemColorHandler(), ModItems.SPAWN_EGG.get(), ModItems.COLOURED_EGG.get(), ModItems.LIQUID_EGG.get());
        ItemProperties.register(ModItems.CHICKEN_ITEM.get(), new ResourceLocation(MODID, ""), RenderChickenItem.getInstance());
        BlockEntityRenderers.register(ModBlocks.ROOST_TILE.get(), p_173571_ -> new RenderRoost());
    }

    public void registerLiquidEggs()
    {
        LiquidEggRegistry.register(new LiquidEggRegistryItem(0, Blocks.WATER, 0x0000ff, Fluids.WATER));
        LiquidEggRegistry.register(new LiquidEggRegistryItem(1, Blocks.LAVA, 0xff0000, Fluids.LAVA));
    }
}
