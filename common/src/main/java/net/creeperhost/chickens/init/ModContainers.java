package net.creeperhost.chickens.init;

import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.creeperhost.chickens.Chickens;
import net.creeperhost.chickens.containers.ContainerBreeder;
import net.creeperhost.chickens.containers.ContainerEggCracker;
import net.creeperhost.chickens.containers.ContainerIncubator;
import net.creeperhost.chickens.containers.ContainerOvoscope;
import net.minecraft.core.Registry;
import net.minecraft.world.inventory.MenuType;


public class ModContainers
{
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(Chickens.MOD_ID, Registry.MENU_REGISTRY);
    public static final RegistrySupplier<MenuType<ContainerBreeder>> BREEDER_CONTAINER = CONTAINERS.register("container_breeder", () -> MenuRegistry.ofExtended(ContainerBreeder::new));
    public static final RegistrySupplier<MenuType<ContainerIncubator>> INCUBATOR = CONTAINERS.register("incubator", () -> MenuRegistry.ofExtended(ContainerIncubator::new));
    public static final RegistrySupplier<MenuType<ContainerEggCracker>> EGG_CRACKER = CONTAINERS.register("egg_cracker", () -> MenuRegistry.ofExtended(ContainerEggCracker::new));
    public static final RegistrySupplier<MenuType<ContainerOvoscope>> OVOSCOPE = CONTAINERS.register("ovoscope", () -> MenuRegistry.ofExtended(ContainerOvoscope::new));

}
