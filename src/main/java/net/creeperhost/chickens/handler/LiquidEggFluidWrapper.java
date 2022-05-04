package net.creeperhost.chickens.handler;

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
//        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
//            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this);
//        }
//        if (capability == CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY) {
//            return CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY.cast(this);
//        }
        return LazyOptional.empty();
    }


    @Override
    public int getTanks()
    {
        return 0;
    }

    @NotNull
    @Override
    public FluidStack getFluidInTank(int tank)
    {
        return null;
    }

    @Override
    public int getTankCapacity(int tank)
    {
        return 0;
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
            return null;
        }

        return drain(resource.getAmount(), action);
    }

    private FluidStack getFluid()
    {
        Fluid fluid = LiquidEggRegistry.findById(container.getDamageValue()).getFluid();
        return new FluidStack(fluid, 1000);
    }

    @Nullable
    @Override
    public FluidStack drain(int maxDrain, FluidAction doDrain)
    {
        if (container.getCount() < 1 || maxDrain < 1000)
        {
            return null;
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
    public ItemStack getContainer()
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
