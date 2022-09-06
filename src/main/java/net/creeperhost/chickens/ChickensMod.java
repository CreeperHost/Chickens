package net.creeperhost.chickens;

import net.creeperhost.chickens.compat.top.TheOneProbePlugin;
import net.creeperhost.chickens.config.ConfigHandler;
import net.creeperhost.chickens.init.*;
import net.creeperhost.chickens.registry.LiquidEggRegistry;
import net.creeperhost.chickens.registry.LiquidEggRegistryItem;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
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

        ConfigHandler.LoadConfigs(ModChickens.generateDefaultChickens());

        ModBlocks.BLOCKS.register(eventBus);
        ModItems.ITEMS.register(eventBus);
        ModBlocks.TILES_ENTITIES.register(eventBus);
        ModContainers.CONTAINERS.register(eventBus);
        ModEntities.ENTITIES.register(eventBus);
        registerLiquidEggs();
        eventBus.addListener(this::clientInit);
        eventBus.addListener(this::commonInit);

        if (ModList.get().isLoaded("theoneprobe")) {
            InterModComms.sendTo("theoneprobe", "getTheOneProbe", TheOneProbePlugin.GetTheOneProbe::new);
        }

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonInit(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> ConfigHandler.MAP.forEach((chickensRegistryItem, s) ->
        {
            ItemStack stack = new ItemStack(Registry.ITEM.get(new ResourceLocation(s)));
            if(stack.isEmpty())
            {
                LOGGER.error("unable to get itemstack for " + chickensRegistryItem.getRegistryName());
            }
            chickensRegistryItem.setLayItem(stack);
            chickensRegistryItem.setDropItem(stack);
        }));
    }

    private void clientInit(final FMLClientSetupEvent event)
    {
        ModScreens.init();
        event.enqueueWork(ChickensClient::init);
    }

    public void registerLiquidEggs()
    {
        LiquidEggRegistry.register(new LiquidEggRegistryItem(0, Blocks.WATER, 0x0000ff, Fluids.WATER));
        LiquidEggRegistry.register(new LiquidEggRegistryItem(1, Blocks.LAVA, 0xff0000, Fluids.LAVA));
    }
}
