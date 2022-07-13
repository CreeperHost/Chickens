package net.creeperhost.chickens;

import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import net.creeperhost.chickens.client.RenderChickensChicken;
import net.creeperhost.chickens.config.ConfigHandler;
import net.creeperhost.chickens.entity.EntityChickensChicken;
import net.creeperhost.chickens.init.*;
import net.creeperhost.chickens.item.ItemColoredEgg;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Chickens
{
    public static final String MOD_ID = "chickens";
    public static final Logger LOGGER = LogManager.getLogger();
    
    public static void init()
    {
        ConfigHandler.LoadConfigs(ModChickens.generateDefaultChickens());
        ModBlocks.BLOCKS.register();
        ModEntities.ENTITIES.register();
        ModBlocks.TILES_ENTITIES.register();
        ModItems.ITEMS.register();
        ModContainers.CONTAINERS.register();
        ClientLifecycleEvent.CLIENT_SETUP.register(instance ->
        {
            ModScreens.init();
            ColorHandlerRegistry.registerItemColors((itemStack, i) ->
            {
                if(itemStack.getItem() instanceof ItemColoredEgg itemColoredEgg)
                {
                    return itemColoredEgg.getColorFromItemStack(itemStack, 1);
                }
                return 0;
            }, ModItems.COLOURED_EGG);

            ModEntities.CHICKENS.forEach((chickensRegistryItem, entityTypeSupplier) -> EntityRendererRegistry.register(entityTypeSupplier, RenderChickensChicken::new));
        });

        ModEntities.CHICKENS.forEach((chickensRegistryItem, entityTypeSupplier) -> EntityAttributeRegistry.register(entityTypeSupplier, EntityChickensChicken::prepareAttributes));
    }
}
