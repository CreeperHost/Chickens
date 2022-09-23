package net.creeperhost.chickens.config;

import net.creeperhost.chickens.api.ChickensRegistryItem;
import net.creeperhost.chickens.polylib.ItemHolderData;

public class ChickenConfig
{
    public String name;
    public int colour;
    public boolean is_enabled;
    public float lay_coefficient;
    public ItemHolderData layitem;
    public ItemHolderData drop_item;
    public String spawn_type;
    public String parent_1;
    public String parent_2;

    public ChickenConfig(String name, int colour, boolean is_enabled, float lay_coefficient, ItemHolderData layitem, ItemHolderData drop_item, String spawn_type, String parent_1, String parent_2)
    {
        this.name = name;
        this.colour = colour;
        this.is_enabled = is_enabled;
        this.lay_coefficient = lay_coefficient;
        this.layitem = layitem;
        this.drop_item = drop_item;
        this.spawn_type = spawn_type;
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
                chickensRegistryItem.layItem != null ? chickensRegistryItem.layItem.toData() : new ItemHolderData("", ""),
                chickensRegistryItem.dropItem != null ? chickensRegistryItem.dropItem.toData() : new ItemHolderData("", ""),
                chickensRegistryItem.getSpawnType().name(),
                chickensRegistryItem.getParent1() != null ? chickensRegistryItem.getParent1().registryName.toString() : "",
                chickensRegistryItem.getParent2() != null ? chickensRegistryItem.getParent2().registryName.toString() : ""
        );
    }
}
