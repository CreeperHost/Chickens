package net.creeperhost.chickens.containers;

import net.creeperhost.chickens.blockentities.BlockEntityEggCracker;
import net.creeperhost.chickens.blockentities.BlockEntityIncubator;
import net.creeperhost.chickens.containers.slots.SlotEgg;
import net.creeperhost.chickens.containers.slots.SlotOutput;
import net.creeperhost.chickens.containers.slots.SlotWaterBucket;
import net.creeperhost.chickens.init.ModContainers;
import net.creeperhost.polylib.containers.PolyContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.NotNull;

public class ContainerEggCracker extends PolyContainer
{
    ContainerData containerData;
    BlockPos blockPos;

    public ContainerEggCracker(int id, Inventory playerInv, FriendlyByteBuf extraData)
    {
        this(id, playerInv, (BlockEntityEggCracker) Minecraft.getInstance().level.getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(3));
    }

    public ContainerEggCracker(int id, Inventory playerInv, BlockEntityEggCracker blockEntityEggCracker, ContainerData containerData)
    {
        super(ModContainers.EGG_CRACKER.get(), id);
        this.containerData = containerData;

        this.addSlot(new Slot(blockEntityEggCracker.inventory, 0, 26, 34));

        int i = 0;
        for (int l = 0; l < 3; ++l)
        {
            for (int k = 0; k < 3; ++k)
            {
                i++;
                this.addSlot(new Slot(blockEntityEggCracker.inventory, i, 105 + k * 18, l * 18 + 17));
            }
        }


        for (int l = 0; l < 3; ++l)
        {
            for (int k = 0; k < 9; ++k)
            {
                this.addSlot(new Slot(playerInv, k + l * 9 + 9, 8 + k * 18, l * 18 + 84));
            }
        }

        for (int i1 = 0; i1 < 9; ++i1)
        {
            this.addSlot(new Slot(playerInv, i1, 8 + i1 * 18, 142));
        }
        addDataSlots(containerData);
    }

    @Override
    public boolean stillValid(@NotNull Player player)
    {
        return true;
    }
}
