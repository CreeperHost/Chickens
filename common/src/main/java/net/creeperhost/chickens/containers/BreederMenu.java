package net.creeperhost.chickens.containers;

import net.creeperhost.chickens.blockentities.BreederBlockEntity;
import net.creeperhost.chickens.init.ModContainers;
import net.creeperhost.polylib.client.modulargui.lib.container.SlotGroup;
import net.creeperhost.polylib.containers.PolyBlockContainerMenu;
import net.creeperhost.polylib.containers.slots.PolySlot;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public class BreederMenu extends PolyBlockContainerMenu<BreederBlockEntity>
{
    public final SlotGroup main = createSlotGroup(0, 1, 2);
    public final SlotGroup hotBar = createSlotGroup(0, 1, 2);

    public final SlotGroup seeds = createSlotGroup(1, 0);
    public final SlotGroup chickens = createSlotGroup(2, 0);
    public final SlotGroup output = createSlotGroup(3, 0);

    public BreederMenu(int windowId, Inventory playerInv, FriendlyByteBuf extraData) {
        this(windowId, playerInv, getClientTile(playerInv, extraData));
    }

    public BreederMenu(int windowId, Inventory playerInv, BreederBlockEntity tile) {
        super(ModContainers.BREEDER_CONTAINER.get(), windowId, playerInv, tile);
//        humidity = new DataSync<>(this, new FloatData(), tile.humidity::get);
//        temperature = new DataSync<>(this, new IntData(), tile.temperature::get);
//        tank = new DataSync<>(this, new FluidData(), tile.tank::getFluid);
//        heatSetting = new DataSync<>(this, new IntData(), tile.heatSetting::get);
//        energy = new DataSync<>(this, new LongData(), tile.energy::getEnergyStored);

        main.addPlayerMain(playerInv);
        hotBar.addPlayerBar(playerInv);

        seeds.addSlot(new PolySlot(tile.inventory, 0));
        chickens.addSlots(2, 1, slot -> new PolySlot(tile.inventory, slot));
        output.addSlots(3, 3, slot -> new PolySlot(tile.inventory, slot).output());
    }




//    public ContainerBreeder(int id, Inventory playerInv, FriendlyByteBuf extraData)
//    {
//        this(id, playerInv, (BlockEntityBreeder) Minecraft.getInstance().level.getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(1));
//    }
//
//    public ContainerBreeder(int id, Inventory playerInv, BlockEntityBreeder blockEntityBreeder, ContainerData containerData)
//    {
//        super(ModContainers.BREEDER_CONTAINER.get(), id);
//        this.containerData = containerData;
//
//        blockEntityBreeder.getSlots().forEach(this::addSlot);
//
//        for (int l = 0; l < 3; ++l)
//        {
//            for (int k = 0; k < 9; ++k)
//            {
//                this.addSlot(new Slot(playerInv, k + l * 9 + 9, 8 + k * 18, l * 18 + 51));
//            }
//        }
//
//        for (int i1 = 0; i1 < 9; ++i1)
//        {
//            this.addSlot(new Slot(playerInv, i1, 8 + i1 * 18, 109));
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
//    public int getProgress()
//    {
//        return containerData.get(0);
//    }
}
