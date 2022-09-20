package net.creeperhost.chickens.handler;

import net.creeperhost.chickens.item.ItemLiquidEgg;
import net.creeperhost.chickens.registry.LiquidEggRegistry;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class LiquidEggFluidWrapper implements IFluidHandler, IFluidHandlerItem, ICapabilityProvider
{
    private final ItemStack container;

    public LiquidEggFluidWrapper(ItemStack container)
    {
        this.container = container;
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability)
    {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return LazyOptional.of(() -> this).cast();
        }
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY) {
            return LazyOptional.of(() -> this).cast();
        }
        return LazyOptional.empty();
    }


    @Override
    public int getTanks()
    {
        return 1;
    }

    @NotNull
    @Override
    public FluidStack getFluidInTank(int tank)
    {
        if(container.getItem() instanceof ItemLiquidEgg itemLiquidEgg)
        {
            return new FluidStack(itemLiquidEgg.getFluid(container), 1000);
        }
        return FluidStack.EMPTY;
    }

    @Override
    public int getTankCapacity(int tank)
    {
        return 1000;
    }

    @Override
    public boolean isFluidValid(int tank, @NotNull FluidStack stack)
    {
        return false;
    }

    @Override
    public int fill(FluidStack resource, FluidAction action)
    {
        return 0;
    }


    @Nullable
    @Override
    public FluidStack drain(FluidStack resource, FluidAction action)
    {
        FluidStack fluidStack = getFluid();
        if (!resource.isFluidEqual(fluidStack))
        {
            return FluidStack.EMPTY;
        }
        return drain(resource.getAmount(), action);
    }

    private FluidStack getFluid()
    {
        if(container.getItem() instanceof ItemLiquidEgg itemLiquidEgg)
        {
            return new FluidStack(itemLiquidEgg.getFluid(container), 1000);
        }
        return FluidStack.EMPTY;
    }

    @Nullable
    @Override
    public FluidStack drain(int maxDrain, FluidAction doDrain)
    {
        if (container.getCount() < 1 || maxDrain < 1000)
        {
            return FluidStack.EMPTY;
        }

        FluidStack fluidStack = getFluid();
        if (doDrain == FluidAction.EXECUTE)
        {
            container.shrink(1);
        }
        return fluidStack;
    }

    /**
     * @return empty stack - item is consumable
     */
    @Override
    public @NotNull ItemStack getContainer()
    {
        return ItemStack.EMPTY;
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @org.jetbrains.annotations.Nullable Direction side)
    {
        return LazyOptional.empty();
    }
}
