package net.creeperhost.chickens.init;

import com.mojang.serialization.Codec;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.creeperhost.chickens.Chickens;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;

public class ModComponentTypes {
    public static final DeferredRegister<DataComponentType<?>> COMPONENTS = DeferredRegister.create(Chickens.MOD_ID, Registries.DATA_COMPONENT_TYPE);

    //Chickens
    public static final RegistrySupplier<DataComponentType<String>> CHICKEN_TYPE = COMPONENTS.register("chicken_type", () -> DataComponentType.<String>builder().
            persistent(Codec.STRING.orElse("")).networkSynchronized(ByteBufCodecs.STRING_UTF8).build());

    public static final RegistrySupplier<DataComponentType<Integer>> CHICKENS_GAIN = COMPONENTS.register("gain", () -> DataComponentType.<Integer>builder().
            persistent(Codec.INT.orElse(0)).networkSynchronized(ByteBufCodecs.INT).build());

    public static final RegistrySupplier<DataComponentType<Integer>> CHICKENS_GROWTH = COMPONENTS.register("growth", () -> DataComponentType.<Integer>builder().
            persistent(Codec.INT.orElse(0)).networkSynchronized(ByteBufCodecs.INT).build());

    public static final RegistrySupplier<DataComponentType<Integer>> CHICKENS_STRENGTH = COMPONENTS.register("strength", () -> DataComponentType.<Integer>builder().
            persistent(Codec.INT.orElse(0)).networkSynchronized(ByteBufCodecs.INT).build());

    public static final RegistrySupplier<DataComponentType<Integer>> CHICKENS_LIFESPAN = COMPONENTS.register("lifespan", () -> DataComponentType.<Integer>builder().
            persistent(Codec.INT.orElse(0)).networkSynchronized(ByteBufCodecs.INT).build());

    public static final RegistrySupplier<DataComponentType<Integer>> LOVE = COMPONENTS.register("love", () -> DataComponentType.<Integer>builder().
            persistent(Codec.INT.orElse(0)).networkSynchronized(ByteBufCodecs.INT).build());

    public static final RegistrySupplier<DataComponentType<Boolean>> IS_BABY = COMPONENTS.register("is_baby", () -> DataComponentType.<Boolean>builder().
            persistent(Codec.BOOL.orElse(false)).networkSynchronized(ByteBufCodecs.BOOL).build());


    //Eggs
    public static final RegistrySupplier<DataComponentType<Boolean>> EGG_VIABLE = COMPONENTS.register("viable", () -> DataComponentType.<Boolean>builder().
            persistent(Codec.BOOL.orElse(false)).networkSynchronized(ByteBufCodecs.BOOL).build());

    public static final RegistrySupplier<DataComponentType<Integer>> EGG_MISSED = COMPONENTS.register("missed", () -> DataComponentType.<Integer>builder().
            persistent(Codec.INT.orElse(0)).networkSynchronized(ByteBufCodecs.INT).build());

    public static final RegistrySupplier<DataComponentType<Integer>> EGG_PROGRESS = COMPONENTS.register("progress", () -> DataComponentType.<Integer>builder().
            persistent(Codec.INT.orElse(0)).networkSynchronized(ByteBufCodecs.INT).build());

    public static final RegistrySupplier<DataComponentType<String>> EGG_CHICKEN_TYPE = COMPONENTS.register("egg_chicken_type", () -> DataComponentType.<String>builder().
            persistent(Codec.STRING.orElse("")).networkSynchronized(ByteBufCodecs.STRING_UTF8).build());
}
