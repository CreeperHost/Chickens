package net.creeperhost.chickens.api;

import net.creeperhost.chickens.init.ModComponentTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class ChickenStats
{
    public int gain;
    public int growth;
    public int strength;
    public int lifespan;

    public ChickenStats(int gain, int growth, int strength, int lifespan)
    {
        this.gain = gain;
        this.growth = growth;
        this.strength = strength;
        this.lifespan = lifespan;
    }

    public ChickenStats(ItemStack stack)
    {
        read(stack);
    }

    public void write(ItemStack stack)
    {
        stack.set(ModComponentTypes.CHICKENS_GAIN.get(), gain);
        stack.set(ModComponentTypes.CHICKENS_GROWTH.get(), growth);
        stack.set(ModComponentTypes.CHICKENS_STRENGTH.get(), strength);
        stack.set(ModComponentTypes.CHICKENS_LIFESPAN.get(), lifespan);
    }

    public void read(ItemStack stack)
    {
        if (!stack.has(ModComponentTypes.CHICKENS_GAIN.get()))
        {
            gain = 1;
            growth = 1;
            strength = 1;
            lifespan = 100;
            write(stack);
        }
        else
        {
            gain = stack.getOrDefault(ModComponentTypes.CHICKENS_GAIN.get(), 1);
            growth = stack.getOrDefault(ModComponentTypes.CHICKENS_GROWTH.get(), 1);
            strength = stack.getOrDefault(ModComponentTypes.CHICKENS_STRENGTH.get(), 1);
            lifespan = stack.getOrDefault(ModComponentTypes.CHICKENS_LIFESPAN.get(), 100);
        }
    }

    public void setGain(int gain)
    {
        if (gain > 10) gain = 10;
        this.gain = gain;
    }

    public int getGain()
    {
        return gain;
    }

    public void setGrowth(int growth)
    {
        if (growth > 10) growth = 10;
        this.growth = growth;
    }

    public int getGrowth()
    {
        return growth;
    }

    public void setStrength(int strength)
    {
        if (strength > 10) strength = 10;
        this.strength = strength;
    }

    public int getStrength()
    {
        return strength;
    }

    public int getLifespan()
    {
        return lifespan;
    }

    public void setLifespan(int lifespan)
    {
        if(lifespan > 100) lifespan = 100;
        this.lifespan = lifespan;
    }

    public void reduceLifespan(int value)
    {
        setLifespan(getLifespan() - value);
    }
}
