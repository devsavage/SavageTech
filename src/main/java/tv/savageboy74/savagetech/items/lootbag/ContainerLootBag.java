package tv.savageboy74.savagetech.items.lootbag;

/*
 * ContainerLootBag.java
 * Copyright (C) 2016 Savage - github.com/savageboy74
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import tv.savageboy74.savagetech.container.base.STContainerBase;
import tv.savageboy74.savagetech.items.lootbag.slot.SlotLootBag;
import tv.savageboy74.savagetech.util.helper.NBTHelper;
import tv.savageboy74.savagetech.util.reference.Names;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContainerLootBag extends STContainerBase
{
    public InventoryLootBag invLootBag;
    private final EntityPlayer player;
    private final InventoryLootBag inventoryLootBag;

    private int rows;
    private int columns;

    public static final int BAG_INVENTORY_ROWS = 9;
    public static final int BAG_INVENTORY_COLUMNS = 13;

    public ContainerLootBag(EntityPlayer entityPlayer, InventoryLootBag inventoryLootBag) {
        this.player = entityPlayer;
        this.inventoryLootBag = inventoryLootBag;

        rows = BAG_INVENTORY_ROWS;
        columns = BAG_INVENTORY_COLUMNS;

        for (int bagRowIndex = 0; bagRowIndex < rows; ++bagRowIndex) {
            for (int bagColumnIndex = 0; bagColumnIndex < columns; ++bagColumnIndex) {
                this.addSlotToContainer(new SlotLootBag(this, inventoryLootBag, player, bagColumnIndex + bagRowIndex * columns, 12 + bagColumnIndex * 18, 8 + bagRowIndex * 18));
            }
        }

        for (int playerRowIndex = 0; playerRowIndex < PLAYER_INVENTORY_ROWS; ++playerRowIndex) {
            for (int playerColumnIndex = 0; playerColumnIndex < PLAYER_INVENTORY_COLUMNS; ++ playerColumnIndex) {
                this.addSlotToContainer(new Slot(player.inventory, playerColumnIndex + playerRowIndex * 9 + 9, 48 + playerColumnIndex * 18, 174 + playerRowIndex * 18));
            }
        }

        for (int actionBarSlotIndex = 0; actionBarSlotIndex < PLAYER_INVENTORY_COLUMNS; ++actionBarSlotIndex) {
            this.addSlotToContainer(new Slot(player.inventory, actionBarSlotIndex, 48 + actionBarSlotIndex * 18, 232));
        }
    }

    @Override
    public void onContainerClosed(EntityPlayer playerIn) {
        super.onContainerClosed(playerIn);

        if (!playerIn.worldObj.isRemote) {
            InventoryPlayer inventory = playerIn.inventory;
            for (ItemStack itemStack : inventory.mainInventory) {
                if (itemStack != null) {
                    if (NBTHelper.hasTag(itemStack, Names.NBT.LOOT_BAG_GUI_OPEN)) {
                        NBTHelper.removeTag(itemStack, Names.NBT.LOOT_BAG_GUI_OPEN);
                    }
                }
            }

            saveInventory(playerIn);
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int slotIndex) {
        ItemStack newItemStack = null;
        Slot slot = (Slot) inventorySlots.get(slotIndex);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemStack = slot.getStack();
            newItemStack = itemStack.copy();


            if (slotIndex < rows * columns) {
                if (!this.mergeItemStack(itemStack, rows * columns, inventorySlots.size(), false)) {
                    return null;
                }
            } else if (itemStack.getItem() instanceof ItemLootBag) {

                if (slotIndex < (rows * columns) + (PLAYER_INVENTORY_ROWS * PLAYER_INVENTORY_COLUMNS)) {
                    if (!this.mergeItemStack(itemStack, (rows * columns) + (PLAYER_INVENTORY_ROWS * PLAYER_INVENTORY_COLUMNS), inventorySlots.size(), false)) {
                        return null;
                    }
                } else if (!this.mergeItemStack(itemStack, rows * columns, (rows * columns) + (PLAYER_INVENTORY_ROWS * PLAYER_INVENTORY_COLUMNS), false)) {
                    return null;
                }
            } else if (!this.mergeItemStack(itemStack, 0, rows * columns, false)) {
                return null;
            }

            if (itemStack.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }
        }

        return newItemStack;
    }

    public void saveInventory(EntityPlayer player) {
        inventoryLootBag.onGuiSaved(player);
    }
}
