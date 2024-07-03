package net.creeperhost.chickens.containers;

import net.creeperhost.chickens.blockentities.OvoscopeBlockEntity;
import net.creeperhost.chickens.config.Config;
import net.creeperhost.chickens.init.ModContainers;
import net.creeperhost.polylib.client.modulargui.lib.container.DataSync;
import net.creeperhost.polylib.client.modulargui.lib.container.SlotGroup;
import net.creeperhost.polylib.containers.PolyBlockContainerMenu;
import net.creeperhost.polylib.containers.slots.PolySlot;
import net.creeperhost.polylib.data.serializable.BooleanData;
import net.creeperhost.polylib.data.serializable.ByteData;
import net.creeperhost.polylib.data.serializable.IntData;
import net.creeperhost.polylib.data.serializable.LongData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class OvoscopeMenu extends PolyBlockContainerMenu<OvoscopeBlockEntity> {
    public final SlotGroup main = Config.INSTANCE.enableEnergy ? createSlotGroup(0, 1, 4) : createSlotGroup(0, 1);
    public final SlotGroup hotBar = Config.INSTANCE.enableEnergy ? createSlotGroup(0, 1, 4) : createSlotGroup(0, 1);

    public final SlotGroup input = createSlotGroup(1, 0);
    public final SlotGroup viable = createSlotGroup(2, 0);
    public final SlotGroup nonViable = createSlotGroup(3, 0);
    public final SlotGroup energySlot = createSlotGroup(4, 0);

    public final DataSync<Boolean> scanning;
    public final DataSync<Integer> progress;
    public final DataSync<Long> energy;
    public final DataSync<Byte> scanCount;

    public OvoscopeMenu(int windowId, Inventory playerInv, FriendlyByteBuf extraData) {
        this(windowId, playerInv, getClientTile(playerInv, extraData));
    }

    public OvoscopeMenu(int windowId, Inventory playerInv, OvoscopeBlockEntity tile) {
        super(ModContainers.OVOSCOPE.get(), windowId, playerInv, tile);
        main.addPlayerMain(playerInv);
        hotBar.addPlayerBar(playerInv);

        input.addSlot(new PolySlot(tile.inventory, 0));
        viable.addSlot(new PolySlot(tile.inventory, 1).output());
        nonViable.addSlot(new PolySlot(tile.inventory, 2).output());

        scanning = new DataSync<>(this, new BooleanData(), () -> tile.scanning);
        progress = new DataSync<>(this, new IntData(), tile.progress::get);
        energy = new DataSync<>(this, new LongData(), tile.energy::getEnergyStored);
        scanCount = new DataSync<>(this, new ByteData(), () -> tile.scanCount);

        if (Config.INSTANCE.enableEnergy) {
            energySlot.addSlot(new PolySlot(tile.inventory, 3).setStackLimit(e -> 1));
        }
    }
}
