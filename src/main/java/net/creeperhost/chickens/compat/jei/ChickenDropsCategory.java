package net.creeperhost.chickens.compat.jei;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.creeperhost.chickens.ChickensMod;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public class ChickenDropsCategory implements IRecipeCategory<ChickenDropsCategory.Recipe>
{
    public static final Component TITLE = Component.literal("Chicken Drops");
    IGuiHelper guiHelper;

    public ChickenDropsCategory(IGuiHelper guiHelper)
    {
        this.guiHelper = guiHelper;
    }

    @Override
    public @NotNull RecipeType<Recipe> getRecipeType()
    {
        return ChickensRecipeTypes.DROPS;
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

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, @NotNull Recipe recipe, @NotNull IFocusGroup focuses)
    {
        builder.addSlot(RecipeIngredientRole.INPUT, 13, 15).addIngredients(Ingredient.of(recipe.input));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 57, 15).addIngredients(Ingredient.of(recipe.output));
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
