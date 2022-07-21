package net.creeperhost.chickens.item;

import net.creeperhost.chickens.client.ChickensCreativeTabs;
import net.creeperhost.chickens.handler.IColorSource;
import net.creeperhost.chickens.handler.LiquidEggFluidWrapper;
import net.creeperhost.chickens.registry.LiquidEggRegistryItem;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemLiquidEgg extends Item implements IColorSource
{
    LiquidEggRegistryItem liquidEggRegistryItem;

    public ItemLiquidEgg(LiquidEggRegistryItem liquidEggRegistryItem)
    {
        super(new Item.Properties().tab(ChickensCreativeTabs.CHICKENS_BLOCKS));
        this.liquidEggRegistryItem = liquidEggRegistryItem;
    }

    @Override
    public int getColorFromItemStack(ItemStack stack, int renderPass)
    {
        return IClientFluidTypeExtensions.of(liquidEggRegistryItem.getFluid()).getTintColor();
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> components, @NotNull TooltipFlag tooltipFlag)
    {
        super.appendHoverText(itemStack, level, components, tooltipFlag);
        if(liquidEggRegistryItem.getFluid() != null)
        {
            String name = Registry.FLUID.getKey(liquidEggRegistryItem.getFluid()).toString();
            components.add(Component.literal(ChatFormatting.BLUE + "Fluid: " + ChatFormatting.WHITE + name));
        }
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext useOnContext)
    {
        BlockPos clickedPos = useOnContext.getClickedPos().relative(useOnContext.getClickedFace());
        Level level = useOnContext.getLevel();
        Fluid fluid = liquidEggRegistryItem.getFluid();
        level.setBlock(clickedPos, fluid.defaultFluidState().createLegacyBlock(), 3);
        useOnContext.getItemInHand().shrink(1);
        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt)
    {
        return new LiquidEggFluidWrapper(stack);
    }
}
