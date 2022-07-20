package net.creeperhost.chickens.blockentities;

import net.creeperhost.chickens.block.BlockIncubator;
import net.creeperhost.chickens.containers.ContainerIncubator;
import net.creeperhost.chickens.init.ModBlocks;
import net.creeperhost.chickens.item.ItemChicken;
import net.creeperhost.chickens.item.ItemChickenEgg;
import net.creeperhost.chickens.polylib.PolyInventory;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class BlockEntityIncubator extends BaseContainerBlockEntity
{
    public final ContainerData containerData = new SimpleContainerData(1)
    {
        @Override
        public int get(int index)
        {
            if (index == 0)
            {
                return lightLevel;
            }
            if(index == 1)
            {
                return temp;
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
            return 2;
        }
    };

    public final PolyInventory inventory = new PolyInventory(10)
    {
        @Override
        public void setChanged()
        {
            super.setChanged();
            BlockEntityIncubator.this.setChanged();
        }
    };

    int progress = 0;
    int defaultTemp = 18;
    int temp = defaultTemp;
    //TODO Move this to Constants
    public static int incrementSize = 4;
    int lightLevel = 0;

    public BlockEntityIncubator(BlockPos blockPos, BlockState blockState)
    {
        super(ModBlocks.INCUBATOR_TILE.get(), blockPos, blockState);
    }

    public boolean isActive()
    {
        return getBlockState().getValue(BlockIncubator.ACTIVE);
    }

    public void tick()
    {
        if(level == null) return;

        int random = level.getRandom().nextInt(0, inventory.getContainerSize());
        progress++;
        if(progress >= 20)
        {
            progress = 0;
            if (isActive() && (temp < (lightLevel * incrementSize)))
            {
                temp++;
            }
            else if(temp > defaultTemp && (temp != (lightLevel * incrementSize)))
            {
                temp--;
            }
            if(!isActive() && temp > defaultTemp)
            {
                temp--;
            }

            ItemStack stack = inventory.getItem(random);
            if(!(stack.getItem() instanceof ItemChickenEgg itemChickenEgg)) return;
            if(!itemChickenEgg.isViable(stack)) return;

            if (isWithinHatchingTemp())
            {
                int progress = itemChickenEgg.getProgress(stack);
                if (progress < 100)
                {
                    progress++;
                    itemChickenEgg.setProgress(stack, progress);
                }
                else
                {
                    //TODO use a random to decide if we hatch or not
                    ItemStack chicken = ItemChicken.of(itemChickenEgg.getType(stack));
                    if (chicken != null && !chicken.isEmpty())
                    {
                        inventory.setItem(random, chicken);
                    }
                }
            }
            else
            {
               if(itemChickenEgg.getProgress(stack) > 0)
               {
                   itemChickenEgg.incrementMissed(stack);
                   int i = Mth.ceil((35 - defaultTemp) * 1.5);
                   if(itemChickenEgg.getMissedCycles(stack) > i)
                   {
                       itemChickenEgg.setNotViable(stack);
                   }
               }
            }
        }
    }

    public boolean isWithinHatchingTemp()
    {
        if(temp >= 35 && temp <= 40) return true;

        return false;
    }

    @Override
    protected Component getDefaultName()
    {
        return Component.literal("Incubator");
    }

    @Override
    protected AbstractContainerMenu createMenu(int i, @NotNull Inventory inventory)
    {
        return new ContainerIncubator(i, inventory, this, containerData);
    }

    @Override
    public int getContainerSize()
    {
        return inventory.getContainerSize();
    }

    @Override
    public int getMaxStackSize()
    {
        return 1;
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
        return inventory.stillValid(player);
    }

    @Override
    public void clearContent()
    {
        inventory.clearContent();
    }

    public int getLightLevel()
    {
        return lightLevel;
    }

    public void setLightLevel(int lightLevel)
    {
        this.lightLevel = lightLevel;
        setChanged();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag compoundTag)
    {
        super.saveAdditional(compoundTag);
        compoundTag.merge(inventory.serializeNBT());
        compoundTag.putInt("temp", temp);
        compoundTag.putInt("light_level", getLightLevel());
    }

    @Override
    public void load(@NotNull CompoundTag compoundTag)
    {
        super.load(compoundTag);
        inventory.deserializeNBT(compoundTag);
        temp = compoundTag.getInt("temp");
        setLightLevel(compoundTag.getInt("light_level"));
    }
}
