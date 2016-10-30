package tv.savageboy74.savagetech.blocks.autofertilizer;

/*
 * TileEntityAutoFertilizer.java
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

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tv.savageboy74.savagetech.client.config.values.ConfigIntegerValues;
import tv.savageboy74.savagetech.handler.ConfigHandler;
import tv.savageboy74.savagetech.init.ModItems;
import tv.savageboy74.savagetech.network.handler.PacketHandler;
import tv.savageboy74.savagetech.tileentity.base.STTileEntityBase;
import tv.savageboy74.savagetech.util.helper.ItemHelper;
import tv.savageboy74.savagetech.util.reference.Names;

public class TileEntityAutoFertilizer extends STTileEntityBase implements IInventory, ITickable, ISidedInventory
{
    private EntityLivingBase fakePlayer;

    public static final int INVENTORY_SIZE = 1;
    public static final int FERTILIZER_SLOT_INVENTORY_INDEX = 0;

    private ItemStack[] inventory;

    private String customName;

    protected short _area = 1;
    protected short min_area = 1;
    protected short max_area = 4;

    private int counter = 0;

    private boolean isActive = false;

    public TileEntityAutoFertilizer() {
        inventory = new ItemStack[INVENTORY_SIZE];
    }


    @Override
    public void update() {
        int fertilizeRate = ConfigIntegerValues.AUTO_FERTILIZER_RATE.getValue();

        counter++;

        if(counter == fertilizeRate) {
            if(this.inventory[FERTILIZER_SLOT_INVENTORY_INDEX] != null) {
                isActive = true;
                ItemStack boneMealStack = new ItemStack(Items.DYE, 3, EnumDyeColor.WHITE.getDyeDamage());
                ItemStack bonemealBundleStack = new ItemStack(ModItems.bonemealBundle);
                ItemStack fertilizerStack = this.inventory[FERTILIZER_SLOT_INVENTORY_INDEX];
                EnumDyeColor enumdyecolor = EnumDyeColor.byDyeDamage(fertilizerStack.getMetadata());
                for(int xPos = this.x() - this._area; xPos <= this.x() + this._area; ++xPos) {
                    for(int zPos = this.z() - this._area; zPos <= this.z() + this._area; ++zPos) {
                        BlockPos targetPos = new BlockPos(xPos, this.y() + 2, zPos);
                        if(applyBonemeal(worldObj, targetPos)) {
                            worldObj.playBroadcastSound(2005, targetPos, 0);
                            if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
                                spawnBonemealParticles(worldObj, targetPos, 4);
                            if(ItemHelper.equalsIgnoreStackSize(fertilizerStack, boneMealStack)) {
                                fertilizerStack.stackSize--;
                            } else if(ItemHelper.equalsIgnoreStackSize(fertilizerStack, bonemealBundleStack)) {
                                fertilizerStack.damageItem(1, getFakePlayer());
                            }

                            if (this.inventory[FERTILIZER_SLOT_INVENTORY_INDEX].stackSize <= 0 || this.inventory[FERTILIZER_SLOT_INVENTORY_INDEX].getItemDamage() <= 0) {
                                setInventorySlotContents(FERTILIZER_SLOT_INVENTORY_INDEX, null);
                                isActive = false;
                            }
                        }
                    }
                }
            }

            this.counter = 0;
        }
    }

    public static boolean applyBonemeal(World worldIn, BlockPos target) {
        if (worldIn instanceof WorldServer)
            return applyBonemeal(worldIn, target, FakePlayerFactory.getMinecraft((net.minecraft.world.WorldServer) worldIn));
        return false;
    }

    public static boolean applyBonemeal(World worldIn, BlockPos target, EntityPlayer player) {
        IBlockState iblockstate = worldIn.getBlockState(target);

        if (iblockstate.getBlock() instanceof IGrowable) {
            IGrowable igrowable = (IGrowable)iblockstate.getBlock();

            if (igrowable.canGrow(worldIn, target, iblockstate, worldIn.isRemote)) {
                if (!worldIn.isRemote) {
                    if (igrowable.canUseBonemeal(worldIn, worldIn.rand, target, iblockstate)) {
                        igrowable.grow(worldIn, worldIn.rand, target, iblockstate);
                    }
                }

                return true;
            }
        }

        return false;
    }

    @Override
    public int getSizeInventory() {
        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int slotIndex) {
        return inventory[slotIndex];
    }

    @Override
    public ItemStack decrStackSize(int slotIndex, int decrementAmount) {
        ItemStack itemStack = getStackInSlot(slotIndex);
        if (itemStack != null) {
            if (itemStack.stackSize <= decrementAmount) {
                setInventorySlotContents(slotIndex, null);
            } else {
                itemStack = itemStack.splitStack(decrementAmount);
                if (itemStack.stackSize == 0) {
                    setInventorySlotContents(slotIndex, null);
                }
            }
        }

        return itemStack;
    }

    @Override
    public ItemStack removeStackFromSlot(int slotIndex) {
        ItemStack itemStack = getStackInSlot(slotIndex);
        if (itemStack != null) {
            setInventorySlotContents(slotIndex, null);
        }
        return itemStack;
    }

    @Override
    public void setInventorySlotContents(int slotIndex, ItemStack itemStack) {
        inventory[slotIndex] = itemStack;
        if (itemStack != null && itemStack.stackSize > getInventoryStackLimit()) {
            itemStack.stackSize = getInventoryStackLimit();
        }
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return this.worldObj.getTileEntity(this.pos) == this && player.getDistanceSq((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D) <= 64D;
    }

    @Override
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {

    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        EnumDyeColor enumdyecolor = EnumDyeColor.byDyeDamage(stack.getMetadata());
        return enumdyecolor == EnumDyeColor.WHITE || stack.getItem() == ModItems.bonemealBundle;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);

        NBTTagList tagList = nbtTagCompound.getTagList(Names.NBT.ITEMS, 10);
        inventory = new ItemStack[this.getSizeInventory()];
        for (int i = 0; i < tagList.tagCount(); ++i) {
            NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
            byte slotIndex = tagCompound.getByte("Slot");
            if (slotIndex >= 0 && slotIndex < inventory.length) {
                inventory[slotIndex] = ItemStack.loadItemStackFromNBT(tagCompound);
            }
        }

        if (nbtTagCompound.hasKey("CustomName", 8)) {
            this.customName = nbtTagCompound.getString("CustomName");
        }

        this._area = nbtTagCompound.getShort("Area");
        this.isActive = nbtTagCompound.getBoolean("Active");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);

        NBTTagList tagList = new NBTTagList();
        for (int currentIndex = 0; currentIndex < inventory.length; ++currentIndex) {
            if (inventory[currentIndex] != null) {
                NBTTagCompound tagCompound = new NBTTagCompound();
                tagCompound.setByte("Slot", (byte) currentIndex);
                inventory[currentIndex].writeToNBT(tagCompound);
                tagList.appendTag(tagCompound);
            }
        }

        nbtTagCompound.setTag(Names.NBT.ITEMS, tagList);
        nbtTagCompound.setShort("Area", this._area);
        nbtTagCompound.setBoolean("Active", this.isActive);

        if (this.hasCustomName()) {
            nbtTagCompound.setString("CustomName", this.customName);
        }

        return nbtTagCompound;
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {
        for (int i = 0; i < this.inventory.length; ++i) {
            this.inventory[i] = null;
        }
    }

    @Override
    public String getName() {
        return this.hasCustomName() ? this.customName : "Auto Fertilizer";
    }

    @Override
    public boolean hasCustomName() {
        return this.customName != null && this.customName.length() > 0;
    }

    @Override
    public ITextComponent getDisplayName() {
        return null;
    }

    @SideOnly(Side.CLIENT)
    public static void spawnBonemealParticles(World worldIn, BlockPos pos, int amount) {
        if (amount == 0) {
            amount = 15;
        }

        IBlockState iblockstate = worldIn.getBlockState(pos);

        if (iblockstate.getMaterial() != Material.AIR) {
            for (int i = 0; i < amount; ++i)
            {
                double d0 = worldIn.rand.nextGaussian() * 0.02D;
                double d1 = worldIn.rand.nextGaussian() * 0.02D;
                double d2 = worldIn.rand.nextGaussian() * 0.02D;
                worldIn.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, (double)((float)pos.getX() + worldIn.rand.nextFloat()), (double)pos.getY() + (double)worldIn.rand.nextFloat() * iblockstate.getBoundingBox(worldIn, pos).maxY, (double)((float)pos.getZ() + worldIn.rand.nextFloat()), d0, d1, d2, new int[0]);
            }
        } else {
            for (int i1 = 0; i1 < amount; ++i1) {
                double d0 = worldIn.rand.nextGaussian() * 0.02D;
                double d1 = worldIn.rand.nextGaussian() * 0.02D;
                double d2 = worldIn.rand.nextGaussian() * 0.02D;
                worldIn.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, (double)((float)pos.getX() + worldIn.rand.nextFloat()), (double)pos.getY() + (double)worldIn.rand.nextFloat() * 1.0f, (double)((float)pos.getZ() + worldIn.rand.nextFloat()), d0, d1, d2, new int[0]);
            }
        }
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        return new int[]{FERTILIZER_SLOT_INVENTORY_INDEX};
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
        return isItemValidForSlot(index, itemStackIn);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        return false;
    }

    public short getArea() {
        return this._area;
    }

    public void setArea(short newArea) {
        if(newArea < min_area) {
            newArea = min_area;
        }

        if(newArea > max_area) {
            newArea = max_area;
        }

        this._area = newArea;
        this.markDirty();
        this.worldObj.addBlockEvent(this.pos, this.getBlockType(), 1, this.getArea());
        this.worldObj.notifyBlockOfStateChange(this.pos, this.getBlockType());
    }

    @Override
    public boolean receiveClientEvent(int id, int type) {
        if (id == 1) {
            this._area = (short) type;
            this.markDirty();
            return true;
        } else {
            return super.receiveClientEvent(id, type);
        }
    }

    private EntityLivingBase getFakePlayer() {
        if(fakePlayer == null) {
            fakePlayer = FakePlayerFactory.getMinecraft(FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(worldObj.provider.getDimension()));
        }

        return fakePlayer;
    }

    public boolean hasFertilizer() {
        return this.isActive;
    }

    public int getFertilizerStackSize() {
        if(this.inventory[FERTILIZER_SLOT_INVENTORY_INDEX] != null) {
            ItemStack activeFertStack = new ItemStack(this.inventory[FERTILIZER_SLOT_INVENTORY_INDEX].getItem());
            ItemStack bonemealBundleStack = new ItemStack(ModItems.bonemealBundle);

            if(ItemHelper.equalsIgnoreStackSize(activeFertStack, bonemealBundleStack)) {
                if (!(activeFertStack.getItemDamage() <= 0)) {
                    return activeFertStack.getItemDamage();
                }
            }

            return (!(activeFertStack.stackSize <= 0)) ? activeFertStack.stackSize : 0;
        }

        return 0;
    }
}
