//package net.creeperhost.chickens.compat.jei;
//
//import mezz.jei.api.constants.VanillaTypes;
//import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
//import mezz.jei.api.gui.drawable.IDrawable;
//import mezz.jei.api.helpers.IGuiHelper;
//import mezz.jei.api.recipe.IFocusGroup;
//import mezz.jei.api.recipe.RecipeIngredientRole;
//import mezz.jei.api.recipe.RecipeType;
//import mezz.jei.api.recipe.category.IRecipeCategory;
//import mezz.jei.common.Constants;
//import net.creeperhost.chickens.init.ModBlocks;
//import net.minecraft.network.chat.Component;
//import net.minecraft.world.item.ItemStack;
//import org.jetbrains.annotations.NotNull;
//
//public class ChickenIncubatorCategory implements IRecipeCategory<ChickenIncubatorCategory.Recipe>
//{
//    public static final Component TITLE = Component.translatable("gui.chicken.incubator");
//
//    IGuiHelper guiHelper;
//
//    public ChickenIncubatorCategory(IGuiHelper guiHelper)
//    {
//        this.guiHelper = guiHelper;
//    }
//
//    @Override
//    public RecipeType<Recipe> getRecipeType()
//    {
//        return ChickensRecipeTypes.INCUBATOR;
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
//        return guiHelper.createDrawable(Constants.RECIPE_GUI_VANILLA, 0, 222, 82, 34);
//    }
//
//    @Override
//    public IDrawable getIcon()
//    {
//        return guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.INCUBATOR.get()));
//    }
//
//    @Override
//    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull Recipe recipe, @NotNull IFocusGroup focuses)
//    {
//        builder.addSlot(RecipeIngredientRole.INPUT, 1, 7)
//                .addItemStack(recipe.getEgg());
//
//        builder.addSlot(RecipeIngredientRole.OUTPUT, 62, 7)
//                .addItemStack(recipe.getChicken());
//    }
//
//    public static class Recipe
//    {
//        ItemStack egg;
//        ItemStack chicken;
//
//        public Recipe(ItemStack egg, ItemStack chicken)
//        {
//            this.egg = egg;
//            this.chicken = chicken;
//        }
//
//        public ItemStack getEgg()
//        {
//            return egg;
//        }
//
//        public ItemStack getChicken()
//        {
//            return chicken;
//        }
//    }
//}
