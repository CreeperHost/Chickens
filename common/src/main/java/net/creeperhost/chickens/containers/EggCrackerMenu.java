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
import net.creeperhost.polylib.data.serializable.LongData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public class EggCrackerMenu extends PolyBlockContainerMenu<EggCrackerBlockEntity> {
    public final DataSync<FluidStack> tank;
    public final DataSync<Long> energy;

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

        main.addPlayerMain(playerInv);
        hotBar.addPlayerBar(playerInv);

        input.addSlot(new PolySlot(tile.inventory, 0));
        output.addSlots(9, 1, slot -> new PolySlot(tile.inventory, slot).output());
        fluidSlot.addSlot(new PolySlot(tile.inventory, 10));
        if (Config.INSTANCE.enableEnergy) {
            energySlot.addSlot(new PolySlot(tile.inventory, 11));
        }
    }


//    public ContainerEggCracker(int id, Inventory playerInv, FriendlyByteBuf extraData)
//    {
//        this(id, playerInv, (EggCrackerBlockEntity) Minecraft.getInstance().level.getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(3));
//    }
//
//    public ContainerEggCracker(int id, Inventory playerInv, EggCrackerBlockEntity blockEntityEggCracker, ContainerData containerData)
//    {
//        super(ModContainers.EGG_CRACKER.get(), id);
//        this.containerData = containerData;
//        this.blockEntityEggCracker = blockEntityEggCracker;
//        blockEntityEggCracker.getSlots().forEach(this::addSlot);
//
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
//    public int getProgress()
//    {
//        return containerData.get(0);
//    }
}
