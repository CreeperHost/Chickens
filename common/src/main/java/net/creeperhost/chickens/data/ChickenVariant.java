package net.creeperhost.chickens.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

/**
 * Created by brandon3055 on 10/11/2025
 *
 * @param id           Internal chicken id
 * @param name         User readable variant name, e.g. Diamond or Coal
 * @param texture      Texture resource location
 * @param product      Product this chicken creates (Will need a product class that can handle items and fluids and stuff)
 * @param eggColour    Egg colour rgb
 * @param traitConfigs List of trait configs
 */
public record ChickenVariant(String id, String name, ResourceLocation texture, ChickenProduct product, int eggColour, List<TraitConfig> traitConfigs) {

    public static final Codec<ChickenVariant> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            Codec.STRING.fieldOf("id").forGetter(ChickenVariant::id),
            Codec.STRING.fieldOf("name").forGetter(ChickenVariant::name),
            ResourceLocation.CODEC.fieldOf("texture").forGetter(ChickenVariant::texture),
            ChickenProduct.CODEC.fieldOf("product").forGetter(ChickenVariant::product),
            Codec.INT.fieldOf("eggColour").forGetter(ChickenVariant::eggColour),
            TraitConfig.CODEC.listOf().fieldOf("traitConfigs").forGetter(ChickenVariant::traitConfigs)
    ).apply(builder, ChickenVariant::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, ChickenVariant> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, ChickenVariant::id,
            ByteBufCodecs.STRING_UTF8, ChickenVariant::name,
            ResourceLocation.STREAM_CODEC, ChickenVariant::texture,
            ChickenProduct.STREAM_CODEC, ChickenVariant::product,
            ByteBufCodecs.INT, ChickenVariant::eggColour,
            TraitConfig.STREAM_CODEC.apply(ByteBufCodecs.list()), ChickenVariant::traitConfigs,
            ChickenVariant::new
    );

}
