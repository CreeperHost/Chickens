package net.creeperhost.chickens.blockentities;

import net.creeperhost.chickens.api.ChickenStats;
import net.creeperhost.chickens.api.ChickensRegistry;
import net.creeperhost.chickens.api.ChickensRegistryItem;
import net.creeperhost.chickens.containers.ContainerBreeder;
import net.creeperhost.chickens.init.ModBlocks;
import net.creeperhost.chickens.init.ModItems;
import net.creeperhost.chickens.item.ItemChicken;
import net.creeperhost.chickens.polylib.CommonTags;
import net.creeperhost.chickens.polylib.PolyInventory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockEntityBreeder extends BaseContainerBlockEntity implements WorldlyContainer
{
    public final PolyInventory inventory = new PolyInventory(6)
    {
        @Override
        public void setChanged()
        {
            super.setChanged();
            BlockEntityBreeder.this.setChanged();
        }
    };

    public final ContainerData containerData = new SimpleContainerData(1)
    {
        @Override
        public int get(int index)
        {
            if (index == 0)
            {
                return progress;
            }
            throw new IllegalArgumentException("Invalid index: " + index);
        }

        @Override
        public void set(int index, int value)
        {
            throw new IllegalStateException("Cannot set values through IIntArray");
        }

        @Override
        public int getCount()
        {
            return 1;
        }
    };

    public int progress = 0;


    public BlockEntityBreeder(BlockPos blockPos, BlockState blockState)
    {
        super(ModBlocks.BREEDER_TILE.get(), blockPos, blockState);
    }

    public void tick()
    {
        boolean canWork = (!inventory.getItem(0).isEmpty() && !inventory.getItem(1).isEmpty() && !inventory.getItem(2).isEmpty());
        if (level != null && !level.isClientSide && canWork)
        {
            if (progress <= 1000)
            {
                progress++;
            }
            else
            {
                ChickensRegistryItem chickensRegistryItem1 = ChickensRegistry.getByRegistryName(ItemChicken.getTypeFromStack(inventory.getItem(0)));
                ChickensRegistryItem chickensRegistryItem2 = ChickensRegistry.getByRegistryName(ItemChicken.getTypeFromStack(inventory.getItem(1)));

                ChickensRegistryItem baby = ChickensRegistry.getRandomChild(chickensRegistryItem1, chickensRegistryItem2);
                if (baby == null)
                {
                    progress = 0;
                    return;
                }
                ItemStack chickenStack = new ItemStack(ModItems.CHICKEN_ITEM.get());
                ItemChicken.applyEntityIdToItemStack(chickenStack, baby.getRegistryName());
                ChickenStats babyStats = increaseStats(chickenStack, inventory.getItem(0), inventory.getItem(1), level.random);
                babyStats.write(chickenStack);
                chickenStack.setCount(1);
                ItemStack inserted = inventory.addItem(chickenStack);
                if (inserted.isEmpty())
                {
                    level.playSound(null, getBlockPos(), SoundEvents.CHICKEN_EGG, SoundSource.NEUTRAL, 0.5F, 0.8F);
                    spawnParticle(level, getBlockPos().getX(), getBlockPos().getY() + 1, getBlockPos().getZ(), level.random);
                    inventory.getItem(2).shrink(1);
                    progress = 0;
                }
            }
        }
        else
        {
            progress = 0;
        }
    }

    public void spawnParticle(Level worldIn, double posX, double posY, double posZ, RandomSource rand)
    {
        for (int i = 0; i < 16; ++i)
        {
            int j = rand.nextInt(2) * 2 - 1;
            int k = rand.nextInt(2) * 2 - 1;
            double d0 = posX + 0.5D + 0.25D * (double) j;
            double d1 = ((float) posY + rand.nextFloat());
            double d2 = posZ + 0.5D + 0.25D * (double) k;
            double d3 = (rand.nextFloat() * (float) j);
            double d4 = (rand.nextFloat() - 0.5D) * 0.125D;
            double d5 = (rand.nextFloat() * (float) k);
            if (worldIn instanceof ServerLevel serverLevel)
            {
                serverLevel.addParticle(ParticleTypes.HEART, d0, d1, d2, d3, d4, d5);
            }
        }
    }

    private static ChickenStats increaseStats(ItemStack baby, ItemStack parent1, ItemStack parent2, RandomSource rand)
    {
        ChickenStats babyStats = new ChickenStats(baby);
        ChickenStats parent1Stats = new ChickenStats(parent1);
        ChickenStats parent2Stats = new ChickenStats(parent2);

        babyStats.setGrowth(calculateNewStat(parent1Stats.getStrength(), parent2Stats.getStrength(), parent1Stats.getGrowth(), parent2Stats.getGrowth(), rand));
        babyStats.setGain(calculateNewStat(parent1Stats.getStrength(), parent2Stats.getStrength(), parent1Stats.getGain(), parent2Stats.getGain(), rand));
        babyStats.setStrength(calculateNewStat(parent1Stats.getStrength(), parent2Stats.getStrength(), parent1Stats.getStrength(), parent2Stats.getStrength(), rand));

        return babyStats;
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
    protected Component getDefaultName()
    {
        return Component.literal("Breeder");
    }

    @Override
    protected AbstractContainerMenu createMenu(int i, @NotNull Inventory inventory)
    {
        return new ContainerBreeder(i, inventory, this, containerData);
    }

    @Override
    public int getContainerSize()
    {
        return inventory.getContainerSize();
    }

    @Override
    public boolean isEmpty()
    {
        return inventory.isEmpty();
    }

    @Override
    public ItemStack getItem(int i)
    {
        return inventory.getItem(i);
    }

    @Override
    public ItemStack removeItem(int i, int j)
    {
        return inventory.removeItem(i, j);
    }

    @Override
    public ItemStack removeItemNoUpdate(int i)
    {
        return inventory.removeItemNoUpdate(i);
    }

    @Override
    public void setItem(int i, @NotNull ItemStack itemStack)
    {
        inventory.setItem(i, itemStack);
    }

    @Override
    public boolean stillValid(@NotNull Player player)
    {
        return true;
    }

    @Override
    public void clearContent()
    {
        inventory.clearContent();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag compoundTag)
    {
        super.saveAdditional(compoundTag);
        compoundTag.merge(inventory.serializeNBT());
    }

    @Override
    public void load(@NotNull CompoundTag compoundTag)
    {
        super.load(compoundTag);
        inventory.deserializeNBT(compoundTag);
    }

    @Override
    public int[] getSlotsForFace(@NotNull Direction direction)
    {
        return new int[]{0, 1, 2, 3, 4, 5, 6};
    }

    @Override
    public boolean canPlaceItemThroughFace(int i, @NotNull ItemStack itemStack, @Nullable Direction direction)
    {
        if(i == 2 && (itemStack.is(CommonTags.SEEDS) || itemStack.is(Items.WHEAT_SEEDS))) return true;
        if((i == 0 || i == 1) && itemStack.getItem() instanceof ItemChicken) return true;

        return false;
    }

    @Override
    public boolean canTakeItemThroughFace(int i, @NotNull ItemStack itemStack, @NotNull Direction direction)
    {
        return i > 3;
    }
}
