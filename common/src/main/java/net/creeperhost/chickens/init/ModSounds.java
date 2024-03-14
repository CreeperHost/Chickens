package net.creeperhost.chickens.init;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.creeperhost.chickens.Chickens;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class ModSounds
{
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(Chickens.MOD_ID, Registries.SOUND_EVENT);
    public static final RegistrySupplier<SoundEvent> CHICKEN_IDLE_1 = SOUNDS.register("chicken_idle_1", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Chickens.MOD_ID, "chicken_idle_1")));
    public static final RegistrySupplier<SoundEvent> CHICKEN_IDLE_2 = SOUNDS.register("chicken_idle_2", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Chickens.MOD_ID, "chicken_idle_2")));
    public static final RegistrySupplier<SoundEvent> CHICKEN_IDLE_3 = SOUNDS.register("chicken_idle_3", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Chickens.MOD_ID, "chicken_idle_3")));
    public static final RegistrySupplier<SoundEvent> CHICKEN_IDLE_4 = SOUNDS.register("chicken_idle_4", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Chickens.MOD_ID, "chicken_idle_4")));

    public static SoundEvent getRandomIdleSound(Level level)
    {
        List<SoundEvent> soundEvents = new ArrayList<>();
        soundEvents.add(CHICKEN_IDLE_1.get());
        soundEvents.add(CHICKEN_IDLE_2.get());
        soundEvents.add(CHICKEN_IDLE_3.get());
        soundEvents.add(CHICKEN_IDLE_4.get());

        int random = level.getRandom().nextInt(0, 4);

        return soundEvents.get(random);
    }

}
