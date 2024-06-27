package net.creeperhost.chickens.init;

import net.creeperhost.chickens.Chickens;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

/**
 * Created by brandon3055 on 21/03/2024
 */
public class ModTags {

    public static class Items {

        public static final TagKey<Item> MOD_EGGS = tag("mod_eggs");
        public static final TagKey<Item> EGGS = tag("eggs");
        public static final TagKey<Item> CHICKENS = tag("chickens");

        private static TagKey<Item> tag(String name) {
            return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Chickens.MOD_ID, name));
        }
    }

}
