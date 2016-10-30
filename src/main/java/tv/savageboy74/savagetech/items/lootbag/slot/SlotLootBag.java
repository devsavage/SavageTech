package tv.savageboy74.savagetech.items.lootbag.slot;

/*
 * SlotLootBag.java
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
import tv.savageboy74.savagetech.items.lootbag.ContainerLootBag;
import tv.savageboy74.savagetech.items.lootbag.ItemLootBag;

public class SlotLootBag extends Slot
{
    private final EntityPlayer entityPlayer;
    private ContainerLootBag containerLootBag;

    public SlotLootBag(ContainerLootBag containerLootBag, IInventory inventory, EntityPlayer entityPlayer, int slotIndex, int x, int y)
    {
        super(inventory, slotIndex, x, y);
        this.entityPlayer = entityPlayer;
        this.containerLootBag = containerLootBag;
    }

    @Override
    public void onSlotChange(ItemStack itemStack1, ItemStack itemStack2)
    {
        super.onSlotChange(itemStack1, itemStack2);
        containerLootBag.saveInventory(entityPlayer);
    }

    /**
     * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
     *
     * This causes an error with Inventory Tweaks?
     */
    @Override
    public boolean isItemValid(ItemStack stack)
    {
        return !(stack.getItem() instanceof ItemLootBag);
    }
}
