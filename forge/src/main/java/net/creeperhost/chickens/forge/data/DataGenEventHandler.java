package net.creeperhost.chickens.forge.data;

import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import net.creeperhost.chickens.Chickens;
import net.creeperhost.chickens.api.ChickensRegistry;
import net.creeperhost.chickens.client.RenderChickensChicken;
import net.creeperhost.chickens.client.gui.BreederGui;
import net.creeperhost.chickens.client.gui.EggCrackerGui;
import net.creeperhost.chickens.client.gui.IncubatorGui;
import net.creeperhost.chickens.client.gui.OvoscopeGui;
import net.creeperhost.chickens.init.ModBlocks;
import net.creeperhost.chickens.init.ModEntities;
import net.creeperhost.chickens.init.ModItems;
import net.creeperhost.chickens.init.ModTags;
import net.creeperhost.chickens.item.ItemChickenEgg;
import net.creeperhost.polylib.PolyLib;
import net.creeperhost.polylib.forge.datagen.providers.DynamicTextureProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.recipes.*;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Created by brandon3055 on 23/02/2024
 */
@Mod.EventBusSubscriber (bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenEventHandler {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();

        if (event.includeClient()) {
            DynamicTextureProvider textureProvider = new DynamicTextureProvider(gen, event.getExistingFileHelper(), Chickens.MOD_ID);
            gen.addProvider(true, textureProvider);

            textureProvider.addDynamicTexture(new ResourceLocation(PolyLib.MOD_ID, "textures/gui/dynamic/gui_vanilla"), new ResourceLocation(Chickens.MOD_ID, "textures/gui/incubator"), IncubatorGui.GUI_WIDTH, IncubatorGui.GUI_HEIGHT, 4, 4, 4, 4);
            textureProvider.addDynamicTexture(new ResourceLocation(PolyLib.MOD_ID, "textures/gui/dynamic/gui_vanilla"), new ResourceLocation(Chickens.MOD_ID, "textures/gui/breeder"), BreederGui.GUI_WIDTH, BreederGui.GUI_HEIGHT, 4, 4, 4, 4);
            textureProvider.addDynamicTexture(new ResourceLocation(PolyLib.MOD_ID, "textures/gui/dynamic/gui_vanilla"), new ResourceLocation(Chickens.MOD_ID, "textures/gui/egg_cracker"), EggCrackerGui.GUI_WIDTH, EggCrackerGui.GUI_HEIGHT, 4, 4, 4, 4);
            textureProvider.addDynamicTexture(new ResourceLocation(PolyLib.MOD_ID, "textures/gui/dynamic/gui_vanilla"), new ResourceLocation(Chickens.MOD_ID, "textures/gui/ovoscope"), OvoscopeGui.GUI_WIDTH, OvoscopeGui.GUI_HEIGHT, 4, 4, 4, 4);
        }

        if (event.includeServer()) {
            gen.addProvider(true, new RecipeGenerator(gen.getPackOutput()));
            gen.addProvider(event.includeServer(), new LootTableProvider(event.getGenerator().getPackOutput(), Set.of(), List.of(new LootTableProvider.SubProviderEntry(BlockLootProvider::new, LootContextParamSets.BLOCK))));


            BlockTagGenerator blockGenerator = new BlockTagGenerator(gen.getPackOutput(), event.getLookupProvider(), Chickens.MOD_ID, event.getExistingFileHelper());
            gen.addProvider(true, blockGenerator);
            gen.addProvider(true, new ItemTagGenerator(gen.getPackOutput(), event.getLookupProvider(), blockGenerator.contentsGetter(), Chickens.MOD_ID, event.getExistingFileHelper()));
        }
    }

    public static class BlockLootProvider extends BlockLootSubProvider {

        protected BlockLootProvider() {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags());
        }

        @Override
        protected void generate() {
            dropSelf(ModBlocks.BREEDER);
            dropSelf(ModBlocks.EGG_CRACKER);
            dropSelf(ModBlocks.INCUBATOR);
            dropSelf(ModBlocks.OVOSCOPE);
        }

        protected void dropSelf(Supplier<? extends Block> pBlock) {
            super.dropSelf(pBlock.get());
        }

        protected void noDrop(Supplier<? extends Block> pBlock) {
            add(pBlock.get(), noDrop());
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return ForgeRegistries.BLOCKS.getEntries().stream().filter(e -> e.getKey().location().getNamespace().equals(Chickens.MOD_ID)).map(Map.Entry::getValue).collect(Collectors.toList());
        }
    }

    public static class BlockTagGenerator extends BlockTagsProvider {
        public BlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, modId, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider arg) {
        }
    }

    private static class ItemTagGenerator extends ItemTagsProvider {
        public ItemTagGenerator(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags, String modId, @Nullable ExistingFileHelper existingFileHelper) {
            super(pOutput, pLookupProvider, pBlockTags, modId, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider arg) {
            tag(ModTags.Items.MOD_EGGS).add(ModItems.CHICKEN_EGG.get());
            tag(ModTags.Items.CHICKENS).add(ModItems.CHICKEN_ITEM.get());
            tag(ModTags.Items.EGGS).add(ModItems.CHICKEN_EGG.get(), Items.EGG);
        }
    }

    public static class RecipeGenerator extends RecipeProvider {

        public RecipeGenerator(PackOutput arg) {
            super(arg);
        }

        @Override
        protected void buildRecipes(RecipeOutput arg) {
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BREEDER.get())
                    .pattern("PPP")
                    .pattern("PSP")
                    .pattern("HHH")
                    .define('P', ItemTags.PLANKS)
                    .define('S', Items.WHEAT_SEEDS)
                    .define('H', Items.HAY_BLOCK)
                    .unlockedBy("has_seeds", has(Items.WHEAT_SEEDS))
                    .save(arg);

            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.INCUBATOR.get())
                    .pattern("PLP")
                    .pattern("GEG")
                    .pattern("HHH")
                    .define('P', ItemTags.PLANKS)
                    .define('L', Items.REDSTONE_LAMP)
                    .define('G', Items.GLASS_PANE)
                    .define('E', ModTags.Items.MOD_EGGS)
                    .define('H', Items.HAY_BLOCK)
                    .unlockedBy("has_breeder", has(ModItems.BREEDER.get()))
                    .save(arg);

            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.EGG_CRACKER.get())
                    .pattern("IPI")
                    .pattern("IEI")
                    .pattern("ICI")
                    .define('I', Items.IRON_INGOT)
                    .define('P', Items.PISTON)
                    .define('E', ModTags.Items.MOD_EGGS)
                    .define('C', Items.CHEST)
                    .unlockedBy("has_breeder", has(ModItems.BREEDER.get()))
                    .save(arg);

            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.OVOSCOPE.get())
                    .pattern("PPP")
                    .pattern("GEG")
                    .pattern("PCP")
                    .define('P', ItemTags.PLANKS)
                    .define('G', Items.GLASS_PANE)
                    .define('E', ModTags.Items.MOD_EGGS)
                    .define('C', Items.COMPARATOR)
                    .unlockedBy("has_breeder", has(ModItems.BREEDER.get()))
                    .save(arg);

            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CATCHER_ITEM.get())
                    .pattern(" E ")
                    .pattern(" S ")
                    .pattern(" F ")
                    .define('E', ModTags.Items.EGGS)
                    .define('S', Items.STICK)
                    .define('F', Items.FEATHER)
                    .unlockedBy("has_stick", has(Items.STICK))
                    .save(arg);

            SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModTags.Items.CHICKENS), RecipeCategory.FOOD, Items.COOKED_CHICKEN, 0.35F, 200)
                    .unlockedBy("has_chicken", has(ModTags.Items.CHICKENS))
                    .save(arg, new ResourceLocation(Chickens.MOD_ID, "cooked_chicken"));
        }
    }
}
