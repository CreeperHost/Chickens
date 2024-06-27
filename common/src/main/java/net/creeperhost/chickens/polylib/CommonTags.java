package net.creeperhost.chickens.polylib;

import dev.architectury.platform.Platform;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.Set;

public class CommonTags {
    public static TagKey<Item> DYE = registerTag("dyes");
    private static TagKey<Item> SEEDS = registerTag("seeds");

    private static final Set<Item> SEED_ITEMS = Set.of(Items.BEETROOT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.WHEAT_SEEDS, Items.TORCHFLOWER_SEEDS, Items.PITCHER_POD);

    public static TagKey<Item> registerTag(String string) {
        if (Platform.isForgeLike()) {
            return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("forge", string));
        }
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", string));
    }

    public static boolean isSeeds(ItemStack stack) {
        if (Platform.isForgeLike()) {
            return stack.is(SEEDS);
        }
        //On fabric there is not default seeds tag
        return stack.is(SEEDS) || SEED_ITEMS.contains(stack.getItem());
    }
}
