package net.creeperhost.chickens.compat.jei;

import mezz.jei.api.recipe.RecipeType;
import net.creeperhost.chickens.ChickensMod;
public class ChickensRecipeTypes
{
    public static final RecipeType<ChickenBreedingCategory.Recipe> BREEDING =
            RecipeType.create(ChickensMod.MODID, "breeding", ChickenBreedingCategory.Recipe.class);

    public static final RecipeType<ChickenDropsCategory.Recipe> DROPS =
            RecipeType.create(ChickensMod.MODID, "drops", ChickenDropsCategory.Recipe.class);

    public static final RecipeType<ChickenLayingCategory.Recipe> LAYING =
            RecipeType.create(ChickensMod.MODID, "laying", ChickenLayingCategory.Recipe.class);
}
