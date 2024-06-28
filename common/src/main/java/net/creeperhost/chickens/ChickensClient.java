package net.creeperhost.chickens;

import dev.architectury.platform.Platform;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import net.creeperhost.chickens.api.ChickensRegistryItem;
import net.creeperhost.chickens.client.RenderChickensChicken;
import net.creeperhost.chickens.client.RenderIncubator;
import net.creeperhost.chickens.init.ModBlocks;
import net.creeperhost.chickens.init.ModEntities;
import net.creeperhost.chickens.init.ModItems;
import net.creeperhost.chickens.init.ModScreens;
import net.creeperhost.chickens.item.ItemChickenEgg;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;

public class ChickensClient
{
    public static void clientSetup(Minecraft minecraft)
    {
        ModScreens.init();

        //TODO this seems to be broken atm
//        ColorHandlerRegistry.registerItemColors((itemStack, i) ->
//        {
//            if (itemStack.getItem() instanceof ItemChickenEgg itemColoredEgg)
//            {
//                ChickensRegistryItem item = itemColoredEgg.getType(itemStack);
//                return item == null ? 0xFFFFFFFF : item.getBgColor();
//            }
//            return i;
//        }, ModItems.CHICKEN_EGG);

        if (Platform.isFabric())
        {
            ModEntities.CHICKENS.forEach((chickensRegistryItem, entityTypeSupplier) -> EntityRendererRegistry.register(entityTypeSupplier, RenderChickensChicken::new));
        }

        BlockEntityRendererRegistry.register(ModBlocks.INCUBATOR_TILE.get(), context -> new RenderIncubator());
        RenderTypeRegistry.register(RenderType.translucent(), ModBlocks.INCUBATOR.get());
        RenderTypeRegistry.register(RenderType.translucent(), ModBlocks.OVOSCOPE.get());
    }
}
