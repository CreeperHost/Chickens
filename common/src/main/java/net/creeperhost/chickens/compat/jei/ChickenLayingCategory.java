package net.creeperhost.chickens.compat.jei;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.creeperhost.chickens.Chickens;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public class ChickenLayingCategory implements IRecipeCategory<ChickenLayingCategory.Recipe>
{
    public static final Component TITLE = Component.translatable("gui.laying");
    IGuiHelper guiHelper;

    public ChickenLayingCategory(IGuiHelper guiHelper)
    {
        this.guiHelper = guiHelper;
    }

    @Override
    public @NotNull RecipeType<Recipe> getRecipeType()
    {
        return ChickensRecipeTypes.LAYING;
    }

    @Override
    public @NotNull Component getTitle()
    {
        return TITLE;
    }

    @Override
    public @NotNull IDrawable getBackground()
    {
        return guiHelper.drawableBuilder(ResourceLocation.fromNamespaceAndPath(Chickens.MOD_ID, "textures/gui/laying.png"), 0, 0, 82, 54).addPadding(0, 20, 0, 0).build();
    }

    @Override
    public IDrawable getIcon()
    {
        return guiHelper.createDrawable(ResourceLocation.fromNamespaceAndPath(Chickens.MOD_ID, "textures/gui/laying_icon.png"), 0, 0, 16, 16);
    }

    @Override
    public void draw(Recipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY)
    {
        IDrawableStatic arrowDrawable = guiHelper.createDrawable(ResourceLocation.fromNamespaceAndPath(Chickens.MOD_ID, "textures/gui/breeding.png"), 82, 0, 7, 7);
        guiHelper.createAnimatedDrawable(arrowDrawable, 200, IDrawableAnimated.StartDirection.BOTTOM, false);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, @NotNull Recipe recipe, @NotNull IFocusGroup focuses)
    {
        builder.addSlot(RecipeIngredientRole.INPUT, 13, 15).addIngredients(Ingredient.of(recipe.chicken));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 57, 15).addIngredients(Ingredient.of(recipe.egg));
    }

    public static class Recipe
    {
        private final ItemStack chicken;
        private final ItemStack egg;
        private final int minTime;
        private final int maxTime;

        public Recipe(ItemStack chicken, ItemStack egg, int minTime, int maxTime)
        {
            this.chicken = chicken;
            this.egg = egg;
            this.minTime = minTime;
            this.maxTime = maxTime;
        }
    }
}
