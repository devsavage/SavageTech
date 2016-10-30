package tv.savageboy74.savagetech.blocks.solargenerator;

/*
 * TileEntitySolarGenerator.java
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

import com.google.common.base.Objects;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tv.savageboy74.savagetech.init.ModBlocks;
import tv.savageboy74.savagetech.tileentity.base.STTileEntityBase;
import tv.savageboy74.savagetech.util.helper.DirectionHelper;
import tv.savageboy74.savagetech.util.reference.Names;

//public class TileEntitySolarGenerator extends STTileEntityBase implements IEnergyHandler, IEnergyProvider, ITickable, IInventory
//{
//    private int maxEnergyGeneration;
//    public static final int INVENTORY_SIZE = 1;
//    public static final int ENERGY_OUTPUT_INDEX = 0;
//    private static final int TRANSFER_TICK_RATE = 5 * 20;
//
//    private SolarEnergyStorage energyStorage;
//
//    private ItemStack blockGenerator = new ItemStack(Item.getItemFromBlock(ModBlocks.blockSolarGenerator));
//
//    private ItemStack[] inventory;
//
//    public TileEntitySolarGenerator() {
//        this(0, 0, 0);
//    }
//
//    public TileEntitySolarGenerator(int energyGeneration, int energyTransfer, int energyCapacity) {
//        maxEnergyGeneration = energyGeneration;
//        energyStorage = new SolarEnergyStorage(energyCapacity, energyTransfer);
//    }
//
//    @Override
//    public void update() {
//        if(isServer()) {
//            generateEnergy();
//            if(shouldTransferEnergy()) {
//                transferEnergy();
//            }
//
//            if(shouldBalanceEnergy()) {
//                tryBalanceEnergyAt(new BlockPos(x() + 1, y(), z()));
//                tryBalanceEnergyAt(new BlockPos(x(), y(), z() + 1));
//            }
//        }
//    }
//
//    private void tryBalanceEnergyAt(BlockPos pos) {
//        TileEntity tile = this.worldObj.getTileEntity(pos);
//        if (tile instanceof TileEntitySolarGenerator) {
//            TileEntitySolarGenerator neighbor = (TileEntitySolarGenerator) tile;
//            energyStorage.balanceStoredEnergy(neighbor.energyStorage, TRANSFER_TICK_RATE);
//        }
//    }
//
//    private boolean shouldBalanceEnergy() {
//        return this.worldObj.getTotalWorldTime() % TRANSFER_TICK_RATE == 0;
//    }
//
//    protected void transferEnergy() {
//        for (EnumFacing direction : DirectionHelper.VALID_DIRECTIONS) {
//            TileEntity tile = this.worldObj.getTileEntity(new BlockPos(x() + direction.getFrontOffsetX(), y() + direction.getFrontOffsetY(), z() + direction.getFrontOffsetZ()));
//            if (!(tile instanceof TileEntitySolarGenerator)) {
//                if (tile instanceof IEnergyReceiver) {
//                    IEnergyReceiver receiver = (IEnergyReceiver) tile;
//                    energyStorage.sendMaxEnergy(receiver, direction.getOpposite());
//                }
//            }
//        }
//    }
//
//    protected boolean shouldTransferEnergy() {
//        return energyStorage.getEnergyStored() > 0;
//    }
//
//    private int getSunProduction() {
//        float multiplication = 1.5F;
//        float displacement = 1.2F;
//        float celestialAngleRadians = worldObj.getCelestialAngleRadians(1.0f);
//
//        if (celestialAngleRadians > Math.PI) {
//            celestialAngleRadians = (2 * 3.141592f - celestialAngleRadians);
//        }
//
//        return Math.round(maxEnergyGeneration * multiplication * MathHelper.cos(celestialAngleRadians / displacement));
//    }
//
//    public int getEnergyProduced() {
//        if (worldObj.canSeeSky(new BlockPos(x(), y() + 1, z()))) {
//            int sunProduction = getSunProduction();
//            if (sunProduction > 0) {
//                if (worldObj.isRaining()) {
//                    sunProduction *= 0.2F;
//                }
//                if (worldObj.isThundering()) {
//                    sunProduction *= 0.2F;
//                }
//                return Math.min(maxEnergyGeneration, sunProduction);
//            }
//        }
//        return 0;
//    }
//
//    protected void generateEnergy() {
//        int produced = getEnergyProduced();
//        if (produced > 0 && !energyStorage.isFull()) {
//            energyStorage.receiveEnergy(produced, false);
//        }
//    }
//
//    public void setEnergyStored(int energy) {
//        this.markDirty();
//        energyStorage.setEnergyStored(energy);
//    }
//
//    public int getEnergyStored() {
//        return getEnergyStored(EnumFacing.DOWN);
//    }
//
//    public int getPercentEnergyStored() {
//        long v = getEnergyStored();
//        return (int) (100 * v / getMaxEnergyStored());
//    }
//
//    @SideOnly(Side.CLIENT)
//    public int getEnergyScaled(int scale) {
//        double stored = getEnergyStored();
//        double max = getMaxEnergyStored();
//        double value = ((stored /max) * scale);
//        return (int)value;
//    }
//
//    @Override
//    public int getSizeInventory() {
//        return 0;
//    }
//
//    @Override
//    public ItemStack getStackInSlot(int index) {
//        return null;
//    }
//
//    @Override
//    public ItemStack decrStackSize(int index, int count) {
//        return null;
//    }
//
//    @Override
//    public ItemStack removeStackFromSlot(int index) {
//        return null;
//    }
//
//    @Override
//    public void setInventorySlotContents(int index, ItemStack stack) {
//
//    }
//
//    @Override
//    public int getInventoryStackLimit() {
//        return 0;
//    }
//
//    @Override
//    public void markDirty() {
//        super.markDirty();
//        worldObj.markBlockForUpdate(pos);
//    }
//
//    @Override
//    public boolean isUseableByPlayer(EntityPlayer player) {
//        return this.worldObj.getTileEntity(this.pos) == this && player.getDistanceSq((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D) <= 64D;
//    }
//
//    @Override
//    public void openInventory(EntityPlayer player) {
//
//    }
//
//    @Override
//    public void closeInventory(EntityPlayer player) {
//
//    }
//
//    @Override
//    public boolean isItemValidForSlot(int index, ItemStack stack) {
//        return false;
//    }
//
//    @Override
//    public int getField(int id) {
//        return 0;
//    }
//
//    @Override
//    public void setField(int id, int value) {
//
//    }
//
//    @Override
//    public int getFieldCount() {
//        return 0;
//    }
//
//    @Override
//    public void clear() {
//        for (int i = 0; i < this.inventory.length; ++i) {
//            this.inventory[i] = null;
//        }
//    }
//
//    @Override
//    public void readFromNBT(NBTTagCompound nbtTagCompound) {
//        super.readFromNBT(nbtTagCompound);
//        maxEnergyGeneration = nbtTagCompound.getInteger(Names.NBT.MAXIMUM_ENERGY_GENERATION);
//        energyStorage.readFromNBT(nbtTagCompound);
//    }
//
//    @Override
//    public void writeToNBT(NBTTagCompound nbtTagCompound) {
//        super.writeToNBT(nbtTagCompound);
//        nbtTagCompound.setInteger(Names.NBT.MAXIMUM_ENERGY_GENERATION, maxEnergyGeneration);
//        energyStorage.writeToNBT(nbtTagCompound);
//    }
//
//    @Override
//    public int getEnergyStored(EnumFacing from) {
//        return energyStorage.getEnergyStored();
//    }
//
//    public int getMaxEnergyStored() {
//        return getMaxEnergyStored(EnumFacing.DOWN);
//    }
//
//    @Override
//    public int getMaxEnergyStored(EnumFacing from) {
//        return energyStorage.getMaxEnergyStored();
//    }
//
//    @Override
//    public boolean canConnectEnergy(EnumFacing from) {
//        return from != EnumFacing.UP;
//    }
//
//    @Override
//    public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
//        return energyStorage.extractEnergy(energyStorage.getMaxEnergyExtract(), simulate);
//    }
//
//    @Override
//    public String toString() {
//        return Objects.toStringHelper(this).add("Hash", hashCode()).add("STMaxEnergyGeneration", maxEnergyGeneration).add("SolarEnergyStorage", energyStorage).toString();
//    }
//
//    public SolarEnergyStorage getEnergyStorage() {
//        return energyStorage;
//    }
//
//    @Override
//    public String getName() {
//        return null;
//    }
//
//    @Override
//    public IChatComponent getDisplayName() {
//        return null;
//    }
//}
