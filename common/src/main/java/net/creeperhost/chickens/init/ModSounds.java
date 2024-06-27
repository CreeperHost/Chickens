package net.creeperhost.chickens.init;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.creeperhost.chickens.Chickens;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;

import java.util.List;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(Chickens.MOD_ID, Registries.SOUND_EVENT);
    public static final RegistrySupplier<SoundEvent> CHICKEN_IDLE_1 = SOUNDS.register("chicken_idle_1", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Chickens.MOD_ID, "chicken_idle_1")));
    public static final RegistrySupplier<SoundEvent> CHICKEN_IDLE_2 = SOUNDS.register("chicken_idle_2", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Chickens.MOD_ID, "chicken_idle_2")));
    public static final RegistrySupplier<SoundEvent> CHICKEN_IDLE_3 = SOUNDS.register("chicken_idle_3", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Chickens.MOD_ID, "chicken_idle_3")));
    public static final RegistrySupplier<SoundEvent> CHICKEN_IDLE_4 = SOUNDS.register("chicken_idle_4", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Chickens.MOD_ID, "chicken_idle_4")));
    private static final List<RegistrySupplier<SoundEvent>> CHICKEN_SOUNDS = List.of(CHICKEN_IDLE_1, CHICKEN_IDLE_2, CHICKEN_IDLE_3, CHICKEN_IDLE_4);

    public static SoundEvent getRandomIdleSound(Level level) {
        return CHICKEN_SOUNDS.get(level.getRandom().nextInt(CHICKEN_SOUNDS.size())).get();
    }
}
