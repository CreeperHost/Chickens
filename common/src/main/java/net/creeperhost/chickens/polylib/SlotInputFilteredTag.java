package net.creeperhost.chickens.polylib;

import net.creeperhost.polylib.containers.slots.SlotInput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class SlotInputFilteredTag extends SlotInput
{
    private final TagKey<Item> tagKey;

    public SlotInputFilteredTag(Container container, int i, int j, int k, TagKey<Item> tagKey)
    {
        super(container, i, j, k);
        this.tagKey = tagKey;
    }

    public TagKey<Item> getTagKey()
    {
        return tagKey;
    }

    @Override
    public boolean mayPlace(ItemStack itemStack)
    {
        return itemStack.is(tagKey);
    }
}
