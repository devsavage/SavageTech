package tv.savageboy74.savagetech.tileentity.base;

/*
 * STTileEntityBaseV2.java
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

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;
import tv.savageboy74.savagetech.util.helper.NBTOwnerHelper;
import tv.savageboy74.savagetech.util.reference.Names;

import java.util.UUID;

public class STTileEntityBaseV2 extends TileEntity
{
    //TODO: Implement public access..
    protected String tileName;
    protected int rotation;
    protected String ownerName;
    protected UUID ownerUUID;

    public STTileEntityBaseV2(String teName) {
        this.rotation = 0;
        this.ownerName = "";
        this.ownerUUID = null;
        this.tileName = teName;
    }

    public String getTEName() {
        return this.tileName;
    }

    public void setRotation(int teRotation) {
        this.rotation = teRotation;
    }

    public void setOwner(EntityPlayer player) {
        if(player != null) {
            this.ownerName = player.getName();
            this.ownerUUID = player.getUniqueID();
        } else {
            this.ownerName = "";
            this.ownerUUID = null;
        }
    }

    public String getOwnerName() {
        return this.ownerName;
    }

    public UUID getOwnerUUID() {
        return this.ownerUUID;
    }

    public int getRotation() {
        return this.rotation;
    }

    public void readFromNBTCustom(NBTTagCompound nbt) {
        this.rotation = nbt.getByte(Names.NBT.ROTATION);

        NBTOwnerHelper ownerHelper = NBTOwnerHelper.getOwnerDataFromNBT(nbt);
        if(ownerHelper != null) {
            this.ownerUUID = ownerHelper.getOwnerUUID();
            this.ownerName = ownerHelper.getOwnerName();
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.readFromNBTCustom(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        compound.setByte(Names.NBT.ROTATION, (byte)this.rotation);

        if(ownerUUID != null && this.ownerName != null) {
            NBTOwnerHelper.addOwnerTagToNBT(compound, this.ownerUUID.getMostSignificantBits(), this.ownerUUID.getLeastSignificantBits(), this.ownerName);
        }

        return compound;
    }

    public NBTTagCompound getDescriptionPacketTag(NBTTagCompound nbt) {
        nbt.setByte(Names.NBT.ROTATION_PACKET, (byte)(this.getRotation() & 0x07));

        if (this.ownerName != null) {
            nbt.setString(Names.NBT.OWNER_PACKET, this.ownerName);
        }

        return nbt;
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        if (this.worldObj != null) {
            return new SPacketUpdateTileEntity(this.getPos(), 0, this.getDescriptionPacketTag(new NBTTagCompound()));
        }

        return null;
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        NBTTagCompound nbt = packet.getNbtCompound();

        if (nbt.hasKey(Names.NBT.ROTATION)) {
            this.setRotation((byte)(nbt.getByte("r") & 0x07));
        }

        if (nbt.hasKey(Names.NBT.OWNER_PACKET, Constants.NBT.TAG_STRING)) {
            this.ownerName = nbt.getString("o");
        }

        IBlockState state = this.worldObj.getBlockState(this.getPos());
        this.worldObj.notifyBlockUpdate(this.getPos(), state, state, 3);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(" + this.getPos() + ")@" + System.identityHashCode(this);
    }
}
