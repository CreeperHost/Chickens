package net.creeperhost.chickens.polylib;

public class ItemHolderData
{
    String type;
    String id;
    String nbt;
    int quantity;

    public ItemHolderData(String type, String id, String nbt, int quantity)
    {
        this.type = type;
        this.id = id;
        this.nbt = nbt;
        this.quantity = quantity;
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

    public int getQuantity() {
        return quantity;
    }
}
