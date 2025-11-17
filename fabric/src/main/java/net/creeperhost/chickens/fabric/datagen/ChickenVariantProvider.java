package net.creeperhost.chickens.fabric.datagen;

import net.creeperhost.chickens.Chickens;
import net.creeperhost.chickens.data.ChickenProduct;
import net.creeperhost.chickens.data.ChickenVariant;
import net.creeperhost.chickens.data.TraitConfig;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricCodecDataProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

/**
 * This is based on Fabric's FabricCodecDataProvider
 * <p>
 * Created by brandon3055 on 10/11/2025
 */
public class ChickenVariantProvider extends FabricCodecDataProvider<ChickenVariant> {
    protected ChickenVariantProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(dataOutput, registriesFuture, PackOutput.Target.DATA_PACK, "variants", ChickenVariant.CODEC);
    }

    private void addChickens(BiConsumer<ResourceLocation, ChickenVariant> consumer, HolderLookup.Provider provider) {
        simple(provider, "black", "Black", Items.BLACK_DYE)
                .build(consumer);
        simple(provider, "blue", "Blue", Items.BLUE_DYE)
                .build(consumer);
        simple(provider, "brown", "Brown", Items.BROWN_DYE)
                .build(consumer);
        simple(provider, "cyan", "Cyan", Items.CYAN_DYE)
                .build(consumer);
        simple(provider, "gray", "Gray", Items.GRAY_DYE)
                .build(consumer);
        simple(provider, "green", "Green", Items.GREEN_DYE)
                .build(consumer);
        simple(provider, "light_blue", "Light Blue", Items.LIGHT_BLUE_DYE)
                .build(consumer);
        simple(provider, "light_gray", "Light Gray", Items.LIGHT_GRAY_DYE)
                .build(consumer);
        simple(provider, "lime", "Lime", Items.LIME_DYE)
                .build(consumer);
        simple(provider, "magenta", "Magenta", Items.MAGENTA_DYE)
                .build(consumer);
        simple(provider, "orange", "Orange", Items.ORANGE_DYE)
                .build(consumer);
        simple(provider, "pink", "Pink", Items.PINK_DYE)
                .build(consumer);
        simple(provider, "purple", "Purple", Items.PURPLE_DYE)
                .build(consumer);
        simple(provider, "red", "Red", Items.RED_DYE)
                .build(consumer);
        simple(provider, "white", "White", Items.WHITE_DYE)
                .build(consumer);
        simple(provider, "yellow", "Yellow", Items.YELLOW_DYE)
                .build(consumer);

        simple(provider, "blaze", "Blaze", Items.BLAZE_ROD)
                .build(consumer);
        simple(provider, "clay", "Clay", Items.CLAY)
                .build(consumer);
        simple(provider, "coal", "Coal", Items.COAL)
                .build(consumer);
        simple(provider, "copper", "Copper", Items.COPPER_INGOT)
                .build(consumer);
        simple(provider, "diamond", "Diamond", Items.DIAMOND)
                .build(consumer);
        simple(provider, "emerald", "Emerald", Items.EMERALD)
                .build(consumer);
        simple(provider, "ender", "Ender", Items.ENDER_PEARL)
                .build(consumer);
        simple(provider, "flint", "Flint", Items.FLINT)
                .build(consumer);
        simple(provider, "ghast", "Ghast", Items.GHAST_TEAR)
                .build(consumer);
        simple(provider, "glass", "Glass", Items.GLASS)
                .build(consumer);
        simple(provider, "glowstone", "Glowstone", Items.GLOWSTONE)
                .build(consumer);
        simple(provider, "gold", "Gold", Items.GOLD_INGOT)
                .build(consumer);
        simple(provider, "gunpowder", "Gunpowder", Items.GUNPOWDER)
                .build(consumer);
        simple(provider, "iron", "Iron", Items.IRON_INGOT)
                .build(consumer);
        simple(provider, "lapis", "Lapis", Items.LAPIS_LAZULI)
                .build(consumer);

        simple(provider, "lava", "Lava", Fluids.LAVA)
                .build(consumer);

        simple(provider, "leather", "Leather", Items.LEATHER)
                .build(consumer);
        simple(provider, "log", "Log", Items.OAK_LOG)
                .build(consumer);
        simple(provider, "magma", "Magma", Items.MAGMA_CREAM)
                .build(consumer);
        simple(provider, "netherite", "Netherite", Items.NETHERITE_SCRAP)
                .build(consumer);
        simple(provider, "netherwart", "Netherwart", Items.NETHER_WART)
                .build(consumer);
        simple(provider, "obsidian", "Obsidian", Items.OBSIDIAN)
                .build(consumer);
        simple(provider, "pcrystal", "Prismarine Crystal", Items.PRISMARINE_CRYSTALS)
                .build(consumer);
        simple(provider, "pshard", "Prismarine Shard", Items.PRISMARINE_SHARD)
                .build(consumer);
        simple(provider, "quartz", "Quartz", Items.QUARTZ)
                .build(consumer);
        simple(provider, "redstone", "Redstone", Items.REDSTONE)
                .build(consumer);
        simple(provider, "sand", "Sand", Items.SAND)
                .build(consumer);
        simple(provider, "slime", "Slime", Items.SLIME_BALL)
                .build(consumer);
        simple(provider, "smart", "Smart", Items.EGG)
                .build(consumer);
        simple(provider, "snowball", "Snowball", Items.SNOWBALL)
                .build(consumer);
        simple(provider, "soulsand", "Soulsand", Items.SOUL_SAND)
                .build(consumer);
        simple(provider, "string", "String", Items.STRING)
                .build(consumer);
        simple(provider, "water", "Water", Fluids.LAVA)
                .build(consumer);
    }


    @Override
    protected void configure(BiConsumer<ResourceLocation, ChickenVariant> consumer, HolderLookup.Provider provider) {
        Map<String, ChickenVariant> variantMap = new HashMap<>();
        addChickens((location, chickenVariant) -> {
            if (variantMap.containsKey(chickenVariant.id())) {
                throw new IllegalArgumentException("Duplicate chicken id " + chickenVariant.id());
            }
            variantMap.put(chickenVariant.id(), chickenVariant);
            consumer.accept(location, chickenVariant);
        }, provider);

        for (ChickenVariant value : variantMap.values()) {
            value.parent1().ifPresent(s -> {
                if (!variantMap.containsKey(s)) {
                    throw new IllegalArgumentException("Could not find parent id " + s + ", for chicken " + value.id());
                }
            });
            value.parent2().ifPresent(s -> {
                if (!variantMap.containsKey(s)) {
                    throw new IllegalArgumentException("Could not find parent id " + s + ", for chicken " + value.id());
                }
            });
        }
    }


    //TODO this is just a quick helper for the initial data gen. Once we start balancing things, this method will likely go away.
    private Builder simple(HolderLookup.Provider provider, String id, String name, Item item) {
        return builder(provider, id, name)
                .itemProduct(item, 1, 1)
                .trait("speed", 0.25, 1, 1, 10, 1);
    }

    private Builder simple(HolderLookup.Provider provider, String id, String name, Fluid fluid) {
        return builder(provider, id, name)
                .fluidProduct(fluid, 1000, 1000)//TODO, this is not going to work with fabric... Maybe I should use mb for this even on fabric?
                .trait("speed", 0.25, 1, 1, 10, 1);
    }

    @Override
    public String getName() {
        return "chickens:chicken-variants";
    }

    private static Builder builder(HolderLookup.Provider provider, String id, String name) {
        return new Builder(provider, id, name);
    }

    @Override
    public CompletableFuture<?> run(CachedOutput writer) {
        return super.run(writer);
    }

    private static class Builder {
        private final HolderLookup.Provider provider;
        private final String id;
        private final String name;
        private ResourceLocation texture;
        private ChickenProduct product = ChickenProduct.EMPTY;
        private int colour = 0xFFFFFFFF;
        private final List<TraitConfig> traits = new ArrayList<>();
        private String parent1 = null;
        private String parent2 = null;

        public Builder(HolderLookup.Provider provider, String id, String name) {
            this.provider = provider;
            this.id = id;
            this.name = name;
            this.texture = ResourceLocation.fromNamespaceAndPath(Chickens.MOD_ID, "textures/entity/" + id + "_chicken.png");
        }

        public Builder parents(String parent1, String parent2) {
            this.parent1 = parent1;
            this.parent2 = parent2;
            return this;
        }

        public Builder texture(ResourceLocation texture) {
            this.texture = texture;
            return this;
        }

        public Builder itemProduct(Item item, int min, int max) {
            this.product = new ChickenProduct(item.builtInRegistryHolder().key().location(), ChickenProduct.Type.ITEM, min, max);
            return this;
        }

        public Builder fluidProduct(Fluid fluid, int min, int max) {
            this.product = new ChickenProduct(fluid.builtInRegistryHolder().key().location(), ChickenProduct.Type.FLUID, min, max);
            return this;
        }

        public Builder eggColour(int colour) {
            this.colour = colour;
            return this;
        }

        public Builder trait(String id, double spawnMin, double spawnMax, double evoRate, double evoLimit, double evoExpo) {
            traits.forEach(e -> {
                if (e.id().endsWith(id)) throw new IllegalArgumentException("Duplicate trait for chicken " + Builder.this.id + ", trait: " + id);
            });
            traits.add(new TraitConfig(id, spawnMin, spawnMax, evoRate, evoLimit, evoExpo));
            return this;
        }

        public void build(BiConsumer<ResourceLocation, ChickenVariant> consumer) {
            consumer.accept(ResourceLocation.fromNamespaceAndPath(Chickens.MOD_ID, id), new ChickenVariant(id, name, texture, product, colour, traits, Optional.ofNullable(parent1), Optional.ofNullable(parent2)));
        }
    }
}
