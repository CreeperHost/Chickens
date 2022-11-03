package net.creeperhost.chickens.polylib;

public class ItemHolderData
{
    String itemID;
    String nbt;

    public ItemHolderData(String itemID, String nbt)
    {
        this.itemID = itemID;
        this.nbt = nbt;
    }

    public String getItemID()
    {
        return itemID;
    }

    public String getNbt()
    {
        return nbt;
    }
}
