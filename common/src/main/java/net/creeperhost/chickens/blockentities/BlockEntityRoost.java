package net.creeperhost.chickens.blockentities;

import net.creeperhost.chickens.api.ChickenStats;
import net.creeperhost.chickens.api.ChickensRegistry;
import net.creeperhost.chickens.api.ChickensRegistryItem;
import net.creeperhost.chickens.containers.ContainerRoost;
import net.creeperhost.chickens.containers.slots.SlotChicken;
import net.creeperhost.chickens.containers.slots.SlotOutput;
import net.creeperhost.chickens.init.ModBlocks;
import net.creeperhost.chickens.init.ModSounds;
import net.creeperhost.chickens.item.ItemChicken;
import net.creeperhost.polylib.blockentity.BlockEntityInventory;
import net.creeperhost.polylib.inventory.PolyItemInventory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class BlockEntityRoost extends BlockEntityInventory
{
    public int progress = 0;

    public BlockEntityRoost(BlockPos blockPos, BlockState blockState)
    {
        super(ModBlocks.ROOST_TILE.get(), blockPos, blockState);
        setInventory(new PolyItemInventory(6));
        setContainerDataSize(1);
        setContainerDataValue(0, progress);
        getInventoryOptional().ifPresent(polyItemInventory ->
        {
            addSlot(new SlotChicken(polyItemInventory, 0, 26, 20));

            for (int i = 0; i < 4; ++i)
            {
                addSlot(new SlotOutput(polyItemInventory, i + 1, 80 + i * 18, 20));
            }
        });
    }

    public void tick()
    {
        if (level != null)
        {
            if (!getItem(0).isEmpty() && progress <= 1000)
            {
                if(level.isClientSide)
                {
                    int random = level.getRandom().nextInt(0, 4);
                    if(random == 3)
                    {
                        level.playSound(null, getBlockPos(), ModSounds.getRandomIdleSound(level), SoundSource.PLAYERS, 1, 1);
                    }
                }
                progress++;
            }
            else
            {
                ChickensRegistryItem chickensRegistryItem = ChickensRegistry.getByRegistryName(ItemChicken.getTypeFromStack(getItem(0)));
                if (chickensRegistryItem != null)
                {
                    ChickenStats chickenStats = new ChickenStats(getItem(0));
                    int gain = chickenStats.getGain();
                    int chickens = getItem(0).getCount();
                    ItemStack itemToLay = chickensRegistryItem.createLayItem();
                    if (gain >= 5)
                    {
                        itemToLay.grow(chickensRegistryItem.createLayItem().getCount());
                    }
                    if (gain >= 10)
                    {
                        itemToLay.grow(chickensRegistryItem.createLayItem().getCount());
                    }
                    int finalCount = itemToLay.getCount() * chickens;
                    itemToLay.setCount(finalCount);
                    ItemStack inserted = getInventoryOptional().get().addItem(itemToLay);
                    if (inserted.isEmpty())
                    {
                        level.playSound(null, getBlockPos(), SoundEvents.CHICKEN_EGG, SoundSource.NEUTRAL, 0.5F, 0.8F);
                        damageChicken(chickenStats);
                        progress = 0;
                    }
                }
            }
        }
    }

    public void damageChicken(ChickenStats chickenStats)
    {
        if(level.isClientSide) return;

        ItemStack stack = getItem(0);
        if(stack.getItem() instanceof ItemChicken)
        {
            int currentHealth = chickenStats.getLifespan();
            if(currentHealth > 1)
            {
                //TODO USE A RANDOM TO DECIDE IF WE TAKE DAMAGE OR NOT
                chickenStats.reduceLifespan(1);
                chickenStats.write(stack);
            }
            else
            {
                setItem(0, ItemStack.EMPTY);
            }
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
            worldIn.addParticle(ParticleTypes.HEART, d0, d1, d2, d3, d4, d5);
        }
    }

    @Override
    protected Component getDefaultName()
    {
        return Component.literal("roost");
    }

    @Override
    protected AbstractContainerMenu createMenu(int i, @NotNull Inventory inventory)
    {
        return new ContainerRoost(i, inventory, this, getContainerData());
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag compoundTag)
    {
        super.saveAdditional(compoundTag);
        compoundTag.putInt("progress", progress);
    }

    @Override
    public void load(@NotNull CompoundTag compoundTag)
    {
        super.load(compoundTag);
        progress = compoundTag.getInt("progress");
    }
}
