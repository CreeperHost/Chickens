package net.creeperhost.chickens.api;

import net.creeperhost.chickens.Chickens;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ChickensRegistry
{
    private static final Map<ResourceLocation, ChickensRegistryItem> items = new HashMap<ResourceLocation, ChickensRegistryItem>();
    private static final Map<String, ChickensRegistryItem> STRING_TO_ITEM = new HashMap<String, ChickensRegistryItem>();

    public static final ResourceLocation SMART_CHICKEN_ID = new ResourceLocation(Chickens.MOD_ID, "smart_chicken");
    public static final ResourceLocation VANILLA_CHICKEN = new ResourceLocation(Chickens.MOD_ID, "vanilla_chicken");

    private static final Random rand = new Random();

    public static void register(ChickensRegistryItem entity)
    {
        validate(entity);
        items.put(entity.getRegistryName(), entity);
        STRING_TO_ITEM.put(entity.getRegistryName().toString(), entity);
    }

    private static void validate(ChickensRegistryItem entity)
    {
        for (ChickensRegistryItem item : items.values())
        {
            if (entity.getRegistryName().toString().compareToIgnoreCase(item.getRegistryName().toString()) == 0)
            {
                throw new RuntimeException("Duplicated ID [" + entity.getRegistryName().toString() + "] of [" + entity.getEntityName() + "] with [" + item.getRegistryName().toString() + "] of [" + item.getEntityName() + "]!");
            }
            if (entity.getEntityName().compareToIgnoreCase(item.getEntityName()) == 0)
            {
                throw new RuntimeException("Duplicated name [" + entity.getEntityName() + "] of [" + entity.getRegistryName().toString() + "] with [" + item.getRegistryName().toString() + "]!");
            }
        }
    }

    @Nullable
    public static ChickensRegistryItem getByResourceLocation(ResourceLocation type)
    {
        ChickensRegistryItem chicken = items.get(type);
        return chicken != null ? items.get(type) : getByRegistryName(type.toString());
    }

    @Nullable
    public static ChickensRegistryItem getByRegistryName(String type)
    {
        return STRING_TO_ITEM.get(type);
    }

    public static Collection<ChickensRegistryItem> getItems()
    {
        List<ChickensRegistryItem> result = new ArrayList<ChickensRegistryItem>();
        for (ChickensRegistryItem chicken : items.values())
        {
            if (chicken.isEnabled())
            {
                result.add(chicken);
            }
        }
        return result;
    }

    private static List<ChickensRegistryItem> getChildren(ChickensRegistryItem parent1, ChickensRegistryItem parent2)
    {
        List<ChickensRegistryItem> result = new ArrayList<ChickensRegistryItem>();
        if (parent1.isEnabled())
        {
            result.add(parent1);
        }
        if (parent2.isEnabled())
        {
            result.add(parent2);
        }
        for (ChickensRegistryItem item : items.values())
        {
            if (item.isEnabled() && item.isChildOf(parent1, parent2))
            {
                result.add(item);
            }
        }
        return result;
    }

    @Deprecated
    @Nullable
    public static ChickensRegistryItem findDyeChicken()
    {
        for (ChickensRegistryItem chicken : items.values())
        {
            if (chicken.isDye())
            {
                return chicken;
            }
        }
        return null;
    }

    public static float getChildChance(ChickensRegistryItem child)
    {
        if (child.getTier() <= 1)
        {
            return 0;
        }

        //noinspection ConstantConditions
        List<ChickensRegistryItem> possibleChildren = getChildren(child.getParent1(), child.getParent2());

        int maxChance = getMaxChance(possibleChildren);
        int maxDiceValue = getMaxDiceValue(possibleChildren, maxChance);

        return ((maxChance - child.getTier()) * 100.0f) / maxDiceValue;
    }

    @Nullable
    public static ChickensRegistryItem getRandomChild(ChickensRegistryItem parent1, ChickensRegistryItem parent2)
    {
        List<ChickensRegistryItem> possibleChildren = getChildren(parent1, parent2);
        if (possibleChildren.size() == 0)
        {
            return null;
        }

        int maxChance = getMaxChance(possibleChildren);
        int maxDiceValue = getMaxDiceValue(possibleChildren, maxChance);

        int diceValue = rand.nextInt(maxDiceValue);
        return getChickenToBeBorn(possibleChildren, maxChance, diceValue);
    }

    @Nullable
    private static ChickensRegistryItem getChickenToBeBorn(List<ChickensRegistryItem> possibleChildren, int maxChance, int diceValue)
    {
        int currentVale = 0;
        for (ChickensRegistryItem child : possibleChildren)
        {
            currentVale += maxChance - child.getTier();
            if (diceValue < currentVale)
            {
                return child;
            }
        }
        return null;
    }

    private static int getMaxDiceValue(List<ChickensRegistryItem> possibleChildren, int maxChance)
    {
        int maxDiceValue = 0;
        for (ChickensRegistryItem child : possibleChildren)
        {
            maxDiceValue += maxChance - child.getTier();
        }
        return maxDiceValue;
    }

    private static int getMaxChance(List<ChickensRegistryItem> possibleChildren)
    {
        int maxChance = 0;
        for (ChickensRegistryItem child : possibleChildren)
        {
            maxChance = Math.max(maxChance, child.getTier());
        }
        maxChance += 1;
        return maxChance;
    }
}
