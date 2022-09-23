package net.creeperhost.chickens.item;

import net.creeperhost.chickens.api.ChickenStats;
import net.creeperhost.chickens.api.ChickensRegistry;
import net.creeperhost.chickens.api.ChickensRegistryItem;
import net.creeperhost.chickens.init.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
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
        super(new Item.Properties().tab(ModItems.CREATIVE_MODE_TAB_EGGS));
    }

    @Override
    public void fillItemCategory(@NotNull CreativeModeTab creativeModeTab, @NotNull NonNullList<ItemStack> nonNullList)
    {
        if(this.allowedIn(creativeModeTab))
        {
            ChickensRegistry.getItems().forEach(chickensRegistryItem -> nonNullList.add(of(chickensRegistryItem)));
        }
    }

    @Override
    public Component getName(@NotNull ItemStack itemStack)
    {
        String name = getType(itemStack).getEntityName().replace("_", " ") + " egg";
        String first = name.substring(0, 1).toUpperCase();
        String formatted = first + name.substring(1);
        return Component.literal(formatted);
    }

    public static ItemStack of(ChickensRegistryItem chickensRegistryItem)
    {
        ItemStack stack = new ItemStack(ModItems.CHICKEN_EGG.get());
        stack.getOrCreateTag().putString("chickentype", chickensRegistryItem.getRegistryName().toString());
        stack.getOrCreateTag().putInt("progress", 0);
        stack.getOrCreateTag().putInt("missed", 0);
        stack.getOrCreateTag().putBoolean("viable", true);
        return stack;
    }

    public ChickensRegistryItem getType(ItemStack stack)
    {
        if(!stack.hasTag()) return null;

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
            if (stack.hasTag() && stack.getTag().contains("progress"))
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
            stack.getOrCreateTag().putInt("missed", ++value);
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
                list.add(Component.literal(ChatFormatting.GOLD + "Item: " + ChatFormatting.WHITE + Registry.ITEM.getKey(chickensRegistryItem.getLayItemHolder().getItem())));
            }
            list.add(Component.literal(ChatFormatting.BLUE + "ChickenType: " + ChatFormatting.WHITE + chickensRegistryItem.getEntityName()));
            list.add(Component.literal(ChatFormatting.LIGHT_PURPLE + "Progress: " + ChatFormatting.WHITE + getProgress(itemStack)));
            list.add(Component.literal("Missed: " + getMissedCycles(itemStack)));
            list.add(Component.literal("Viable: " + isViable(itemStack)));
        }

        ChickenStats chickenStats = new ChickenStats(itemStack);
        list.add(Component.literal(ChatFormatting.DARK_PURPLE + "Growth: " + chickenStats.getGrowth()));
        list.add(Component.literal(ChatFormatting.DARK_PURPLE + "Gain: " + chickenStats.getGain()));
        list.add(Component.literal(ChatFormatting.DARK_PURPLE + "Strength: " + chickenStats.getStrength()));
    }
}