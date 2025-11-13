package net.creeperhost.chickens.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.StringRepresentable;

import java.util.function.IntFunction;

/**
 * Created by brandon3055 on 10/11/2025
 *
 * @param id   Item or Fluid registry name
 * @param type (ITEM, FLUID)
 * @param min  Minimum amount that can be obtained from an egg (Integer stack size / millibuckets)
 * @param max  Maximum amount that can be obtained from an egg (Integer stack size / millibuckets)
 *             Amount obtained will be a random value between min and max
 */
public record ChickenProduct(ResourceLocation id, Type type, int min, int max) {

    public static final ChickenProduct EMPTY = new ChickenProduct(ResourceLocation.withDefaultNamespace("air"), Type.ITEM, 0, 0);

    public static final Codec<ChickenProduct> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            ResourceLocation.CODEC.fieldOf("id").forGetter(ChickenProduct::id),
            Type.CODEC.fieldOf("type").forGetter(ChickenProduct::type),
            Codec.INT.fieldOf("min").forGetter(ChickenProduct::min),
            Codec.INT.fieldOf("max").forGetter(ChickenProduct::max)
    ).apply(builder, ChickenProduct::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, ChickenProduct> STREAM_CODEC = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC, ChickenProduct::id,
            Type.STREAM_CODEC, ChickenProduct::type,
            ByteBufCodecs.INT, ChickenProduct::min,
            ByteBufCodecs.INT, ChickenProduct::max,
            ChickenProduct::new
    );

    public enum Type implements StringRepresentable {
        ITEM,
        FLUID;

        public static final StringRepresentable.EnumCodec<Type> CODEC = StringRepresentable.fromEnum(Type::values);
        public static final IntFunction<Type> BY_ID = ByIdMap.continuous(Type::ordinal, values(), ByIdMap.OutOfBoundsStrategy.WRAP);
        public static final StreamCodec<ByteBuf, Type> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, Type::ordinal);

        @Override
        public String getSerializedName() {
            return this.name();
        }
    }
}
