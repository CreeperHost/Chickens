package net.creeperhost.chickens.compat.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.creeperhost.chickens.ChickensMod;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public class ChickenBreedingCategory implements IRecipeCategory<ChickenBreedingCategory.Recipe>
{
    public static final Component TITLE = Component.literal("Chicken Breeding");
    IGuiHelper guiHelper;

    public ChickenBreedingCategory(IGuiHelper guiHelper)
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
        return guiHelper.drawableBuilder(new ResourceLocation(ChickensMod.MODID, "textures/gui/breeding.png"), 0, 0, 82, 54).addPadding(0, 20, 0, 0).build();
    }

    @Override
    public @NotNull IDrawable getIcon()
    {
        return guiHelper.createDrawable(new ResourceLocation(ChickensMod.MODID, "textures/gui/breeding_icon.png"), 0, 0, 16, 16);
    }

    @Override
    public @NotNull RecipeType<Recipe> getRecipeType()
    {
        return ChickensRecipeTypes.BREEDING;
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull Recipe recipe, @NotNull IFocusGroup focuses)
    {
        builder.addSlot(RecipeIngredientRole.INPUT, 10, 15).addIngredients(Ingredient.of(recipe.parent1));
        builder.addSlot(RecipeIngredientRole.INPUT, 53, 15).addIngredients(Ingredient.of(recipe.parent2));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 33, 30).addIngredients(Ingredient.of(recipe.child));
    }
    @Override
    public void draw(@NotNull Recipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull PoseStack stack, double mouseX, double mouseY)
    {
        IRecipeCategory.super.draw(recipe, recipeSlotsView, stack, mouseX, mouseY);

        IDrawableStatic arrowDrawable = guiHelper.createDrawable(new ResourceLocation(ChickensMod.MODID, "textures/gui/breeding.png"), 82, 0, 7, 7);
        guiHelper.createAnimatedDrawable(arrowDrawable, 200, IDrawableAnimated.StartDirection.BOTTOM, false);
    }

    public static class Recipe
    {
        private final ItemStack parent1;
        private final ItemStack parent2;
        private final ItemStack child;
        private final float chance;

        public Recipe(ItemStack parent1, ItemStack parent2, ItemStack child, float chance)
        {
            this.parent1 = parent1;
            this.parent2 = parent2;
            this.child = child;
            this.chance = chance;
        }
    }
}
