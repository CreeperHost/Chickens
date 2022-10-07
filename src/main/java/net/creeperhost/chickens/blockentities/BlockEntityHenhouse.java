package net.creeperhost.chickens.blockentities;

import net.creeperhost.chickens.containers.ContainerHenhouse;
import net.creeperhost.chickens.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class BlockEntityHenhouse extends BlockEntity implements MenuProvider
{
    public static final int hayBaleEnergy = 100;

    public static final int hayBaleSlotIndex = 0;
    public static final int dirtSlotIndex = 1;
    public static final int firstItemSlotIndex = 2;
    private static final int lastItemSlotIndex = 10;
    private static final double HENHOUSE_RADIUS = 0.5;
    private static final double FENCE_THRESHOLD = 0.5;

    public BlockEntityHenhouse(BlockPos blockPos, BlockState blockState)
    {
        super(ModBlocks.HEN_HOUSE_TILE.get(), blockPos, blockState);
    }

    public final ItemStackHandler inventory = new ItemStackHandler(11)
    {
        @Override
        protected void onContentsChanged(int slot)
        {
            super.onContentsChanged(slot);
            setChanged();
        }
    };

    private int energy = 0;

    @Nullable
    public static ItemStack pushItemStack(ItemStack itemToLay, Level worldObj, Vec3 pos)
    {
        List<BlockEntityHenhouse> henhouses = findHenhouses(worldObj, pos, 4 + HENHOUSE_RADIUS + FENCE_THRESHOLD);
        for (BlockEntityHenhouse henhouse : henhouses)
        {
            itemToLay = henhouse.pushItemStack(itemToLay);
            if (itemToLay.isEmpty())
            {
                break;
            }
        }
        return itemToLay;
    }

    private static List<BlockEntityHenhouse> findHenhouses(Level worldObj, Vec3 pos, double radius)
    {
        int firstChunkX = Mth.floor((pos.x - radius - 100) / 16.0D);
        int lastChunkX = Mth.floor((pos.x + radius + 100) / 16.0D);
        int firstChunkY = Mth.floor((pos.z - radius - 100) / 16.0D);
        int lastChunkY = Mth.floor((pos.z + radius + 100) / 16.0D);

        List<Double> distances = new ArrayList<Double>();
        List<BlockEntityHenhouse> result = new ArrayList<BlockEntityHenhouse>();
        for (int chunkX = firstChunkX; chunkX <= lastChunkX; ++chunkX)
        {
            for (int chunkY = firstChunkY; chunkY <= lastChunkY; ++chunkY)
            {
                LevelChunk chunk = worldObj.getChunk(chunkX, chunkY);
                for (BlockEntity tileEntity : chunk.getBlockEntities().values())
                {
                    if (tileEntity instanceof BlockEntityHenhouse)
                    {
                        Vec3 tileEntityPos = new Vec3(tileEntity.getBlockPos().getX(), tileEntity.getBlockPos().getY(), tileEntity.getBlockPos().getZ()).add(HENHOUSE_RADIUS, HENHOUSE_RADIUS, HENHOUSE_RADIUS);
                        boolean inRage = testRange(pos, tileEntityPos, radius);
                        if (inRage)
                        {
                            double distance = pos.distanceTo(tileEntityPos);
                            addHenhouseToResults((BlockEntityHenhouse) tileEntity, distance, distances, result);
                        }
                    }
                }
            }
        }
        return result;
    }

    private static boolean testRange(Vec3 pos1, Vec3 pos2, double range)
    {
        return Math.abs(pos1.x - pos2.x) <= range && Math.abs(pos1.y - pos2.y) <= range && Math.abs(pos1.z - pos2.z) <= range;
    }

    private static void addHenhouseToResults(BlockEntityHenhouse henhouse, double distance, List<Double> distances, List<BlockEntityHenhouse> henhouses)
    {
        for (int resultIndex = 0; resultIndex < distances.size(); resultIndex++)
        {
            if (distance < distances.get(resultIndex))
            {
                distances.add(resultIndex, distance);
                henhouses.add(resultIndex, henhouse);
                return;
            }
        }
        distances.add(distance);
        henhouses.add(henhouse);
    }

    private ItemStack pushItemStack(ItemStack stack)
    {

        int capacity = getEffectiveCapacity();
        if (capacity <= 0)
        {
            return stack;
        }

        for (int slotIndex = firstItemSlotIndex; slotIndex <= lastItemSlotIndex; slotIndex++)
        {

            if (stack.isEmpty()) break;
            int stackSizePre = stack.getCount();
            ItemStack simulated = inventory.insertItem(slotIndex, stack, true);
            int powerToUse = stackSizePre - simulated.getCount();

            if (powerToUse > 0)
            {
                consumeEnergy(powerToUse);
                capacity -= powerToUse;

                stack = inventory.insertItem(slotIndex, stack, false);
            }
        }

        setChanged();
        return stack;
    }

    private void consumeEnergy(int amount)
    {
        while (amount > 0)
        {
            if (energy == 0)
            {
                assert !inventory.getStackInSlot(hayBaleSlotIndex).isEmpty();
//                inventory.insertItem(hayBaleSlotIndex, 1, false);
                energy += hayBaleEnergy;
            }

            int consumed = Math.min(amount, energy);
            energy -= consumed;
            amount -= consumed;

            if (energy <= 0)
            {
                inventory.insertItem(dirtSlotIndex, new ItemStack(Blocks.DIRT, 1), false);
            }
        }
    }


    private int getEffectiveCapacity()
    {
        return Math.min(getInputCapacity(), getOutputCapacity());
    }

    private int getInputCapacity()
    {
        int potential = energy;

        ItemStack hayBaleStack = inventory.getStackInSlot(hayBaleSlotIndex);
        if (!hayBaleStack.isEmpty() && hayBaleStack.getItem() == Item.byBlock(Blocks.HAY_BLOCK))
        {
            potential += hayBaleStack.getCount() * hayBaleEnergy;
        }

        return potential;
    }

    private int getOutputCapacity()
    {
        ItemStack dirtStack = inventory.getStackInSlot(dirtSlotIndex);
        if (dirtStack.isEmpty())
        {
            return inventory.getSlotLimit(dirtSlotIndex) * hayBaleEnergy;
        }
        if (dirtStack.getItem() != Item.byBlock(Blocks.DIRT))
        {
            return 0;
        }
        return (inventory.getSlotLimit(dirtSlotIndex) - dirtStack.getCount()) * hayBaleEnergy;
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag compound)
    {
        super.saveAdditional(compound);
        compound.putInt("energy", energy);
        compound.merge(inventory.serializeNBT());
    }

    @Override
    public void load(@NotNull CompoundTag compound)
    {
        super.load(compound);
        energy = compound.getInt("energy");
        inventory.deserializeNBT(compound);
    }

    public int getEnergy()
    {
        return energy;
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
    public @NotNull Component getDisplayName()
    {
        return new TranslatableComponent("container.henhouse");
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player)
    {
        return new ContainerHenhouse(id, inventory, this);
    }
}
