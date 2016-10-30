package tv.savageboy74.savagetech.blocks.solargenerator;

/*
 * SolarEnergyStorage.java
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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import tv.savageboy74.savagetech.util.reference.Names;

//public class SolarEnergyStorage implements IEnergyStorage
//{
//    protected int maxEnergy;
//    protected int maxCapacity;
//    protected int maxEnergyReceive;
//    protected int maxEnergyExtract;
//
//    public SolarEnergyStorage(int energyCapacity, int energyReceive) {
//        this(energyCapacity, energyReceive, energyReceive);
//    }
//
//    public SolarEnergyStorage(int energyCapacity, int energyReceive, int energyExtract) {
//        maxCapacity = energyCapacity;
//        maxEnergyReceive = energyReceive;
//        maxEnergyExtract = energyExtract;
//    }
//
//    public void readFromNBT(NBTTagCompound nbt) {
//        setMaxEnergyStored(nbt.getInteger(Names.NBT.CAPACITY));
//        setEnergyStored(nbt.getInteger(Names.NBT.ENERGY));
//        setMaxEnergyReceived(nbt.getInteger(Names.NBT.MAX_TRANSFER_RECEIVE));
//        setMaxEnergyExtracted(nbt.getInteger(Names.NBT.MAX_TRANSFER_EXTRACT));
//    }
//
//    public void writeToNBT(NBTTagCompound nbt) {
//        nbt.setInteger(Names.NBT.CAPACITY, getMaxEnergyStored());
//        nbt.setInteger(Names.NBT.ENERGY, getEnergyStored());
//        nbt.setInteger(Names.NBT.MAX_TRANSFER_RECEIVE, getMaxEnergyReceived());
//        nbt.setInteger(Names.NBT.MAX_TRANSFER_EXTRACT, getMaxEnergyExtracted());
//    }
//
//    public int sendMaxEnergy(IEnergyReceiver energyReceiver, EnumFacing direction) {
//        return extractEnergy(energyReceiver.receiveEnergy(direction, getMaxEnergyExtract(), false), false);
//    }
//
//    public int balanceStoredEnergy(SolarEnergyStorage oEnergyStorage) {
//        int delta = getEnergyStored() - oEnergyStorage.getEnergyStored();
//        if (delta < 0) {
//            return oEnergyStorage.balanceStoredEnergy(this);
//        } else if (delta > 0 && !oEnergyStorage.isFull()) {
//            return extractEnergy(oEnergyStorage.receiveEnergy(delta / 2, false), false);
//        }
//
//        return 0;
//    }
//
//    public int balanceStoredEnergy(SolarEnergyStorage energyStorage, int transferSpeed) {
//        maxEnergyExtract *= transferSpeed;
//        maxEnergyReceive *= transferSpeed;
//        energyStorage.maxEnergyExtract *= transferSpeed;
//        energyStorage.maxEnergyReceive *= transferSpeed;
//        int result = balanceStoredEnergy(energyStorage);
//        maxEnergyExtract /= transferSpeed;
//        maxEnergyReceive /= transferSpeed;
//        energyStorage.maxEnergyExtract /= transferSpeed;
//        energyStorage.maxEnergyReceive /= transferSpeed;
//        return result;
//    }
//
//    public int getMaxEnergyReceive() {
//        return Math.min(maxCapacity - maxEnergy, maxEnergyReceive);
//    }
//
//    public int getMaxEnergyExtract() {
//        return Math.min(maxEnergy, maxEnergyExtract);
//    }
//
//    @Override
//    public int receiveEnergy(int maxReceive, boolean simulate) {
//        int energyReceived = Math.min(getMaxEnergyReceive(), Math.max(maxReceive, 0));
//
//        if (!simulate) {
//            maxEnergy += energyReceived;
//        }
//
//        return energyReceived;
//    }
//
//    @Override
//    public int extractEnergy(int maxExtract, boolean simulate) {
//        int energyExtracted = Math.min(getMaxEnergyExtract(), Math.max(maxExtract, 0));
//        if (!simulate) {
//            maxEnergy -= energyExtracted;
//        }
//        return energyExtracted;
//    }
//
//    @Override
//    public int getEnergyStored() {
//        return maxEnergy;
//    }
//
//    public void setEnergyStored(int energy) {
//        maxEnergy = energy;
//
//        if (maxEnergy > maxCapacity) {
//            maxEnergy = maxCapacity;
//        } else if (maxEnergy < 0) {
//            maxEnergy = 0;
//        }
//    }
//
//    @Override
//    public int getMaxEnergyStored() {
//        return maxCapacity;
//    }
//
//    public void setMaxEnergyStored(int capacity) {
//        maxCapacity = capacity;
//        if (maxEnergy > maxCapacity) {
//            maxEnergy = maxCapacity;
//        }
//    }
//
//    public boolean isFull() {
//        return getEnergyStored() == getMaxEnergyStored();
//    }
//
//    public int getMaxEnergyReceived() {
//        return maxEnergyReceive;
//    }
//
//    public void setMaxEnergyReceived(int energyToReceive) {
//        maxEnergyReceive = energyToReceive;
//    }
//
//    public int getMaxEnergyExtracted() {
//        return maxEnergyExtract;
//    }
//
//    public void setMaxEnergyExtracted(int energyToExtract) {
//        maxEnergyExtract = energyToExtract;
//    }
//
//    public void setMaxEnergyToTransfer(int transferAmount) {
//        setMaxEnergyReceived(transferAmount);
//        setMaxEnergyExtracted(transferAmount);
//    }
//
//    @Override
//    public String toString() {
//        return Objects.toStringHelper(this).add("STEnergy", getEnergyStored()).add("STCapacity", getMaxEnergyStored()).add("STMaxEnergyReceived", getMaxEnergyReceived()).add("STMaxEnergyExtracted", getMaxEnergyExtracted()).toString();
//    }
//}
