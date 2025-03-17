package net.creeperhost.chickens.init;

import joptsimple.internal.Strings;
import net.creeperhost.chickens.Chickens;
import net.creeperhost.chickens.api.ChickensRegistry;
import net.creeperhost.chickens.api.ChickensRegistryItem;
import net.creeperhost.chickens.polylib.ItemHolder;
import net.creeperhost.polylib.inventory.fluid.FluidManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class ModChickens
{
    public static List<ChickensRegistryItem> generateDefaultChickens()
    {
        List<ChickensRegistryItem> chickens = new ArrayList<ChickensRegistryItem>();

        chickens.add(new ChickensRegistryItem(ChickensRegistry.VANILLA_CHICKEN, "vanilla_chicken", new ResourceLocation("minecraft", "textures/entity/chicken.png"), new ItemStack(Items.EGG), 0xffffff));
        chickens.add(new ChickensRegistryItem(ChickensRegistry.SMART_CHICKEN_ID, "smart_chicken", new ResourceLocation("chickens", "textures/entity/smart_chicken.png"), new ItemStack(Items.EGG), 0xffffff));

        // dye chickens
        ChickensRegistryItem whiteChicken = createDyeChicken(DyeColor.WHITE, "white_chicken");
        chickens.add(whiteChicken);
        ChickensRegistryItem yellowChicken = createDyeChicken(DyeColor.YELLOW, "yellow_chicken");
        chickens.add(yellowChicken);
        ChickensRegistryItem blueChicken = createDyeChicken(DyeColor.BLUE, "blue_chicken");
        chickens.add(blueChicken);
        ChickensRegistryItem greenChicken = createDyeChicken(DyeColor.GREEN, "green_chicken");
        chickens.add(greenChicken);
        ChickensRegistryItem redChicken = createDyeChicken(DyeColor.RED, "red_chicken");
        chickens.add(redChicken);
        ChickensRegistryItem blackChicken = createDyeChicken(DyeColor.BLACK, "black_chicken");
        chickens.add(blackChicken);

        ChickensRegistryItem pinkChicken = createDyeChicken(DyeColor.PINK, "pink_chicken").setParentsNew(redChicken, whiteChicken);
        chickens.add(pinkChicken);
        ChickensRegistryItem purpleChicken = createDyeChicken(DyeColor.PURPLE, "purple_chicken").setParentsNew(blueChicken, redChicken);
        chickens.add(purpleChicken);
        chickens.add(createDyeChicken(DyeColor.ORANGE, "orange_chicken").setParentsNew(redChicken, yellowChicken));
        chickens.add(createDyeChicken(DyeColor.LIGHT_BLUE, "light_blue_chicken").setParentsNew(whiteChicken, blueChicken));
        chickens.add(createDyeChicken(DyeColor.LIME, "lime_chicken").setParentsNew(greenChicken, whiteChicken));
        ChickensRegistryItem grayChicken = createDyeChicken(DyeColor.GRAY, "gray_chicken").setParentsNew(blackChicken, whiteChicken);
        chickens.add(grayChicken);
        chickens.add(createDyeChicken(DyeColor.CYAN, "cyan_chicken").setParentsNew(blueChicken, greenChicken));

        chickens.add(createDyeChicken(DyeColor.LIGHT_GRAY, "light_gray_chicken").setParentsNew(grayChicken, whiteChicken));

        chickens.add(createDyeChicken(DyeColor.MAGENTA, "magenta_chicken").setParentsNew(purpleChicken, pinkChicken));

        // base chickens
        ChickensRegistryItem flintChicken = new ChickensRegistryItem(new ResourceLocation(Chickens.MOD_ID, "flint_chicken"), "flint_chicken", new ResourceLocation("chickens", "textures/entity/flint_chicken.png"), new ItemStack(Items.FLINT), 0x6b6b47);
        chickens.add(flintChicken);

        ChickensRegistryItem quartzChicken = new ChickensRegistryItem(new ResourceLocation(Chickens.MOD_ID, "quartz_chicken"), "quartz_chicken", new ResourceLocation("chickens", "textures/entity/quartz_chicken.png"), new ItemStack(Items.QUARTZ), 0x4d0000);
        chickens.add(quartzChicken);

        ChickensRegistryItem logChicken = new ChickensRegistryItem(new ResourceLocation(Chickens.MOD_ID, "log_chicken"), "log_chicken", new ResourceLocation("chickens", "textures/entity/log_chicken.png"), new ItemStack(Blocks.OAK_LOG), 0x98846d);
        chickens.add(logChicken);

        ChickensRegistryItem sandChicken = new ChickensRegistryItem(new ResourceLocation(Chickens.MOD_ID, "sand_chicken"), "sand_chicken", new ResourceLocation("chickens", "textures/entity/sand_chicken.png"), new ItemStack(Blocks.SAND), 0xece5b1);
        chickens.add(sandChicken);

        // Tier 2
        ChickensRegistryItem stringChicken = new ChickensRegistryItem(new ResourceLocation(Chickens.MOD_ID, "string_chicken"), "string_chicken", new ResourceLocation("chickens", "textures/entity/string_chicken.png"), new ItemStack(Items.STRING), 16777215, blackChicken, logChicken);
        stringChicken.setLayItem(new ItemStack(Items.STRING));
        chickens.add(stringChicken);

        ChickensRegistryItem glowstoneChicken = new ChickensRegistryItem(new ResourceLocation(Chickens.MOD_ID, "glowstone_chicken"), "glowstone_chicken", new ResourceLocation("chickens", "textures/entity/glowstone_chicken.png"), new ItemStack(Items.GLOWSTONE_DUST), 0xffff66, quartzChicken, yellowChicken);
        chickens.add(glowstoneChicken);

        ChickensRegistryItem gunpowderChicken = new ChickensRegistryItem(new ResourceLocation(Chickens.MOD_ID, "gunpowder_chicken"), "gunpowder_chicken", new ResourceLocation("chickens", "textures/entity/gunpowder_chicken.png"), new ItemStack(Items.GUNPOWDER), 0x999999, sandChicken, flintChicken);
        chickens.add(gunpowderChicken);

        ChickensRegistryItem redstoneChicken = new ChickensRegistryItem(new ResourceLocation(Chickens.MOD_ID, "redstone_chicken"), "redstone_chicken", new ResourceLocation("chickens", "textures/entity/redstone_chicken.png"), new ItemStack(Items.REDSTONE), 0xe60000, redChicken, sandChicken);
        chickens.add(redstoneChicken);

        ChickensRegistryItem lapisChicken = new ChickensRegistryItem(new ResourceLocation(Chickens.MOD_ID, "lapis_chicken"), "lapis_chicken", new ResourceLocation("chickens", "textures/entity/lapis_chicken.png"), new ItemStack(Items.LAPIS_LAZULI), 0x0000e6, blueChicken, sandChicken);
        chickens.add(lapisChicken);

        ChickensRegistryItem glassChicken = new ChickensRegistryItem(new ResourceLocation(Chickens.MOD_ID, "glass_chicken"), "glass_chicken", new ResourceLocation("chickens", "textures/entity/glass_chicken.png"), new ItemStack(Blocks.GLASS), 0xffffff, quartzChicken, redstoneChicken);
        chickens.add(glassChicken);

        ChickensRegistryItem ironChicken = new ChickensRegistryItem(new ResourceLocation(Chickens.MOD_ID, "iron_chicken"), "iron_chicken", new ResourceLocation("chickens", "textures/entity/iron_chicken.png"), new ItemStack(Items.IRON_INGOT), 0xffffcc, flintChicken, whiteChicken);
        chickens.add(ironChicken);

        ChickensRegistryItem coalChicken = new ChickensRegistryItem(new ResourceLocation(Chickens.MOD_ID, "coal_chicken"), "coal_chicken", new ResourceLocation("chickens", "textures/entity/coal_chicken.png"), new ItemStack(Items.COAL), 0x262626, flintChicken, logChicken);
        chickens.add(coalChicken);

        ChickensRegistryItem brownChicken = createDyeChicken(DyeColor.BROWN, "brown_chicken").setParentsNew(redChicken, greenChicken);
        chickens.add(brownChicken);

        // tier 3
        ChickensRegistryItem goldChicken = new ChickensRegistryItem(new ResourceLocation(Chickens.MOD_ID, "gold_chicken"), "gold_chicken", new ResourceLocation("chickens", "textures/entity/gold_chicken.png"), new ItemStack(Items.GOLD_NUGGET), 0xcccc00, ironChicken, yellowChicken);
        chickens.add(goldChicken);

        ChickensRegistryItem snowballChicken = new ChickensRegistryItem(new ResourceLocation(Chickens.MOD_ID, "snowball_chicken"), "snowball_chicken", new ResourceLocation("chickens", "textures/entity/snowball_chicken.png"), new ItemStack(Items.SNOWBALL), 0x33bbff, blueChicken, logChicken);
        chickens.add(snowballChicken);

        ChickensRegistryItem waterChicken = new ChickensRegistryItem(new ResourceLocation(Chickens.MOD_ID, "water_chicken"), "water_chicken", new ResourceLocation("chickens", "textures/entity/water_chicken.png"), new ItemHolder("fluid", "minecraft:water", (int) FluidManager.BUCKET), 0x000099, gunpowderChicken, snowballChicken);
        chickens.add(waterChicken);

        ChickensRegistryItem lavaChicken = new ChickensRegistryItem(new ResourceLocation(Chickens.MOD_ID, "lava_chicken"), "lava_chicken", new ResourceLocation("chickens", "textures/entity/lava_chicken.png"), new ItemHolder("fluid", "minecraft:lava", (int) FluidManager.BUCKET), 0xcc3300, coalChicken, quartzChicken);
        chickens.add(lavaChicken);

        ChickensRegistryItem clayChicken = new ChickensRegistryItem(new ResourceLocation(Chickens.MOD_ID, "clay_chicken"), "clay_chicken", new ResourceLocation("chickens", "textures/entity/clay_chicken.png"), new ItemStack(Items.CLAY_BALL), 0xcccccc, snowballChicken, sandChicken);
        chickens.add(clayChicken);

        ChickensRegistryItem leatherChicken = new ChickensRegistryItem(new ResourceLocation(Chickens.MOD_ID, "leather_chicken"), "leather_chicken", new ResourceLocation("chickens", "textures/entity/leather_chicken.png"), new ItemStack(Items.LEATHER), 0xA7A06C, stringChicken, brownChicken);
        chickens.add(leatherChicken);

        ChickensRegistryItem netherwartChicken = new ChickensRegistryItem(new ResourceLocation(Chickens.MOD_ID, "netherwart_chicken"), "netherwart_chicken", new ResourceLocation("chickens", "textures/entity/netherwart_chicken.png"), new ItemStack(Items.NETHER_WART), 0x800000, brownChicken, glowstoneChicken);
        chickens.add(netherwartChicken);

        // Tier 4
        ChickensRegistryItem diamondChicken = new ChickensRegistryItem(new ResourceLocation(Chickens.MOD_ID, "diamond_chicken"), "diamond_chicken", new ResourceLocation("chickens", "textures/entity/diamond_chicken.png"), new ItemStack(Items.DIAMOND), 0x99ccff, glassChicken, goldChicken);
        chickens.add(diamondChicken);

        ChickensRegistryItem blazeChicken = new ChickensRegistryItem(new ResourceLocation(Chickens.MOD_ID, "blaze_chicken"), "blaze_chicken", new ResourceLocation("chickens", "textures/entity/blaze_chicken.png"), new ItemStack(Items.BLAZE_ROD), 0xffff66, goldChicken, lavaChicken);
        chickens.add(blazeChicken);

        ChickensRegistryItem slimeChicken = new ChickensRegistryItem(new ResourceLocation(Chickens.MOD_ID, "slime_chicken"), "slime_chicken", new ResourceLocation("chickens", "textures/entity/slime_chicken.png"), new ItemStack(Items.SLIME_BALL), 0x009933, clayChicken, greenChicken);
        chickens.add(slimeChicken);

        // Tier 5
        ChickensRegistryItem enderChicken = new ChickensRegistryItem(new ResourceLocation(Chickens.MOD_ID, "ender_chicken"), "ender_chicken", new ResourceLocation("chickens", "textures/entity/ender_chicken.png"), new ItemStack(Items.ENDER_PEARL), 0x001a00, diamondChicken, netherwartChicken);
        chickens.add(enderChicken);

        ChickensRegistryItem ghastChicken = new ChickensRegistryItem(new ResourceLocation(Chickens.MOD_ID, "ghast_chicken"), "ghast_chicken", new ResourceLocation("chickens", "textures/entity/ghast_chicken.png"), new ItemStack(Items.GHAST_TEAR), 0xffffcc, whiteChicken, blazeChicken);
        chickens.add(ghastChicken);

        ChickensRegistryItem emeraldChicken = new ChickensRegistryItem(new ResourceLocation(Chickens.MOD_ID, "emerald_chicken"), "emerald_chicken", new ResourceLocation("chickens", "textures/entity/emerald_chicken.png"), new ItemStack(Items.EMERALD), 0x00cc00, diamondChicken, greenChicken);
        chickens.add(emeraldChicken);

        ChickensRegistryItem magmaChicken = new ChickensRegistryItem(new ResourceLocation(Chickens.MOD_ID, "magma_chicken"), "magma_chicken", new ResourceLocation("chickens", "textures/entity/magma_chicken.png"), new ItemStack(Items.MAGMA_CREAM), 0x1a0500, slimeChicken, blazeChicken);
        chickens.add(magmaChicken);

        ChickensRegistryItem pShardChicken = new ChickensRegistryItem(new ResourceLocation(Chickens.MOD_ID, "pshard_chicken"), "pshard_chicken", new ResourceLocation("chickens", "textures/entity/pshard_chicken.png"), new ItemStack(Items.PRISMARINE_SHARD), 0x43806e, waterChicken, blueChicken);
        chickens.add(pShardChicken);

        ChickensRegistryItem pCrystalChicken = new ChickensRegistryItem(new ResourceLocation(Chickens.MOD_ID, "pcrystal_chicken"), "pcrystal_chicken", new ResourceLocation("chickens", "textures/entity/pcrystal_chicken.png"), new ItemStack(Items.PRISMARINE_CRYSTALS, 1), 0x4e6961, waterChicken, emeraldChicken);
        chickens.add(pCrystalChicken);

        ChickensRegistryItem obsidianChicken = new ChickensRegistryItem(new ResourceLocation(Chickens.MOD_ID, "obsidian_chicken"), "obsidian_chicken", new ResourceLocation("chickens", "textures/entity/obsidian_chicken.png"), new ItemStack(Blocks.OBSIDIAN, 1), 0x08080e, waterChicken, lavaChicken);
        chickens.add(obsidianChicken);

        ChickensRegistryItem soulSandChicken = new ChickensRegistryItem(new ResourceLocation(Chickens.MOD_ID, "soulsand_chicken"), "soulsand_chicken", new ResourceLocation("chickens", "textures/entity/soulsand_chicken.png"), new ItemStack(Blocks.SOUL_SAND, 1), 0x453125);
        chickens.add(soulSandChicken);

        return chickens;
    }

    public static ChickensRegistryItem createDyeChicken(DyeColor color, String name)
    {
        DyeItem dyeItem = DyeItem.byColor(color);

        return new ChickensRegistryItem(new ResourceLocation(Chickens.MOD_ID, name), name, new ResourceLocation("chickens", "textures/entity/" + Strings.join(name.split("(?=[A-Z])"), "_").toLowerCase() + ".png"), new ItemStack(dyeItem, 1), dyeItem.getDyeColor().getFireworkColor());
    }
}
