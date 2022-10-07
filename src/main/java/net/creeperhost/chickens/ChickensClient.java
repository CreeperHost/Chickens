package net.creeperhost.chickens;

import net.creeperhost.chickens.client.render.RenderChickenItem;
import net.creeperhost.chickens.client.render.RenderRoost;
import net.creeperhost.chickens.handler.ItemColorHandler;
import net.creeperhost.chickens.init.ModBlocks;
import net.creeperhost.chickens.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChickensClient
{
    public static void init()
    {
        ItemProperties.register(ModItems.CHICKEN_ITEM.get(), new ResourceLocation(ChickensMod.MODID, ""), RenderChickenItem.getInstance());

        Minecraft.getInstance().getItemColors().register(new ItemColorHandler(), ModItems.SPAWN_EGG.get(), ModItems.COLOURED_EGG.get(), ModItems.LIQUID_EGG.get());
        BlockEntityRenderers.register(ModBlocks.ROOST_TILE.get(), p_173571_ -> new RenderRoost());
    }
}
