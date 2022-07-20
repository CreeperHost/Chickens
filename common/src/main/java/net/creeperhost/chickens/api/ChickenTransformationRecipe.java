package net.creeperhost.chickens.api;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;

public class ChickenTransformationRecipe
{
    private final EntityType<?> entityTypeIn;
    private final ItemStack stack;
    private final EntityType<?> entityTypeOut;

    public ChickenTransformationRecipe(EntityType<?> entityTypeIn, ItemStack stack, EntityType<?> entityTypeOut)
    {
        this.entityTypeIn = entityTypeIn;
        this.stack = stack;
        this.entityTypeOut = entityTypeOut;
    }

    public EntityType<?> getEntityTypeIn()
    {
        return entityTypeIn;
    }

    public ItemStack getStack()
    {
        return stack;
    }

    public EntityType<?> getEntityTypeOut()
    {
        return entityTypeOut;
    }
}
