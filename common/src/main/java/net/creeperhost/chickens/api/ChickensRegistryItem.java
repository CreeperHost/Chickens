package net.creeperhost.chickens.api;

import net.creeperhost.chickens.polylib.CommonTags;
import net.creeperhost.chickens.polylib.ItemHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ChickensRegistryItem
{
    public ResourceLocation registryName;
    public String entityName;
    public ItemHolder layItem;
    public int bgColor;
    public ResourceLocation texture;
    public ChickensRegistryItem parent1;
    public ChickensRegistryItem parent2;
    public boolean isEnabled = true;
    public float layCoefficient = 1.0f;

    public ChickensRegistryItem(ResourceLocation registryName, String entityName, ResourceLocation texture, ItemStack layItem, int bgColor)
    {
        this(registryName, entityName, texture, layItem, bgColor, null, null);
    }

    public ChickensRegistryItem(ResourceLocation registryName, String entityName, ResourceLocation texture, ItemStack layItem, int bgColor, @Nullable ChickensRegistryItem parent1, @Nullable ChickensRegistryItem parent2)
    {
        this(registryName, entityName, texture, new ItemHolder(layItem, false), bgColor, parent1, parent2);
    }

    public ChickensRegistryItem(ResourceLocation registryName, String entityName, ResourceLocation texture, ItemHolder layItem, int bgColor, @Nullable ChickensRegistryItem parent1, @Nullable ChickensRegistryItem parent2)
    {
        this.registryName = registryName;
        this.entityName = entityName;
        this.layItem = layItem;
        this.bgColor = bgColor;
        this.texture = texture;
        this.parent1 = parent1;
        this.parent2 = parent2;
    }

    public ItemHolder getLayItemHolder()
    {
        return this.layItem;
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

    public ResourceLocation getTexture()
    {
        return texture;
    }

    public ItemStack createLayItem()
    {
        return layItem.getStack();
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
        if(layItem == null || layItem.getStack() == null) return false;
        return layItem.getStack().is(CommonTags.DYE);
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
