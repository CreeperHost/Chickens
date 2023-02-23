package net.creeperhost.chickens.compat.jei;

import mezz.jei.api.registration.IRecipeCatalystRegistration;
import net.creeperhost.chickens.ChickensMod;
import net.creeperhost.chickens.init.ModItems;
import net.creeperhost.chickens.item.ItemChicken;
import net.creeperhost.chickens.registry.ChickensRegistry;
import net.creeperhost.chickens.registry.ChickensRegistryItem;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@JeiPlugin
public class ChickensJeiPlugin implements IModPlugin
{
    private static final ResourceLocation ID = new ResourceLocation(ChickensMod.MODID, "jei_plugin");

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry)
    {
        IJeiHelpers jeiHelpers = registry.getJeiHelpers();
        registry.addRecipeCategories(new ChickenBreedingCategory(jeiHelpers.getGuiHelper()));
        registry.addRecipeCategories(new ChickenDropsCategory(jeiHelpers.getGuiHelper()));
        registry.addRecipeCategories(new ChickenLayingCategory(jeiHelpers.getGuiHelper()));
    }

    @Override
    public void registerRecipeCatalysts(@NotNull IRecipeCatalystRegistration registration)
    {
        registration.addRecipeCatalyst(new ItemStack(ModItems.BREEDER.get()), ChickensRecipeTypes.BREEDING);
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration)
    {
        registration.useNbtForSubtypes(ModItems.SPAWN_EGG.get(), ModItems.COLOURED_EGG.get(), ModItems.CHICKEN_ITEM.get());
    }

    @Override
    public void registerRecipes(IRecipeRegistration registry)
    {
        registry.addRecipes(ChickensRecipeTypes.BREEDING, getBreedingRecipes());
        registry.addRecipes(ChickensRecipeTypes.LAYING, getLayingRecipes());
        registry.addRecipes(ChickensRecipeTypes.DROPS, getDropRecipes());
    }

    private List<ChickenLayingCategory.Recipe> getLayingRecipes()
    {
        List<ChickenLayingCategory.Recipe> result = new ArrayList<>();
        for (ChickensRegistryItem chicken : ChickensRegistry.getItems())
        {
            ItemStack itemstack = new ItemStack(ModItems.CHICKEN_ITEM.get(), 1);
            ItemChicken.applyEntityIdToItemStack(itemstack, chicken.getRegistryName());

            result.add(new ChickenLayingCategory.Recipe(itemstack, chicken.createLayItem(), chicken.getMinLayTime(), chicken.getMaxLayTime()));
        }
        return result;
    }

    private List<ChickenDropsCategory.Recipe> getDropRecipes()
    {
        List<ChickenDropsCategory.Recipe> result = new ArrayList<>();
        for (ChickensRegistryItem chicken : ChickensRegistry.getItems())
        {

            ItemStack itemstack = new ItemStack(ModItems.CHICKEN_ITEM.get());
            ItemChicken.applyEntityIdToItemStack(itemstack, chicken.getRegistryName());

            result.add(new ChickenDropsCategory.Recipe(itemstack, chicken.createDropItem()));
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
                ItemStack itemstack = new ItemStack(ModItems.CHICKEN_ITEM.get());
                ItemChicken.applyEntityIdToItemStack(itemstack, chicken.getRegistryName());

                ItemStack parent1 = new ItemStack(ModItems.CHICKEN_ITEM.get());
                ItemChicken.applyEntityIdToItemStack(parent1, chicken.getParent1().getRegistryName());

                ItemStack parent2 = new ItemStack(ModItems.CHICKEN_ITEM.get());
                ItemChicken.applyEntityIdToItemStack(parent2, chicken.getParent2().getRegistryName());

                result.add(new ChickenBreedingCategory.Recipe(parent1, parent2, itemstack, ChickensRegistry.getChildChance(chicken)));
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
