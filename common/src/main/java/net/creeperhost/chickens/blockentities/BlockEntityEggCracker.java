package net.creeperhost.chickens.blockentities;

import dev.architectury.fluid.FluidStack;
import net.creeperhost.chickens.containers.ContainerEggCracker;
import net.creeperhost.chickens.containers.slots.SlotEgg;
import net.creeperhost.chickens.init.ModBlocks;
import net.creeperhost.chickens.item.ItemChickenEgg;
import net.creeperhost.chickens.network.PacketHandler;
import net.creeperhost.polylib.blockentity.BlockEntityInventory;
import net.creeperhost.polylib.containers.slots.SlotOutput;
import net.creeperhost.polylib.inventory.PolyFluidInventory;
import net.creeperhost.polylib.inventory.PolyItemInventory;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

public class BlockEntityEggCracker extends BlockEntityInventory
{
    public BlockEntityEggCracker(BlockPos blockPos, BlockState blockState)
    {
        super(ModBlocks.EGG_CRACKER_TILE.get(), blockPos, blockState);
        setInventory(new PolyItemInventory(10));
        getInventoryOptional().ifPresent(polyItemInventory ->
        {
            this.addSlot(new SlotEgg(polyItemInventory, 0, 26, 34, 64));

            int i = 0;
            for (int l = 0; l < 3; ++l)
            {
                for (int k = 0; k < 3; ++k)
                {
                    i++;
                    this.addSlot(new SlotOutput(polyItemInventory, i, 91 + k * 18, l * 18 + 17));
                }
            }
        });
        setContainerDataSize(1);
    }

    public final PolyFluidInventory fluidTank = new PolyFluidInventory(16000)
    {
        @Override
        public void setChanged()
        {
            super.setChanged();
            BlockEntityEggCracker.this.setChanged();
            sync();
        }
    };

    int progress = 0;

    public void tick()
    {
        if(level == null) return;
        if(level.isClientSide) return;
        if(!getItem(0).isEmpty() && getItem(0).getItem() instanceof ItemChickenEgg itemChickenEgg)
        {
            progress++;
            if(progress >= 100)
            {
                ItemStack drop = itemChickenEgg.getType(getItem(0)).getLayItemHolder().getStack();
                if (!drop.isEmpty())
                {
                    if (itemChickenEgg.isViable(getItem(0)))
                    {
                        ItemStack stack = getInventoryOptional().get().addItem(drop);
                        if (stack.isEmpty()) getItem(0).shrink(1);
                    }
                    else
                    {
                        getItem(0).shrink(1);
                    }
                    progress = 0;
                }
                else
                {
                    Fluid fluid = itemChickenEgg.getType(getItem(0)).getLayItemHolder().getFluid();
                    long amount = 1000;//itemChickenEgg.getType(getItem(0)).getDropItemHolder().getAmount();
                    if(fluid != Fluids.EMPTY)
                    {
                        if(fluidTank.getFluidStack().isEmpty())
                        {
                            fluidTank.setFluidStack(FluidStack.create(fluid, amount));
                            getItem(0).shrink(1);
                            progress = 0;
                        }
                        else if(fluidTank.getFluidStack().getFluid() == fluid)
                        {
                            long stored = fluidTank.getFluidStack().getAmount();
                            long freeSpace = fluidTank.getCapacity() - stored;
                            if(freeSpace > amount)
                            {
                                long newValue = stored + amount;
                                fluidTank.setFluidStack(FluidStack.create(fluid, newValue));
                                getItem(0).shrink(1);
                                progress = 0;
                            }
                        }
                    }
                }
            }
        }
        if(getItem(0).isEmpty() && progress > 0) progress = 0;
        setContainerDataValue(0, progress);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag compoundTag)
    {
        super.saveAdditional(compoundTag);
        compoundTag.merge(fluidTank.serializeNBT());
    }

    @Override
    public void load(@NotNull CompoundTag compoundTag)
    {
        super.load(compoundTag);
        fluidTank.deserializeNBT(compoundTag);
    }

    @Override
    protected Component getDefaultName()
    {
        return Component.literal("Egg Cracker");
    }

    @Override
    protected AbstractContainerMenu createMenu(int i, @NotNull Inventory inventory)
    {
        sync();
        return new ContainerEggCracker(i, inventory, this, getContainerData());
    }

    public void sync()
    {
        if(level == null) return;
        if(level.isClientSide) return;

//        ((ServerChunkCache) level.getChunkSource()).chunkMap.getPlayers(new ChunkPos(this.getBlockPos()), false).
//                forEach(serverPlayer -> PacketHandler.HANDLER.sendToPlayer(serverPlayer, new PacketFluidSync(getBlockPos(), fluidTank.getFluidStack())));
    }
}
