package net.creeperhost.chickens.entity;

import net.creeperhost.chickens.data.ChickenDataManager;
import net.creeperhost.chickens.data.ChickenVariant;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

/**
 * Created by brandon3055 on 17/11/2025
 */
public class ChickensChicken extends Chicken {

    private static final EntityDataAccessor<String> CHICKEN_VARIANT = SynchedEntityData.defineId(ChickensChicken.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Boolean> IS_ROOSTER = SynchedEntityData.defineId(ChickensChicken.class, EntityDataSerializers.BOOLEAN);

    public ChickensChicken(EntityType<? extends Chicken> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(CHICKEN_VARIANT, ChickenVariant.MISSING.id());//TODO, would like to pick a random naturally spawned chicken, but need to figure out spawning first.
        builder.define(IS_ROOSTER, false);
    }

    public void setRooster(boolean isRooster) {
        this.entityData.set(IS_ROOSTER, isRooster);
    }

    public boolean isRooster() {
        return this.entityData.get(IS_ROOSTER);
    }

    public void setChickenVariant(ChickenVariant variant) {
        this.entityData.set(CHICKEN_VARIANT, variant.id());
    }

    public ChickenVariant getChickenVariant() {
        return ChickenDataManager.getVariantOrMissing(this.entityData.get(CHICKEN_VARIANT));
    }

    public void setVariantString(String variantId) {
        this.entityData.set(CHICKEN_VARIANT, variantId);
    }

    public String getVariantString() {
        return this.entityData.get(CHICKEN_VARIANT);
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putString("chicken_variant", getVariantString());
        output.putBoolean("is_rooster", isRooster());
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        setVariantString(input.getStringOr("chicken_variant", ChickenVariant.MISSING.id()));
        setRooster(input.getBooleanOr("is_rooster", false));
    }
}
