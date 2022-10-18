package net.creeperhost.chickens.blockentities;

import net.creeperhost.chickens.capability.SmartInventory;
import net.creeperhost.chickens.containers.ContainerRoost;
import net.creeperhost.chickens.data.ChickenStats;
import net.creeperhost.chickens.init.ModBlocks;
import net.creeperhost.chickens.item.ItemChicken;
import net.creeperhost.chickens.registry.ChickensRegistry;
import net.creeperhost.chickens.registry.ChickensRegistryItem;
import net.creeperhost.chickens.util.InventoryHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class BlockEntityRoost extends BlockEntity implements MenuProvider
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
            if(stack.getItem() instanceof ItemChicken && slot == 0) return true;
            if(stack.getItem() instanceof ItemChicken && slot > 0) return false;
            return super.isItemValid(slot, stack);
        }

        @NotNull
        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate)
        {
            if(slot == 0) return ItemStack.EMPTY;
            return super.extractItem(slot, amount, simulate);
        }

        @NotNull
        @Override
        public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate)
        {
            if(slot > 0) return stack;
            return super.insertItem(slot, stack, simulate);
        }

        @Override
        public int getSlotLimit(int slot)
        {
            if(slot == 0) return 16;
            return super.getSlotLimit(slot);
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

    public BlockEntityRoost(BlockPos blockPos, BlockState blockState)
    {
        super(ModBlocks.ROOST_TILE.get(), blockPos, blockState);
    }

    @Override
    public void setChanged()
    {
        super.setChanged();
        if(level != null)
            this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }

    public void tick()
    {
        if(level != null && !level.isClientSide)
        {
            if (canRun())
            {
                if(progress <= 1000){
                    progress++;
                }
                else
                {
                    ItemStack inserted = moveOutput(getOutputStack(), false);//InventoryHelper.insertItemStacked(inventory, getOutputStack(), false);
                    if(inserted.isEmpty())
                    {
                        level.playSound(null, getBlockPos(), SoundEvents.CHICKEN_EGG, SoundSource.NEUTRAL, 0.5F, 0.8F);

                        spawnParticle(level, getBlockPos().getX(), getBlockPos().getY() + 1, getBlockPos().getZ(), level.random);
                        progress = 0;
                    }
                }
            }
            else
            {
                progress = 0;
            }
        }
    }

    //TODO I hate all of this but its a quick fix
    public ItemStack moveOutput(ItemStack stack, boolean simulate)
    {
        for (int i = 1; i <= 4; i++)
        {
            if(inventory.getStackInSlot(i).isEmpty())
            {
                if(!simulate) inventory.setStackInSlot(i, stack);
                return ItemStack.EMPTY;
            }
            else
            {
                if(ItemStack.isSameItemSameTags(stack, inventory.getStackInSlot(i)))
                {
                    int startStackSize = stack.getCount();
                    int count = inventory.getStackInSlot(i).getCount();
                    int max = stack.getMaxStackSize();
                    if(count <= max)
                    {
                        if(startStackSize + count > max)
                        {
                            int fitSize = max - count;
                            if(fitSize > startStackSize)
                            {
                                int returnSize = startStackSize - fitSize;
                                stack.setCount(fitSize);
                                if(!simulate) inventory.setStackInSlot(i, stack);
                                ItemStack returnStack = stack.copy();
                                returnStack.setCount(returnSize);
                                return returnStack;
                            }
                        }
                        else
                        {
                            int newCount = count + stack.getCount();
                            stack.setCount(newCount);
                            if (!simulate) inventory.setStackInSlot(i, stack);
                            return ItemStack.EMPTY;
                        }
                    }
                }
            }
        }
        return stack;
    }

    public boolean canRun()
    {
        if(inventory.getStackInSlot(0).isEmpty()) return false;
        if(!(inventory.getStackInSlot(0).getItem() instanceof ItemChicken)) return false;
        ItemStack insert = moveOutput(getOutputStack(), true);//InventoryHelper.insertItemStacked(inventory, getOutputStack(), true);
        return insert.isEmpty();
    }

    public ItemStack getOutputStack()
    {
        ChickensRegistryItem chickensRegistryItem = ChickensRegistry.getByRegistryName(ItemChicken.getTypeFromStack(inventory.getStackInSlot(0)));
        if (chickensRegistryItem != null)
        {
            ChickenStats chickenStats = new ChickenStats(inventory.getStackInSlot(0));
            int gain = chickenStats.getGain();
            int chickens = inventory.getStackInSlot(0).getCount();
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
            return itemToLay;
        }
        return ItemStack.EMPTY;
    }

    public void spawnParticle(Level worldIn, double posX, double posY, double posZ, Random rand)
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

    @org.jetbrains.annotations.Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag()
    {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.merge(inventory.serializeNBT());
        return compoundTag;
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
    protected void saveAdditional(CompoundTag compound)
    {
        super.saveAdditional(compound);
        compound.merge(inventory.serializeNBT());
        compound.putInt("progress", progress);
    }

    @Override
    public void load(CompoundTag compound)
    {
        super.load(compound);
        inventory.deserializeNBT(compound);
        progress = compound.getInt("progress");
    }

    @Override
    public @NotNull Component getDisplayName()
    {
        return new TextComponent("chickens.container.roost");
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player p_39956_)
    {
        return new ContainerRoost(id, inventory, this, containerData);
    }
}
