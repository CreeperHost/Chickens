package net.creeperhost.chickens.item;

import net.creeperhost.chickens.api.ChickenStats;
import net.creeperhost.chickens.api.ChickensRegistry;
import net.creeperhost.chickens.api.ChickensRegistryItem;
import net.creeperhost.chickens.entity.EntityChickensChicken;
import net.creeperhost.chickens.init.ModComponentTypes;
import net.creeperhost.chickens.init.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemChicken extends Item
{
    public ItemChicken(Properties properties)
    {
        super(properties);
    }

    public static ItemStack of(ChickensRegistryItem chickensRegistryItem)
    {
        ItemStack stack = new ItemStack(ModItems.CHICKEN_ITEM.get());
        applyEntityIdToItemStack(stack, chickensRegistryItem.getRegistryName());
        return stack;
    }

    @Override
    public InteractionResult useOn(UseOnContext useOnContext)
    {
        Level level = useOnContext.getLevel();
        if (!level.isClientSide)
        {
            InteractionHand hand = useOnContext.getHand();
            ItemStack stack = useOnContext.getPlayer().getItemInHand(hand);
            BlockPos blockPos = correctPosition(useOnContext.getClickedPos(), useOnContext.getClickedFace());
            spawn(stack, level, blockPos);
            if (!useOnContext.getPlayer().isCreative())
            {
                stack.shrink(1);
            }
        }
        return InteractionResult.PASS;
    }

    public static BlockPos correctPosition(BlockPos pos, Direction side)
    {
        final int[] offsetsXForSide = new int[]{0, 0, 0, 0, -1, 1};
        final int[] offsetsYForSide = new int[]{-1, 1, 0, 0, 0, 0};
        final int[] offsetsZForSide = new int[]{0, 0, -1, 1, 0, 0};

        int posX = pos.getX() + offsetsXForSide[side.ordinal()];
        int posY = pos.getY() + offsetsYForSide[side.ordinal()];
        int posZ = pos.getZ() + offsetsZForSide[side.ordinal()];

        return new BlockPos(posX, posY, posZ);
    }

    public static void spawn(ItemStack stack, Level worldIn, BlockPos pos)
    {
        ResourceLocation entityName = ResourceLocation.tryParse(getTypeFromStack(stack));
        Entity entity = BuiltInRegistries.ENTITY_TYPE.get(entityName).create(worldIn);
        if(entity instanceof EntityChickensChicken chicken)
        {
            ChickenStats chickenStats = new ChickenStats(stack);
            if (entity == null) return;

            chicken.setStatsAnalyzed(true);
            chicken.setGain(chickenStats.getGain());
            chicken.setStrength(chickenStats.getStrength());
            chicken.setGrowth(chickenStats.getGrowth());
            chicken.setLifeSpan(chickenStats.getLifespan());

            if (stack.has(ModComponentTypes.IS_BABY.get())) chicken.setBaby(stack.get(ModComponentTypes.IS_BABY.get()));
            if (stack.has(ModComponentTypes.LOVE.get())) chicken.setInLoveTime(stack.get(ModComponentTypes.LOVE.get()));

            chicken.setChickenType(getTypeFromStack(stack));
        }
        entity.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
        worldIn.addFreshEntity(entity);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> components, TooltipFlag tooltipFlag)
    {
        super.appendHoverText(itemStack, tooltipContext, components, tooltipFlag);
        ChickenStats chickenStats = new ChickenStats(itemStack);
        if (Screen.hasShiftDown())
        {
            components.add(Component.translatable("entity.ChickensChicken.growth").append(" " + chickenStats.getGrowth()).withStyle(ChatFormatting.DARK_PURPLE));
            components.add(Component.translatable("entity.ChickensChicken.gain").append(" " + chickenStats.getGain()).withStyle(ChatFormatting.DARK_PURPLE));
            components.add(Component.translatable("entity.ChickensChicken.strength").append(" " + chickenStats.getStrength()).withStyle(ChatFormatting.DARK_PURPLE));
            components.add(Component.translatable("entity.ChickensChicken.lifespan").append(" " + chickenStats.getLifespan() + "%").withStyle(ChatFormatting.DARK_PURPLE));
        }
        else
        {
            components.add(Component.translatable("screen.shift.tooltip"));
        }
    }

    @Override
    public @NotNull Component getName(ItemStack stack)
    {
        ChickensRegistryItem chickenDescription = ChickensRegistry.getByRegistryName(getTypeFromStack(stack));
        if (chickenDescription == null) return Component.translatable("item.chickens.chicken.name");
        return Component.translatable("entity.chickens." + chickenDescription.getEntityName());
    }

    public static void applyEntityIdToItemStack(ItemStack stack, ResourceLocation entityId)
    {
        stack.set(ModComponentTypes.CHICKEN_TYPE.get(), entityId.toString());
    }

    @Nullable
    public static String getTypeFromStack(ItemStack stack)
    {
        return stack.get(ModComponentTypes.CHICKEN_TYPE.get());
    }
}
