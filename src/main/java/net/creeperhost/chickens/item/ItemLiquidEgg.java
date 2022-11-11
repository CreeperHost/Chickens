package net.creeperhost.chickens.item;

import net.creeperhost.chickens.handler.IColorSource;
import net.creeperhost.chickens.handler.LiquidEggFluidWrapper;
import net.creeperhost.chickens.init.ModItems;
import net.creeperhost.chickens.registry.LiquidEggRegistry;
import net.creeperhost.chickens.registry.LiquidEggRegistryItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemLiquidEgg extends Item implements IColorSource
{
    public ItemLiquidEgg(Item.Properties properties)
    {
        super(properties);
    }

    @Override
    public int getColorFromItemStack(ItemStack stack, int renderPass)
    {
        if (!stack.hasTag()) return 0;
        String[] strings = stack.getTag().get("id").toString().replace("\"", "").split(":");

        ResourceLocation resourceLocation = new ResourceLocation(strings[0], strings[1]);
        Fluid fluid = Registry.FLUID.get(resourceLocation);
        if (fluid != null)
        {
            return fluid.getAttributes().getColor();
        }
        return 0;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag)
    {
        super.appendHoverText(itemStack, level, components, tooltipFlag);
        components.add(new TranslatableComponent("item.liquid_egg.tooltip"));
    }

    @Override
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> subItems)
    {
        if (this.allowdedIn(tab))
        {
            for (LiquidEggRegistryItem liquid : LiquidEggRegistry.getAll())
            {
                subItems.add(of(liquid));
            }
        }
    }

    public static ItemStack of(LiquidEggRegistryItem liquidEggRegistryItem)
    {
        ItemStack stack = new ItemStack(ModItems.LIQUID_EGG.get());
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putString("id", liquidEggRegistryItem.getFluid().getRegistryName().toString());
        stack.setTag(compoundTag);
        return stack;
    }

    public static ItemStack of(FluidStack fluidStack)
    {
        ItemStack stack = new ItemStack(ModItems.LIQUID_EGG.get());
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putString("id", fluidStack.getFluid().getRegistryName().toString());
        stack.setTag(compoundTag);
        return stack;
    }

    @Override
    public Component getName(ItemStack stack)
    {
        if (!stack.hasTag()) return super.getName(stack);

        String[] strings = stack.getTag().getString("id").split(":");
        return new TranslatableComponent("item.liquid_egg." + strings[1] + ".name");
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext useOnContext)
    {
        BlockPos clickedPos = useOnContext.getClickedPos().relative(useOnContext.getClickedFace());
        ItemStack stack = useOnContext.getItemInHand();
        Level level = useOnContext.getLevel();
        Fluid fluid = getFluid(stack);
        if(level.getBlockState(clickedPos).isAir())
        {
            level.setBlock(clickedPos, fluid.defaultFluidState().createLegacyBlock(), 3);
            useOnContext.getItemInHand().shrink(1);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    public Fluid getFluid(ItemStack stack)
    {
        String id = stack.getTag().getString("id");
        return Registry.FLUID.get(new ResourceLocation(id));
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt)
    {
        return new LiquidEggFluidWrapper(stack);
    }
}
