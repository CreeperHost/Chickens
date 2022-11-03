package net.creeperhost.chickens.item;

import net.creeperhost.chickens.api.ChickenStats;
import net.creeperhost.chickens.api.ChickensRegistry;
import net.creeperhost.chickens.api.ChickensRegistryItem;
import net.creeperhost.chickens.entity.EntityChickensChicken;
import net.creeperhost.chickens.init.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.CreativeModeTab;
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

    @Override
    public void fillItemCategory(@NotNull CreativeModeTab tab, @NotNull NonNullList<ItemStack> subItems)
    {
        if (this.allowedIn(tab))
        {
            for (ChickensRegistryItem chicken : ChickensRegistry.getItems())
            {
                subItems.add(of(chicken));
            }
        }
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
        EntityChickensChicken entity = (EntityChickensChicken) Registry.ENTITY_TYPE.get(entityName).create(worldIn);
        ChickenStats chickenStats = new ChickenStats(stack);
        if (entity == null) return;

        entity.setStatsAnalyzed(true);
        entity.setGain(chickenStats.getGain());
        entity.setStrength(chickenStats.getStrength());
        entity.setGrowth(chickenStats.getGrowth());
        entity.setLifeSpan(chickenStats.getLifespan());

        if(stack.getTag() != null)
        {
            if(stack.getTag().contains("baby"))
                entity.setBaby(stack.getTag().getBoolean("baby"));
            if(stack.getTag().contains("love"))
                entity.setInLoveTime(stack.getTag().getInt("love"));
        }

        entity.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
        entity.setChickenType(getTypeFromStack(stack));

        worldIn.addFreshEntity(entity);
    }


    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @org.jetbrains.annotations.Nullable Level level, @NotNull List<Component> components, @NotNull TooltipFlag tooltipFlag)
    {
        super.appendHoverText(itemStack, level, components, tooltipFlag);
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
    public Component getName(@NotNull ItemStack stack)
    {
        ChickensRegistryItem chickenDescription = ChickensRegistry.getByRegistryName(getTypeFromStack(stack));
        if (chickenDescription == null) return Component.literal("nul1");
        return Component.translatable("entity.chickens." + chickenDescription.getEntityName());
    }

    public static void applyEntityIdToItemStack(ItemStack stack, ResourceLocation entityId)
    {
        CompoundTag nbttagcompound = stack.hasTag() ? stack.getTag() : new CompoundTag();
        CompoundTag nbttagcompound1 = new CompoundTag();
        nbttagcompound1.putString("id", entityId.toString());
        nbttagcompound.put("ChickenType", nbttagcompound1);
        stack.setTag(nbttagcompound);
    }

    @Nullable
    public static String getTypeFromStack(ItemStack stack)
    {
        CompoundTag nbttagcompound = stack.getTag();

        if (nbttagcompound != null && nbttagcompound.contains("ChickenType", 10))
        {
            new CompoundTag();
            CompoundTag chickentag = nbttagcompound.getCompound("ChickenType");
            return chickentag.getString("id");
        }
        return null;
    }
}
