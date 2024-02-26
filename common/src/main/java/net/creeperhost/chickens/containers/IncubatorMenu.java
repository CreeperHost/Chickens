package net.creeperhost.chickens.containers;

import dev.architectury.fluid.FluidStack;
import net.creeperhost.chickens.blockentities.IncubatorBlockEntity;
import net.creeperhost.chickens.config.Config;
import net.creeperhost.chickens.init.ModContainers;
import net.creeperhost.polylib.client.modulargui.lib.container.DataSync;
import net.creeperhost.polylib.client.modulargui.lib.container.SlotGroup;
import net.creeperhost.polylib.containers.PolyBlockContainerMenu;
import net.creeperhost.polylib.containers.slots.PolySlot;
import net.creeperhost.polylib.data.serializable.FloatData;
import net.creeperhost.polylib.data.serializable.FluidData;
import net.creeperhost.polylib.data.serializable.IntData;
import net.creeperhost.polylib.data.serializable.LongData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

/**
 * Created by brandon3055 on 22/02/2024
 */
public class IncubatorMenu extends PolyBlockContainerMenu<IncubatorBlockEntity>
{
    public final DataSync<Float> humidity;
    public final DataSync<Integer> temperature;
    public final DataSync<FluidStack> tank;
    public final DataSync<Integer> heatSetting;
    public final DataSync<Long> energy;

    public final SlotGroup main = createSlotGroup(0, 1, 2, 3);
    public final SlotGroup hotBar = createSlotGroup(0, 1, 2, 3);

    public final SlotGroup eggSlots = createSlotGroup(1, 0);
    public final SlotGroup waterSlot = createSlotGroup(2, 0);
    public final SlotGroup energySlot = createSlotGroup(3, 0);

    public IncubatorMenu(int windowId, Inventory playerInv, FriendlyByteBuf extraData) {
        this(windowId, playerInv, getClientTile(playerInv, extraData));
    }

    public IncubatorMenu(int windowId, Inventory playerInv, IncubatorBlockEntity tile) {
        super(ModContainers.INCUBATOR.get(), windowId, playerInv, tile);
        humidity = new DataSync<>(this, new FloatData(), tile.humidity::get);
        temperature = new DataSync<>(this, new IntData(), tile.temperature::get);
        tank = new DataSync<>(this, new FluidData(), tile.tank::getFluid);
        heatSetting = new DataSync<>(this, new IntData(), tile.heatSetting::get);
        energy = new DataSync<>(this, new LongData(), tile.energy::getEnergyStored);

        main.addPlayerMain(playerInv);
        hotBar.addPlayerBar(playerInv);
        eggSlots.addSlots(9, 0, slot -> new PolySlot(tile.inventory, slot));
        waterSlot.addSlot(new PolySlot(tile.inventory, 9));
        if (Config.INSTANCE.enableEnergy){
            energySlot.addSlot(new PolySlot(tile.inventory, 10));
        }
    }


//    public ContainerIncubator(int id, Inventory playerInv, FriendlyByteBuf extraData)
//    {
//        this(id, playerInv, (BlockEntityIncubator) Minecraft.getInstance().level.getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(3));
//    }
//
//    public ContainerIncubator(int id, Inventory playerInv, BlockEntityIncubator blockEntityIncubator, ContainerData containerData)
//    {
//        super(ModContainers.INCUBATOR.get(), id);
//        this.containerData = containerData;
//        this.blockPos = blockEntityIncubator.getBlockPos();
//        this.blockEntityIncubator = blockEntityIncubator;
//
//        blockEntityIncubator.getSlots().forEach(this::addSlot);
//
//        for (int l = 0; l < 3; ++l)
//        {
//            for (int k = 0; k < 9; ++k)
//            {
//                this.addSlot(new Slot(playerInv, k + l * 9 + 9, 8 + k * 18, l * 18 + 84));
//            }
//        }
//
//        for (int i1 = 0; i1 < 9; ++i1)
//        {
//            this.addSlot(new Slot(playerInv, i1, 8 + i1 * 18, 142));
//        }
//        addDataSlots(containerData);
//    }
//
//    @Override
//    public boolean stillValid(@NotNull Player player)
//    {
//        return true;
//    }
//
//    public int getLightLevel()
//    {
//        return containerData.get(0);
//    }
//
//    public int getTemp()
//    {
//        return containerData.get(1);
//    }
//
//    public int getTankStored()
//    {
//        return containerData.get(2);
//    }
//
//    public BlockPos getBlockPos()
//    {
//        return blockPos;
//    }
//
//    public BlockEntityIncubator getBlockEntityIncubator()
//    {
//        return blockEntityIncubator;
//    }
}
