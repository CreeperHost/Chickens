package net.creeperhost.chickens.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.creeperhost.chickens.Chickens;
import net.creeperhost.chickens.api.ChickenAPI;
import net.creeperhost.chickens.api.ChickensRegistry;
import net.creeperhost.chickens.api.ChickensRegistryItem;
import net.creeperhost.chickens.init.ModItems;
import net.creeperhost.chickens.item.ItemChicken;
import net.creeperhost.chickens.item.ItemChickenEgg;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@JeiPlugin
public class ChickensJeiPlugin implements IModPlugin
{
    private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Chickens.MOD_ID, "jei_plugin");

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry)
    {
        IJeiHelpers jeiHelpers = registry.getJeiHelpers();
        registry.addRecipeCategories(new ChickenBreedingCategory(jeiHelpers.getGuiHelper()));
        registry.addRecipeCategories(new ChickenDropsCategory(jeiHelpers.getGuiHelper()));
        registry.addRecipeCategories(new ChickenLayingCategory(jeiHelpers.getGuiHelper()));
        registry.addRecipeCategories(new ChickenIncubatorCategory(jeiHelpers.getGuiHelper()));
        registry.addRecipeCategories(new ChickenInteractionCategory(jeiHelpers.getGuiHelper()));
    }

    @Override
    public void registerRecipeCatalysts(@NotNull IRecipeCatalystRegistration registration)
    {
        registration.addRecipeCatalyst(new ItemStack(ModItems.BREEDER.get()), ChickensRecipeTypes.BREEDING);
        registration.addRecipeCatalyst(new ItemStack(ModItems.INCUBATOR.get()), ChickensRecipeTypes.INCUBATOR);
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration)
    {
        registration.registerSubtypeInterpreter(ModItems.CHICKEN_ITEM.get(), ChickenSubtypeInterpreter.INSTANCE);
        //TODO
//        registration.useNbtForSubtypes(ModItems.CHICKEN_EGG.get());
    }

    @Override
    public void registerRecipes(IRecipeRegistration registry)
    {
        registry.addRecipes(ChickensRecipeTypes.BREEDING, getBreedingRecipes());
        registry.addRecipes(ChickensRecipeTypes.LAYING, getLayingRecipes());
        registry.addRecipes(ChickensRecipeTypes.DROPS, getDropRecipes());
        registry.addRecipes(ChickensRecipeTypes.INCUBATOR, getIncubatorRecipes());
        registry.addRecipes(ChickensRecipeTypes.INTERACTION, ChickenAPI.TRANSFORMATION_RECIPES);
    }

    private List<ChickenIncubatorCategory.Recipe> getIncubatorRecipes()
    {
        List<ChickenIncubatorCategory.Recipe> recipes = new ArrayList<>();
        for (ChickensRegistryItem chickensRegistryItem : ChickensRegistry.getItems())
        {
            ItemStack egg = ItemChickenEgg.of(chickensRegistryItem);
            if(egg.isEmpty()) continue;
            ItemStack chicken = ItemChicken.of(chickensRegistryItem);
            if(chicken.isEmpty()) continue;
            recipes.add(new ChickenIncubatorCategory.Recipe(egg, chicken));
        }
        return recipes;
    }

    private List<ChickenLayingCategory.Recipe> getLayingRecipes()
    {
        List<ChickenLayingCategory.Recipe> result = new ArrayList<>();
        for (ChickensRegistryItem chicken : ChickensRegistry.getItems())
        {
            ItemStack itemstack = ItemChicken.of(chicken);

            result.add(new ChickenLayingCategory.Recipe(itemstack, chicken.createLayItem(), chicken.getMinLayTime(), chicken.getMaxLayTime()));
        }
        return result;
    }

    private List<ChickenDropsCategory.Recipe> getDropRecipes()
    {
        List<ChickenDropsCategory.Recipe> result = new ArrayList<>();
        for (ChickensRegistryItem chicken : ChickensRegistry.getItems())
        {

            ItemStack itemstack = ItemChicken.of(chicken);
            result.add(new ChickenDropsCategory.Recipe(itemstack, chicken.createLayItem()));
        }
        return result;
    }

    private List<ChickenBreedingCategory.Recipe> getBreedingRecipes()
    {
        List<ChickenBreedingCategory.Recipe> result = new ArrayList<>();
        for (ChickensRegistryItem chicken : ChickensRegistry.getItems())
        {
            if (chicken.isBreedable())
            {
                ItemStack egg = ItemChickenEgg.of(chicken);

                if(chicken.getParent1() == null) continue;
                ItemStack parent1 = ItemChicken.of(chicken.getParent1());

                if(chicken.getParent2() == null) continue;
                ItemStack parent2 = ItemChicken.of(chicken.getParent2());

                result.add(new ChickenBreedingCategory.Recipe(parent1, parent2, egg, ChickensRegistry.getChildChance(chicken)));
            }
        }
        return result;
    }

    @Override
    public @NotNull ResourceLocation getPluginUid()
    {
        return ID;
    }
}
