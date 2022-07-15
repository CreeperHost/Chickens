package net.creeperhost.chickens.api;

import net.minecraft.world.item.ItemStack;

public class ChickenStats
{
    public int gain;
    public int growth;
    public int strength;

    public ChickenStats(int gain, int growth, int strength)
    {
        this.gain = gain;
        this.growth = growth;
        this.strength = strength;
    }

    public ChickenStats(ItemStack stack)
    {
        read(stack);
    }

    public void write(ItemStack stack)
    {
        stack.getOrCreateTag().putInt("gain", gain);
        stack.getOrCreateTag().putInt("growth", growth);
        stack.getOrCreateTag().putInt("strength", strength);
    }

    public void read(ItemStack stack)
    {
        if (!stack.hasTag())
        {
            gain = 1;
            growth = 1;
            strength = 1;
            write(stack);
        }
        else
        {
            if (stack.getTag() != null)
            {
                if (stack.getTag().contains("gain"))
                {
                    gain = stack.getTag().getInt("gain");
                }
                else
                {
                    gain = 1;
                }
                if (stack.getTag().contains("growth"))
                {
                    growth = stack.getTag().getInt("growth");
                }
                else
                {
                    growth = 1;
                }
                if (stack.getTag().contains("strength"))
                {
                    strength = stack.getTag().getInt("strength");
                }
                else
                {
                    strength = 1;
                }
            }
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
}
