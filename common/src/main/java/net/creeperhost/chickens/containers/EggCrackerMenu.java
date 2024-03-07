package net.creeperhost.chickens.containers;

import dev.architectury.fluid.FluidStack;
import net.creeperhost.chickens.blockentities.EggCrackerBlockEntity;
import net.creeperhost.chickens.config.Config;
import net.creeperhost.chickens.init.ModContainers;
import net.creeperhost.polylib.client.modulargui.lib.container.DataSync;
import net.creeperhost.polylib.client.modulargui.lib.container.SlotGroup;
import net.creeperhost.polylib.containers.PolyBlockContainerMenu;
import net.creeperhost.polylib.containers.slots.PolySlot;
import net.creeperhost.polylib.data.serializable.FluidData;
import net.creeperhost.polylib.data.serializable.IntData;
import net.creeperhost.polylib.data.serializable.LongData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public class EggCrackerMenu extends PolyBlockContainerMenu<EggCrackerBlockEntity> {
    public final DataSync<FluidStack> tank;
    public final DataSync<Long> energy;
    public final DataSync<Integer> progress;

    public final SlotGroup main = createSlotGroup(0, 1, 2, 3);
    public final SlotGroup hotBar = createSlotGroup(0, 1, 2, 3);

    public final SlotGroup input = createSlotGroup(1, 0);
    public final SlotGroup fluidSlot = createSlotGroup(2, 0);
    public final SlotGroup energySlot = createSlotGroup(3, 0);
    public final SlotGroup output = createSlotGroup(4, 0);

    public EggCrackerMenu(int windowId, Inventory playerInv, FriendlyByteBuf extraData) {
        this(windowId, playerInv, getClientTile(playerInv, extraData));
    }

    public EggCrackerMenu(int windowId, Inventory playerInv, EggCrackerBlockEntity tile) {
        super(ModContainers.EGG_CRACKER.get(), windowId, playerInv, tile);
        tank = new DataSync<>(this, new FluidData(), tile.tank::getFluid);
        energy = new DataSync<>(this, new LongData(), tile.energy::getEnergyStored);
        progress = new DataSync<>(this, new IntData(), tile.progress::get);

        main.addPlayerMain(playerInv);
        hotBar.addPlayerBar(playerInv);

        input.addSlot(new PolySlot(tile.inventory, 0));
        output.addSlots(6, 1, slot -> new PolySlot(tile.inventory, slot).output());
        fluidSlot.addSlot(new PolySlot(tile.inventory, 7));
        if (Config.INSTANCE.enableEnergy) {
            energySlot.addSlot(new PolySlot(tile.inventory, 8));
        }
    }
}
