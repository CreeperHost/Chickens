//package net.creeperhost.chickens.compat.rei;
//
//import me.shedaniel.rei.api.client.gui.Renderer;
//import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
//import me.shedaniel.rei.api.common.category.CategoryIdentifier;
//import me.shedaniel.rei.api.common.display.Display;
//import me.shedaniel.rei.api.common.entry.EntryIngredient;
//import me.shedaniel.rei.api.common.util.EntryStacks;
//import net.creeperhost.chickens.init.ModBlocks;
//import net.minecraft.network.chat.Component;
//
//import java.util.List;
//
//public class ChickenBreedingCategory implements DisplayCategory<ChickenBreedingCategory.ChickenBreedingDisplay>
//{
//    @Override
//    public Renderer getIcon()
//    {
//        return EntryStacks.of(ModBlocks.BREEDER.get());
//    }
//
//    @Override
//    public Component getTitle()
//    {
//        return Component.translatable("chickens.breeding");
//    }
//
//    @Override
//    public CategoryIdentifier<? extends ChickenBreedingDisplay> getCategoryIdentifier()
//    {
//        return ChickensCategorys.BREEDING;
//    }
//
//
//    public static class ChickenBreedingDisplay implements Display
//    {
//        @Override
//        public List<EntryIngredient> getInputEntries()
//        {
//            return null;
//        }
//
//        @Override
//        public List<EntryIngredient> getOutputEntries()
//        {
//            return null;
//        }
//
//        @Override
//        public CategoryIdentifier<?> getCategoryIdentifier()
//        {
//            return null;
//        }
//    }
//}
