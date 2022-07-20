package net.creeperhost.chickens.init;

import net.creeperhost.chickens.Chickens;
import net.creeperhost.chickens.api.ChickenAPI;
import net.creeperhost.chickens.api.ChickensRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ModRecipes
{
    public static void init()
    {
        registerTransformationRecipe(new ItemStack(Items.BOOK), ChickensRegistry.SMART_CHICKEN_ID);

        registerTransformationRecipe(new ItemStack(Items.WHITE_DYE), new ResourceLocation(Chickens.MOD_ID, "white_chicken"));
        registerTransformationRecipe(new ItemStack(Items.ORANGE_BANNER), new ResourceLocation(Chickens.MOD_ID, "orange_chicken"));
        registerTransformationRecipe(new ItemStack(Items.MAGENTA_DYE), new ResourceLocation(Chickens.MOD_ID, "magenta_chicken"));
        registerTransformationRecipe(new ItemStack(Items.LIGHT_BLUE_DYE), new ResourceLocation(Chickens.MOD_ID, "light_blue_chicken"));
        registerTransformationRecipe(new ItemStack(Items.YELLOW_DYE), new ResourceLocation(Chickens.MOD_ID, "yellow_chicken"));
        registerTransformationRecipe(new ItemStack(Items.LIME_DYE), new ResourceLocation(Chickens.MOD_ID, "lime_chicken"));
        registerTransformationRecipe(new ItemStack(Items.PINK_DYE), new ResourceLocation(Chickens.MOD_ID, "pink_chicken"));
        registerTransformationRecipe(new ItemStack(Items.GRAY_DYE), new ResourceLocation(Chickens.MOD_ID, "gray_chicken"));
        registerTransformationRecipe(new ItemStack(Items.LIGHT_GRAY_DYE), new ResourceLocation(Chickens.MOD_ID, "light_gray_chicken"));
        registerTransformationRecipe(new ItemStack(Items.CYAN_DYE), new ResourceLocation(Chickens.MOD_ID, "cyan_chicken"));
        registerTransformationRecipe(new ItemStack(Items.PURPLE_DYE), new ResourceLocation(Chickens.MOD_ID, "purple_chicken"));
        registerTransformationRecipe(new ItemStack(Items.BLUE_DYE), new ResourceLocation(Chickens.MOD_ID, "blue_chicken"));
        registerTransformationRecipe(new ItemStack(Items.BROWN_DYE), new ResourceLocation(Chickens.MOD_ID, "brown_chicken"));
        registerTransformationRecipe(new ItemStack(Items.GREEN_DYE), new ResourceLocation(Chickens.MOD_ID, "green_chicken"));
        registerTransformationRecipe(new ItemStack(Items.RED_DYE), new ResourceLocation(Chickens.MOD_ID, "red_chicken"));
        registerTransformationRecipe(new ItemStack(Items.BLACK_DYE), new ResourceLocation(Chickens.MOD_ID, "black_chicken"));
    }

    public static void registerTransformationRecipe(ItemStack stack, ResourceLocation resourceLocation)
    {
        EntityType<?> entityType;
        try
        {
            entityType = Registry.ENTITY_TYPE.get(resourceLocation);
            ChickenAPI.registerTransformationRecipe(EntityType.CHICKEN, stack, entityType);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
