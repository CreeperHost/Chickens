package net.creeperhost.chickens.containers;

import net.creeperhost.chickens.blockentities.BlockEntityOvoscope;
import net.creeperhost.chickens.init.ModContainers;
import net.creeperhost.polylib.containers.PolyContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.NotNull;

public class ContainerOvoscope extends PolyContainer
{
    ContainerData containerData;

    public ContainerOvoscope(int id, Inventory playerInv, FriendlyByteBuf extraData)
    {
        this(id, playerInv, (BlockEntityOvoscope) Minecraft.getInstance().level.getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(1));
    }

    public ContainerOvoscope(int id, Inventory playerInv, BlockEntityOvoscope blockEntityOvoscope, ContainerData containerData)
    {
        super(ModContainers.OVOSCOPE.get(), id);
        this.containerData = containerData;

        blockEntityOvoscope.getSlots().forEach(this::addSlot);

        drawPlayersInv(playerInv, 15, 132);
        drawPlayersHotBar(playerInv, 15, 132 + 58);
        addDataSlots(containerData);
    }

    @Override
    public boolean stillValid(@NotNull Player player)
    {
        return true;
    }
}
