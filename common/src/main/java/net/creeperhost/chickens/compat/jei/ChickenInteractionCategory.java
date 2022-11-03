package net.creeperhost.chickens.compat.jei;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.common.Constants;
import net.creeperhost.chickens.api.ChickenTransformationRecipe;
import net.creeperhost.chickens.api.ChickensRegistry;
import net.creeperhost.chickens.entity.EntityChickensChicken;
import net.creeperhost.chickens.init.ModItems;
import net.creeperhost.chickens.item.ItemChicken;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

public class ChickenInteractionCategory implements IRecipeCategory<ChickenTransformationRecipe>
{
    public static final Component TITLE = Component.translatable("gui.chicken.interaction");
    IGuiHelper guiHelper;

    public ChickenInteractionCategory(IGuiHelper guiHelper)
    {
        this.guiHelper = guiHelper;
    }

    @Override
    public RecipeType<ChickenTransformationRecipe> getRecipeType()
    {
        return ChickensRecipeTypes.INTERACTION;
    }

    @Override
    public Component getTitle()
    {
        return TITLE;
    }

    @Override
    public IDrawable getBackground()
    {
        return guiHelper.createDrawable(Constants.RECIPE_GUI_VANILLA, 0, 222, 82, 34);
    }

    @Override
    public IDrawable getIcon()
    {
        return guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModItems.CATCHER_ITEM.get()));
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull ChickenTransformationRecipe recipe, @NotNull IFocusGroup focuses)
    {
        ItemStack input = new ItemStack(ModItems.CHICKEN_ITEM.get());
        ItemChicken.applyEntityIdToItemStack(input, ChickensRegistry.VANILLA_CHICKEN);
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 7)
                .addItemStack(input);

        builder.addSlot(RecipeIngredientRole.INPUT, 32, 7)
                .addItemStack(recipe.getStack());

        if(Minecraft.getInstance().level == null) return;
        Entity entity = recipe.getEntityTypeOut().create(Minecraft.getInstance().level);
        if(entity instanceof EntityChickensChicken entityChickensChicken)
        {
            ItemStack chicken = ItemChicken.of(entityChickensChicken.getChickenRegistryItem());
            builder.addSlot(RecipeIngredientRole.OUTPUT, 62, 7)
                    .addItemStack(chicken);
        }
    }
}
