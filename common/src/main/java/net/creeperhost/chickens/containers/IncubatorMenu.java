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

    public final SlotGroup main = createSlotGroup(0, 1, 2);
    public final SlotGroup hotBar = createSlotGroup(0, 1, 2);

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
            main.quickMoveTo.add(3);
            hotBar.quickMoveTo.add(3);
        }
    }
}
