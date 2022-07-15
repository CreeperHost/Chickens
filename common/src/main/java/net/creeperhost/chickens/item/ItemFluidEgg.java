package net.creeperhost.chickens.item;

import net.creeperhost.chickens.init.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Deprecated
public class ItemFluidEgg extends Item
{
    public ItemFluidEgg()
    {
        super(new Item.Properties().tab(ModItems.CREATIVE_MODE_TAB));
    }

    @Override
    public Component getName(@NotNull ItemStack itemStack)
    {
        if(getFluid(itemStack).isSame(Fluids.EMPTY)) return Component.literal("Empty Egg");

        Fluid fluid = getFluid(itemStack);
        String name = Registry.FLUID.getKey(fluid).getPath().replace("_", " ");
        String first = name.substring(0, 1).toUpperCase();
        String formatted = first + name.substring(1);
        return Component.literal(formatted + " Fluid Egg");
    }

    @Override
    public InteractionResult useOn(UseOnContext useOnContext)
    {
        BlockPos clickedPos = useOnContext.getClickedPos().relative(useOnContext.getClickedFace());
        Level level = useOnContext.getLevel();
        Fluid fluid = getFluid(useOnContext.getItemInHand());
        level.setBlock(clickedPos, fluid.defaultFluidState().createLegacyBlock(), 3);
        useOnContext.getItemInHand().shrink(1);
        return InteractionResult.SUCCESS;
    }

    @Override
    public void fillItemCategory(@NotNull CreativeModeTab creativeModeTab, @NotNull NonNullList<ItemStack> nonNullList)
    {
        if(this.allowedIn(creativeModeTab))
        {
            Registry.FLUID.forEach(fluid ->
            {
                if(!fluid.isSame(Fluids.EMPTY))
                {
                    nonNullList.add(of(fluid));
                }
            });
        }
    }

    public static ItemStack of(Fluid fluid)
    {
        ItemStack stack = new ItemStack(ModItems.FLUID_EGG.get());
        stack.getOrCreateTag().putString("fluid", Registry.FLUID.getKey(fluid).toString());
        return stack;
    }

    public Fluid getFluid(ItemStack stack)
    {
        if(!stack.hasTag()) return Fluids.EMPTY;
        try
        {
            ResourceLocation fluidlocation = ResourceLocation.tryParse(stack.getTag().getString("fluid"));
            return Registry.FLUID.get(fluidlocation);
        }
        catch (Exception e)
        {
            return Fluids.EMPTY;
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag)
    {
        if(!getFluid(itemStack).isSame(Fluids.EMPTY))
        {
            list.add(Component.literal(ChatFormatting.AQUA + "Fluid Type: " + Registry.FLUID.getKey(getFluid(itemStack)).getPath()));
        }
    }
}
