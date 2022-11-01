package net.creeperhost.chickens.containers;

import net.creeperhost.chickens.blockentities.BlockEntityHenhouse;
import net.creeperhost.chickens.init.ModContainers;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class ContainerHenhouse extends ContainerBase
{
    public final BlockEntityHenhouse tileEntityHenhouse;
    private final ContainerData data;

    public ContainerHenhouse(int id, Inventory playerInv, FriendlyByteBuf extraData)
    {
        this(id, playerInv, (BlockEntityHenhouse) Minecraft.getInstance().level.getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(1));
    }

    public ContainerHenhouse(int id, Inventory playerInv, BlockEntityHenhouse tileEntityHenhouse, ContainerData data)
    {
        super(ModContainers.HENHOUSE_CONTAINER.get(), id);
        this.tileEntityHenhouse = tileEntityHenhouse;
        this.data = data;

        this.addSlot(new SlotItemHandler(tileEntityHenhouse.inventory, BlockEntityHenhouse.hayBaleSlotIndex, 25, 19)
        {
            @Override
            public boolean mayPickup(Player playerIn)
            {
                return !tileEntityHenhouse.inventory.extractItem(BlockEntityHenhouse.hayBaleSlotIndex, 1, true).isEmpty();
            }

            @NotNull
            @Override
            public ItemStack remove(int amount)
            {
                return tileEntityHenhouse.inventory.extractItem(BlockEntityHenhouse.hayBaleSlotIndex, amount, false);
            }
        });

        //output
        this.addSlot(new SlotItemHandler(tileEntityHenhouse.inventory, BlockEntityHenhouse.dirtSlotIndex, 25, 55));

        for (int row = 0; row < 3; row++)
        {
            for (int column = 0; column < 3; column++)
            {
                this.addSlot(new SlotItemHandler(tileEntityHenhouse.inventory, BlockEntityHenhouse.firstItemSlotIndex + (row * 3) + column, 98 + column * 18, 17 + row * 18));
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
        addDataSlots(data);
    }

    @Override
    public boolean stillValid(Player player)
    {
        return true;
    }

    public int getEnergy()
    {
        return this.data.get(0);
    }
}
