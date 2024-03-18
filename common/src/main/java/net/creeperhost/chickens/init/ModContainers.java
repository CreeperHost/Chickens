package net.creeperhost.chickens.init;

import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.creeperhost.chickens.Chickens;
import net.creeperhost.chickens.containers.BreederMenu;
import net.creeperhost.chickens.containers.EggCrackerMenu;
import net.creeperhost.chickens.containers.IncubatorMenu;
import net.creeperhost.chickens.containers.OvoscopeMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;


public class ModContainers {
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(Chickens.MOD_ID, Registries.MENU);
    public static final RegistrySupplier<MenuType<BreederMenu>> BREEDER_CONTAINER = CONTAINERS.register("container_breeder", () -> MenuRegistry.ofExtended(BreederMenu::new));
    public static final RegistrySupplier<MenuType<IncubatorMenu>> INCUBATOR = CONTAINERS.register("incubator", () -> MenuRegistry.ofExtended(IncubatorMenu::new));
    public static final RegistrySupplier<MenuType<EggCrackerMenu>> EGG_CRACKER = CONTAINERS.register("egg_cracker", () -> MenuRegistry.ofExtended(EggCrackerMenu::new));
    public static final RegistrySupplier<MenuType<OvoscopeMenu>> OVOSCOPE = CONTAINERS.register("ovoscope", () -> MenuRegistry.ofExtended(OvoscopeMenu::new));
}
