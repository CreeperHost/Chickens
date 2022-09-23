package net.creeperhost.chickens.item;

import net.creeperhost.chickens.api.ChickenStats;
import net.creeperhost.chickens.api.ChickensRegistry;
import net.creeperhost.chickens.entity.EntityChickensChicken;
import net.creeperhost.chickens.init.ModItems;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ItemChickenCatcher extends Item
{
    public ItemChickenCatcher(Properties properties)
    {
        super(properties);
    }

    @Override
    public InteractionResult interactLivingEntity(@NotNull ItemStack itemStack, @NotNull Player player, @NotNull LivingEntity livingEntity, @NotNull InteractionHand hand)
    {
        Level level = livingEntity.getLevel();

        if (livingEntity instanceof EntityChickensChicken entityChickensChicken)
        {
            ItemStack chicken = new ItemStack(ModItems.CHICKEN_ITEM.get());
            ResourceLocation resourceLocation = Registry.ENTITY_TYPE.getKey(entityChickensChicken.getType());
            ItemChicken.applyEntityIdToItemStack(chicken, resourceLocation);

            ChickenStats chickenStats = new ChickenStats(entityChickensChicken.getGain(), entityChickensChicken.getGrowth(), entityChickensChicken.getStrength(), entityChickensChicken.getLifeSpan());
            chickenStats.write(chicken);

            ItemEntity itemEntity = new ItemEntity(level, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), chicken);
            level.addFreshEntity(itemEntity);
            livingEntity.remove(Entity.RemovalReason.DISCARDED);
            return InteractionResult.PASS;
        }
        else if (livingEntity instanceof Chicken)
        {
            ItemStack stack = new ItemStack(ModItems.CHICKEN_ITEM.get());
            ItemChicken.applyEntityIdToItemStack(stack, ChickensRegistry.VANILLA_CHICKEN);

            //Vanilla chickens don't have any stats so lets create set them to default
            ChickenStats chickenStats = new ChickenStats(1,1, 1, 100);
            chickenStats.write(stack);

            ItemEntity itemEntity = new ItemEntity(level, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), stack);
            level.addFreshEntity(itemEntity);
            livingEntity.remove(Entity.RemovalReason.DISCARDED);
            return InteractionResult.PASS;
        }
        return InteractionResult.PASS;
    }
}