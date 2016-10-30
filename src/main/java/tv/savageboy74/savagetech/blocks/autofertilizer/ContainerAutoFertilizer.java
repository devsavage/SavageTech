package tv.savageboy74.savagetech.blocks.autofertilizer;

/*
 * ContainerAutoFertilizer.java
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
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import tv.savageboy74.savagetech.container.base.STContainerBase;
import tv.savageboy74.savagetech.init.ModItems;

public class ContainerAutoFertilizer extends STContainerBase
{
    private TileEntityAutoFertilizer tileEntityAutoFertilizer;

    public ContainerAutoFertilizer(InventoryPlayer invPlayer, TileEntityAutoFertilizer tileEntityAutoFertilizer) {
        this.tileEntityAutoFertilizer = tileEntityAutoFertilizer;

        this.addSlotToContainer(new SlotFertilizer(tileEntityAutoFertilizer, TileEntityAutoFertilizer.FERTILIZER_SLOT_INVENTORY_INDEX, 80, 35));

        for (int inventoryRowIndex = 0; inventoryRowIndex < 3; inventoryRowIndex++) {
            for (int inventoryColumnIndex = 0; inventoryColumnIndex < 9; inventoryColumnIndex++) {
                this.addSlotToContainer(new Slot(invPlayer, inventoryColumnIndex + inventoryRowIndex * 9 + 9, 8 + inventoryColumnIndex * 18, 84 + inventoryRowIndex * 18));
            }
        }

        for (int actionBarSlotIndex = 0; actionBarSlotIndex < 9; actionBarSlotIndex++) {
            this.addSlotToContainer(new Slot(invPlayer, actionBarSlotIndex, 8 + actionBarSlotIndex * 18, 142));
        }
    }

    @Override
    public void onContainerClosed(EntityPlayer playerIn) {
        super.onContainerClosed(playerIn);
        tileEntityAutoFertilizer.closeInventory(playerIn);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return this.tileEntityAutoFertilizer.isUseableByPlayer(playerIn);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        short area = tileEntityAutoFertilizer.getArea();
        for(int i = 0; i < listeners.size(); i++) {
            ((IContainerListener)listeners.get(i)).sendProgressBarUpdate(this, 100, area);
        }
    }

    @Override
    public void updateProgressBar(int id, int data) {
        super.updateProgressBar(id, data);

        if(id == 100)
            tileEntityAutoFertilizer.setArea((short) data);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int slotIndex) {
        ItemStack itemStack = null;
        Slot slot = (Slot) inventorySlots.get(slotIndex);

        if (slot != null && slot.getHasStack()) {
            ItemStack slotItemStack = slot.getStack();
            itemStack = slotItemStack.copy();

            if (slotIndex < TileEntityAutoFertilizer.INVENTORY_SIZE) {

                if (!this.mergeItemStack(slotItemStack, this.tileEntityAutoFertilizer.getSizeInventory(), inventorySlots.size(), true)) {
                    return null;
                }
            } else {
                if (!this.mergeItemStack(slotItemStack, 0, TileEntityAutoFertilizer.INVENTORY_SIZE, false)) {
                    return null;
                }
            }

            if (slotItemStack.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }
        }

        return itemStack;
    }

    private class SlotFertilizer extends Slot
    {

        public SlotFertilizer(IInventory inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            EnumDyeColor enumdyecolor = EnumDyeColor.byDyeDamage(stack.getMetadata());
            return enumdyecolor == EnumDyeColor.WHITE || stack.getItem() == ModItems.bonemealBundle;
        }
    }
}
