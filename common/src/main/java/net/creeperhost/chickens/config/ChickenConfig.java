package net.creeperhost.chickens.config;

import net.creeperhost.chickens.api.ChickensRegistryItem;
import net.creeperhost.chickens.polylib.ItemHolderData;

public class ChickenConfig
{
    public String name;
    public int colour;
    public boolean is_enabled;
    public float lay_coefficient;
    public float breed_speed_multiplier;
    public ItemHolderData lay_item;
    public String parent_1;
    public String parent_2;

    public ChickenConfig(String name, int colour, boolean is_enabled, float lay_coefficient, float breed_speed_multiplier, ItemHolderData lay_item, String parent_1, String parent_2)
    {
        this.name = name;
        this.colour = colour;
        this.is_enabled = is_enabled;
        this.lay_coefficient = lay_coefficient;
        this.breed_speed_multiplier = breed_speed_multiplier;
        this.lay_item = lay_item;
        this.parent_1 = parent_1;
        this.parent_2 = parent_2;
    }

    public static ChickenConfig of(ChickensRegistryItem chickensRegistryItem)
    {
        return new ChickenConfig(
                chickensRegistryItem.getRegistryName().toString(),
                chickensRegistryItem.bgColor,
                chickensRegistryItem.isEnabled(),
                chickensRegistryItem.layCoefficient,
                chickensRegistryItem.breedSpeedMultiplier,
                chickensRegistryItem.layItem != null ? chickensRegistryItem.layItem.toData() : new ItemHolderData("", "", "", 0),
                chickensRegistryItem.getParent1() != null ? chickensRegistryItem.getParent1().registryName.toString() : "",
                chickensRegistryItem.getParent2() != null ? chickensRegistryItem.getParent2().registryName.toString() : ""
        );
    }
}
