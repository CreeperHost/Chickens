package net.creeperhost.chickens.item;

import net.creeperhost.chickens.api.ChickenStats;
import net.creeperhost.chickens.api.ChickensRegistry;
import net.creeperhost.chickens.api.ChickensRegistryItem;
import net.creeperhost.chickens.init.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ItemChickenEgg extends Item
{
    public ItemChickenEgg()
    {
        super(new Item.Properties());
    }

    @Override
    public Component getName(@NotNull ItemStack itemStack)
    {
        if (getType(itemStack) == null) {
            return Component.translatable("item.chickens.egg.name");
        }
        return Component.translatable("entity.chickens."+getType(itemStack).getEntityName()).append(Component.translatable("item.chickens.chicken_egg.egg"));
    }

    public static ItemStack of(ChickensRegistryItem chickensRegistryItem)
    {
        return of(chickensRegistryItem, true);
    }

    public static ItemStack of(ChickensRegistryItem chickensRegistryItem, boolean viable)
    {
        ItemStack stack = new ItemStack(ModItems.CHICKEN_EGG.get());
        stack.getOrCreateTag().putString("chickentype", chickensRegistryItem.getRegistryName().toString());
        stack.getOrCreateTag().putInt("progress", 0);
        stack.getOrCreateTag().putInt("missed", 0);
        stack.getOrCreateTag().putBoolean("viable", viable);
        return stack;
    }

    public ChickensRegistryItem getType(ItemStack stack)
    {
        if(stack.isEmpty()) return null;
        if(stack.getTag() == null) return null;

        ResourceLocation resourceLocation = ResourceLocation.tryParse(stack.getTag().getString("chickentype"));
        if(resourceLocation == null || resourceLocation.toString().isEmpty()) return null;
        AtomicReference<ChickensRegistryItem> value = new AtomicReference<>(null);
        ChickensRegistry.getItems().forEach(chickensRegistryItem1 ->
        {
            if(chickensRegistryItem1 != null && chickensRegistryItem1.getRegistryName().toString().equalsIgnoreCase(resourceLocation.toString()))
            {
                value.set(chickensRegistryItem1);
            }
        });
        return value.get();
    }

    public int getProgress(ItemStack stack)
    {
        if(stack.getItem() instanceof ItemChickenEgg)
        {
            if (stack.getTag() != null && stack.getTag().contains("progress"))
            {
                return stack.getTag().getInt("progress");
            }
        }
        return 0;
    }

    public void setProgress(ItemStack stack, int amount)
    {
        if(stack.getItem() instanceof ItemChickenEgg)
        {
            stack.getOrCreateTag().putInt("progress", amount);
        }
    }

    public void incrementMissed(ItemStack stack)
    {
        if(stack.getItem() instanceof ItemChickenEgg)
        {
            int value = stack.getOrCreateTag().getInt("missed");
            stack.getOrCreateTag().putInt("missed", value + 1);
        }
    }

    public int getMissedCycles(ItemStack stack)
    {
        if(stack.getItem() instanceof ItemChickenEgg)
        {
            return stack.getOrCreateTag().getInt("missed");
        }
        return 0;
    }

    public void setNotViable(ItemStack stack)
    {
        if(stack.getItem() instanceof ItemChickenEgg)
        {
            stack.getOrCreateTag().putBoolean("viable", false);
        }
    }

    public boolean isViable(ItemStack stack)
    {
        if(stack.getItem() instanceof ItemChickenEgg)
        {
            return stack.getOrCreateTag().getBoolean("viable");
        }
        return false;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag)
    {
        if(getType(itemStack) != null && tooltipFlag.isAdvanced())
        {
            ChickensRegistryItem chickensRegistryItem = getType(itemStack);
            list.add(Component.literal(ChatFormatting.AQUA + "Registry name: " + ChatFormatting.WHITE + chickensRegistryItem.getRegistryName().toString()));
            if(chickensRegistryItem.getLayItemHolder().getItem() != null)
            {
                if(chickensRegistryItem.getLayItemHolder().getType().equals("item"))
                {
                    list.add(Component.literal(ChatFormatting.GOLD + "Item: " + ChatFormatting.WHITE + BuiltInRegistries.ITEM.getKey(chickensRegistryItem.getLayItemHolder().getItem())));
                }
                else
                {
                    list.add(Component.literal(ChatFormatting.GOLD + "Fluid: " + ChatFormatting.WHITE + BuiltInRegistries.FLUID.getKey(chickensRegistryItem.getLayItemHolder().getFluid())));
                }
            }
            list.add(Component.literal(ChatFormatting.BLUE + "ChickenType: " + ChatFormatting.WHITE + chickensRegistryItem.getEntityName()));
            list.add(Component.literal(ChatFormatting.LIGHT_PURPLE + "Progress: " + ChatFormatting.WHITE + getProgress(itemStack)));
//            list.add(Component.literal("Missed: " + getMissedCycles(itemStack)));
//            list.add(Component.literal("Viable: " + isViable(itemStack)));
        }

        ChickenStats chickenStats = new ChickenStats(itemStack);
        list.add(Component.translatable("entity.ChickensChicken.growth").append(" " + chickenStats.getGrowth()).withStyle(ChatFormatting.DARK_PURPLE));
        list.add(Component.translatable("entity.ChickensChicken.gain").append(" " + chickenStats.getGain()).withStyle(ChatFormatting.DARK_PURPLE));
        list.add(Component.translatable("entity.ChickensChicken.strength").append(" " + chickenStats.getStrength()).withStyle(ChatFormatting.DARK_PURPLE));
    }
}
