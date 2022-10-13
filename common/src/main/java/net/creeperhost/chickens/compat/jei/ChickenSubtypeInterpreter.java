package net.creeperhost.chickens.compat.jei;

import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.creeperhost.chickens.item.ItemChicken;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ChickenSubtypeInterpreter implements IIngredientSubtypeInterpreter<ItemStack>
{
    public static ChickenSubtypeInterpreter INSTANCE = new ChickenSubtypeInterpreter();

    private ChickenSubtypeInterpreter(){}

    @Override
    public String apply(@NotNull ItemStack ingredient, @NotNull UidContext context)
    {
        if(!ingredient.hasTag()) return IIngredientSubtypeInterpreter.NONE;

        if(ingredient.getItem() instanceof ItemChicken)
        {
            return ItemChicken.getTypeFromStack(ingredient);
        }

        return IIngredientSubtypeInterpreter.NONE;
    }
}
