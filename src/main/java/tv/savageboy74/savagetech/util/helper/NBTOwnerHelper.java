package tv.savageboy74.savagetech.util.helper;

/*
 * NBTOwnerHelper.java
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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants;
import tv.savageboy74.savagetech.util.reference.Names;

import java.util.UUID;

public class NBTOwnerHelper
{
    //TODO: Add ability to have public access.

    private long ownerUUIDMost;
    private long ownerUUIDLeast;
    private String ownerName;
    private UUID ownerUUID;

    public NBTOwnerHelper() {
        this.ownerUUIDMost = 0;
        this.ownerUUIDLeast = 0;
        this.ownerName = "";
    }

    public String getOwnerName() {
        return this.ownerName;
    }

    public UUID getOwnerUUID() {
        return this.ownerUUID;
    }

    public static boolean hasPlayerTag(NBTTagCompound nbt) {
        if (nbt == null || !nbt.hasKey(Names.NBT.OWNER, Constants.NBT.TAG_COMPOUND)) {
            return false;
        }

        NBTTagCompound tag = nbt.getCompoundTag(Names.NBT.OWNER);
        return tag != null &&
                   tag.hasKey(Names.NBT.OWNER_UUID_MOST_SIG, Constants.NBT.TAG_LONG) &&
                   tag.hasKey(Names.NBT.OWNER_UUID_LEAST_SIG, Constants.NBT.TAG_LONG) &&
                   tag.hasKey(Names.NBT.OWNER_NAME, Constants.NBT.TAG_STRING);

    }

    public static boolean itemHasPlayerTag(ItemStack stack) {
        return (stack != null && hasPlayerTag(stack.getTagCompound()));
    }

    public NBTTagCompound readFromNBT(NBTTagCompound nbt) {
        if (!hasPlayerTag(nbt)) {
            return null;
        }

        NBTTagCompound tag = nbt.getCompoundTag(Names.NBT.OWNER);
        this.ownerUUIDMost = tag.getLong(Names.NBT.OWNER_UUID_MOST_SIG);
        this.ownerUUIDLeast = tag.getLong(Names.NBT.OWNER_UUID_LEAST_SIG);
        this.ownerName = tag.getString(Names.NBT.OWNER_NAME);
        this.ownerUUID = new UUID(this.ownerUUIDMost, this.ownerUUIDLeast);

        return tag;
    }

    public String getPlayerName() {
        if (this.ownerName == null) {
            this.ownerName = "";
        }

        return this.ownerName;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        return addOwnerTagToNBT(nbt, this.ownerUUIDMost, this.ownerUUIDLeast, this.ownerName);
    }

    public boolean writeToItem(ItemStack stack) {
        if (stack != null) {
            stack.setTagCompound(this.writeToNBT(stack.getTagCompound()));
            return true;
        }

        return false;
    }

    public static NBTTagCompound addOwnerTagToNBT(NBTTagCompound nbt, long uuidMost, long uuidLeast, String name) {
        if (nbt == null) {
            nbt = new NBTTagCompound();
        }

        NBTTagCompound tag = new NBTTagCompound();
        tag.setLong(Names.NBT.OWNER_UUID_MOST_SIG, uuidMost);
        tag.setLong(Names.NBT.OWNER_UUID_LEAST_SIG, uuidLeast);
        tag.setString(Names.NBT.OWNER_NAME, name);
        nbt.setTag(Names.NBT.OWNER, tag);

        return nbt;
    }

    public static NBTTagCompound addOwnerTagToNBT(NBTTagCompound nbt, EntityPlayer player) {
        if (nbt == null) {
            nbt = new NBTTagCompound();
        }

        if (player == null) {
            nbt.removeTag(Names.NBT.OWNER);
            return nbt;
        }

        return addOwnerTagToNBT(nbt, player.getUniqueID().getMostSignificantBits(), player.getUniqueID().getLeastSignificantBits(), player.getName());
    }

    public static void addOwnerTagToItem(ItemStack stack, EntityPlayer player) {
        if (stack != null) {
            stack.setTagCompound(addOwnerTagToNBT(stack.getTagCompound(), player));
        }
    }

    public static NBTOwnerHelper getOwnerDataFromNBT(NBTTagCompound nbt) {
        NBTOwnerHelper owner = new NBTOwnerHelper();
        if(owner.readFromNBT(nbt) != null) {
            return owner;
        }

        return null;
    }

    public static NBTOwnerHelper getOwnerDataFromItem(ItemStack stack) {
        if (stack != null) {
            return getOwnerDataFromNBT(stack.getTagCompound());
        }

        return null;
    }

    public boolean isOwner(EntityPlayer player) {
        if (player == null) {
            return false;
        }

        if (this.ownerUUIDMost == player.getUniqueID().getMostSignificantBits() && this.ownerUUIDLeast == player.getUniqueID().getLeastSignificantBits()) {
            return true;
        }

        return false;
    }

    public boolean canAccess(EntityPlayer player) {
        return (this.isOwner(player));
    }

    public static boolean isItemOwner(ItemStack stack, EntityPlayer player) {
        if (stack != null && stack.getTagCompound() != null) {
            NBTOwnerHelper owner = new NBTOwnerHelper();
            if (owner.readFromNBT(stack.getTagCompound()) != null) {
                return owner.isOwner(player);
            }
        }

        return false;
    }

    public static boolean canAccessItem(ItemStack stack, EntityPlayer player) {
        if (stack == null) {
            return false;
        }

        if (stack.getTagCompound() == null) {
            return true;
        }

        NBTOwnerHelper requestee = getOwnerDataFromItem(stack);
        return (requestee == null || requestee.isOwner(player));
    }
}
