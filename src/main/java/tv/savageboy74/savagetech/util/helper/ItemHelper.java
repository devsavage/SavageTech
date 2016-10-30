package tv.savageboy74.savagetech.util.helper;

/*
 * ItemHelper.java
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

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.ForgeEventFactory;
import tv.savageboy74.savagetech.items.soul.tools.ItemSoulMatterHammer;
import tv.savageboy74.savagetech.util.reference.Names;

public class ItemHelper
{
    public static ItemStack cloneItemStack(ItemStack itemStack, int stackSize) {
        ItemStack clonedItemStack = itemStack.copy();
        clonedItemStack.stackSize = stackSize;
        return clonedItemStack;
    }

    public static boolean equalsIgnoreStackSize(ItemStack itemStack1, ItemStack itemStack2) {
        if (itemStack1 != null && itemStack2 != null) {
            // Sort on itemID
            if (Item.getIdFromItem(itemStack1.getItem()) - Item.getIdFromItem(itemStack2.getItem()) == 0) {
                // Sort on item
                if (itemStack1.getItem() == itemStack2.getItem()) {
                    // Then sort on meta
                    if (itemStack1.getItemDamage() == itemStack2.getItemDamage()) {
                        // Then sort on NBT
                        if (itemStack1.hasTagCompound() && itemStack2.hasTagCompound()) {
                            // Then sort on stack size
                            if (ItemStack.areItemStackTagsEqual(itemStack1, itemStack2)) {
                                return true;
                            }
                        } else {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public static EntityItem dropItemStackInWorld(World worldObj, BlockPos pos, ItemStack stack) {
        float f = 0.7F;
        float d0 = worldObj.rand.nextFloat() * f + (1.0F - f) * 0.5F;
        float d1 = worldObj.rand.nextFloat() * f + (1.0F - f) * 0.5F;
        float d2 = worldObj.rand.nextFloat() * f + (1.0F - f) * 0.5F;
        EntityItem entityitem = new EntityItem(worldObj, pos.getX() + d0, pos.getY() + d1, pos.getZ() + d2, stack);
        entityitem.setPickupDelay(10);

        if (stack.hasTagCompound()) {
            entityitem.getEntityItem().setTagCompound((NBTTagCompound) stack.getTagCompound().copy());
        }

        worldObj.spawnEntityInWorld(entityitem);
        return entityitem;
    }

    public static void dropAsEntity(World world, BlockPos pos, ItemStack stack) {
        if (stack != null) {
            double d = 0.7D;
            double e = (double) world.rand.nextFloat() * d + (1.0D - d) * 0.5D;
            double f = (double) world.rand.nextFloat() * d + (1.0D - d) * 0.5D;
            double g = (double) world.rand.nextFloat() * d + (1.0D - d) * 0.5D;
            EntityItem entityItem = new EntityItem(world, (double) pos.getX() + e, (double) pos.getY() + f, (double) pos.getZ() + g, stack.copy());
            entityItem.setPickupDelay(10);
            world.spawnEntityInWorld(entityItem);
        }
    }

    public static void setOwner(ItemStack itemStack, EntityPlayer entityPlayer) {
        NBTHelper.setString(itemStack, Names.NBT.OWNER, entityPlayer.getDisplayName().toString());
        NBTHelper.setLong(itemStack, Names.NBT.OWNER_UUID_MOST_SIG, entityPlayer.getUniqueID().getMostSignificantBits());
        NBTHelper.setLong(itemStack, Names.NBT.OWNER_UUID_LEAST_SIG, entityPlayer.getUniqueID().getLeastSignificantBits());
    }

    // The following methods are from Tinkers' Construct to be used for the Soul Matter Hammer.
    public static ImmutableList<BlockPos> calcAOEBlocks(ItemStack stack, World world, EntityPlayer player, BlockPos origin, int width, int height, int depth) {
        return calcAOEBlocks(stack, world, player, origin, width, height, depth, -1);
    }

    public static ImmutableList<BlockPos> calcAOEBlocks(ItemStack stack, World world, EntityPlayer player, BlockPos origin, int width, int height, int depth, int distance) {
        if (stack == null || !(stack.getItem() instanceof ItemSoulMatterHammer))
            return ImmutableList.of();

        IBlockState state = world.getBlockState(origin);
        Block block = state.getBlock();

        if (block.getMaterial(state) == Material.AIR) {
            return ImmutableList.of();
        }

        RayTraceResult mop = ((ItemSoulMatterHammer) stack.getItem()).rayTrace(world, player, false);
        if (mop == null) {
            return ImmutableList.of();
        }

        int x, y, z;
        BlockPos start = origin;
        switch (mop.sideHit) {
            case DOWN:
            case UP:
                Vec3i vec = player.getHorizontalFacing().getDirectionVec();
                x = vec.getX() * height + vec.getZ() * width;
                y = mop.sideHit.getAxisDirection().getOffset() * -depth;
                z = vec.getX() * width + vec.getZ() * height;
                start = start.add(-x / 2, 0, -z / 2);
                if (x % 2 == 0) {
                    if (x > 0 && mop.hitVec.xCoord - mop.getBlockPos().getX() > 0.5d) start = start.add(1, 0, 0);
                    else if (x < 0 && mop.hitVec.xCoord - mop.getBlockPos().getX() < 0.5d) start = start.add(-1, 0, 0);
                }
                if (z % 2 == 0) {
                    if (z > 0 && mop.hitVec.zCoord - mop.getBlockPos().getZ() > 0.5d) start = start.add(0, 0, 1);
                    else if (z < 0 && mop.hitVec.zCoord - mop.getBlockPos().getZ() < 0.5d) start = start.add(0, 0, -1);
                }
                break;
            case NORTH:
            case SOUTH:
                x = width;
                y = height;
                z = mop.sideHit.getAxisDirection().getOffset() * -depth;
                start = start.add(-x / 2, -y / 2, 0);
                if (x % 2 == 0 && mop.hitVec.xCoord - mop.getBlockPos().getX() > 0.5d) start = start.add(1, 0, 0);
                if (y % 2 == 0 && mop.hitVec.yCoord - mop.getBlockPos().getY() > 0.5d) start = start.add(0, 1, 0);
                break;
            case WEST:
            case EAST:
                x = mop.sideHit.getAxisDirection().getOffset() * -depth;
                y = height;
                z = width;
                start = start.add(-0, -y / 2, -z / 2);
                if (y % 2 == 0 && mop.hitVec.yCoord - mop.getBlockPos().getY() > 0.5d) start = start.add(0, 1, 0);
                if (z % 2 == 0 && mop.hitVec.zCoord - mop.getBlockPos().getZ() > 0.5d) start = start.add(0, 0, 1);
                break;
            default:
                x = y = z = 0;
        }

        ImmutableList.Builder<BlockPos> builder = ImmutableList.builder();
        for (int xp = start.getX(); xp != start.getX() + x; xp += x / MathHelper.abs_int(x)) {
            for (int yp = start.getY(); yp != start.getY() + y; yp += y / MathHelper.abs_int(y)) {
                for (int zp = start.getZ(); zp != start.getZ() + z; zp += z / MathHelper.abs_int(z)) {
                    if (xp == origin.getX() && yp == origin.getY() && zp == origin.getZ()) {
                        continue;
                    }
                    if (distance > 0 && MathHelper.abs_int(xp - origin.getX()) + MathHelper.abs_int(yp - origin.getY()) + MathHelper.abs_int(
                        zp - origin.getZ()) > distance) {
                        continue;
                    }
                    BlockPos pos = new BlockPos(xp, yp, zp);
                    if (isToolEffective2(stack, world.getBlockState(pos))) {
                        builder.add(pos);
                    }
                }
            }
        }

        return builder.build();
    }

    public static boolean isToolEffective(ItemStack stack, IBlockState state) {
        for (String type : stack.getItem().getToolClasses(stack)) {
            if (state.getBlock().isToolEffective(type, state)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isToolEffective2(ItemStack stack, IBlockState state) {
        if (isToolEffective(stack, state))
            return true;

        if (stack.getItem() instanceof ItemSoulMatterHammer && (stack.getItem()).canHarvestBlock(state))
            return true;

        return false;
    }

    public static void breakExtraBlock(ItemStack stack, World world, EntityPlayer player, BlockPos pos, BlockPos refPos) {
        if (world.isAirBlock(pos))
            return;

        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        if (!isToolEffective2(stack, state)) {
            return;
        }

        IBlockState refState = world.getBlockState(refPos);
        float refStrength = ForgeHooks.blockStrength(refState, player, world, refPos);
        float strength = ForgeHooks.blockStrength(state, player, world, pos);

        if (!ForgeHooks.canHarvestBlock(block, player, world, pos) || refStrength / strength > 10f)
            return;

        if (player.capabilities.isCreativeMode) {
            block.onBlockHarvested(world, pos, state, player);
            if (block.removedByPlayer(state, world, pos, player, false))
                block.onBlockDestroyedByPlayer(world, pos, state);

            if (!world.isRemote) {
                ((EntityPlayerMP) player).connection.sendPacket(new SPacketBlockChange(world, pos));
            }

            return;
        }

        stack.onBlockDestroyed(world, state, pos, player);

        if (!world.isRemote) {
            int xp = ForgeHooks.onBlockBreakEvent(world, ((EntityPlayerMP) player).interactionManager.getGameType(), (EntityPlayerMP) player, pos);

            if (xp == -1) {
                return;
            }

            block.onBlockHarvested(world, pos, state, player);

            if (block.removedByPlayer(state, world, pos, player, true)) {
                block.onBlockDestroyedByPlayer(world, pos, state);
                block.harvestBlock(world, player, pos, state, world.getTileEntity(pos), stack);
                block.dropXpOnBlockBreak(world, pos, xp);
            }

            EntityPlayerMP mpPlayer = (EntityPlayerMP) player;
            mpPlayer.connection.sendPacket(new SPacketBlockChange(world, pos));
        } else {
            world.playBroadcastSound(2001, pos, Block.getStateId(state));

            if (block.removedByPlayer(state, world, pos, player, true)) {
                block.onBlockDestroyedByPlayer(world, pos, state);
            }

            stack.onBlockDestroyed(world, state, pos, player);

            if (stack.stackSize == 0 && stack == player.getHeldItemMainhand()) {
                ForgeEventFactory.onPlayerDestroyItem(player, stack, EnumHand.MAIN_HAND);
                player.setHeldItem(EnumHand.MAIN_HAND, null);
            }

            Minecraft.getMinecraft().getConnection().sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, Minecraft.getMinecraft().objectMouseOver.sideHit));
        }
    }

//    public static boolean isValidEnergyItem(ItemStack container) {
//        return container != null && container.getItem() instanceof IEnergyContainerItem;
//    }
}
