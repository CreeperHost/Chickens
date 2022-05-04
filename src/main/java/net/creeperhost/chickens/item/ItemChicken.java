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
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.IItemRenderProperties;

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
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> subItems)
    {
        if(this.allowdedIn(tab))
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
    public InteractionResult useOn(UseOnContext useOnContext)
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
    public void appendHoverText(ItemStack itemStack, @org.jetbrains.annotations.Nullable Level level, List<Component> components, TooltipFlag tooltipFlag)
    {
        super.appendHoverText(itemStack, level, components, tooltipFlag);
        ChickenStats chickenStats = new ChickenStats(itemStack);
        if(Screen.hasShiftDown())
        {
            components.add(new TextComponent(ChatFormatting.DARK_PURPLE + "Growth: " + chickenStats.getGrowth()));
            components.add(new TextComponent(ChatFormatting.DARK_PURPLE + "Gain: " + chickenStats.getGain()));
            components.add(new TextComponent(ChatFormatting.DARK_PURPLE + "Strength: " + chickenStats.getStrength()));
        }
        else
        {
            components.add(new TextComponent("Hold <Shift> for stats"));
        }
    }

    @Override
    public Component getName(ItemStack stack)
    {
        ChickensRegistryItem chickenDescription = ChickensRegistry.getByRegistryName(getTypeFromStack(stack));
        if(chickenDescription == null) return new TextComponent("nul1");
        return new TranslatableComponent("entity.chickens." + chickenDescription.getEntityName());
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
    public void initializeClient(Consumer<IItemRenderProperties> consumer)
    {
        consumer.accept(new IItemRenderProperties() {
            @Override
            public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
                return RenderChickenItem.getInstance();
            }
        });
    }
}
