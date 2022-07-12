package net.creeperhost.chickens;

import dev.architectury.platform.Platform;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import net.creeperhost.chickens.client.RenderChickensChicken;
import net.creeperhost.chickens.config.ConfigHandler;
import net.creeperhost.chickens.entity.EntityChickensChicken;
import net.creeperhost.chickens.init.*;
import net.fabricmc.api.EnvType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
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

        ModEntities.CHICKENS.forEach((chickensRegistryItem, entityTypeSupplier) ->
        {
            EntityAttributeRegistry.register(entityTypeSupplier, EntityChickensChicken::prepareAttributes);

            if(Platform.getEnv() == EnvType.CLIENT)
            {
                EntityRendererRegistry.register(entityTypeSupplier, RenderChickensChicken::new);
            }
        });

        if(Platform.getEnv() == EnvType.CLIENT)
        {
            ModScreens.init();
        }
    }
}
