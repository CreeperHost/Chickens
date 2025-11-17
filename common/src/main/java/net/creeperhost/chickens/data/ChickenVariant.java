package net.creeperhost.chickens.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.creeperhost.chickens.Chickens;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by brandon3055 on 10/11/2025
 *
 * @param id           Internal chicken id
 * @param name         User readable variant name, e.g. Diamond or Coal
 * @param texture      Texture resource location
 * @param product      Product this chicken creates (Will need a product class that can handle items and fluids and stuff)
 * @param eggColour    Egg colour rgb
 * @param traitConfigs List of trait configs
 * @param parent1      First parent variant required to breed this chicken.
 * @param parent2      Second parent variant required to breed this chicken.
 */
public record ChickenVariant(String id, String name, ResourceLocation texture, ChickenProduct product, int eggColour, List<TraitConfig> traitConfigs, Optional<String> parent1, Optional<String> parent2) {
    /**Used as a fallback ic a chicken variant is no longer available*/
    public static final ChickenVariant MISSING = new ChickenVariant("_invalid_id_", "[Invalid or unknown chicken ID]", ResourceLocation.fromNamespaceAndPath(Chickens.MOD_ID, "textures/entity/invalid_chicken.png"), new ChickenProduct(ResourceLocation.parse("minecraft:air"), ChickenProduct.Type.ITEM, 1, 1), 0xf800f8, Collections.emptyList(), Optional.empty(), Optional.empty());

    public static final Codec<ChickenVariant> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            Codec.STRING.fieldOf("id").forGetter(ChickenVariant::id),
            Codec.STRING.fieldOf("name").forGetter(ChickenVariant::name),
            ResourceLocation.CODEC.fieldOf("texture").forGetter(ChickenVariant::texture),
            ChickenProduct.CODEC.fieldOf("product").forGetter(ChickenVariant::product),
            Codec.INT.fieldOf("eggColour").forGetter(ChickenVariant::eggColour),
            TraitConfig.CODEC.listOf().fieldOf("traitConfigs").forGetter(ChickenVariant::traitConfigs),
            Codec.STRING.optionalFieldOf("parent1").forGetter(ChickenVariant::parent1),
            Codec.STRING.optionalFieldOf("parent2").forGetter(ChickenVariant::parent2)
    ).apply(builder, ChickenVariant::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, ChickenVariant> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, ChickenVariant::id,
            ByteBufCodecs.STRING_UTF8, ChickenVariant::name,
            ResourceLocation.STREAM_CODEC, ChickenVariant::texture,
            ChickenProduct.STREAM_CODEC, ChickenVariant::product,
            ByteBufCodecs.INT, ChickenVariant::eggColour,
            TraitConfig.STREAM_CODEC.apply(ByteBufCodecs.list()), ChickenVariant::traitConfigs,
            ByteBufCodecs.optional(ByteBufCodecs.STRING_UTF8), ChickenVariant::parent1,
            ByteBufCodecs.optional(ByteBufCodecs.STRING_UTF8), ChickenVariant::parent2,
            ChickenVariant::new
    );

}
