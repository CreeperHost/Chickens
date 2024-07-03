package net.creeperhost.chickens.fabric;

import net.creeperhost.chickens.Chickens;
import net.creeperhost.chickens.init.ModBlocks;
import net.creeperhost.chickens.init.ModItems;
import net.creeperhost.chickens.init.ModTags;
import net.creeperhost.polylib.fabric.datagen.ModuleType;
import net.creeperhost.polylib.fabric.datagen.providers.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;

import static net.minecraft.data.recipes.RecipeProvider.has;


public class DataGen implements DataGeneratorEntrypoint
{

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator)
    {
        var pack = fabricDataGenerator.createPack();
        pack.addProvider((output, registriesFuture) ->
        {
            PolyLanguageProvider provider = new PolyLanguageProvider(output, ModuleType.COMMON, registriesFuture);

            provider.add("itemGroup.chickens.creative_tab", "Chickens", ModuleType.COMMON);
            provider.add("itemGroup.chickens.creative_tab_eggs", "Chicken Eggs", ModuleType.COMMON);
            provider.add("entity.chickens.rooster", "Rooster", ModuleType.COMMON);

            provider.add("item.liquid_egg.tooltip", "It's like one-off bucket.", ModuleType.COMMON);
            provider.add("item.liquid_egg.water.name", "Water Egg", ModuleType.COMMON);
            provider.add("item.liquid_egg.lava.name", "Lava Egg", ModuleType.COMMON);
            provider.add("item.chickens.catcher", "Chicken Catcher", ModuleType.COMMON);
            provider.add("item.chickens.water_fluid_egg", "Water Egg", ModuleType.COMMON);
            provider.add("item.chickens.lava_fluid_egg", "Lava Egg", ModuleType.COMMON);

            provider.add("item.colored_egg.tooltip", "Throw it to have a chance to spawn the chicken.", ModuleType.COMMON);

            provider.add("item.chickens.egg.name", "Chickens Mod Egg", ModuleType.COMMON);
            provider.add("item.chickens.chicken.name", "Chickens Mod Chicken", ModuleType.COMMON);


            provider.add("entity.chickens.smart_chicken", "Smart Chicken", ModuleType.COMMON);
            provider.add("entity.chickens.vanilla_chicken", "Chicken", ModuleType.COMMON);
            provider.add("entity.chickens.white_chicken", "Bone White Chicken", ModuleType.COMMON);
            provider.add("item.colored_egg.white.name", "Bone White Chicken Egg", ModuleType.COMMON);
            provider.add("entity.chickens.orange_chicken", "Orange Chicken", ModuleType.COMMON);
            provider.add("item.colored_egg.orange.name", "Orange Chicken Egg", ModuleType.COMMON);
            provider.add("entity.chickens.magenta_chicken", "Magenta Chicken", ModuleType.COMMON);
            provider.add("item.colored_egg.magenta.name", "Magenta Chicken Egg", ModuleType.COMMON);
            provider.add("entity.chickens.light_blue_chicken", "Light Blue Chicken", ModuleType.COMMON);
            provider.add("item.colored_egg.light_blue.name", "Light Blue Chicken Egg", ModuleType.COMMON);
            provider.add("entity.chickens.yellow_chicken", "Yellow Chicken", ModuleType.COMMON);
            provider.add("item.colored_egg.yellow.name", "Yellow Chicken Egg", ModuleType.COMMON);
            provider.add("entity.chickens.lime_chicken", "Lime Chicken", ModuleType.COMMON);
            provider.add("item.colored_egg.lime.name", "Lime Chicken Egg", ModuleType.COMMON);
            provider.add("entity.chickens.pink_chicken", "Pink Chicken", ModuleType.COMMON);
            provider.add("item.colored_egg.pink.name", "Pink Chicken Egg", ModuleType.COMMON);
            provider.add("entity.chickens.gray_chicken", "Gray Chicken", ModuleType.COMMON);
            provider.add("item.colored_egg.gray.name", "Gray Chicken Egg", ModuleType.COMMON);
            provider.add("entity.chickens.light_gray_chicken", "Light Gray Chicken", ModuleType.COMMON);
            provider.add("item.colored_egg.light_gray.name", "Light Gray Chicken Egg", ModuleType.COMMON);
            provider.add("entity.chickens.cyan_chicken", "Cyan Chicken", ModuleType.COMMON);
            provider.add("item.colored_egg.cyan.name", "Cyan Chicken Egg", ModuleType.COMMON);
            provider.add("entity.chickens.purple_chicken", "Purple Chicken", ModuleType.COMMON);
            provider.add("item.colored_egg.purple.name", "Purple Chicken Egg", ModuleType.COMMON);
            provider.add("entity.chickens.blue_chicken", "Lapis Blue Chicken", ModuleType.COMMON);
            provider.add("item.colored_egg.blue.name", "Lapis Blue Chicken Egg", ModuleType.COMMON);
            provider.add("entity.chickens.brown_chicken", "Cocoa Brown Chicken", ModuleType.COMMON);
            provider.add("item.colored_egg.brown.name", "Cocoa Brown Chicken Egg", ModuleType.COMMON);
            provider.add("entity.chickens.green_chicken", "Cactus Green Chicken", ModuleType.COMMON);
            provider.add("item.colored_egg.green.name", "Cactus Green Chicken Egg", ModuleType.COMMON);
            provider.add("entity.chickens.red_chicken", "Red Chicken", ModuleType.COMMON);
            provider.add("item.colored_egg.red.name", "Red Chicken Egg", ModuleType.COMMON);
            provider.add("entity.chickens.black_chicken", "Ink Black Chicken", ModuleType.COMMON);
            provider.add("item.colored_egg.black.name", "Ink Black Chicken Egg", ModuleType.COMMON);
            provider.add("entity.chickens.gunpowder_chicken", "Gunpowder Chicken", ModuleType.COMMON);
            provider.add("entity.chickens.flint_chicken", "Flint Chicken", ModuleType.COMMON);
            provider.add("entity.chickens.snowball_chicken", "Snowball Chicken", ModuleType.COMMON);
            provider.add("entity.chickens.lava_chicken", "Lava Chicken", ModuleType.COMMON);
            provider.add("entity.chickens.quartz_chicken", "Nether Quartz Chicken", ModuleType.COMMON);
            provider.add("entity.chickens.redstone_chicken", "Redstone Chicken", ModuleType.COMMON);
            provider.add("entity.chickens.glowstone_chicken", "Glowstone Chicken", ModuleType.COMMON);
            provider.add("entity.chickens.iron_chicken", "Iron Chicken", ModuleType.COMMON);
            provider.add("entity.chickens.coal_chicken", "Coal Chicken", ModuleType.COMMON);
            provider.add("entity.chickens.clay_chicken", "Clay Chicken", ModuleType.COMMON);
            provider.add("entity.chickens.slime_chicken", "Slime Chicken", ModuleType.COMMON);
            provider.add("entity.chickens.water_chicken", "Water Chicken", ModuleType.COMMON);
            provider.add("entity.chickens.netherwart_chicken", "Nether Wart Chicken", ModuleType.COMMON);
            provider.add("entity.chickens.gold_chicken", "Gold Chicken", ModuleType.COMMON);
            provider.add("entity.chickens.diamond_chicken", "Diamond Chicken", ModuleType.COMMON);
            provider.add("entity.chickens.blaze_chicken", "Blaze Rod Chicken", ModuleType.COMMON);
            provider.add("entity.chickens.emerald_chicken", "Emerald Chicken", ModuleType.COMMON);
            provider.add("entity.chickens.ender_chicken", "Ender Pearl Chicken", ModuleType.COMMON);
            provider.add("entity.chickens.ghast_chicken", "Ghast Tear Chicken", ModuleType.COMMON);
            provider.add("entity.chickens.magma_chicken", "Magma Cream Chicken", ModuleType.COMMON);
            provider.add("entity.chickens.string_chicken", "String Chicken", ModuleType.COMMON);
            provider.add("entity.chickens.log_chicken", "Log Chicken", ModuleType.COMMON);
            provider.add("entity.chickens.sand_chicken", "Sand Chicken", ModuleType.COMMON);
            provider.add("entity.chickens.glass_chicken", "Glass Chicken", ModuleType.COMMON);
            provider.add("entity.chickens.leather_chicken", "Leather Chicken", ModuleType.COMMON);
            provider.add("entity.chickens.pshard_chicken", "Prismarine Shard Chicken", ModuleType.COMMON);
            provider.add("entity.chickens.xpchicken", "Xp Chicken", ModuleType.COMMON);
            provider.add("entity.chickens.pcrystal_chicken", "Prismarine Crystal Chicken", ModuleType.COMMON);
            provider.add("entity.chickens.obsidian_chicken", "Obsidian Chicken", ModuleType.COMMON);
            provider.add("entity.chickens.soulsand_chicken", "Soul Sand Chicken", ModuleType.COMMON);

            provider.add("entity.ChickensChicken.tier", "Tier", ModuleType.COMMON);
            provider.add("entity.ChickensChicken.layProgress", "Next egg in ~%1$smin.", ModuleType.COMMON);
            provider.add("entity.ChickensChicken.nextEggSoon", "Next egg in <1min.", ModuleType.COMMON);
            provider.add("entity.ChickensChicken.growth", "Growth", ModuleType.COMMON);
            provider.add("entity.ChickensChicken.gain", "Gain", ModuleType.COMMON);
            provider.add("entity.ChickensChicken.strength", "Strength", ModuleType.COMMON);
            provider.add("entity.ChickensChicken.lifespan", "Lifespan", ModuleType.COMMON);

            provider.add("entity.ChickensChicken.top.tier", "Tier", ModuleType.COMMON);
            provider.add("entity.ChickensChicken.top.layProgress", "Next egg in", ModuleType.COMMON);
            provider.add("entity.ChickensChicken.top.layProgressEnd", "min.", ModuleType.COMMON);
            provider.add("entity.ChickensChicken.top.nextEggSoon", "Next egg in <1min.", ModuleType.COMMON);

            provider.add("entity.ChickensChicken.top.growth", "Growth", ModuleType.COMMON);
            provider.add("entity.ChickensChicken.top.gain", "Gain", ModuleType.COMMON);
            provider.add("entity.ChickensChicken.top.strength", "Strength", ModuleType.COMMON);

            provider.add("gui.laying", "Laying Egg", ModuleType.COMMON);
            provider.add("gui.laying.time", "%1$s-%2$smin.", ModuleType.COMMON);
            provider.add("gui.breeding", "Chicken Breeding", ModuleType.COMMON);
            provider.add("gui.breeding.time", "%1$s%%", ModuleType.COMMON);
            provider.add("gui.drops", "Chicken Drop, ModuleType.COMMON);", ModuleType.COMMON);
            provider.add("gui.throws", "Color Egg Throwing", ModuleType.COMMON);
            provider.add("chickens.screen.ovoscope", "Ovoscope", ModuleType.COMMON);
            provider.add("gui.chicken.incubator", "Chicken Incubator", ModuleType.COMMON);
            provider.add("gui.chicken.interaction", "Chicken Interaction", ModuleType.COMMON);

            provider.add("block.chickens.breeder", "Breeder", ModuleType.COMMON);
            provider.add("block.chickens.roost", "Roost", ModuleType.COMMON);
            provider.add("block.chickens.incubator", "Incubator", ModuleType.COMMON);
            provider.add("block.chickens.egg_cracker", "Egg Cracker", ModuleType.COMMON);
            provider.add("block.chickens.ovoscope", "Ovoscope", ModuleType.COMMON);
            provider.add("screen.shift.tooltip", "Hold <Shift> for stats", ModuleType.COMMON);

            provider.add("advancement.hard_boiled.title", "Hard Boiled", ModuleType.COMMON);
            provider.add("advancement.hard_boiled.desc", "Make an egg no longer viable for hatching", ModuleType.COMMON);

            provider.add("gui.chickens.incubator.water_slot", "Add water to tank.", ModuleType.COMMON);
            provider.add("gui.chickens.incubator.heat", "Heat,", ModuleType.COMMON);
            provider.add("gui.chickens.incubator.increase_heat", "Increase heat setting", ModuleType.COMMON);
            provider.add("gui.chickens.incubator.decrease_heat", "Decrease heat setting", ModuleType.COMMON);
            provider.add("gui.chickens.incubator.temperature.info", "Incubator Temperature", ModuleType.COMMON);
            provider.add("gui.chickens.incubator.temperature.temp", " %sc", ModuleType.COMMON);
            provider.add("gui.chickens.incubator.temperature.hot", "Too Hot!", ModuleType.COMMON);
            provider.add("gui.chickens.incubator.temperature.cold", "Too Cold!", ModuleType.COMMON);
            provider.add("gui.chickens.incubator.hygrometer.info", "Incubator Humidity", ModuleType.COMMON);
            provider.add("gui.chickens.incubator.hygrometer.value", " %s%%", ModuleType.COMMON);
            provider.add("gui.chickens.incubator.hygrometer.dry", "Too Dry!, ModuleType.COMMON); Water tank is empty!", ModuleType.COMMON);

            provider.add("gui.chickens.cracker.fluid_slot", "Drain Fluid", ModuleType.COMMON);

            provider.add("gui.chickens.ovoscope.viable", "Viable", ModuleType.COMMON);
            provider.add("gui.chickens.ovoscope.non_viable", "Non Viable", ModuleType.COMMON);

            provider.add("gui.chickens.breeder.chicken_slot", "Add Chickens!", ModuleType.COMMON);
            provider.add("gui.chickens.breeder.seed_slot", "Add Seeds!", ModuleType.COMMON);

            return provider;
        });

        var blocktags = pack.addProvider((output, registriesFuture) ->
        {
            PolyBlockTagProvider provider = new PolyBlockTagProvider(output, registriesFuture, ModuleType.COMMON);
            ModBlocks.BLOCKS.forEach(blockRegistrySupplier -> provider.add(BlockTags.MINEABLE_WITH_PICKAXE, blockRegistrySupplier.get(), ModuleType.COMMON));
            return provider;
        });

        pack.addProvider((output, registriesFuture) ->
        {
            PolyItemTagProvider provider = new PolyItemTagProvider(output, registriesFuture, blocktags, ModuleType.COMMON);
            provider.add(ModTags.Items.CHICKENS, ModItems.CHICKEN_ITEM.get(), ModuleType.COMMON);
            provider.add(ModTags.Items.EGGS, ModItems.CHICKEN_EGG.get(), ModuleType.COMMON);
            provider.add(ModTags.Items.EGGS, Items.EGG, ModuleType.COMMON);
            provider.add(ModTags.Items.MOD_EGGS, ModItems.CHICKEN_EGG.get(), ModuleType.COMMON);
            return provider;
        });

        pack.addProvider((output, registriesFuture) ->
        {
            PolyBlockLootProvider provider = new PolyBlockLootProvider(output, ModuleType.COMMON, registriesFuture);
            ModBlocks.BLOCKS.forEach(blockRegistrySupplier -> provider.addSelfDrop(blockRegistrySupplier.get(), ModuleType.COMMON));
            return provider;
        });

        pack.addProvider((output, registriesFuture) ->
        {
            PolyRecipeProvider provider = new PolyRecipeProvider(output, ModuleType.COMMON, registriesFuture);

            provider.add(ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.BREEDER.get())
                    .pattern("PPP")
                    .pattern("PSP")
                    .pattern("HHH")
                    .define('H', Blocks.HAY_BLOCK)
                    .define('P', ItemTags.PLANKS)
                    .define('S', Items.WHEAT_SEEDS)
                    .group(Chickens.MOD_ID)
                    .unlockedBy("has_item", has(Blocks.HAY_BLOCK)), ModuleType.COMMON);

            provider.add(ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CATCHER_ITEM.get())
                    .pattern(" E ")
                    .pattern(" S ")
                    .pattern(" F ")
                    .define('E', ModTags.Items.EGGS)
                    .define('S', Items.STICK)
                    .define('F', Items.FEATHER)
                    .group(Chickens.MOD_ID)
                    .unlockedBy("has_item", has(ModTags.Items.EGGS)), ModuleType.COMMON);

            provider.add(ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.EGG_CRACKER.get())
                    .pattern("IPI")
                    .pattern("IEI")
                    .pattern("ICI")
                    .define('I', Items.IRON_INGOT)
                    .define('C', Items.CHEST)
                    .define('E', ModTags.Items.EGGS)
                    .define('P', Items.PISTON)
                    .group(Chickens.MOD_ID)
                    .unlockedBy("has_item", has(ModTags.Items.EGGS)), ModuleType.COMMON);

            provider.add(ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.INCUBATOR.get())
                    .pattern("PLP")
                    .pattern("GEG")
                    .pattern("HHH")
                    .define('E', ModTags.Items.MOD_EGGS)
                    .define('G', Items.GLASS_PANE)
                    .define('H', Items.HAY_BLOCK)
                    .define('L', Items.REDSTONE_LAMP)
                    .define('P', ItemTags.PLANKS)
                    .group(Chickens.MOD_ID)
                    .unlockedBy("has_item", has(ModTags.Items.MOD_EGGS)), ModuleType.COMMON);

            provider.add(ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.OVOSCOPE.get())
                    .pattern("PPP")
                    .pattern("GEG")
                    .pattern("PCP")
                    .define('C', Items.COMPARATOR)
                    .define('E', ModTags.Items.MOD_EGGS)
                    .define('G', Items.GLASS_PANE)
                    .define('P', ItemTags.PLANKS)
                    .group(Chickens.MOD_ID)
                    .unlockedBy("has_item", has(ModTags.Items.MOD_EGGS)), ModuleType.COMMON);


            provider.add(SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModTags.Items.CHICKENS),
                    RecipeCategory.FOOD, Items.COOKED_CHICKEN, 0.35F, 200)
                    .group(Chickens.MOD_ID)
                    .unlockedBy("has_item", has(ModTags.Items.CHICKENS)), ModuleType.COMMON);

            return provider;
        });
    }
}
