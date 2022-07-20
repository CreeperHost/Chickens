package net.creeperhost.chickens;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.event.events.common.InteractionEvent;
import dev.architectury.platform.Platform;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import net.creeperhost.chickens.api.ChickenAPI;
import net.creeperhost.chickens.api.ChickenTransformationRecipe;
import net.creeperhost.chickens.api.ChickensRegistry;
import net.creeperhost.chickens.client.RenderChickensChicken;
import net.creeperhost.chickens.client.RenderIncubator;
import net.creeperhost.chickens.client.RenderRoost;
import net.creeperhost.chickens.config.ConfigHandler;
import net.creeperhost.chickens.entity.EntityChickensChicken;
import net.creeperhost.chickens.init.*;
import net.creeperhost.chickens.item.ItemChickenEgg;
import net.creeperhost.chickens.network.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.Registry;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
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
        ModRecipes.init();
        ClientLifecycleEvent.CLIENT_SETUP.register(Chickens::clientSetup);

        ModEntities.CHICKENS.forEach((chickensRegistryItem, entityTypeSupplier) -> EntityAttributeRegistry.register(entityTypeSupplier, EntityChickensChicken::prepareAttributes));

        InteractionEvent.INTERACT_ENTITY.register(Chickens::onEntityInteract);
        PacketHandler.init();

        if (Platform.isFabric())
        {
            ModEntities.CHICKENS.forEach((chickensRegistryItem, entityTypeSupplier) -> ModEntities.registerSpawn(entityTypeSupplier, chickensRegistryItem));
        }
    }

    private static void clientSetup(Minecraft minecraft)
    {
        ModScreens.init();

        ColorHandlerRegistry.registerItemColors((itemStack, i) ->
        {
            if (itemStack.getItem() instanceof ItemChickenEgg itemColoredEgg)
            {
                if(itemColoredEgg.getType(itemStack).isDye())
                {
                    if(itemColoredEgg.getType(itemStack).getLayItemHolder().getItem() instanceof DyeItem dyeItem)
                    {
                        DyeColor dyeColor = dyeItem.getDyeColor();
                        return dyeColor.getFireworkColor();
                    }
                }
                else
                {
                    return itemColoredEgg.getType(itemStack).getFgColor();
                }
            }
            return 0;
        }, ModItems.CHICKEN_EGG);

        if (Platform.isFabric())
        {
            ModEntities.CHICKENS.forEach((chickensRegistryItem, entityTypeSupplier) -> EntityRendererRegistry.register(entityTypeSupplier, RenderChickensChicken::new));
        }

        BlockEntityRendererRegistry.register(ModBlocks.ROOST_TILE.get(), context -> new RenderRoost());
        BlockEntityRendererRegistry.register(ModBlocks.INCUBATOR_TILE.get(), context -> new RenderIncubator());
        RenderTypeRegistry.register(RenderType.translucent(), ModBlocks.INCUBATOR.get());

    }

    private static EventResult onEntityInteract(Player player, Entity entity, InteractionHand interactionHand)
    {
        Level level = player.getLevel();
        if(!player.getItemInHand(interactionHand).isEmpty())
        {
            for (ChickenTransformationRecipe transformationRecipe : ChickenAPI.TRANSFORMATION_RECIPES)
            {
                if(transformationRecipe.getEntityTypeIn() == entity.getType() && player.getItemInHand(interactionHand).sameItem(transformationRecipe.getStack()))
                {
                    Entity newEntity = transformationRecipe.getEntityTypeOut().create(level);
                    if(newEntity != null)
                    {
                        newEntity.setPos(entity.position());
                        level.addFreshEntity(newEntity);
                        entity.remove(Entity.RemovalReason.DISCARDED);
                        if (!player.isCreative())
                        {
                            player.getItemInHand(interactionHand).shrink(1);
                        }
                    }
                }
            }
        }
        return EventResult.pass();
    }
}
