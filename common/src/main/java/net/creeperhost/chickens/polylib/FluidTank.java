package net.creeperhost.chickens.polylib;

import dev.architectury.fluid.FluidStack;
import dev.architectury.hooks.fluid.FluidStackHooks;
import net.minecraft.nbt.CompoundTag;

public class FluidTank
{
    private int capacity;
    private int stored;
    private FluidStack fluidStack;

    public FluidTank(int stored, int capacity, FluidStack fluidStack)
    {
        this.stored = stored;
        this.capacity = capacity;
        this.fluidStack = fluidStack;
        setChanged();
    }

    public FluidTank(int capacity)
    {
        this(0, capacity, FluidStack.empty());
    }

    public void setFluidStack(FluidStack fluidStack)
    {
        this.fluidStack = fluidStack;
        setChanged();
    }

    public FluidStack getFluidStack()
    {
        return fluidStack;
    }

    public void setCapacity(int capacity)
    {
        this.capacity = capacity;
        setChanged();
    }

    public int getCapacity()
    {
        return capacity;
    }

    public void setStored(int stored)
    {
        this.fluidStack.setAmount(stored);
        this.stored = stored;
        setChanged();
    }

    public int getStored()
    {
        return stored;
    }

    public void setChanged()
    {

    }

    public CompoundTag serializeNBT()
    {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putInt("capacity", getCapacity());
        compoundTag.putInt("stored", getStored());
        FluidStackHooks.write(getFluidStack(), compoundTag);

        return compoundTag;
    }

    public void deserializeNBT(CompoundTag compoundTag)
    {
        setCapacity(compoundTag.getInt("capacity"));
        setStored(compoundTag.getInt("stored"));
        setFluidStack(FluidStackHooks.read(compoundTag));
    }
}
