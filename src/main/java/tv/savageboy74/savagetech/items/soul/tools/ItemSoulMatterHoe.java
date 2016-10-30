package tv.savageboy74.savagetech.items.soul.tools;

/*
 * ItemSoulMatterHoe.java
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

import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import tv.savageboy74.savagetech.init.ModItems;
import tv.savageboy74.savagetech.items.soul.tools.base.STItemSoulTool;
import tv.savageboy74.savagetech.items.soul.tools.handler.ToolLevelHandler;
import tv.savageboy74.savagetech.util.helper.NBTHelper;
import tv.savageboy74.savagetech.util.reference.ModReference;
import tv.savageboy74.savagetech.util.reference.Names;

import java.util.Set;

@SuppressWarnings("Duplicates")
public class ItemSoulMatterHoe extends STItemSoulTool
{
    private static Set<Block> EFFECTIVE_ON = Sets.newHashSet(new Block[]{});
    public ItemSoulMatterHoe() {
        super("hoe", 1, EFFECTIVE_ON);
        this.setUnlocalizedName(Names.Items.soulHoe);
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        if(!playerIn.canPlayerEdit(pos, facing, stack)) {
            return EnumActionResult.FAIL;
        }

        if(playerIn.isSneaking() && (stack.getItem() == ModItems.soulHoe) && ToolLevelHandler.hasLevelTags(stack) && ToolLevelHandler.isMaxToolLevel(stack, NBTHelper.getTagCompound(stack, ToolLevelHandler.SMTOOL_TAG))) {
            //Do I need this again?
            if (!playerIn.canPlayerEdit(pos, facing, stack)) {
                return EnumActionResult.FAIL;
            } else {
                EnumActionResult success = EnumActionResult.FAIL;

                for (int x = pos.getX() - 1; x <= pos.getX() + 1; ++x) {
                    for (int z = pos.getZ() - 1; z <= pos.getZ() + 1; ++z) {
                        Block target = worldIn.getBlockState(new BlockPos(x, pos.getY(), z)).getBlock();
                        worldIn.getBlockState(new BlockPos(x, pos.getY() + 1, z)).getBlock();
                        if (facing != EnumFacing.DOWN && worldIn.isAirBlock(new BlockPos(x, pos.getY(), z).up()) && target == Blocks.GRASS || target == Blocks.DIRT) {
                            if (!worldIn.isRemote) {
                                worldIn.setBlockState(new BlockPos(x, pos.getY(), z), Blocks.FARMLAND.getDefaultState());
                                success = EnumActionResult.SUCCESS;
                                if(ToolLevelHandler.hasLevelTags(stack) && ToolLevelHandler.isMaxToolLevel(stack, NBTHelper.getTagCompound(stack, ToolLevelHandler.SMTOOL_TAG))) {
                                    stack.damageItem(0, playerIn);
                                } else {
                                    if(ToolLevelHandler.hasLevelTags(stack) && !ToolLevelHandler.isMaxToolLevel(stack, NBTHelper.getTagCompound(stack, ToolLevelHandler.SMTOOL_TAG)))
                                        ToolLevelHandler.addXp(stack, playerIn, 2);
                                    stack.damageItem(1, playerIn);
                                }
                            }
                        }
                    }
                }

                if (success == EnumActionResult.SUCCESS) {
                    worldIn.playSound(playerIn, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }

                return success;
            }
        } else {
            if(!playerIn.canPlayerEdit(pos.offset(facing), facing, stack)) {
                return EnumActionResult.FAIL;
            } else {
                int hook = ForgeEventFactory.onHoeUse(stack, playerIn, worldIn, pos);

                if(hook != 0) {
                    if(hook > 0)
                        return EnumActionResult.SUCCESS;
                    else
                        return EnumActionResult.FAIL;
                }

                IBlockState iBlockState = worldIn.getBlockState(pos);
                Block block = iBlockState.getBlock();

                if(facing != EnumFacing.DOWN && worldIn.isAirBlock(pos.up())) {
                    if(block == Blocks.GRASS || block == Blocks.GRASS_PATH) {
                        this.useHoe(stack, playerIn, worldIn, pos, Blocks.FARMLAND.getDefaultState());
                        return EnumActionResult.SUCCESS;
                    }

                    if(block == Blocks.DIRT) {
                        switch ((BlockDirt.DirtType)iBlockState.getValue(BlockDirt.VARIANT)) {
                            case DIRT:
                                this.useHoe(stack, playerIn, worldIn, pos, Blocks.FARMLAND.getDefaultState());
                                return EnumActionResult.SUCCESS;
                            case COARSE_DIRT:
                                this.useHoe(stack, playerIn, worldIn, pos, Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
                                return EnumActionResult.SUCCESS;
                        }
                    }
                }

                return EnumActionResult.PASS;
            }
        }
    }

    private boolean useHoe(ItemStack stack, EntityPlayer player, World worldIn, BlockPos target, IBlockState newState) {
        worldIn.playSound(player, target, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);

        if (worldIn.isRemote) {
            return true;
        } else {
            worldIn.setBlockState(target, newState);
            if(ToolLevelHandler.hasLevelTags(stack) && ToolLevelHandler.isMaxToolLevel(stack, NBTHelper.getTagCompound(stack, ToolLevelHandler.SMTOOL_TAG))) {
                stack.damageItem(0, player);
                return true;
            } else {
                if(ToolLevelHandler.hasLevelTags(stack) && !ToolLevelHandler.isMaxToolLevel(stack, NBTHelper.getTagCompound(stack, ToolLevelHandler.SMTOOL_TAG)))
                    ToolLevelHandler.addXp(stack, player, 2);
                stack.damageItem(1, player);
                return true;
            }
        }
    }

    @Override
    public String getUnlocalizedName() {
        return String.format("item.%s%s", ModReference.MOD_DOMAIN, getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack) {
        return String.format("item.%s%s", ModReference.MOD_DOMAIN, getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    protected String getUnwrappedUnlocalizedName(String unlocalizedName) {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }
}
