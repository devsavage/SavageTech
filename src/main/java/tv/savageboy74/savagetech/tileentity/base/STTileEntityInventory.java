package tv.savageboy74.savagetech.tileentity.base;

/*
 * STTileEntityInventory.java
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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.IItemHandler;
import tv.savageboy74.savagetech.container.base.STContainerBase;
import tv.savageboy74.savagetech.util.reference.ModReference;
import tv.savageboy74.savagetech.util.reference.Names;

public class STTileEntityInventory extends STTileEntityBaseV2
{
    protected String customInventoryName;

    public STTileEntityInventory(String teName) {
        super(teName);
    }

    public void setCustomInventoryName(String name) {
        this.customInventoryName = name;
    }

    public boolean hasCustomName() {
        return this.customInventoryName != null && this.customInventoryName.length() > 0;
    }

    public String getName() {
        return this.hasCustomName() ? this.customInventoryName : ModReference.MOD_ID + ".container." + this.tileName;
    }

    @Override
    public void readFromNBTCustom(NBTTagCompound compound) {
        super.readFromNBTCustom(compound);

        if (compound.hasKey(Names.NBT.CUSTOM_NAME, Constants.NBT.TAG_STRING)) {
            this.customInventoryName = compound.getString(Names.NBT.CUSTOM_NAME);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        if (this.hasCustomName()) {
            compound.setString(Names.NBT.CUSTOM_NAME, this.customInventoryName);
        }

        return compound;
    }

    public boolean isUseableByPlayer(EntityPlayer player) {
        return this.worldObj.getTileEntity(this.getPos()) == this && player.getDistanceSq(this.getPos()) < 64.0d;

    }
}
