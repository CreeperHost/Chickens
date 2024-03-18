package net.creeperhost.chickens.polylib;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.creeperhost.chickens.Chickens;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class ItemHolder
{
    private String source = null;

    private String type;
    private String itemID;
    private CompoundTag nbtData;

    private boolean isComplete = false;

    private ItemStack stack = ItemStack.EMPTY;

    private int stackSize = 1;

    public static HashMap<String, Integer> ErroredItems = new HashMap<String, Integer>();

    Gson gson = new Gson();

    public ItemHolder()
    {
        type = "item";
        itemID = getRegistryName(Items.AIR).toString();
        nbtData = null;
        stack = ItemStack.EMPTY;
    }

    public ItemHolder(Item itemIn)
    {
        type = "item";
        itemID = getRegistryName(itemIn).toString();
        nbtData = null;
        stack = ItemStack.EMPTY;
    }

    public ItemHolder(ItemStack stackIn, boolean isFinal)
    {
        type = "item";
        itemID = getRegistryName(stackIn.getItem()).toString();
        stack = stackIn;
        nbtData = stackIn.hasTag() ? stackIn.getTag() : null;
        stackSize = stackIn.getCount();
        isComplete = isFinal;
    }

    public ItemHolder(String type, String itemID, int qty)
    {
        this.type = type;
        this.itemID = itemID;
        this.nbtData = null;
        this.stackSize = qty;
    }

    public ItemHolder(String type, String itemID, String nbt, int qty)
    {
        this.type = type;
        this.itemID = itemID;
        try
        {
            this.nbtData = TagParser.parseTag(nbt);
        } catch (Exception e)
        {
            this.nbtData = null;
            e.printStackTrace();
        }
        this.stackSize = qty;
    }

    public ResourceLocation getRegistryName(Item item)
    {
        return BuiltInRegistries.ITEM.getKey(item);
    }

    public ResourceLocation getRegistryName(Fluid fluid)
    {
        return BuiltInRegistries.FLUID.getKey(fluid);
    }

    public boolean hasSource()
    {
        return this.source != null;
    }

    public String getSource()
    {
        return this.source;
    }

    public ItemHolder setSource(String sourceIn)
    {
        this.source = sourceIn;
        return this;
    }

    @Nullable
    public Item getItem()
    {
        if(this.itemID == null) return ItemStack.EMPTY.getItem();

        return BuiltInRegistries.ITEM.get(new ResourceLocation(this.itemID));
    }

    public Fluid getFluid()
    {
        if(this.itemID == null) return Fluids.EMPTY;

        return BuiltInRegistries.FLUID.get(new ResourceLocation(this.itemID));
    }

    public int getStackSize()
    {
        return !this.stack.isEmpty() ? this.stack.getCount() : this.stackSize;
    }

    public int getAmount()
    {
        return stackSize;
    }

    public ItemStack getStack()
    {
        if (!isComplete)
        {
            Item item = getItem();
            if (item != null)
            {
                stack = new ItemStack(getItem(), this.getAmount());
                if (this.nbtData != null && !this.nbtData.isEmpty()) stack.setTag(this.nbtData);

                isComplete = true;
            }
            else
            {
                handleItemNotFound();
            }
        }

        //System.out.println("Getting: "+ stack.getDisplayName());
        return stack.copy();
    }

    private void handleItemNotFound()
    {
        if (!ErroredItems.containsKey(this.itemID)) ErroredItems.put(this.itemID, 1);
        else ErroredItems.replace(this.itemID, ErroredItems.get(this.itemID) + 1);

        if (ErroredItems.get(this.itemID) <= 3)
        {
            Chickens.LOGGER.error("Could not find specfied Item: [" + this.itemID + "]" + (this.hasSource() ? " | Source: [" + this.getSource() + "]" : "") + " | Dropping Default Item: [" + this.stack.getDisplayName() + "]");
            if (ErroredItems.get(this.itemID) == 3)
                Chickens.LOGGER.error("Will silent error this itemID: [" + this.itemID + "]");
        }
    }

    public ItemHolder readJsonObject(JsonObject data) throws NumberFormatException
    {
        itemID = data.has("itemID") ? data.get("itemID").getAsString() : getRegistryName(Items.AIR).toString();
        stackSize = data.has("qty") ? data.get("qty").getAsInt() : 1;
        String nbtString = data.has("nbt") ? data.get("nbt").getAsString() : null;
        if(nbtString != null && !nbtString.isEmpty())
        {
            try
            {
                nbtData = TagParser.parseTag(nbtString);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return this;
    }

    public JsonObject writeJsonObject(JsonObject data) throws NumberFormatException
    {
        data.addProperty("itemID", itemID);

        if (stackSize > 1) data.addProperty("qty", getStackSize());

        if (nbtData != null && !nbtData.isEmpty())
        {
            JsonElement element = gson.fromJson(nbtData.toString(), JsonElement.class);
            data.add("nbt", element.getAsJsonObject());
        }

        return data;
    }

    public String getType()
    {
        return type;
    }

    @Override
    public String toString()
    {
        return this.itemID + ":" + this.stackSize + (this.nbtData != null ? ":" + this.nbtData.toString() : "");
    }

    public ItemHolderData toData()
    {
        return new ItemHolderData(type, itemID, nbtData == null ? "" : nbtData.toString(), stackSize);
    }
}
