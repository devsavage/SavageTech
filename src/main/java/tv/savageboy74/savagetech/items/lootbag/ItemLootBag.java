package tv.savageboy74.savagetech.items.lootbag;

/*
 * ItemLootBag.java
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
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import tv.savageboy74.savagetech.SavageTech;
import tv.savageboy74.savagetech.items.base.STItem;
import tv.savageboy74.savagetech.util.helper.ItemHelper;
import tv.savageboy74.savagetech.util.helper.NBTHelper;
import tv.savageboy74.savagetech.util.reference.Names;

public class ItemLootBag extends STItem
{
    public ItemLootBag() {
        this.setMaxStackSize(1);
        this.setUnlocalizedName(Names.Items.lootBag);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
        if (!worldIn.isRemote) {
            ItemHelper.setOwner(itemStackIn, playerIn);
            NBTHelper.setUUID(itemStackIn);
            NBTHelper.setBoolean(itemStackIn, Names.NBT.LOOT_BAG_GUI_OPEN, true);
            playerIn.openGui(SavageTech.instance, Names.Gui.LOOT_BAG_GUI, playerIn.worldObj, (int) playerIn.posX, (int) playerIn.posY, (int) playerIn.posZ);

            return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
        }

        return new ActionResult(EnumActionResult.FAIL, itemStackIn);
    }
}
