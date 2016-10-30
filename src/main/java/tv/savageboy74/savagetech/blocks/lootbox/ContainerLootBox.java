package tv.savageboy74.savagetech.blocks.lootbox;

/*
 * ContainerLootBox.java
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContainerLootBox extends STContainerBase
{
    public static final int CHEST_INVENTORY_ROWS = 9;
    public static final int CHEST_INVENTORY_COLUMNS = 13;
    public static final int INVENTORY_SIZE = CHEST_INVENTORY_ROWS * CHEST_INVENTORY_COLUMNS;

    private TileEntityLootBox tileEntityLootBox;
    private int rows;
    private int columns;

    public ContainerLootBox(EntityPlayer player, TileEntityLootBox chest) {
        this.tileEntityLootBox = chest;
        tileEntityLootBox.openInventory(player);

        rows = CHEST_INVENTORY_ROWS;
        columns = CHEST_INVENTORY_COLUMNS;

        for (int chestRowIndex = 0; chestRowIndex < rows; ++chestRowIndex) {
            for (int chestColumnIndex = 0; chestColumnIndex < columns; ++chestColumnIndex) {
                this.addSlotToContainer(new Slot(tileEntityLootBox, chestColumnIndex + chestRowIndex * columns, 12 + chestColumnIndex * 18, 8 + chestRowIndex * 18));
            }
        }

        for (int inventoryRowIndex = 0; inventoryRowIndex < PLAYER_INVENTORY_ROWS; ++inventoryRowIndex) {
            for (int inventoryColumnIndex = 0; inventoryColumnIndex < PLAYER_INVENTORY_COLUMNS; ++inventoryColumnIndex) {
                this.addSlotToContainer(new Slot(player.inventory, inventoryColumnIndex + inventoryRowIndex * 9 + 9, 48 + inventoryColumnIndex * 18, 174 + inventoryRowIndex * 18));
            }
        }

        for (int actionBarSlotIndex = 0; actionBarSlotIndex < PLAYER_INVENTORY_COLUMNS; ++actionBarSlotIndex) {
            this.addSlotToContainer(new Slot(player.inventory, actionBarSlotIndex, 48 + actionBarSlotIndex * 18, 232));
        }
    }

    @Override
    public void onContainerClosed(EntityPlayer playerIn) {
        super.onContainerClosed(playerIn);
        tileEntityLootBox.closeInventory(playerIn);
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
}
