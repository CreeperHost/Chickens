package net.creeperhost.chickens;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.event.events.common.InteractionEvent;
import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.platform.Platform;
import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import net.creeperhost.chickens.api.ChickenAPI;
import net.creeperhost.chickens.api.ChickenTransformationRecipe;
import net.creeperhost.chickens.api.ChickensRegistry;
import net.creeperhost.chickens.api.ChickensRegistryItem;
import net.creeperhost.chickens.config.ChickenConfig;
import net.creeperhost.chickens.config.Config;
import net.creeperhost.chickens.entity.EntityChickensChicken;
import net.creeperhost.chickens.init.*;
import net.creeperhost.chickens.network.PacketHandler;
import net.creeperhost.chickens.polylib.ItemHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class Chickens
{
    public static final String MOD_ID = "chickens";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final File CHICKENS_CONFIG_DIR = new File("config/chickens");
    public static final File CHICKENS_CONFIG_JSON = new File(CHICKENS_CONFIG_DIR, "chickens.json");

    public static void init()
    {
        Config.init(CHICKENS_CONFIG_JSON);
        for (ChickenConfig chickenConfig : Config.INSTANCE.chickens)
        {
            ResourceLocation texture = new ResourceLocation("chickens", "textures/entity/" + new ResourceLocation(chickenConfig.name).getPath() + ".png");
            //Special case for the vanilla chicken as we don't want to ship this texture
            if(chickenConfig.name.equalsIgnoreCase(ChickensRegistry.VANILLA_CHICKEN.toString()))
            {
                texture = new ResourceLocation("minecraft", "textures/entity/chicken.png");
            }

            ChickensRegistryItem chickensRegistryItem = new ChickensRegistryItem(
                    new ResourceLocation(chickenConfig.name),
                    new ResourceLocation(chickenConfig.name).getPath(),
                    texture,
                    new ItemHolder(chickenConfig.lay_item.getType(), chickenConfig.lay_item.getId(), chickenConfig.lay_item.getNbt()),
                    chickenConfig.colour,
                    ChickensRegistry.getByResourceLocation(new ResourceLocation(chickenConfig.parent_1)),
                    ChickensRegistry.getByResourceLocation(new ResourceLocation(chickenConfig.parent_2))
            );
            ChickensRegistry.register(chickensRegistryItem);
        }
        ModBlocks.BLOCKS.register();
        ModEntities.ENTITIES.register();
        ModBlocks.TILES_ENTITIES.register();
        ModItems.ITEMS.register();
        ModContainers.CONTAINERS.register();
        ModSounds.SOUNDS.register();
        ClientLifecycleEvent.CLIENT_SETUP.register(ChickensClient::clientSetup);

        ModEntities.CHICKENS.forEach((chickensRegistryItem, entityTypeSupplier) -> EntityAttributeRegistry.register(entityTypeSupplier, EntityChickensChicken::prepareAttributes));

        InteractionEvent.INTERACT_ENTITY.register(Chickens::onEntityInteract);
        PacketHandler.init();

        if (Platform.isFabric())
        {
            ModEntities.CHICKENS.forEach((chickensRegistryItem, entityTypeSupplier) -> ModEntities.registerSpawn(entityTypeSupplier.get(), chickensRegistryItem));
        }

        //We need to do this late or the entities are not registered yet
        LifecycleEvent.SETUP.register(ModRecipes::init);
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
