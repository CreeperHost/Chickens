package net.creeperhost.chickens;

import dev.architectury.platform.Platform;
import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import net.creeperhost.chickens.api.ChickensRegistryItem;
import net.creeperhost.chickens.client.RenderChickensChicken;
import net.creeperhost.chickens.client.RenderIncubator;
import net.creeperhost.chickens.client.RenderRooster;
import net.creeperhost.chickens.client.RoosterModel;
import net.creeperhost.chickens.init.ModBlocks;
import net.creeperhost.chickens.init.ModEntities;
import net.creeperhost.chickens.init.ModItems;
import net.creeperhost.chickens.init.ModScreens;
import net.creeperhost.chickens.item.ItemChickenEgg;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.FastColor;

public class ChickensClient
{
    public static void clientSetup(Minecraft minecraft)
    {
        ModScreens.init();

        ColorHandlerRegistry.registerItemColors((itemStack, i) ->
        {
            if (itemStack.getItem() instanceof ItemChickenEgg itemColoredEgg)
            {
                ChickensRegistryItem item = itemColoredEgg.getType(itemStack);
                if(item != null && i > 0){
                    return FastColor.ABGR32.opaque(item.getBgColor());
                }
            }
            return 0xFFFFFFFF;
        }, ModItems.CHICKEN_EGG);

        if (Platform.isFabric())
        {
            ModEntities.CHICKENS.forEach((chickensRegistryItem, entityTypeSupplier) -> EntityRendererRegistry.register(entityTypeSupplier, RenderChickensChicken::new));
            EntityModelLayerRegistry.register(RoosterModel.LAYER_LOCATION, RoosterModel::createBodyLayer);
            EntityRendererRegistry.register(ModEntities.ROOSTER, RenderRooster::new);
        }

        BlockEntityRendererRegistry.register(ModBlocks.INCUBATOR_TILE.get(), context -> new RenderIncubator());
        RenderTypeRegistry.register(RenderType.translucent(), ModBlocks.INCUBATOR.get());
        RenderTypeRegistry.register(RenderType.translucent(), ModBlocks.OVOSCOPE.get());
    }
}
