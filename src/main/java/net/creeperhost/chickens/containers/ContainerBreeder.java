package net.creeperhost.chickens.containers;

import net.creeperhost.chickens.blockentities.BlockEntityBreeder;
import net.creeperhost.chickens.containers.slots.SlotChicken;
import net.creeperhost.chickens.containers.slots.SlotOutput;
import net.creeperhost.chickens.containers.slots.SlotSeed;
import net.creeperhost.chickens.init.ModContainers;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;

public class ContainerBreeder extends ContainerBase
{
    ContainerData containerData;
    BlockEntityBreeder breeder;

    public ContainerBreeder(int id, Inventory playerInv, FriendlyByteBuf extraData)
    {
        this(id, playerInv, (BlockEntityBreeder) Minecraft.getInstance().level.getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(1));
    }

    public ContainerBreeder(int id, Inventory playerInv, BlockEntityBreeder blockEntityBreeder, ContainerData containerData)
    {
        super(ModContainers.BREEDER_CONTAINER.get(), id);
        this.containerData = containerData;
        this.breeder = blockEntityBreeder;

        addSlot(new SlotChicken(blockEntityBreeder.inventory, 0, 44, 20));
        addSlot(new SlotChicken(blockEntityBreeder.inventory, 1, 62, 20));
        addSlot(new SlotSeed(blockEntityBreeder.inventory, 2, 8, 20));

        for (int i = 0; i < 3; ++i)
        {
            addSlot(new SlotOutput(blockEntityBreeder.inventory, i + 3, 116 + i * 18, 20));
        }

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
    public boolean stillValid(Player player)
    {
        return player.level.getBlockEntity(breeder.getBlockPos()) != null;
    }

    public int getProgress()
    {
        return containerData.get(0);
    }
}
