package tv.savageboy74.savagetech.blocks.infusion;

/*
 * ContainerSoulInfuser.java
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
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import tv.savageboy74.savagetech.container.base.STContainerBase;
import tv.savageboy74.savagetech.init.ModItems;

public class ContainerSoulInfuser extends STContainerBase
{
    public EntityPlayer player;
    public TileEntitySoulInfuser tileEntitySoulInfuser;
    private int infuserBurnTime;
    private int rawSoulMatterBurnTime;
    private int cookTime;
    private int totalCookTime;

    public ContainerSoulInfuser(EntityPlayer player, TileEntitySoulInfuser tileEntitySoulInfuser) {
        this.player = player;
        this.tileEntitySoulInfuser = tileEntitySoulInfuser;

        this.addSlotToContainer(new SlotItemInfuser(tileEntitySoulInfuser, TileEntitySoulInfuser.INFUSED_STONE_SLOT_INDEX, 8, 61));
        this.addSlotToContainer(new SlotRawSoulMatter(tileEntitySoulInfuser, TileEntitySoulInfuser.RAW_SOUL_MATTER_SLOT_INDEX, 80, 53));
        this.addSlotToContainer(new SlotSoulMatterOutput(tileEntitySoulInfuser, TileEntitySoulInfuser.SOUL_MATTER_SLOT_INDEX, 80, 17));

        for (int inventoryRowIndex = 0; inventoryRowIndex < 3; inventoryRowIndex++) {
            for (int inventoryColumnIndex = 0; inventoryColumnIndex < 9; inventoryColumnIndex++) {
                this.addSlotToContainer(new Slot(player.inventory, inventoryColumnIndex + inventoryRowIndex * 9 + 9, 8 + inventoryColumnIndex * 18, 84 + inventoryRowIndex * 18));
            }
        }

        for (int actionBarSlotIndex = 0; actionBarSlotIndex < 9; actionBarSlotIndex++) {
            this.addSlotToContainer(new Slot(player.inventory, actionBarSlotIndex, 8 + actionBarSlotIndex * 18, 142));
        }
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
    }

    @Override
    public void onContainerClosed(EntityPlayer playerIn) {
        super.onContainerClosed(playerIn);
        tileEntitySoulInfuser.closeInventory(playerIn);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return this.tileEntitySoulInfuser.isUseableByPlayer(playerIn);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int slotIndex) {
        ItemStack itemStack = null;
        Slot slot = (Slot) inventorySlots.get(slotIndex);

        if (slot != null && slot.getHasStack()) {
            ItemStack slotItemStack = slot.getStack();
            itemStack = slotItemStack.copy();

            if (slotIndex < TileEntitySoulInfuser.INVENTORY_SIZE) {
                if (!this.mergeItemStack(slotItemStack, this.tileEntitySoulInfuser.getSizeInventory(), inventorySlots.size(), true)) {
                    return null;
                }
            } else {
                if (!this.mergeItemStack(slotItemStack, 0, TileEntitySoulInfuser.INVENTORY_SIZE, false)) {
                    return null;
                }
            }

            if (slotItemStack.stackSize == itemStack.stackSize) {
                return null;
            }

            if (slotItemStack.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }
        }

        return itemStack;
    }

    private class SlotItemInfuser extends Slot
    {
        public SlotItemInfuser(IInventory inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return stack.getItem() == ModItems.soulEnergizer;
        }
    }

    private class SlotRawSoulMatter extends Slot
    {
        public SlotRawSoulMatter(IInventory inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return stack.getItem() == ModItems.rawSoulMatter;
        }
    }

    private class SlotSoulMatterOutput extends Slot
    {
        public SlotSoulMatterOutput(IInventory inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return false;
        }

        @Override
        public ItemStack decrStackSize(int amount) {
            return super.decrStackSize(amount);
        }
    }
}
