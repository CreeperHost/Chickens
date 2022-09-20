package net.creeperhost.chickens.registry;

import net.creeperhost.chickens.handler.ItemHolder;
import net.creeperhost.chickens.handler.SpawnType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.Tags;

import javax.annotation.Nullable;

public class ChickensRegistryItem
{
    private final ResourceLocation registryName;

    private final String entityName;
    private ItemHolder layItem;
    private ItemHolder dropItem;
    private final int bgColor;
    private final int fgColor;
    private final ResourceLocation texture;
    private ChickensRegistryItem parent1;
    private ChickensRegistryItem parent2;
    private SpawnType spawnType;
    private boolean isEnabled = true;
    private float layCoefficient = 1.0f;

    public ChickensRegistryItem(ResourceLocation registryName, String entityName, ResourceLocation texture, ItemStack layItem, int bgColor, int fgColor)
    {
        this(registryName, entityName, texture, layItem, bgColor, fgColor, null, null);
    }

    public ChickensRegistryItem(ResourceLocation registryName, String entityName, ResourceLocation texture, ItemStack layItem, int bgColor, int fgColor, @Nullable ChickensRegistryItem parent1, @Nullable ChickensRegistryItem parent2)
    {
        this(registryName, entityName, texture, new ItemHolder(layItem, false), bgColor, fgColor, parent1, parent2);
    }

    public ChickensRegistryItem(ResourceLocation registryName, String entityName, ResourceLocation texture, ItemHolder layItem, int bgColor, int fgColor, @Nullable ChickensRegistryItem parent1, @Nullable ChickensRegistryItem parent2)
    {
        this.registryName = registryName;
        this.entityName = entityName;
        this.layItem = layItem;
        this.bgColor = bgColor;
        this.fgColor = fgColor;
        this.texture = texture;
        this.spawnType = SpawnType.NORMAL;
        this.parent1 = parent1;
        this.parent2 = parent2;
    }


    public ItemHolder getDropItemHolder()
    {
        return this.dropItem == null ? this.layItem : this.dropItem;
    }

    public ItemHolder getLayItemHolder()
    {
        return this.layItem;
    }

    public ChickensRegistryItem setDropItem(ItemHolder itemHolder)
    {
        dropItem = itemHolder;
        return this;
    }

    public ChickensRegistryItem setDropItem(ItemStack itemstack)
    {
        return setDropItem(new ItemHolder(itemstack, false));
    }

    public ChickensRegistryItem setSpawnType(SpawnType type)
    {
        spawnType = type;
        return this;
    }

    public ChickensRegistryItem setLayCoefficient(float coef)
    {
        layCoefficient = coef;
        return this;
    }

    public String getEntityName()
    {
        return entityName;
    }

    @Nullable
    public ChickensRegistryItem getParent1()
    {
        return parent1;
    }

    @Nullable
    public ChickensRegistryItem getParent2()
    {
        return parent2;
    }

    public int getBgColor()
    {
        return bgColor;
    }

    public int getFgColor()
    {
        return fgColor;
    }

    public ResourceLocation getTexture()
    {
        return texture;
    }

    public ItemStack createLayItem()
    {
        return layItem.getStack();
    }

    public ItemStack createDropItem()
    {
        if (dropItem != null)
        {
            return dropItem.getStack();
        }
        return createLayItem();
    }

    public int getTier()
    {
        if (parent1 == null || parent2 == null)
        {
            return 1;
        }
        return Math.max(parent1.getTier(), parent2.getTier()) + 1;
    }

    public boolean isChildOf(ChickensRegistryItem parent1, ChickensRegistryItem parent2)
    {
        return this.parent1 == parent1 && this.parent2 == parent2 || this.parent1 == parent2 && this.parent2 == parent1;
    }

    public boolean isDye()
    {
        return layItem.getStack().is(Tags.Items.DYES);
    }

    public boolean canSpawn()
    {
        return getTier() == 1 && spawnType != SpawnType.NONE;
    }

    public ResourceLocation getRegistryName()
    {
        return registryName;
    }

    public int getMinLayTime()
    {
        return (int) Math.max(6000 * getTier() * layCoefficient, 1.0f);
    }

    public int getMaxLayTime()
    {
        return 2 * getMinLayTime();
    }

    public SpawnType getSpawnType()
    {
        return spawnType;
    }

    public boolean isImmuneToFire()
    {
        return spawnType == SpawnType.HELL;
    }

    public void setEnabled(boolean enabled)
    {
        isEnabled = enabled;
    }

    public boolean isEnabled()
    {
        return !(!isEnabled || parent1 != null && !parent1.isEnabled() || parent2 != null && !parent2.isEnabled());
    }

    public void setLayItem(ItemHolder itemHolder)
    {
        layItem = itemHolder;
    }

    @Deprecated
    public void setLayItem(ItemStack itemstack)
    {
        setLayItem(new ItemHolder(itemstack, false));
    }

    public void setNoParents()
    {
        parent1 = null;
        parent2 = null;
    }

    public ChickensRegistryItem setParentsNew(ChickensRegistryItem parent1, ChickensRegistryItem parent2)
    {
        this.parent1 = parent1;
        this.parent2 = parent2;
        return this;
    }

    @Deprecated
    public void setParents(ChickensRegistryItem parent1, ChickensRegistryItem parent2)
    {
        this.parent1 = parent1;
        this.parent2 = parent2;
    }

    public boolean isBreedable()
    {
        return parent1 != null && parent2 != null;
    }
}
