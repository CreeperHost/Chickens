package net.creeperhost.chickens.data;


import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

/**
 * Created by brandon3055 on 10/11/2025
 *
 * @param id       The internal trait ID that this config is referring to.
 * @param spawnMin The minimum trait value for natural spawned chickens.
 * @param spawnMax The maximum trait value for natural spawned chickens,
 *                 If min and max are both zero, then chickens will not spawn with this trait.
 * @param evoRate  Controls the rate that this trait will increase with breeding, (Also effected by breeding modifier)
 * @param evoLimit "Theoretical" trait value limit, though it won't actually be possible to reach this value.
 * @param evoExpo  The closer a trait value is to its evoLimit, the harder it is to progress further. Meaning its likely impossible to actually ever get to the 'evoLimit' due to diminishing returns.
 *                 This value controls the difficulty curve. A value of 1 will result in a linear difficulty increase from 0 to evoLimit
 *                 A value greater than 1 will reduce the difficulty early on, but the difficulty will increase exponentially the closer you get to 'evoLimit', the further you increase this value, the further you will push back that exponential curve.
 *                 A value less than 1 will cause the opposite effect, the difficulty will increase rapidly early on, before tapering off.
 *                 The equation for evolution difficulty is: (traitValue / evoLimit) ^ evoExponent (Once the result of this equation reaches 1, no further trait progression is possible)
 *                 Example difficulty curves <a href="https://ss.brandon3055.com/0f888.png">value: 1</a>, <a href="https://ss.brandon3055.com/fb81a.png">value: 5</a>, <a href="https://ss.brandon3055.com/78c39.png">value: 0.5</a>, <a href="https://ss.brandon3055.com/63952.png">value: 0.1</a>
 */
public record TraitConfig(String id, double spawnMin, double spawnMax, double evoRate, double evoLimit, double evoExpo) {

    public static final Codec<TraitConfig> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            Codec.STRING.fieldOf("id").forGetter(TraitConfig::id),
            Codec.DOUBLE.fieldOf("spawnMin").forGetter(TraitConfig::spawnMin),
            Codec.DOUBLE.fieldOf("spawnMax").forGetter(TraitConfig::spawnMax),
            Codec.DOUBLE.fieldOf("evoRate").forGetter(TraitConfig::evoRate),
            Codec.DOUBLE.fieldOf("evoLimit").forGetter(TraitConfig::evoLimit),
            Codec.DOUBLE.fieldOf("evoExpo").forGetter(TraitConfig::evoExpo)
    ).apply(builder, TraitConfig::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, TraitConfig> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, TraitConfig::id,
            ByteBufCodecs.DOUBLE, TraitConfig::spawnMin,
            ByteBufCodecs.DOUBLE, TraitConfig::spawnMax,
            ByteBufCodecs.DOUBLE, TraitConfig::evoRate,
            ByteBufCodecs.DOUBLE, TraitConfig::evoLimit,
            ByteBufCodecs.DOUBLE, TraitConfig::evoExpo,
            TraitConfig::new
    );
}
