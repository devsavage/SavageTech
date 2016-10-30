package tv.savageboy74.savagetech.blocks.furnace;

/*
 * TileEntityFurnace.java
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
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tv.savageboy74.savagetech.tileentity.base.STTileEntityBase;
import tv.savageboy74.savagetech.tileentity.base.STTileEntityInventory;
import tv.savageboy74.savagetech.util.reference.Names;

public class TileEntitySoulFurnace extends STTileEntityInventory implements ITickable
{
    public static final int[] UPGRADE_SLOTS = new int[] {0, 1, 2, 3};
    public static final int SLOT_FUEL_INDEX = 0;
    public static final int SLOT_INPUT_INDEX = 1;
    public static final int SLOT_OUTPUT_INDEX = 2;

    public TileEntitySoulFurnace() {
        super(Names.Blocks.soulFurnace);
    }

    @Override
    public void update() {

    }

    @Override
    public void readFromNBTCustom(NBTTagCompound compound) {
        super.readFromNBTCustom(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        return compound;
    }

    @Override
    public NBTTagCompound getDescriptionPacketTag(NBTTagCompound nbt) {
        return super.getDescriptionPacketTag(nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        super.onDataPacket(net, packet);
    }
}