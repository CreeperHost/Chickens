package net.creeperhost.chickens.api;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ChickenAPI
{
    public static final List<ChickenTransformationRecipe> TRANSFORMATION_RECIPES = new ArrayList<>();

    public static void registerTransformationRecipe(EntityType<?> entityTypeIn, ItemStack itemStack, EntityType<?> entityTypeOut)
    {
        ChickenTransformationRecipe recipe = new ChickenTransformationRecipe(entityTypeIn, itemStack, entityTypeOut);
        if(!TRANSFORMATION_RECIPES.contains(recipe))
        {
            TRANSFORMATION_RECIPES.add(recipe);
        }
    }
}
