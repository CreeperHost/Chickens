package net.creeperhost.chickens.compat.jei;

import mezz.jei.api.recipe.RecipeType;
import net.creeperhost.chickens.Chickens;
import net.creeperhost.chickens.api.ChickenTransformationRecipe;

public class ChickensRecipeTypes
{
    public static final RecipeType<ChickenBreedingCategory.Recipe> BREEDING = RecipeType.create(Chickens.MOD_ID, "breeding", ChickenBreedingCategory.Recipe.class);

    public static final RecipeType<ChickenDropsCategory.Recipe> DROPS = RecipeType.create(Chickens.MOD_ID, "drops", ChickenDropsCategory.Recipe.class);

    public static final RecipeType<ChickenLayingCategory.Recipe> LAYING = RecipeType.create(Chickens.MOD_ID, "laying", ChickenLayingCategory.Recipe.class);

    public static final RecipeType<ChickenIncubatorCategory.Recipe> INCUBATOR = RecipeType.create(Chickens.MOD_ID, "incubator", ChickenIncubatorCategory.Recipe.class);

    public static final RecipeType<ChickenTransformationRecipe> INTERACTION = RecipeType.create(Chickens.MOD_ID, "interaction", ChickenTransformationRecipe.class);


}
