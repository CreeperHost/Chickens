package net.creeperhost.chickens.config;

import com.google.gson.JsonObject;
import net.creeperhost.chickens.ChickensMod;
import net.creeperhost.chickens.handler.ItemHolder;
import net.creeperhost.chickens.handler.SpawnType;
import net.creeperhost.chickens.registry.ChickensRegistry;
import net.creeperhost.chickens.registry.ChickensRegistryItem;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ConfigHandler
{
    public static final File configDir = new File("config/chickens");
    public static final File ChickensMainFile = new File(configDir, "main_chickens.cfg");
    public static final File ChickensFile = new File(configDir, "chickens.json");

    private static JsonConfig config;

    public static ArrayList<String> ErrorList = new ArrayList<String>();

    public static int chickenEntityId = 30000;
    public static int spawnProbability = 10;
    public static int minBroodSize = 3;
    public static int maxBroodSize = 5;
    public static float netherSpawnChanceMultiplier = 1.0f;
    public static boolean alwaysShowStats = false;


    public static void LoadConfigs(List<ChickensRegistryItem> allchickens)
    {
        loadChickens(allchickens);
    }


    /**
     * Load json file of all Chickens.
     *
     * @param allChickens
     */
    public static void loadChickensFromFile(File fileIn, Collection<ChickensRegistryItem> allChickens)
    {
        config = new JsonConfig(fileIn);

        if(!fileIn.exists())
        {
            config.Load();

            // Add Comments
            String comment = "_comment";
            config.getString(comment, "name", "Just a Reference to the old system naming. Changing does nothing.");
            config.getString(comment, "is_enabled", "Is chicken enabled?");
            config.getString(comment, "lay_item", "Item the chicken will Lay. Changing the qty will double that amount on each gain bonus. ");
            config.getFullJson().get(comment).getAsJsonObject().add("lay_item_example", new ItemHolder(new ItemStack(Items.GOLD_INGOT), true).writeJsonObject(new JsonObject()));
            config.getString(comment, "drop_item", "Item the chicken will Lay. Changing the qty will double that amount on each gain bonus. ");
            config.getString(comment, "spawn_type", "Chicken spawn type, can be: " + String.join(",", SpawnType.names()));
            config.getString(comment, "parent_1", "First parent, empty if it cant be breed. modid:chickenid #example: chickens:waterchicken");
            config.getString(comment, "parent_2", "Second parent, empty if it cant be breed. ");

            for (ChickensRegistryItem chicken : allChickens)
            {

                String registryName = chicken.getRegistryName().toString();

                config.getString(registryName, "name", chicken.getEntityName());

                boolean enabled = config.getBoolean(registryName, "is_enabled", true);
                chicken.setEnabled(enabled);

                float layCoefficient = config.getFloat(registryName, "lay_coefficient", 1.0f, 0.01f, 100f);
                chicken.setLayCoefficient(layCoefficient);

                chicken.setLayItem(loadItemStack(config, registryName, chicken, "lay_item", chicken.getLayItemHolder().setSource(registryName)));
                chicken.setDropItem(loadItemStack(config, registryName, chicken, "drop_item", chicken.getDropItemHolder().setSource(registryName)));

                SpawnType spawnType = SpawnType.valueOf(config.getString(registryName, "spawn_type", chicken.getSpawnType().toString()));
                chicken.setSpawnType(spawnType);

                ChickensRegistry.register(chicken);
            }

            // Set Parents after Chickens have been registered
            for (ChickensRegistryItem chicken : allChickens)
            {

                ChickensRegistryItem parent1 = ChickensRegistry.getByRegistryName(getChickenParent(config, "parent_1", allChickens, chicken, chicken.getParent1()));
                ChickensRegistryItem parent2 = ChickensRegistry.getByRegistryName(getChickenParent(config, "parent_2", allChickens, chicken, chicken.getParent2()));

                if (parent1 != null && parent2 != null)
                {
                    chicken.setParentsNew(parent1, parent2);
                } else
                {
                    chicken.setNoParents();
                }
            }

            if (config.hasChanged)
            {
                config.Save();
            }
        }
        else
        {
            JsonObject jsonObject = config.ReadFile();
            jsonObject.entrySet().forEach(stringJsonElementEntry ->
            {
                if(!stringJsonElementEntry.getKey().startsWith("_comment"))
                {
                    try
                    {
                        String name = stringJsonElementEntry.getValue().getAsJsonObject().getAsJsonPrimitive("name").getAsString();
                        boolean enabled = stringJsonElementEntry.getValue().getAsJsonObject().getAsJsonPrimitive("is_enabled").getAsBoolean();
                        float layCoefficient = stringJsonElementEntry.getValue().getAsJsonObject().getAsJsonPrimitive("lay_coefficient").getAsFloat();
                        String stack = stringJsonElementEntry.getValue().getAsJsonObject().getAsJsonObject("lay_item").getAsJsonPrimitive("itemID").getAsString();
                        String parent1Name = stringJsonElementEntry.getValue().getAsJsonObject().getAsJsonPrimitive("parent_1").getAsString();
                        String parent2Name = stringJsonElementEntry.getValue().getAsJsonObject().getAsJsonPrimitive("parent_2").getAsString();
                        ItemStack stack1 = new ItemStack(Registry.ITEM.get(new ResourceLocation(stack)));

                        ChickensRegistryItem chickensRegistryItem = new ChickensRegistryItem(new ResourceLocation(ChickensMod.MODID, name), name, new ResourceLocation("chickens", "textures/entity/" + name + ".png"), stack1, 0, 0);

                        chickensRegistryItem.setDropItem(stack1);
                        chickensRegistryItem.setLayItem(stack1);

                        ChickensRegistryItem parent1 = ChickensRegistry.getByRegistryName(parent1Name);
                        ChickensRegistryItem parent2 = ChickensRegistry.getByRegistryName(parent2Name);
                        if (parent1 != null && parent2 != null)
                        {
                            chickensRegistryItem.setParentsNew(parent1, parent2);
                        }

                        chickensRegistryItem.setEnabled(enabled);
                        chickensRegistryItem.setLayCoefficient(layCoefficient);

                        ChickensRegistry.register(chickensRegistryItem);
                    } catch (Exception e)
                    {
                        ChickensMod.LOGGER.error("Exception caused when loading chicken with name " + stringJsonElementEntry.getKey());
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public static void loadChickens(Collection<ChickensRegistryItem> allChickens)
    {
        loadChickensFromFile(ChickensFile, allChickens);
    }

    private static String getChickenParent(JsonConfig configuration, String propertyName, Collection<ChickensRegistryItem> allChickens, ChickensRegistryItem chicken, ChickensRegistryItem parent)
    {
        String Category = chicken.getRegistryName().toString();
        return configuration.getString(Category, propertyName, parent != null ? parent.getRegistryName().toString() : "");
    }

    private static ItemHolder loadItemStack(JsonConfig configuration, String Category, ChickensRegistryItem chicken, String prefix, ItemHolder defaultItemStack)
    {
        return configuration.getItemHolder(Category, prefix, defaultItemStack);
    }
}
