package net.creeperhost.chickens.compat.jei;

import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.creeperhost.chickens.item.ItemChicken;
import net.minecraft.world.item.ItemStack;

public class ChickenSubtypeInterpreter implements IIngredientSubtypeInterpreter<ItemStack>
{
    public static final ChickenSubtypeInterpreter INSTANCE = new ChickenSubtypeInterpreter();

    private ChickenSubtypeInterpreter(){}

    @Override
    public String apply(ItemStack itemStack, UidContext context)
    {
        if (!itemStack.hasTag()) return IIngredientSubtypeInterpreter.NONE;

        if(itemStack.getItem() instanceof ItemChicken)
        {
            return ItemChicken.getTypeFromStack(itemStack);
        }

        return IIngredientSubtypeInterpreter.NONE;
    }
}
