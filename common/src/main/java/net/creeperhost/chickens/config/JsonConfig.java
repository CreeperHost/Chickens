//package net.creeperhost.chickens.config;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.google.gson.JsonObject;
//import net.creeperhost.chickens.polylib.ItemHolder;
//import net.minecraft.core.Registry;
//import net.minecraft.world.item.ItemStack;
//import org.jetbrains.annotations.Nullable;
//
//import java.io.File;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//
//public class JsonConfig
//{
//    boolean hasChanged = false;
//
//    private File configFile;
//    private JsonObject json;
//    private Gson gson;
//
//    public JsonConfig(File file)
//    {
//        configFile = file;
//        gson = new Gson();
//        json = new JsonObject();
//    }
//
//    public void Save()
//    {
//        WriteFile();
//        hasChanged = false;
//    }
//
//    public void Load()
//    {
//        try
//        {
//            SetupFile();
//        } catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//
//        json = ReadFile();
//    }
//
//    public JsonObject getFullJson()
//    {
//        return this.json;
//    }
//
//
//    /**
//     * Returns one of the base Categories of the Json Object <br>
//     * This does not work for nested Objects only the first in the array
//     *
//     * @param categoryProperty
//     * @return
//     */
//    public JsonObject getCategory(String categoryProperty)
//    {
//        JsonObject object = new JsonObject();
//
//        if (json.has(categoryProperty))
//        {
//            object = json.getAsJsonObject(categoryProperty);
//        }
//        else
//        {
//            json.add(categoryProperty, object);
//            setHasChanged(true);
//        }
//        return object;
//    }
//
//
//    /**
//     * Get or set a boolean
//     *
//     * @param categoryProperty
//     * @param property
//     * @param value
//     * @return
//     */
//    public boolean getBoolean(String categoryProperty, String property, boolean value)
//    {
//        JsonObject object = getCategory(categoryProperty);
//
//        if (object.has(property))
//        {
//            value = object.get(property).getAsBoolean();
//        }
//        else
//        {
//            object.addProperty(property, value);
//            setHasChanged(true);
//        }
//        return value;
//    }
//
//
//    /**
//     * Get or Set a String Object
//     *
//     * @param categoryProperty
//     * @param property
//     * @param value
//     * @return
//     */
//    public String getString(String categoryProperty, String property, String value)
//    {
//
//        JsonObject object = getCategory(categoryProperty);
//
//        if (object.has(property))
//        {
//            value = object.get(property).getAsString();
//        }
//        else
//        {
//            object.addProperty(property, value);
//            setHasChanged(true);
//        }
//
//        return value;
//    }
//
//
//    /**
//     * Get or set a Float Object
//     *
//     * @param categoryProperty
//     * @param property
//     * @param value
//     * @param min
//     * @param max
//     * @return
//     */
//    public float getFloat(String categoryProperty, String property, float value, float min, float max)
//    {
//
//        JsonObject object = getCategory(categoryProperty);
//        if (object.has(property))
//        {
//            value = object.get(property).getAsFloat();
//        }
//        else
//        {
//            object.addProperty(property, value);
//            setHasChanged(true);
//        }
//
//        if (value > max) value = max;
//        if (value < min) value = min;
//
//        return value;
//    }
//
//
//    @Nullable
//    public ItemHolder getItemHolder(String categoryProperty, String property, ItemHolder defaultItemHolder)
//    {
//        JsonObject object = getCategory(categoryProperty);
//        ItemHolder holder = defaultItemHolder;
//        boolean useDefault = true;
//
//        if (object.has(property))
//        {
//            if (object.get(property).isJsonObject())
//            {
//                holder.readJsonObject(object.get(property).getAsJsonObject());
//                useDefault = false;
//            }
//            else
//            {
//                // Use old code reader to update old configs
//                ItemStack oldItemStack = getItemStack(categoryProperty, property, defaultItemHolder.getStack());
//                if (oldItemStack != null)
//                {
//                    object.add(property, holder.writeJsonObject(new JsonObject()));
//                    useDefault = false;
//                    setHasChanged(true);
//                }
//            }
//        }
//
//        if (useDefault)
//        {
//            object.add(property, holder.writeJsonObject(new JsonObject()));
//            setHasChanged(true);
//        }
//
//        return holder;
//    }
//
//    @Nullable
//    @Deprecated
//    public ItemStack getItemStack(String categoryProperty, String property, ItemStack stack)
//    {
//        JsonObject object = getCategory(categoryProperty);
//
//        if (object.has(property))
//        {
//            stack = getItemStackFromID(object.get(property).getAsString());
//        }
//        else
//        {
//            object.addProperty(property, getIDfromItemStack(stack));
//            setHasChanged(true);
//        }
//
//        return stack;
//    }
//
//
//    public boolean hasChanged()
//    {
//        return this.hasChanged;
//    }
//
//    protected void setHasChanged(boolean val)
//    {
//        this.hasChanged = val;
//    }
//
//    private void SetupFile() throws IOException
//    {
//        if (!configFile.exists())
//        {
//            hasChanged = true;
//            configFile.getParentFile().mkdirs();
//            configFile.createNewFile();
//        }
//    }
//
//    protected JsonObject ReadFile()
//    {
//
//        JsonObject obj = new JsonObject();
//
//        try
//        {
//            FileReader fr = new FileReader(configFile);
//            JsonObject jsonobject = gson.fromJson(fr, JsonObject.class);
//            fr.close();
//
//            return jsonobject != null ? jsonobject : obj;
//
//        } catch (Exception e)
//        {
//            throw new RuntimeException("Error " + e.getCause() + " loading file: " + configFile.getPath());
//        }
//    }
//
//    protected void WriteFile()
//    {
//        try
//        {
//            FileWriter fw = new FileWriter(configFile);
//            new GsonBuilder().setPrettyPrinting().create().toJson(json, fw);
//            fw.flush();
//            fw.close();
//        } catch (IOException ioexception)
//        {
//            ioexception.printStackTrace();
//        }
//    }
//
//    protected static String getIDfromItemStack(ItemStack stack)
//    {
//        String name = Registry.ITEM.getKey(stack.getItem()).toString();
//        return name + (stack.getDamageValue() != 0 || stack.getCount() > 1 ? ":" + stack.getDamageValue() + (stack.getCount() > 1 ? ":" + stack.getCount() : "") : "");
//    }
//
//    //TODO
//    @Nullable
//    protected static ItemStack getItemStackFromID(String itemID)
//    {
//        return ItemStack.EMPTY;
//        //		String[] args = itemID.split(":");
//        //
//        //		ItemStack stack = null;
//        //		Item item = null;
//        //		int meta = 0;
//        //		int qty = 1;
//        //
//        //		if(args.length >= 2) {
//        //			item = Item.getByNameOrId(args[0] +":"+ args[1]);
//        //		}
//        //
//        //		if(args.length >= 3){
//        //			try{
//        //				meta = Integer.parseInt(args[2]);
//        //			} catch(Exception e) {
//        //				ChickensMod.log.error("Could not parse meta value: "+ itemID);
//        //			}
//        //		}
//        //
//        //		if(args.length == 4) {
//        //			try {
//        //				qty = Integer.parseInt(args[3]);
//        //			} catch(Exception e) {
//        //				ChickensMod.log.error("Could not parse qty value: "+ itemID);
//        //			}
//        //		}
//        //
//        //		if(item != null) {
//        //			stack = new ItemStack(item, qty);
//        //		} else {
//        //			ChickensMod.log.error("Item could not be Found: "+ itemID);
//        //		}
//        //
//        //		return stack;
//    }
//
//}
