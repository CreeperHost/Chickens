package net.creeperhost.chickens.entity;

import net.creeperhost.chickens.api.ChickensRegistry;
import net.creeperhost.chickens.api.ChickensRegistryItem;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class EntityChickensChicken extends Chicken
{
    private static final EntityDataAccessor<String> CHICKEN_TYPE;
    private static final EntityDataAccessor<Boolean> CHICKEN_STATS_ANALYZED;
    private static final EntityDataAccessor<Integer> CHICKEN_GROWTH;
    private static final EntityDataAccessor<Integer> CHICKEN_GAIN;
    private static final EntityDataAccessor<Integer> CHICKEN_STRENGTH;
    private static final EntityDataAccessor<Integer> LAY_PROGRESS;

    private static final String TYPE_NBT = "Type";
    private static final String CHICKEN_STATS_ANALYZED_NBT = "Analyzed";
    private static final String CHICKEN_GROWTH_NBT = "Growth";
    private static final String CHICKEN_GAIN_NBT = "Gain";
    private static final String CHICKEN_STRENGTH_NBT = "Strength";
    private EntityType<?> entityType;

    static
    {
        CHICKEN_TYPE = SynchedEntityData.defineId(EntityChickensChicken.class, EntityDataSerializers.STRING);
        CHICKEN_STATS_ANALYZED = SynchedEntityData.defineId(EntityChickensChicken.class, EntityDataSerializers.BOOLEAN);
        CHICKEN_GROWTH = SynchedEntityData.defineId(EntityChickensChicken.class, EntityDataSerializers.INT);
        CHICKEN_GAIN = SynchedEntityData.defineId(EntityChickensChicken.class, EntityDataSerializers.INT);
        CHICKEN_STRENGTH = SynchedEntityData.defineId(EntityChickensChicken.class, EntityDataSerializers.INT);
        LAY_PROGRESS = SynchedEntityData.defineId(EntityChickensChicken.class, EntityDataSerializers.INT);
    }

    public EntityChickensChicken(EntityType<? extends Chicken> entityType, Level worldIn)
    {
        super(entityType, worldIn);
        this.entityType = entityType;
        setChickenType(getRegistryName(entityType).toString());
        setChickenTypeInternal(getRegistryName(entityType).toString());
    }

    public ResourceLocation getRegistryName(EntityType<?> entityType)
    {
        return Registry.ENTITY_TYPE.getKey(entityType);
    }

    public static AttributeSupplier.Builder prepareAttributes()
    {
        return LivingEntity.createLivingAttributes().add(Attributes.MAX_HEALTH, 4.0D).add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.FOLLOW_RANGE, 15.0D);
    }

    public boolean getStatsAnalyzed()
    {
        return entityData.get(CHICKEN_STATS_ANALYZED);
    }

    public void setStatsAnalyzed(boolean statsAnalyzed)
    {
        entityData.set(CHICKEN_STATS_ANALYZED, statsAnalyzed);
    }

    public int getGain()
    {
        return entityData.get(CHICKEN_GAIN);
    }

    public void setGain(int gain)
    {
        entityData.set(CHICKEN_GAIN, gain);
    }

    public int getGrowth()
    {
        return entityData.get(CHICKEN_GROWTH);
    }

    public void setGrowth(int growth)
    {
        entityData.set(CHICKEN_GROWTH, growth);
    }

    public int getStrength()
    {
        return entityData.get(CHICKEN_STRENGTH);
    }

    public void setStrength(int strength)
    {
        entityData.set(CHICKEN_STRENGTH, strength);
    }

    public ResourceLocation getTexture()
    {
        ChickensRegistryItem chickenDescription = getChickenDescription();
        return chickenDescription.getTexture();
    }

    private ChickensRegistryItem getChickenDescription()
    {
        ChickensRegistryItem description = ChickensRegistry.getByRegistryName(getRegistryName(entityType).toString());
        if (description == null || !description.isEnabled())
        {
            description = ChickensRegistry.getByResourceLocation(ChickensRegistry.SMART_CHICKEN_ID);
            if (!description.isEnabled())
            {
                this.remove(RemovalReason.DISCARDED);
            }
        }
        return description;
    }

    public int getTier()
    {
        return getChickenDescription().getTier();
    }

    @Override
    public EntityChickensChicken getBreedOffspring(ServerLevel serverLevel, AgeableMob ageable)
    {
        EntityChickensChicken mateChicken = (EntityChickensChicken) ageable;

        ChickensRegistryItem chickenDescription = getChickenDescription();
        ChickensRegistryItem mateChickenDescription = mateChicken.getChickenDescription();

        ChickensRegistryItem childToBeBorn = ChickensRegistry.getRandomChild(chickenDescription, mateChickenDescription);
        if (childToBeBorn == null)
        {
            return null;
        }

        EntityChickensChicken newChicken = (EntityChickensChicken) entityType.create(level);
        newChicken.setChickenType(childToBeBorn.getRegistryName().toString());

        boolean mutatingStats = chickenDescription.getRegistryName() == mateChickenDescription.getRegistryName() && childToBeBorn.getRegistryName() == chickenDescription.getRegistryName();
        if (mutatingStats)
        {
            increaseStats(newChicken, this, mateChicken, random);
        } else if (chickenDescription.getRegistryName() == childToBeBorn.getRegistryName())
        {
            inheritStats(newChicken, this);
        } else if (mateChickenDescription.getRegistryName() == childToBeBorn.getRegistryName())
        {
            inheritStats(newChicken, mateChicken);
        }

        return newChicken;
    }

    private static void inheritStats(EntityChickensChicken newChicken, EntityChickensChicken parent)
    {
        newChicken.setGrowth(parent.getGrowth());
        newChicken.setGain(parent.getGain());
        newChicken.setStrength(parent.getStrength());
    }

    private static void increaseStats(EntityChickensChicken newChicken, EntityChickensChicken parent1, EntityChickensChicken parent2, RandomSource rand)
    {
        int parent1Strength = parent1.getStrength();
        int parent2Strength = parent2.getStrength();
        newChicken.setGrowth(calculateNewStat(parent1Strength, parent2Strength, parent1.getGrowth(), parent2.getGrowth(), rand));
        newChicken.setGain(calculateNewStat(parent1Strength, parent2Strength, parent2.getGain(), parent2.getGain(), rand));
        newChicken.setStrength(calculateNewStat(parent1Strength, parent2Strength, parent1Strength, parent2Strength, rand));
    }

    private static int calculateNewStat(int thisStrength, int mateStrength, int stat1, int stat2, RandomSource rand)
    {
        int mutation = rand.nextInt(2) + 1;
        int newStatValue = (stat1 * thisStrength + stat2 * mateStrength) / (thisStrength + mateStrength) + mutation;
        if (newStatValue <= 1) return 1;
        if (newStatValue >= 10) return 10;
        return newStatValue;
    }

    @Override
    public void tick()
    {
        if (!this.level.isClientSide && !this.isBaby() && !this.isChickenJockey())
        {
            int newTimeUntilNextEgg = eggTime - 1;
            setTimeUntilNextEgg(newTimeUntilNextEgg);
            if (newTimeUntilNextEgg <= 1)
            {
                ChickensRegistryItem chickenDescription = getChickenDescription();
                ItemStack itemToLay = chickenDescription.createLayItem();

                int gain = getGain();
                if (gain >= 5)
                {
                    itemToLay.grow(chickenDescription.createLayItem().getCount());
                }
                if (gain >= 10)
                {
                    itemToLay.grow(chickenDescription.createLayItem().getCount());
                }

                //TODO
//                itemToLay = BlockEntityHenhouse.pushItemStack(itemToLay, level, new Vec3(getX(), getY(), getZ()));

                if (!itemToLay.isEmpty())
                {
                    ItemEntity itemEntity = new ItemEntity(level, getX(), getY(), getZ(), chickenDescription.createLayItem());
                    level.addFreshEntity(itemEntity);
                    playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                }

                resetTimeUntilNextEgg();
            }
        }
        super.tick();
    }

    private void setTimeUntilNextEgg(int value)
    {
        eggTime = value;
        updateLayProgress();
    }

    public int getLayProgress()
    {
        return entityData.get(LAY_PROGRESS);
    }

    private void updateLayProgress()
    {
        entityData.set(LAY_PROGRESS, eggTime / 60 / 20 / 2);
    }

    private void resetTimeUntilNextEgg()
    {
        ChickensRegistryItem chickenDescription = getChickenDescription();
        int newBaseTimeUntilNextEgg = (chickenDescription.getMinLayTime() + random.nextInt(chickenDescription.getMaxLayTime() - chickenDescription.getMinLayTime()));
        int newTimeUntilNextEgg = (int) Math.max(1.0f, (newBaseTimeUntilNextEgg * (10.f - getGrowth() + 1.f)) / 10.f);
        setTimeUntilNextEgg(newTimeUntilNextEgg * 2);
    }

    public void setChickenType(String registryName)
    {
        setChickenTypeInternal(registryName);
        resetTimeUntilNextEgg();
    }

    private void setChickenTypeInternal(String registryName)
    {
        this.entityData.set(CHICKEN_TYPE, registryName);
    }

    private String getChickenTypeInternal()
    {
        return this.entityData.get(CHICKEN_TYPE);
    }

    @Override
    public void defineSynchedData()
    {
        super.defineSynchedData();
        entityData.define(CHICKEN_TYPE, "");
        entityData.define(CHICKEN_GROWTH, 1);
        entityData.define(CHICKEN_GAIN, 1);
        entityData.define(CHICKEN_STRENGTH, 1);
        entityData.define(LAY_PROGRESS, 0);
        entityData.define(CHICKEN_STATS_ANALYZED, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tagCompound)
    {
        super.addAdditionalSaveData(tagCompound);
        tagCompound.putString(TYPE_NBT, getChickenTypeInternal());
        tagCompound.putBoolean(CHICKEN_STATS_ANALYZED_NBT, getStatsAnalyzed());
        tagCompound.putInt(CHICKEN_GROWTH_NBT, getGrowth());
        tagCompound.putInt(CHICKEN_GAIN_NBT, getGain());
        tagCompound.putInt(CHICKEN_STRENGTH_NBT, getStrength());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tagCompound)
    {
        super.readAdditionalSaveData(tagCompound);
        setChickenTypeInternal(tagCompound.getString(TYPE_NBT));
        setStatsAnalyzed(tagCompound.getBoolean(CHICKEN_STATS_ANALYZED_NBT));
        setGrowth(getStatusValue(tagCompound, CHICKEN_GROWTH_NBT));
        setGain(getStatusValue(tagCompound, CHICKEN_GAIN_NBT));
        setStrength(getStatusValue(tagCompound, CHICKEN_STRENGTH_NBT));
        updateLayProgress();
    }

    private int getStatusValue(CompoundTag compound, String statusName)
    {
        return compound.contains(statusName) ? compound.getInt(statusName) : 1;
    }
}
