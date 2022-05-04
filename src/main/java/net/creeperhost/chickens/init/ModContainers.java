package net.creeperhost.chickens.init;

import net.creeperhost.chickens.ChickensMod;
import net.creeperhost.chickens.containers.ContainerBreeder;
import net.creeperhost.chickens.containers.ContainerHenhouse;
import net.creeperhost.chickens.containers.ContainerRoost;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModContainers
{
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, ChickensMod.MODID);
    public static final RegistryObject<MenuType<ContainerHenhouse>> HENHOUSE_CONTAINER = CONTAINERS.register("container_henhouse", () -> IForgeMenuType.create(ContainerHenhouse::new));
    public static final RegistryObject<MenuType<ContainerBreeder>> BREEDER_CONTAINER = CONTAINERS.register("container_breeder", () -> IForgeMenuType.create(ContainerBreeder::new));
    public static final RegistryObject<MenuType<ContainerRoost>> ROOST_CONTAINER = CONTAINERS.register("container_roost", () -> IForgeMenuType.create(ContainerRoost::new));
}
