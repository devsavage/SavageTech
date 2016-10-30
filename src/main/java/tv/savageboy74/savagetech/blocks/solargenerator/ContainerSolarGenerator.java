package tv.savageboy74.savagetech.blocks.solargenerator;

/*
 * ContainerSolarGenerator.java
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

//public class ContainerSolarGenerator extends STContainerBase
//{
//    private TileEntitySolarGenerator tileSolarGenerator;
//    private EntityPlayer player;
//
//    public int pastInternalEnergyStored;
//
//    public ContainerSolarGenerator(EntityPlayer player, TileEntitySolarGenerator tileEntitySolarGenerator) {
//        this.tileSolarGenerator = tileEntitySolarGenerator;
//        this.player = player;
//
//        this.addSlotToContainer(new SlotEnergy(tileSolarGenerator, TileEntitySolarGenerator.ENERGY_OUTPUT_INDEX, 8, 65));
//
//        for (int inventoryRowIndex = 0; inventoryRowIndex < PLAYER_INVENTORY_ROWS; ++inventoryRowIndex) {
//            for (int inventoryColumnIndex = 0; inventoryColumnIndex < PLAYER_INVENTORY_COLUMNS; ++inventoryColumnIndex) {
//                this.addSlotToContainer(new Slot(player.inventory, inventoryColumnIndex + inventoryRowIndex * 9 + 9, 8 + inventoryColumnIndex * 18, 84 + inventoryRowIndex * 18));
//            }
//        }
//
//        for (int actionBarSlotIndex = 0; actionBarSlotIndex < PLAYER_INVENTORY_COLUMNS; ++actionBarSlotIndex) {
//            this.addSlotToContainer(new Slot(player.inventory, actionBarSlotIndex, 8 + actionBarSlotIndex * 18, 142));
//        }
//    }
//
//    @Override
//    public void detectAndSendChanges() {
//        super.detectAndSendChanges();
//
//        // Does this fix the casting EntityPlayerMP -> EntityPlayerSP bug?
//        if(!tileSolarGenerator.getWorld().isRemote)
//            if (this.pastInternalEnergyStored != this.tileSolarGenerator.getEnergyStored()) {
//                MessageSolarGenerator message = new MessageSolarGenerator(tileSolarGenerator.x(), tileSolarGenerator.y(), tileSolarGenerator.z(), tileSolarGenerator.getEnergyStored(null));
//                PacketHandler.sendTo(message, (EntityPlayerMP) player);
//            }
//
//        this.pastInternalEnergyStored = this.tileSolarGenerator.getEnergyStored(null);
//    }
//
//    @Override
//    public ItemStack transferStackInSlot(EntityPlayer playerIn, int slotIndex) {
//        ItemStack newItemStack = null;
//        Slot slot = (Slot) inventorySlots.get(slotIndex);
//
//        if (slot != null && slot.getHasStack()) {
//            ItemStack itemStack = slot.getStack();
//            newItemStack = itemStack.copy();
//
//
//            if (slotIndex < PLAYER_INVENTORY_ROWS * PLAYER_INVENTORY_COLUMNS) {
//                if (!this.mergeItemStack(itemStack, PLAYER_INVENTORY_ROWS * PLAYER_INVENTORY_COLUMNS, inventorySlots.size(), false)) {
//                    return null;
//                }
//            } else if (!this.mergeItemStack(itemStack, 0, PLAYER_INVENTORY_ROWS * PLAYER_INVENTORY_COLUMNS, false)) {
//                return null;
//            }
//
//
//            if (itemStack.stackSize == 0) {
//                slot.putStack(null);
//            } else {
//                slot.onSlotChanged();
//            }
//        }
//
//        return newItemStack;
//    }
//
//    @Override
//    public void onContainerClosed(EntityPlayer playerIn) {
//        super.onContainerClosed(playerIn);
//        tileSolarGenerator.closeInventory(playerIn);
//    }
//
//    @Override
//    public boolean canInteractWith(EntityPlayer playerIn) {
//        return this.tileSolarGenerator.isUseableByPlayer(playerIn);
//    }
//
//    public class SlotEnergy extends Slot
//    {
//
//        public SlotEnergy(IInventory inventoryIn, int slotIndex, int xPosition, int yPosition) {
//            super(inventoryIn, slotIndex, xPosition, yPosition);
//        }
//
//        @Override
//        public boolean isItemValid(ItemStack stack) {
//            return ItemHelper.isValidEnergyItem(stack);
//        }
//    }
//}
