package net.creeperhost.chickens.containers;

import net.creeperhost.chickens.blockentities.BlockEntityBreeder;
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

public class ContainerBreeder extends PolyContainer
{
    ContainerData containerData;

    public ContainerBreeder(int id, Inventory playerInv, FriendlyByteBuf extraData)
    {
        this(id, playerInv, (BlockEntityBreeder) Minecraft.getInstance().level.getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(1));
    }

    public ContainerBreeder(int id, Inventory playerInv, BlockEntityBreeder blockEntityBreeder, ContainerData containerData)
    {
        super(ModContainers.BREEDER_CONTAINER.get(), id);
        this.containerData = containerData;

        blockEntityBreeder.getSlots().forEach(this::addSlot);

        for (int l = 0; l < 3; ++l)
        {
            for (int k = 0; k < 9; ++k)
            {
                this.addSlot(new Slot(playerInv, k + l * 9 + 9, 8 + k * 18, l * 18 + 51));
            }
        }

        for (int i1 = 0; i1 < 9; ++i1)
        {
            this.addSlot(new Slot(playerInv, i1, 8 + i1 * 18, 109));
        }
        addDataSlots(containerData);
    }

    @Override
    public boolean stillValid(@NotNull Player player)
    {
        return true;
    }

    public int getProgress()
    {
        return containerData.get(0);
    }
}