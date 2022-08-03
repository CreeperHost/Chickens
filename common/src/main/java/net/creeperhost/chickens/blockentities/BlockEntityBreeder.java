package net.creeperhost.chickens.blockentities;

import net.creeperhost.chickens.api.ChickenStats;
import net.creeperhost.chickens.api.ChickensRegistry;
import net.creeperhost.chickens.api.ChickensRegistryItem;
import net.creeperhost.chickens.block.BlockBreeder;
import net.creeperhost.chickens.containers.ContainerBreeder;
import net.creeperhost.chickens.init.ModBlocks;
import net.creeperhost.chickens.init.ModItems;
import net.creeperhost.chickens.item.ItemChicken;
import net.creeperhost.chickens.polylib.CommonTags;
import net.creeperhost.chickens.polylib.SlotInputFiltered;
import net.creeperhost.chickens.polylib.SlotInputFilteredTag;
import net.creeperhost.polylib.blockentity.BlockEntityInventory;
import net.creeperhost.polylib.containers.slots.SlotOutput;
import net.creeperhost.polylib.inventory.PolyItemInventory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class BlockEntityBreeder extends BlockEntityInventory
{
    public int progress = 0;

    public BlockEntityBreeder(BlockPos blockPos, BlockState blockState)
    {
        super(ModBlocks.BREEDER_TILE.get(), blockPos, blockState);
        setInventory(new PolyItemInventory(6));
        getInventoryOptional().ifPresent(polyItemInventory ->
        {
            addSlot(new SlotInputFiltered(polyItemInventory, 0, 44, 20, new ItemStack(ModItems.CHICKEN_ITEM.get())));
            addSlot(new SlotInputFiltered(polyItemInventory, 1, 62, 20, new ItemStack(ModItems.CHICKEN_ITEM.get())));
            addSlot(new SlotInputFilteredTag(polyItemInventory, 2, 8, 20, CommonTags.SEEDS));

            for (int i = 0; i < 3; ++i)
            {
                addSlot(new SlotOutput(polyItemInventory, i + 3, 116 + i * 18, 20));
            }
        });
        setContainerDataSize(1);
    }

    public void updateBlockState(Level level, BlockPos blockPos, boolean canWork)
    {
        BlockState current = level.getBlockState(blockPos);
        if(current != null)
        {
            boolean hasSeeds = !getItem(0).isEmpty();
            level.setBlock(blockPos, current.setValue(BlockBreeder.HAS_SEEDS, hasSeeds).setValue(BlockBreeder.IS_BREEDING, canWork), 3);
        }
    }

    public void tick()
    {
        boolean canWork = (!getItem(0).isEmpty() && !getItem(1).isEmpty() && !getItem(2).isEmpty());
        if (level != null && !level.isClientSide)
        {
            updateBlockState(level, getBlockPos(), canWork);
            if(canWork)
            {
                if (progress <= 1000)
                {
                    progress++;
                }
                else
                {
                    ChickensRegistryItem chickensRegistryItem1 = ChickensRegistry.getByRegistryName(ItemChicken.getTypeFromStack(getItem(0)));
                    ChickensRegistryItem chickensRegistryItem2 = ChickensRegistry.getByRegistryName(ItemChicken.getTypeFromStack(getItem(1)));

                    ChickensRegistryItem baby = ChickensRegistry.getRandomChild(chickensRegistryItem1, chickensRegistryItem2);
                    if (baby == null)
                    {
                        progress = 0;
                        return;
                    }
                    ItemStack chickenStack = new ItemStack(ModItems.CHICKEN_ITEM.get());
                    ItemChicken.applyEntityIdToItemStack(chickenStack, baby.getRegistryName());
                    ChickenStats babyStats = increaseStats(chickenStack, getItem(0), getItem(1), level.random);
                    babyStats.write(chickenStack);
                    chickenStack.setCount(1);
                    ItemStack inserted = getInventoryOptional().get().addItem(chickenStack);
                    if (inserted.isEmpty())
                    {
                        level.playSound(null, getBlockPos(), SoundEvents.CHICKEN_EGG, SoundSource.NEUTRAL, 0.5F, 0.8F);
                        spawnParticle(level, getBlockPos().getX(), getBlockPos().getY() + 1, getBlockPos().getZ(), level.random);
                        getItem(2).shrink(1);
                        progress = 0;
                    }
                }
            }
        }
        else
        {
            progress = 0;
        }
        setContainerDataValue(0, progress);
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
        return new ContainerBreeder(i, inventory, this, getContainerData());
    }
}
