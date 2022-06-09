//package net.creeperhost.chickens.compat.jei;
//
//import com.mojang.blaze3d.vertex.PoseStack;
//import net.creeperhost.chickens.ChickensMod;
//import mezz.jei.api.constants.VanillaTypes;
//import mezz.jei.api.gui.IRecipeLayout;
//import mezz.jei.api.gui.drawable.IDrawable;
//import mezz.jei.api.gui.drawable.IDrawableAnimated;
//import mezz.jei.api.gui.drawable.IDrawableStatic;
//import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
//import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
//import mezz.jei.api.helpers.IGuiHelper;
//import mezz.jei.api.ingredients.IIngredients;
//import mezz.jei.api.recipe.category.IRecipeCategory;
//import net.minecraft.network.chat.Component;
//import net.minecraft.network.chat.TextComponent;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.crafting.Ingredient;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ChickenLayingCategory implements IRecipeCategory<ChickenLayingCategory.Recipe>
//{
//    public static final ResourceLocation UID = new ResourceLocation(ChickensMod.MODID, "chicken_laying");
//    public static final Component TITLE = new TextComponent("Chicken Laying");
//    IGuiHelper guiHelper;
//
//    public ChickenLayingCategory(IGuiHelper guiHelper)
//    {
//        this.guiHelper = guiHelper;
//    }
//
//    @Override
//    public Component getTitle()
//    {
//        return TITLE;
//    }
//
//    @Override
//    public IDrawable getBackground()
//    {
//        return guiHelper.drawableBuilder(new ResourceLocation(ChickensMod.MODID, "textures/gui/laying.png"), 0, 0, 82, 54).addPadding(0, 20, 0, 0).build();
//    }
//
//    @SuppressWarnings("removal")
//    @Override
//    public IDrawable getIcon()
//    {
//        return guiHelper.createDrawable(new ResourceLocation(ChickensMod.MODID, "textures/gui/laying_icon.png"), 0, 0, 16, 16);
//    }
//
//    @SuppressWarnings("removal")
//    @Override
//    public ResourceLocation getUid()
//    {
//        return UID;
//    }
//
//    @SuppressWarnings("removal")
//    @Override
//    public Class getRecipeClass()
//    {
//        return Recipe.class;
//    }
//
//    @Override
//    public void draw(Recipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY)
//    {
//        IRecipeCategory.super.draw(recipe, recipeSlotsView, stack, mouseX, mouseY);
//
//        IDrawableStatic arrowDrawable = guiHelper.createDrawable(new ResourceLocation(ChickensMod.MODID, "textures/gui/breeding.png"), 82, 0, 7, 7);
//        guiHelper.createAnimatedDrawable(arrowDrawable, 200, IDrawableAnimated.StartDirection.BOTTOM, false);
//    }
//
//    @SuppressWarnings("removal")
//    @Override
//    public void setIngredients(Recipe recipe, IIngredients ingredients)
//    {
//        List<Ingredient> list = new ArrayList<>();
//        list.add(Ingredient.of(recipe.chicken));
//        ingredients.setInputIngredients(list);
//
//        ingredients.setOutput(VanillaTypes.ITEM, recipe.egg);
//    }
//
//    @SuppressWarnings("removal")
//    @Override
//    public void setRecipe(IRecipeLayout recipeLayout, Recipe recipe, IIngredients ingredients)
//    {
//        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
//
//        guiItemStacks.init(0, true, 13, 15);
//        guiItemStacks.init(1, false, 57, 15);
//        guiItemStacks.set(ingredients);
//    }
//
//    public static class Recipe
//    {
//        private final ItemStack chicken;
//        private final ItemStack egg;
//        private final int minTime;
//        private final int maxTime;
//
//        public Recipe(ItemStack chicken, ItemStack egg, int minTime, int maxTime)
//        {
//            this.chicken = chicken;
//            this.egg = egg;
//            this.minTime = minTime;
//            this.maxTime = maxTime;
//        }
//    }
//}
