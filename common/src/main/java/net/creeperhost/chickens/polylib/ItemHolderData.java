package net.creeperhost.chickens.polylib;

public class ItemHolderData
{
    String type;
    String id;
    String nbt;

    public ItemHolderData(String type, String id, String nbt)
    {
        this.type = type;
        this.id = id;
        this.nbt = nbt;
    }

    public String getType()
    {
        return type;
    }

    public String getId()
    {
        return id;
    }

    public String getNbt()
    {
        return nbt;
    }
}
