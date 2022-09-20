package net.creeperhost.chickens.data;

public class ItemData
{
    private String item;
    private String nbt;

    public ItemData(String item, String nbt)
    {
        this.item = item;
        this.nbt = nbt;
    }

    public String getItem()
    {
        return item;
    }

    public String getNbt()
    {
        return nbt;
    }
}
