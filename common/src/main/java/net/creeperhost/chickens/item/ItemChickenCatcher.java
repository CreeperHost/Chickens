package net.creeperhost.chickens.item;

import net.creeperhost.chickens.init.ModItems;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
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
        //TODO
//        if(livingEntity instanceof EntityChickensChicken entityChickensChicken)
//        {
//            Level level = livingEntity.getLevel();
//            ItemStack chicken = new ItemStack(ModItems.CHICKEN_ITEM.get());
//            ResourceLocation resourceLocation = Registry.ENTITY_TYPE.getKey(entityChickensChicken.getType());
//            ItemChicken.applyEntityIdToItemStack(chicken, resourceLocation);
//
//            ChickenStats chickenStats = new ChickenStats(entityChickensChicken.getGain(), entityChickensChicken.getGrowth(), entityChickensChicken.getStrength());
//            chickenStats.write(chicken);
//
//            ItemEntity itemEntity = new ItemEntity(level, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), chicken);
//            level.addFreshEntity(itemEntity);
//            livingEntity.remove(Entity.RemovalReason.DISCARDED);
//            return InteractionResult.PASS;
//        }
        return InteractionResult.PASS;
    }
}
