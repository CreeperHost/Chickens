package net.creeperhost.chickens.compat.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import net.creeperhost.chickens.ChickensMod;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ChickenBreedingCategory implements IRecipeCategory<ChickenBreedingCategory.Recipe>
{
    public static final ResourceLocation UID = new ResourceLocation(ChickensMod.MODID, "chicken_breeding");
    public static final Component TITLE = new TextComponent("Chicken Breeding");
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

    @Override
    public void draw(@NotNull Recipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull PoseStack stack, double mouseX, double mouseY)
    {
        IRecipeCategory.super.draw(recipe, recipeSlotsView, stack, mouseX, mouseY);

        IDrawableStatic arrowDrawable = guiHelper.createDrawable(new ResourceLocation(ChickensMod.MODID, "textures/gui/breeding.png"), 82, 0, 7, 7);
        guiHelper.createAnimatedDrawable(arrowDrawable, 200, IDrawableAnimated.StartDirection.BOTTOM, false);
        Minecraft.getInstance().font.draw(stack, recipe.chance + "%", 5F, 60F, 0);
    }

    @SuppressWarnings("removal")
    @Override
    public void setIngredients(Recipe recipe, IIngredients ingredients)
    {
        List<Ingredient> list = new ArrayList<>();
        list.add(Ingredient.of(recipe.parent1));
        list.add(Ingredient.of(recipe.parent2));
        ingredients.setInputIngredients(list);

        ingredients.setOutput(VanillaTypes.ITEM, recipe.child);
    }

    @SuppressWarnings("removal")
    @Override
    public void setRecipe(IRecipeLayout recipeLayout, @NotNull Recipe recipe, @NotNull IIngredients ingredients)
    {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        guiItemStacks.init(0, true, 10, 15);
        guiItemStacks.init(1, true, 53, 15);
        guiItemStacks.init(2, false, 33, 30);

        guiItemStacks.set(ingredients);
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
