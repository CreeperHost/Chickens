package net.creeperhost.chickens.client;

import com.mojang.serialization.MapCodec;
import net.creeperhost.chickens.api.ChickensRegistryItem;
import net.creeperhost.chickens.item.ItemChickenEgg;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public final class ChickenEggTint implements ItemTintSource {
    public static final ChickenEggTint INSTANCE = new ChickenEggTint();
    public static final MapCodec<ChickenEggTint> MAP_CODEC;

    private ChickenEggTint() {
    }

    @Override
    public int calculate(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity) {
        if (stack.getItem() instanceof ItemChickenEgg eggItem) {
            ChickensRegistryItem item = eggItem.getType(stack);
            if (item != null) {
                return ARGB.opaque(item.getBgColor());
            }
        }
        return -1;
    }

    @Override
    public MapCodec<? extends ItemTintSource> type() {
        return MAP_CODEC;
    }

    static {
        MAP_CODEC = MapCodec.unit(INSTANCE);
    }
}