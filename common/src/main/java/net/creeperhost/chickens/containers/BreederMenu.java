package net.creeperhost.chickens.containers;

import net.creeperhost.chickens.blockentities.BreederBlockEntity;
import net.creeperhost.chickens.init.ModContainers;
import net.creeperhost.polylib.client.modulargui.lib.container.DataSync;
import net.creeperhost.polylib.client.modulargui.lib.container.SlotGroup;
import net.creeperhost.polylib.containers.PolyBlockContainerMenu;
import net.creeperhost.polylib.containers.slots.PolySlot;
import net.creeperhost.polylib.data.serializable.IntData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public class BreederMenu extends PolyBlockContainerMenu<BreederBlockEntity> {
    public final SlotGroup main = createSlotGroup(0, 1, 2);
    public final SlotGroup hotBar = createSlotGroup(0, 1, 2);

    public final SlotGroup seeds = createSlotGroup(1, 0);
    public final SlotGroup chickens = createSlotGroup(2, 0);
    public final SlotGroup output = createSlotGroup(3, 0);

    public final DataSync<Integer> progress;

    public BreederMenu(int windowId, Inventory playerInv, FriendlyByteBuf extraData) {
        this(windowId, playerInv, getClientTile(playerInv, extraData));
    }

    public BreederMenu(int windowId, Inventory playerInv, BreederBlockEntity tile) {
        super(ModContainers.BREEDER_CONTAINER.get(), windowId, playerInv, tile);
        progress = new DataSync<>(this, new IntData(), tile.progress::get);

        main.addPlayerMain(playerInv);
        hotBar.addPlayerBar(playerInv);

        seeds.addSlot(new PolySlot(tile.inventory, 0));
        chickens.addSlots(2, 1, slot -> new PolySlot(tile.inventory, slot).setStackLimit(stack -> 1));
        output.addSlots(3, 3, slot -> new PolySlot(tile.inventory, slot).output());
    }
}
