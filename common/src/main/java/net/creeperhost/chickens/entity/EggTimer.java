package net.creeperhost.chickens.entity;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent;
import dev.architectury.event.events.common.TickEvent;
import net.creeperhost.chickens.config.Config;
import net.creeperhost.chickens.item.ItemChickenEgg;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.WeakHashMap;

/**
 * Created by brandon3055 on 06/06/2024
 */
public class EggTimer {

    private static final WeakHashMap<ItemEntity, Long> EGGS = new WeakHashMap<>();

    public static void init() {
        EntityEvent.ADD.register(EggTimer::entitySpawn);
        TickEvent.SERVER_PRE.register(EggTimer::serverTick);
    }

    private static EventResult entitySpawn(Entity entity, Level level) {
        if (entity instanceof ItemEntity item) {
            ItemStack stack = item.getItem();
            if (stack.getItem() instanceof ItemChickenEgg egg && egg.isViable(stack)) {
                EGGS.put(item, System.currentTimeMillis());
            }
        }

        return EventResult.pass();
    }

    private static void serverTick(MinecraftServer server) {
        EGGS.entrySet().removeIf(e -> {
            if (System.currentTimeMillis() > e.getValue() + (Config.INSTANCE.eggItemMaxTimeOnGround * 1000L)) {
                ItemStack stack = e.getKey().getItem();
                if (stack.getItem() instanceof ItemChickenEgg egg) {
                    egg.setNotViable(stack);
                }
                return true;
            }
            return false;
        });
    }

}
