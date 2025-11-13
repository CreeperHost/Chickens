package net.creeperhost.chickens.fabric.datagen;

import com.mojang.serialization.Codec;
import net.creeperhost.chickens.Chickens;
import net.creeperhost.chickens.data.ChickenProduct;
import net.creeperhost.chickens.data.ChickenVariant;
import net.creeperhost.chickens.data.TraitConfig;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricCodecDataProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;

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

    @Override
    protected void configure(BiConsumer<ResourceLocation, ChickenVariant> consumer, HolderLookup.Provider provider) {
        builder(provider, "diamond", "Diamond")
                .itemProduct(Items.DIAMOND, 1, 1)
                .trait("speed", 0.25, 1, 1, 10, 1)
                .build(consumer);

    }

    @Override
    public String getName() {
        return "chickens:chicken-variants";
    }

    private static Builder builder(HolderLookup.Provider provider, String id, String name) {
        return new Builder(provider, id, name);
    }

    private static class Builder {
        private final HolderLookup.Provider provider;
        private final String id;
        private final String name;
        private ResourceLocation texture;
        private ChickenProduct product = ChickenProduct.EMPTY;
        private int colour = 0xFFFFFFFF;
        private final List<TraitConfig> traits = new ArrayList<>();

        public Builder(HolderLookup.Provider provider, String id, String name) {
            this.provider = provider;
            this.id = id;
            this.name = name;
            this.texture = ResourceLocation.fromNamespaceAndPath(Chickens.MOD_ID, "textures/entity/" + id);
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
            consumer.accept(ResourceLocation.fromNamespaceAndPath(Chickens.MOD_ID, id), new ChickenVariant(id, name, texture, product, colour, traits));
        }
    }
}
