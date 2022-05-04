package net.creeperhost.chickens.entity;

import net.creeperhost.chickens.init.ModEntities;
import net.creeperhost.chickens.init.ModItems;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class EntityColoredEgg extends ThrowableItemProjectile
{
    private static final EntityDataAccessor<String> CHICKEN_TYPE = SynchedEntityData.defineId(EntityColoredEgg.class, EntityDataSerializers.STRING);
    public static final String TYPE_NBT = "Type";

    private static ItemStack itemstack = ItemStack.EMPTY;

    public EntityColoredEgg(Level p_37394_, double p_37395_, double p_37396_, double p_37397_)
    {
        super(ModEntities.EGG.get(), p_37395_, p_37396_, p_37397_, p_37394_);
    }

    public EntityColoredEgg(Level worldIn)
    {
        super(ModEntities.EGG.get(), worldIn);
    }

    public EntityColoredEgg(Level level, LivingEntity livingEntity)
    {
        super(ModEntities.EGG.get(), livingEntity, level);
    }

    public EntityColoredEgg(EntityType<EntityColoredEgg> entityColoredEggEntityType, Level level)
    {
        super(entityColoredEggEntityType, level);
    }


    public void setChickenType(String type)
    {
        this.entityData.set(CHICKEN_TYPE, type);
    }

    private String getChickenType()
    {
        return this.entityData.get(CHICKEN_TYPE);
    }

    @Override
    public void defineSynchedData()
    {
        super.defineSynchedData();
        entityData.define(CHICKEN_TYPE, "");
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tagCompound)
    {
        super.addAdditionalSaveData(tagCompound);
        tagCompound.putString(TYPE_NBT, getChickenType());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tagCompound)
    {
        super.readAdditionalSaveData(tagCompound);
        setChickenType(tagCompound.getString(TYPE_NBT));
    }

    @Override
    public void onHitEntity(EntityHitResult entityHitResult)
    {
        super.onHitEntity(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        int i = entity instanceof Blaze ? 3 : 0;
        entity.hurt(DamageSource.thrown(this, this.getOwner()), (float) i);
    }

    @Override
    public void onHit(HitResult hitResult)
    {
        super.onHit(hitResult);
        if (!this.level.isClientSide)
        {
            if (this.random.nextInt(8) == 0)
            {
                int i = 1;
                if (this.random.nextInt(32) == 0)
                {
                    i = 4;
                }

                for (int j = 0; j < i; ++j)
                {
                    try
                    {
                        ResourceLocation resourceLocation = ResourceLocation.tryParse(getChickenType());
                        EntityType<?> entityType = Registry.ENTITY_TYPE.get(resourceLocation);
                        EntityChickensChicken chicken = (EntityChickensChicken) entityType.create(level);
                        chicken.setPos(hitResult.getLocation());
                        level.addFreshEntity(chicken);
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }

        this.level.broadcastEntityEvent(this, (byte) 3);
        this.discard();
    }

    @Override
    public Item getDefaultItem()
    {
        return ModItems.COLOURED_EGG.get();
    }
}
