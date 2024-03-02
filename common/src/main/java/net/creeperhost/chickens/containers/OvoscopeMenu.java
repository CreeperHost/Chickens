package net.creeperhost.chickens.containers;

import net.creeperhost.chickens.blockentities.OvoscopeBlockEntity;
import net.creeperhost.chickens.init.ModContainers;
import net.creeperhost.polylib.client.modulargui.lib.container.SlotGroup;
import net.creeperhost.polylib.containers.PolyBlockContainerMenu;
import net.creeperhost.polylib.containers.slots.PolySlot;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public class OvoscopeMenu extends PolyBlockContainerMenu<OvoscopeBlockEntity>
{
    public final SlotGroup main = createSlotGroup(0, 1);
    public final SlotGroup hotBar = createSlotGroup(0, 1);

    public final SlotGroup input = createSlotGroup(1, 0);
    public final SlotGroup viable = createSlotGroup(2, 0);
    public final SlotGroup nonViable = createSlotGroup(3, 0);

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
    }











//
//    public ContainerOvoscope(int id, Inventory playerInv, FriendlyByteBuf extraData)
//    {
//        this(id, playerInv, (OvoscopeBlockEntity) Minecraft.getInstance().level.getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(1));
//    }
//
//    public ContainerOvoscope(int id, Inventory playerInv, OvoscopeBlockEntity blockEntityOvoscope, ContainerData containerData)
//    {
//        super(ModContainers.OVOSCOPE.get(), id);
//        this.containerData = containerData;
//
//        blockEntityOvoscope.getSlots().forEach(this::addSlot);
//
//        drawPlayersInv(playerInv, 15, 132);
//        drawPlayersHotBar(playerInv, 15, 132 + 58);
//        addDataSlots(containerData);
//    }

}
