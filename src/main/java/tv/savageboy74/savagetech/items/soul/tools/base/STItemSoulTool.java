package tv.savageboy74.savagetech.items.soul.tools.base;

/*
 * STItemTool.java
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

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import tv.savageboy74.savagetech.SavageTech;
import tv.savageboy74.savagetech.init.ModItems;
import tv.savageboy74.savagetech.items.soul.tools.handler.ToolLevelHandler;
import tv.savageboy74.savagetech.items.soul.tools.material.SoulMaterial;
import tv.savageboy74.savagetech.util.helper.NBTHelper;

import java.util.List;
import java.util.Set;

public class STItemSoulTool extends ItemTool
{
    private final String name;
    private Set<Block> effectiveBlocks;
    public STItemSoulTool(String name, float damage, Set<Block> effectiveBlocks) {
        super(damage, 1, SoulMaterial.soulMatterTool, effectiveBlocks);

        this.name = name;
        this.effectiveBlocks = effectiveBlocks;

        this.canRepair = true;
        this.setCreativeTab(SavageTech.tabSavageTech);

    }

    @Override
    public boolean canHarvestBlock(IBlockState state) {
        switch (name) {
            case "axe":
                return effectiveBlocks.contains(state.getBlock()) || state.getMaterial() == Material.WOOD || state.getMaterial() == Material.PLANTS || state.getMaterial() == Material.VINE;
            case "pickaxe":
                return effectiveBlocks.contains(state.getBlock()) || state.getMaterial() == Material.ROCK || state.getMaterial() == Material.ANVIL || state.getMaterial() == Material.IRON;
            case "sword":
                return effectiveBlocks.contains(state.getBlock()) || state.getBlock() == Blocks.WEB;
            case "hammer":
                return effectiveBlocks.contains(state.getBlock()) || state == Blocks.OBSIDIAN ? this.toolMaterial.getHarvestLevel() == 3 : (state.getBlock() != Blocks.DIAMOND_BLOCK && state.getBlock() != Blocks.DIAMOND_ORE ? (state.getBlock() != Blocks.EMERALD_ORE && state.getBlock() != Blocks.EMERALD_BLOCK ? (state.getBlock() != Blocks.GOLD_BLOCK && state.getBlock() != Blocks.GOLD_ORE ? (state.getBlock() != Blocks.IRON_BLOCK && state.getBlock() != Blocks.IRON_DOOR ? (state.getBlock() != Blocks.LAPIS_BLOCK && state.getBlock() != Blocks.LAPIS_ORE ? (state.getBlock() != Blocks.REDSTONE_ORE && state.getBlock() != Blocks.LIT_REDSTONE_ORE ? (state.getMaterial() == Material.ROCK ? true : (state.getMaterial() == Material.IRON ? true : state.getMaterial() == Material.ANVIL)) : this.toolMaterial.getHarvestLevel() >= 2) : this.toolMaterial.getHarvestLevel() >= 1) : this.toolMaterial.getHarvestLevel() >= 1) : this.toolMaterial.getHarvestLevel() >= 2) : this.toolMaterial.getHarvestLevel() >= 2) : this.toolMaterial.getHarvestLevel() >= 2);
            case "shovel":
                return effectiveBlocks.contains(state.getBlock()) || state.getMaterial() == Material.GRASS || state.getMaterial() == Material.GROUND || state.getMaterial() == Material.SAND || state.getMaterial() == Material.SNOW;
            default:
                return super.canHarvestBlock(state);
        }
    }

    @Override
    public float getStrVsBlock(ItemStack stack, IBlockState state) {
        if(canHarvestBlock(state) && ToolLevelHandler.hasLevelTags(stack)) {
            if(ToolLevelHandler.isMaxToolLevel(stack, stack.getTagCompound().getCompoundTag(ToolLevelHandler.SMTOOL_TAG))) {
                return getToolMaterial().getEfficiencyOnProperMaterial() * ToolLevelHandler.getToolLevel(stack, NBTHelper.getTagCompound(stack, ToolLevelHandler.SMTOOL_TAG));
            } else {
                return getToolMaterial().getEfficiencyOnProperMaterial();
            }
        }

        return 1.0F;
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        if(ToolLevelHandler.hasLevelTags(stack) && !ToolLevelHandler.isMaxToolLevel(stack, NBTHelper.getTagCompound(stack, ToolLevelHandler.SMTOOL_TAG))) {
            if (stack.getItem() == ModItems.soulSword) {
                if (attacker instanceof EntityPlayer)
                    ToolLevelHandler.addXp(stack, (EntityPlayer) attacker, 6);
            }

            stack.damageItem(1, attacker);
        }
        else
            stack.damageItem(0, attacker);
        return true;
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if(!NBTHelper.hasTag(stack, ToolLevelHandler.SMTOOL_TAG) && !worldIn.isRemote) {
            NBTHelper.setString(stack, ToolLevelHandler.SMTOOL_TAG, stack.getDisplayName());
            ToolLevelHandler.addLevelTag(stack);
        } else {
            if(stack.getItemDamage() == stack.getMaxDamage() - 1)
                stack.setItemDamage(0);
        }
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
        if (worldIn.isRemote || !(entityLiving instanceof EntityPlayer)) {
            return false;
        }

        if (ToolLevelHandler.isMaxToolLevel(stack, NBTHelper.getTagCompound(stack, ToolLevelHandler.SMTOOL_TAG))) {
            stack.damageItem(0, entityLiving);
            return true;
        }

        if (effectiveBlocks.contains(worldIn.getBlockState(pos).getBlock()) || canHarvestBlock(state) && ToolLevelHandler.hasLevelTags(stack)) {
            ToolLevelHandler.addXp(stack, (EntityPlayer) entityLiving, entityLiving.getRNG().nextInt(6));
            if(!ToolLevelHandler.isMaxToolLevel(stack, NBTHelper.getTagCompound(stack, ToolLevelHandler.SMTOOL_TAG)))
                stack.damageItem(1, entityLiving);
            else
                stack.damageItem(0, entityLiving);
        } else {
            if(ToolLevelHandler.hasLevelTags(stack) && !ToolLevelHandler.isMaxToolLevel(stack, NBTHelper.getTagCompound(stack, ToolLevelHandler.SMTOOL_TAG)))
                stack.damageItem(1, entityLiving);
            else
                stack.damageItem(0, entityLiving);
        }

        return true;
    }

    @Override
    public Set<String> getToolClasses(ItemStack stack) {
        return ImmutableSet.of(name);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        if (ToolLevelHandler.hasLevelTags(stack) && !ToolLevelHandler.isMaxToolLevel(stack, NBTHelper.getTagCompound(stack, ToolLevelHandler.SMTOOL_TAG))) {
            tooltip.add("Level: " + TextFormatting.AQUA + ToolLevelHandler.getToolLevel(stack, NBTHelper.getTagCompound(stack, ToolLevelHandler.SMTOOL_TAG)));
            tooltip.add("XP: " + TextFormatting.GREEN + ToolLevelHandler.getToolExperience(stack, NBTHelper.getTagCompound(stack, ToolLevelHandler.SMTOOL_TAG)));
        } else if (ToolLevelHandler.hasLevelTags(stack) && ToolLevelHandler.isMaxToolLevel(stack, NBTHelper.getTagCompound(stack, ToolLevelHandler.SMTOOL_TAG))) {
            tooltip.add(TextFormatting.DARK_GREEN + "Max Level");
        }
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return toRepair.getItem() instanceof STItemSoulTool && repair.getItem() == ModItems.soulMatter;
    }

    @Override
    public int getHarvestLevel(ItemStack stack, String toolClass) {
        if(ToolLevelHandler.hasLevelTags(stack)) {
            return getToolMaterial().getHarvestLevel() * ToolLevelHandler.getToolLevel(stack, NBTHelper.getTagCompound(stack, ToolLevelHandler.SMTOOL_TAG));
        } else {
            return getToolMaterial().getHarvestLevel();
        }
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        EnumActionResult used = EnumActionResult.FAIL;
        int hotbarSlot = playerIn.inventory.currentItem;
        int itemSlot = hotbarSlot == 0 ? 8 : hotbarSlot + 1;
        ItemStack nearbyStack = null;

        if (hotbarSlot < 8) {
            nearbyStack = playerIn.inventory.getStackInSlot(itemSlot);

            if (nearbyStack != null) {
                Item item = nearbyStack.getItem();

                if (item == Item.getItemFromBlock(Blocks.TORCH)) {
                    int posX = pos.getX();
                    int posY = pos.getY();
                    int posZ = pos.getZ();

                    EnumFacing whichSide = EnumFacing.values()[facing.getIndex()];

                    switch (whichSide) {
                        case DOWN:
                            --posY;
                            break;
                        case UP:
                            ++posY;
                            break;
                        case SOUTH:
                            --posZ;
                            break;
                        case NORTH:
                            ++posZ;
                            break;
                        case WEST:
                            --posX;
                            break;
                        case EAST:
                            ++posX;
                            break;
                    }

                    AxisAlignedBB blockBounds = new AxisAlignedBB(posX, posY, posZ, posX + 1, posY + 1, posZ + 1);
                    AxisAlignedBB playerBounds = playerIn.getEntityBoundingBox();

                    IBlockState blockToPlace = ((ItemBlock) item).block.getDefaultState();
                    if (blockToPlace.getMaterial().blocksMovement()) {
                        if (playerBounds.intersectsWith(blockBounds))
                            return EnumActionResult.FAIL;
                    }

                    int dmg = nearbyStack.getItemDamage();
                    int count = nearbyStack.stackSize;

                    used = item.onItemUse(nearbyStack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);

                    if (playerIn.capabilities.isCreativeMode) {
                        nearbyStack.setItemDamage(dmg);
                        nearbyStack.stackSize = count;
                    }

                    if (nearbyStack.stackSize < 1) {
                        nearbyStack = null;
                        playerIn.inventory.setInventorySlotContents(itemSlot, null);
                    }
                }
            }
        }

        return used;
    }
}
