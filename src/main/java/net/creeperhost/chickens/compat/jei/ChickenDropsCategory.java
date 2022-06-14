package net.creeperhost.chickens.compat.jei;

import net.creeperhost.chickens.ChickensMod;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ChickenDropsCategory implements IRecipeCategory<ChickenDropsCategory.Recipe>
{
    public static final ResourceLocation UID = new ResourceLocation(ChickensMod.MODID, "chicken_drops");
    public static final Component TITLE = Component.literal("Chicken Drops");
    IGuiHelper guiHelper;

    public ChickenDropsCategory(IGuiHelper guiHelper)
    {
        this.guiHelper = guiHelper;
    }

    @Override
    public @NotNull Component getTitle()
    {
        return TITLE;
    }

    @Override
    public @NotNull IDrawable getBackground()
    {
        return guiHelper.drawableBuilder(new ResourceLocation(ChickensMod.MODID, "textures/gui/drops.png"), 0, 0, 82, 54).addPadding(0, 20, 0, 0).build();
    }

    @Override
    public @NotNull IDrawable getIcon()
    {
        return guiHelper.createDrawable(new ResourceLocation(ChickensMod.MODID, "textures/gui/drops_icon.png"), 0, 0, 16, 16);
    }

    @SuppressWarnings("removal")
    @Override
    public @NotNull ResourceLocation getUid()
    {
        return UID;
    }

    @SuppressWarnings("removal")
    @Override
    public @NotNull Class getRecipeClass()
    {
        return Recipe.class;
    }

    @SuppressWarnings("removal")
    @Override
    public void setIngredients(ChickenDropsCategory.Recipe recipe, IIngredients ingredients)
    {
        List<Ingredient> list = new ArrayList<>();
        list.add(Ingredient.of(recipe.input));
        ingredients.setInputIngredients(list);

        ingredients.setOutput(VanillaTypes.ITEM, recipe.output);
    }

    @SuppressWarnings("removal")
    @Override
    public void setRecipe(IRecipeLayout recipeLayout, Recipe recipe, IIngredients ingredients)
    {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        guiItemStacks.init(0, true, 13, 15);
        guiItemStacks.init(1, false, 57, 15);

        guiItemStacks.set(ingredients);
    }

    public static class Recipe
    {
        private final ItemStack input;
        private final ItemStack output;

        public Recipe(ItemStack input, ItemStack output)
        {
            this.input = input;
            this.output = output;
        }
    }
}
