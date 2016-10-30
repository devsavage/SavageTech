package tv.savageboy74.savagetech.blocks.infusion;

/*
 * TileEntitySoulInfuser.java
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

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.fml.common.FMLCommonHandler;
import tv.savageboy74.savagetech.init.ModItems;
import tv.savageboy74.savagetech.tileentity.base.STTileEntityBase;
import tv.savageboy74.savagetech.util.reference.Names;

import java.util.List;

public class TileEntitySoulInfuser extends STTileEntityBase implements ITickable, IInventory
{
    private EntityLivingBase fakePlayer;
    public static final int INVENTORY_SIZE = 3;
    public static final int INFUSED_STONE_SLOT_INDEX = 0;
    public static final int RAW_SOUL_MATTER_SLOT_INDEX = 1;
    public static final int SOUL_MATTER_SLOT_INDEX = 2;

    private int counter = 0;

    private ItemStack[] inventory;

    public TileEntitySoulInfuser() {
        inventory = new ItemStack[INVENTORY_SIZE];
    }


    @Override
    public void update() {
        this.counter++;
        if(this.counter == 20) {
            if(shouldInfuse()) {
                infuse();
            }
            this.counter = 0;
        }
    }

    public boolean shouldInfuse() {
        if(this.inventory[INFUSED_STONE_SLOT_INDEX] != null) {
            ItemStack infusedStack = this.inventory[INFUSED_STONE_SLOT_INDEX];

            if(infusedStack.getItem() == ModItems.soulEnergizer
                   && this.inventory[RAW_SOUL_MATTER_SLOT_INDEX] != null
                   && this.inventory[RAW_SOUL_MATTER_SLOT_INDEX].getItem() == ModItems.rawSoulMatter
                   && checkForSurroundingHostileMobs()) {
                return true;
            }
        }

        return false;
    }

    private boolean checkForSurroundingHostileMobs() {
        for(int xPos = this.x() - 4; xPos <= this.x() + 4; ++xPos) {
            for(int zPos = this.z() - 4; zPos <= this.z() + 4; ++zPos) {
                BlockPos pos = new BlockPos(xPos, this.y(), zPos);
                AxisAlignedBB axisAlignedBB = new AxisAlignedBB(pos);
                List<?> entities = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, axisAlignedBB);
                for (Object entity : entities) {
                    EntityLivingBase livingBase = (EntityLivingBase) entity;
                    if (entity instanceof EntityMob) {
                        EntityMob infusableMob = (EntityMob) livingBase;
                        infusableMob.attackEntityFrom(EntityDamageSource.generic, 200);
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private void infuse() {
        int infuseCounter = 0;

        infuseCounter++;

        ItemStack soulMatter = new ItemStack(ModItems.soulMatter);

        if(infuseCounter == 1) {
            if(this.inventory[RAW_SOUL_MATTER_SLOT_INDEX] != null) {
                if(this.inventory[SOUL_MATTER_SLOT_INDEX] == null) {
                    this.setInventorySlotContents(SOUL_MATTER_SLOT_INDEX, soulMatter.copy());

                    this.inventory[RAW_SOUL_MATTER_SLOT_INDEX].stackSize--;

                    if(this.inventory[RAW_SOUL_MATTER_SLOT_INDEX].stackSize <= 0) {
                        this.setInventorySlotContents(RAW_SOUL_MATTER_SLOT_INDEX, null);
                    }

                    this.inventory[INFUSED_STONE_SLOT_INDEX].damageItem(1, getFakePlayer());
                    if(this.inventory[INFUSED_STONE_SLOT_INDEX].getItemDamage() >= this.inventory[INFUSED_STONE_SLOT_INDEX].getMaxDamage()) {
                        this.setInventorySlotContents(INFUSED_STONE_SLOT_INDEX, null);
                    }
                    infuseCounter = 0;
                } else if(this.inventory[SOUL_MATTER_SLOT_INDEX].stackSize < soulMatter.getMaxStackSize()) {
                    this.inventory[SOUL_MATTER_SLOT_INDEX].stackSize++;
                    infuseCounter = 0;
                    if(this.inventory[RAW_SOUL_MATTER_SLOT_INDEX] != null && this.inventory[RAW_SOUL_MATTER_SLOT_INDEX].stackSize > 0) {
                        this.inventory[RAW_SOUL_MATTER_SLOT_INDEX].stackSize--;
                        if(this.inventory[RAW_SOUL_MATTER_SLOT_INDEX].stackSize <= 0) {
                            this.setInventorySlotContents(RAW_SOUL_MATTER_SLOT_INDEX, null);
                        }

                        this.inventory[INFUSED_STONE_SLOT_INDEX].damageItem(1, getFakePlayer());
                        if(this.inventory[INFUSED_STONE_SLOT_INDEX].getItemDamage() >= this.inventory[INFUSED_STONE_SLOT_INDEX].getMaxDamage()) {
                            this.setInventorySlotContents(INFUSED_STONE_SLOT_INDEX, null);
                        }

                        infuseCounter = 0;
                    }
                }
            }
        }
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

        return nbtTagCompound;
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
    }

    @Override
    public int getSizeInventory() {
        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return inventory[index];
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
    public void setInventorySlotContents(int slotIndex, ItemStack stack) {
        boolean flag = stack != null && stack.isItemEqual(this.inventory[slotIndex]) && ItemStack.areItemStackTagsEqual(stack, this.inventory[slotIndex]);
        inventory[slotIndex] = stack;

        if (stack != null && stack.stackSize > getInventoryStackLimit()) {
            stack.stackSize = getInventoryStackLimit();
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
        return true;
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

    }

    @Override
    public String getName() {
        return "container.savagetech:soulInfuser";
    }

    @Override
    public ITextComponent getDisplayName() {
        return (ITextComponent)(this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName(), new Object[0]));
    }

    private EntityLivingBase getFakePlayer() {
        if(fakePlayer == null) {
            fakePlayer = FakePlayerFactory.getMinecraft(FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(worldObj.provider.getDimension()));
        }

        return fakePlayer;
    }
}
