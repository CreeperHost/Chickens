package net.creeperhost.chickens.blockentities;

import net.creeperhost.chickens.block.BlockBreeder;
import net.creeperhost.chickens.capability.SmartInventory;
import net.creeperhost.chickens.containers.ContainerBreeder;
import net.creeperhost.chickens.data.ChickenStats;
import net.creeperhost.chickens.entity.EntityChickensChicken;
import net.creeperhost.chickens.init.ModBlocks;
import net.creeperhost.chickens.init.ModItems;
import net.creeperhost.chickens.item.ItemChicken;
import net.creeperhost.chickens.item.ItemSpawnEgg;
import net.creeperhost.chickens.registry.ChickensRegistry;
import net.creeperhost.chickens.registry.ChickensRegistryItem;
import net.creeperhost.chickens.util.InventoryHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class BlockEntityBreeder extends BlockEntity implements MenuProvider
{
    public SmartInventory inventory = new SmartInventory(6)
    {
        @Override
        protected void onContentsChanged(int slot)
        {
            super.onContentsChanged(slot);
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack)
        {
            if((slot == 0 && stack.is(ModItems.CHICKEN_ITEM.get()))) return true;
            if((slot == 1 && stack.is(ModItems.CHICKEN_ITEM.get()))) return true;
            if((slot == 2 && stack.is(Tags.Items.SEEDS))) return true;
            return false;
        }

        @NotNull
        @Override
        public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate)
        {
            return super.insertItem(slot, stack, simulate);
        }

        @NotNull
        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate)
        {
            if(slot <= 2) return ItemStack.EMPTY;
            return super.extractItem(slot, amount, simulate);
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
        boolean canWork = (inventory.getStackInSlot(0).getItem() instanceof ItemChicken && (inventory.getStackInSlot(1).getItem() instanceof ItemChicken) && (inventory.getStackInSlot(2).is(Tags.Items.SEEDS)));
        if(level == null) return;
        if(level.isClientSide) return;
        if(canWork)
        {
            if (progress <= 1000)
            {
                progress++;
            } else
            {
                ChickensRegistryItem chickensRegistryItem1 = ChickensRegistry.getByRegistryName(ItemSpawnEgg.getTypeFromStack(inventory.getStackInSlot(0)));
                ChickensRegistryItem chickensRegistryItem2 = ChickensRegistry.getByRegistryName(ItemSpawnEgg.getTypeFromStack(inventory.getStackInSlot(1)));

                ChickensRegistryItem baby = ChickensRegistry.getRandomChild(chickensRegistryItem1, chickensRegistryItem2);
                if(baby == null)
                {
                    progress = 0;
                    return;
                }
                ItemStack chickenStack = new ItemStack(ModItems.CHICKEN_ITEM.get());
                ItemChicken.applyEntityIdToItemStack(chickenStack, baby.getRegistryName());
                ChickenStats babyStats = increaseStats(chickenStack, inventory.getStackInSlot(0), inventory.getStackInSlot(1), level.random);
                babyStats.write(chickenStack);
                chickenStack.setCount(1);
                ItemStack inserted = moveOutput(chickenStack);
                if(inserted.isEmpty())
                {
                    level.playSound(null, getBlockPos(), SoundEvents.CHICKEN_EGG, SoundSource.NEUTRAL, 0.5F, 0.8F);
                    spawnParticle(level, getBlockPos().getX(), getBlockPos().getY() + 1, getBlockPos().getZ(), level.random);
                    inventory.getStackInSlot(2).shrink(1);
                    progress = 0;
                }
            }
        }
        else
        {
            progress = 0;
        }
    }

    public ItemStack moveOutput(ItemStack stack)
    {
        for (int i = 3; i <= 5; i++)
        {
            if(inventory.getStackInSlot(i).isEmpty())
            {
                inventory.setStackInSlot(i, stack);
                return ItemStack.EMPTY;
            }
            else
            {
                if(ItemStack.isSameItemSameTags(stack, inventory.getStackInSlot(i)))
                {
                    int count = inventory.getStackInSlot(i).getCount();
                    int max = 16;
                    if(count < max)
                    {
                        int newCount = count + 1;
                        stack.setCount(newCount);
                        inventory.setStackInSlot(i, stack);
                        return ItemStack.EMPTY;
                    }
                }
            }
        }
        return stack;
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
            if(worldIn instanceof ServerLevel serverLevel)
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

        boolean mutatingStats = getChickenFromStack(parent1).getRegistryName() == getChickenFromStack(parent2).getRegistryName() && getChickenFromStack(baby).getRegistryName() == getChickenFromStack(parent1).getRegistryName();

        if(mutatingStats)
        {
            babyStats.setGrowth(calculateNewStat(parent1Stats.getStrength(), parent2Stats.getStrength(), parent1Stats.getGrowth(), parent2Stats.getGrowth(), rand));
            babyStats.setGain(calculateNewStat(parent1Stats.getStrength(), parent2Stats.getStrength(), parent1Stats.getGain(), parent2Stats.getGain(), rand));
            babyStats.setStrength(calculateNewStat(parent1Stats.getStrength(), parent2Stats.getStrength(), parent1Stats.getStrength(), parent2Stats.getStrength(), rand));
            return babyStats;
        }
        else if(getChickenFromStack(parent1).getRegistryName() == getChickenFromStack(baby).getRegistryName())
        {
            inheritStats(babyStats, parent1Stats);
            return babyStats;
        }
        else if(getChickenFromStack(parent2).getRegistryName() == getChickenFromStack(baby).getRegistryName())
        {
            inheritStats(babyStats, parent2Stats);
            return babyStats;
        }
        return new ChickenStats(baby);
    }

    private static void inheritStats(ChickenStats babyStats, ChickenStats parent1Stats)
    {
        babyStats.setGrowth(parent1Stats.getGrowth());
        babyStats.setGain(parent1Stats.getGain());
        babyStats.setStrength(parent1Stats.getStrength());
    }
    
    public static ResourceLocation getRegistryName(ItemStack stack)
    {
        return Registry.ITEM.getKey(stack.getItem());
    }

    private static ChickensRegistryItem getChickenFromStack(ItemStack stack)
    {
        if(stack.getItem() instanceof ItemChicken)
        {
            return ChickensRegistry.getByRegistryName(ItemSpawnEgg.getTypeFromStack(stack));
        }
        return null;
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
    public void setChanged()
    {
        super.setChanged();
        updateState();
    }

    public void updateState()
    {
        if(level != null)
        {
            BlockState state = level.getBlockState(worldPosition);
            state.setValue(BlockBreeder.HAS_SEEDS, true);
            boolean hasSeeds = !inventory.getStackInSlot(2).isEmpty();
            boolean isBreeding = (!inventory.getStackInSlot(0).isEmpty() && !inventory.getStackInSlot(1).isEmpty());
            level.setBlock(getBlockPos(), level.getBlockState(getBlockPos()).setValue(BlockBreeder.HAS_SEEDS, hasSeeds).setValue(BlockBreeder.IS_BREEDING, isBreeding), 4);
        }
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, final @Nullable Direction side)
    {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return LazyOptional.of(() -> inventory).cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag compound)
    {
        super.saveAdditional(compound);
        compound.merge(inventory.serializeNBT());
        compound.putInt("progress", progress);
    }

    @Override
    public void load(@NotNull CompoundTag compound)
    {
        super.load(compound);
        inventory.deserializeNBT(compound);
        progress = compound.getInt("progress");
    }

    @Override
    public @NotNull Component getDisplayName()
    {
        return Component.translatable("chickens.container.breeder");
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player)
    {
        return new ContainerBreeder(id, inventory, this, containerData);
    }
}
