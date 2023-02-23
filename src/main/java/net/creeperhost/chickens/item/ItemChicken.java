package net.creeperhost.chickens.item;

import net.creeperhost.chickens.client.render.RenderChickenItem;
import net.creeperhost.chickens.data.ChickenStats;
import net.creeperhost.chickens.registry.ChickensRegistry;
import net.creeperhost.chickens.registry.ChickensRegistryItem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
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
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class ItemChicken extends Item
{
    public ItemChicken(Properties properties)
    {
        super(properties);
    }

    @Override
    public void fillItemCategory(@NotNull CreativeModeTab tab, @NotNull NonNullList<ItemStack> subItems)
    {
        if(this.allowedIn(tab))
        {
            for (ChickensRegistryItem chicken : ChickensRegistry.getItems())
            {
                ItemStack itemstack = new ItemStack(this);
                applyEntityIdToItemStack(itemstack, chicken.getRegistryName());
                subItems.add(itemstack);
            }
        }
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext useOnContext)
    {
        Level level = useOnContext.getLevel();
        if(!level.isClientSide)
        {
            InteractionHand hand = useOnContext.getHand();
            ItemStack stack = useOnContext.getPlayer().getItemInHand(hand);
            BlockPos blockPos = ItemSpawnEgg.correctPosition(useOnContext.getClickedPos(), useOnContext.getClickedFace());
            ItemSpawnEgg.activate(stack, level, blockPos);
            if (!useOnContext.getPlayer().isCreative())
            {
                stack.shrink(1);
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @org.jetbrains.annotations.Nullable Level level, @NotNull List<Component> components, @NotNull TooltipFlag tooltipFlag)
    {
        super.appendHoverText(itemStack, level, components, tooltipFlag);
        ChickenStats chickenStats = new ChickenStats(itemStack);
        if(Screen.hasShiftDown())
        {
            components.add(Component.translatable("entity.ChickensChicken.top.growth").append(" " + chickenStats.getGrowth()).withStyle(ChatFormatting.DARK_PURPLE));
            components.add(Component.translatable("entity.ChickensChicken.top.gain").append(" " + chickenStats.getGain()).withStyle(ChatFormatting.DARK_PURPLE));
            components.add(Component.translatable("entity.ChickensChicken.top.strength").append(" " + chickenStats.getStrength()).withStyle(ChatFormatting.DARK_PURPLE));
        }
        else
        {
            components.add(Component.translatable("gui.hold.shift.tooltip"));
        }
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack stack)
    {
        ChickensRegistryItem chickenDescription = ChickensRegistry.getByRegistryName(getTypeFromStack(stack));
        if(chickenDescription == null) return Component.translatable("Any Chicken");
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


    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer)
    {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return RenderChickenItem.getInstance();
            }
        });
    }
}
