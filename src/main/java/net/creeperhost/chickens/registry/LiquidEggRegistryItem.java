package net.creeperhost.chickens.registry;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class LiquidEggRegistryItem
{
    private final int id;
    private final Block liquid;
    private final int eggColor;
    private final Fluid fluid;

    public LiquidEggRegistryItem(int id, Block liquid, int eggColor, Fluid fluid)
    {
        this.id = id;
        this.liquid = liquid;
        this.eggColor = eggColor;
        this.fluid = fluid;
    }

    public int getId()
    {
        return id;
    }

    public Block getLiquid()
    {
        return liquid;
    }

    public int getEggColor()
    {
        return eggColor;
    }

    public Fluid getFluid()
    {
        return fluid;
    }
}
