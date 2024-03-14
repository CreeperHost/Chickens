package net.creeperhost.chickens.polylib;

import dev.architectury.platform.Platform;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class CommonTags
{
    public static TagKey<Item> DYE = registerTag("dyes");
    public static TagKey<Item> SEEDS = registerTag("seeds");

    public static TagKey<Item> registerTag(String string)
    {
        if (Platform.isForge())
        {
            return TagKey.create(Registries.ITEM, new ResourceLocation("forge", string));
        }
        return TagKey.create(Registries.ITEM, new ResourceLocation("c", string));
    }
}
