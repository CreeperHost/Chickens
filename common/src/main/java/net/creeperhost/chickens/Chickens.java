package net.creeperhost.chickens;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.event.events.common.EntityEvent;
import dev.architectury.event.events.common.InteractionEvent;
import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.platform.Platform;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import net.creeperhost.chickens.api.ChickensRegistry;
import net.creeperhost.chickens.api.ChickensRegistryItem;
import net.creeperhost.chickens.client.RenderChickensChicken;
import net.creeperhost.chickens.client.RenderRoost;
import net.creeperhost.chickens.config.ConfigHandler;
import net.creeperhost.chickens.entity.EntityChickensChicken;
import net.creeperhost.chickens.init.*;
import net.creeperhost.chickens.item.ItemColoredEgg;
import net.creeperhost.polylib.entities.SpawnRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.Registry;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.function.Supplier;

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
        ClientLifecycleEvent.CLIENT_SETUP.register(Chickens::clientSetup);

        ModEntities.CHICKENS.forEach((chickensRegistryItem, entityTypeSupplier) -> EntityAttributeRegistry.register(entityTypeSupplier, EntityChickensChicken::prepareAttributes));

        InteractionEvent.INTERACT_ENTITY.register(Chickens::onEntityInteract);


//        LifecycleEvent.SERVER_STARTED.register(instance -> ModEntities.CHICKENS.forEach((chickensRegistryItem, entityTypeSupplier) -> ModEntities.registerSpawn(entityTypeSupplier, chickensRegistryItem)));
    }

    private static void clientSetup(Minecraft minecraft)
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

        if(Platform.isFabric())
        {
            ModEntities.CHICKENS.forEach((chickensRegistryItem, entityTypeSupplier) -> EntityRendererRegistry.register(entityTypeSupplier, RenderChickensChicken::new));
            EntityRendererRegistry.register(ModEntities.EGG, ThrownItemRenderer::new);
        }

        BlockEntityRendererRegistry.register(ModBlocks.ROOST_TILE.get(), context -> new RenderRoost());
    }

    private static EventResult onEntityInteract(Player player, Entity entity, InteractionHand interactionHand)
    {
        Level level = player.getLevel();
        if(!player.getItemInHand(interactionHand).isEmpty() && player.getItemInHand(interactionHand).getItem() == Items.BOOK)
        {
            if(entity.getType() == EntityType.CHICKEN)
            {
                EntityType<?> entityType = Registry.ENTITY_TYPE.get(ChickensRegistry.SMART_CHICKEN_ID);
                EntityChickensChicken chicken = (EntityChickensChicken) entityType.create(level);
                if(chicken != null)
                {
                    chicken.setPos(entity.position());
                    level.addFreshEntity(chicken);
                    entity.remove(Entity.RemovalReason.DISCARDED);
                    if (!player.isCreative())
                    {
                        player.getItemInHand(interactionHand).shrink(1);
                    }
                }
            }
        }
        return EventResult.pass();
    }
}
