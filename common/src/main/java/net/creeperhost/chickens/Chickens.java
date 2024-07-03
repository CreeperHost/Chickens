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
import net.creeperhost.chickens.entity.EggTimer;
import net.creeperhost.chickens.entity.EntityChickensChicken;
import net.creeperhost.chickens.init.*;
import net.creeperhost.chickens.network.PacketHandler;
import net.creeperhost.chickens.polylib.ItemHolder;
import net.fabricmc.api.EnvType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class Chickens
{
    public static final String MOD_ID = "chickens";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final File CONFIG_DIR = new File("config/chickens");
    public static final File CONFIG_FILE = new File(CONFIG_DIR, "chickens.json");

    public static void init()
    {
        Config.init();
        for (ChickenConfig chickenConfig : Config.INSTANCE.chickens)
        {
            ResourceLocation texture = ResourceLocation.fromNamespaceAndPath("chickens", "textures/entity/" + ResourceLocation.parse(chickenConfig.name).getPath() + ".png");
            //Special case for the vanilla chicken as we don't want to ship this texture
            if(chickenConfig.name.equalsIgnoreCase(ChickensRegistry.VANILLA_CHICKEN.toString()))
            {
                texture = ResourceLocation.withDefaultNamespace("textures/entity/chicken.png");
            }

            ChickensRegistryItem chickensRegistryItem = new ChickensRegistryItem(
                    ResourceLocation.parse(chickenConfig.name),
                    ResourceLocation.parse(chickenConfig.name).getPath(),
                    texture,
                    new ItemHolder(chickenConfig.lay_item.getType(), chickenConfig.lay_item.getId(), chickenConfig.lay_item.getNbt(), chickenConfig.lay_item.getQuantity()),
                    chickenConfig.colour,
                    chickenConfig.lay_coefficient,
                    chickenConfig.breed_speed_multiplier,
                    ChickensRegistry.getByResourceLocation(ResourceLocation.parse(chickenConfig.parent_1)),
                    ChickensRegistry.getByResourceLocation(ResourceLocation.parse(chickenConfig.parent_2))
            );
            ChickensRegistry.register(chickensRegistryItem);
        }
        ModBlocks.BLOCKS.register();
        ModEntities.ENTITIES.register();
        ModBlocks.TILES_ENTITIES.register();
        ModItems.ITEMS.register();
        ModItems.TABS.register();
        ModContainers.CONTAINERS.register();
        ModSounds.SOUNDS.register();
        ModComponentTypes.COMPONENTS.register();

        if (Platform.getEnv() == EnvType.CLIENT) {
            ClientLifecycleEvent.CLIENT_SETUP.register(ChickensClient::clientSetup);
        }

        ModEntities.CHICKENS.forEach((chickensRegistryItem, entityTypeSupplier) -> EntityAttributeRegistry.register(entityTypeSupplier, EntityChickensChicken::prepareAttributes));
        EntityAttributeRegistry.register(ModEntities.ROOSTER, EntityChickensChicken::prepareAttributes);

        InteractionEvent.INTERACT_ENTITY.register(Chickens::onEntityInteract);
        PacketHandler.init();
        EggTimer.init();

        if (Platform.isFabric())
        {
            ModEntities.CHICKENS.forEach((chickensRegistryItem, entityTypeSupplier) -> ModEntities.registerSpawn(entityTypeSupplier.get(), chickensRegistryItem));
        }

        //We need to do this late or the entities are not registered yet
        LifecycleEvent.SETUP.register(ModRecipes::init);
    }



    private static EventResult onEntityInteract(Player player, Entity entity, InteractionHand interactionHand)
    {
        Level level = player.level();
        if(!player.getItemInHand(interactionHand).isEmpty())
        {
            for (ChickenTransformationRecipe transformationRecipe : ChickenAPI.TRANSFORMATION_RECIPES)
            {
                if(transformationRecipe.getEntityTypeIn() == entity.getType() && ItemStack.isSameItem(player.getItemInHand(interactionHand), transformationRecipe.getStack()))
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
