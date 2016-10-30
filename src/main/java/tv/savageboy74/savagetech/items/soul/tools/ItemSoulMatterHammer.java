package tv.savageboy74.savagetech.items.soul.tools;

/*
 * ItemSoulMatterHammer.java
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
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import tv.savageboy74.savagetech.items.soul.tools.base.STItemSoulTool;
import tv.savageboy74.savagetech.items.soul.tools.handler.ToolLevelHandler;
import tv.savageboy74.savagetech.util.helper.ItemHelper;
import tv.savageboy74.savagetech.util.helper.NBTHelper;
import tv.savageboy74.savagetech.util.reference.ModReference;
import tv.savageboy74.savagetech.util.reference.Names;

import java.util.Set;

@SuppressWarnings("Duplicates")
public class ItemSoulMatterHammer extends STItemSoulTool
{
    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.ACTIVATOR_RAIL, Blocks.COAL_ORE, Blocks.COBBLESTONE, Blocks.DETECTOR_RAIL, Blocks.DIAMOND_BLOCK, Blocks.DIAMOND_ORE, Blocks.DOUBLE_STONE_SLAB, Blocks.GOLDEN_RAIL, Blocks.GOLD_BLOCK, Blocks.GOLD_ORE, Blocks.ICE, Blocks.IRON_BLOCK, Blocks.IRON_ORE, Blocks.LAPIS_BLOCK, Blocks.LAPIS_ORE, Blocks.LIT_REDSTONE_ORE, Blocks.MOSSY_COBBLESTONE, Blocks.NETHERRACK, Blocks.PACKED_ICE, Blocks.RAIL, Blocks.REDSTONE_ORE, Blocks.SANDSTONE, Blocks.RED_SANDSTONE, Blocks.STONE, Blocks.STONE_SLAB);
    private static final Set<Block> SPECIAL_BLOCKS = Sets.newHashSet(Blocks.IRON_BLOCK, Blocks.GOLD_BLOCK, Blocks.DIAMOND_BLOCK);

    public ItemSoulMatterHammer() {
        super("pickaxe", 5, EFFECTIVE_ON);
        this.setUnlocalizedName(Names.Items.soulHammer);
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        if(playerIn.isSneaking()) {
            Block target = worldIn.getBlockState(pos).getBlock();

            if(SPECIAL_BLOCKS.contains(target) && ForgeHooks.canHarvestBlock(target, playerIn, worldIn, pos) && !worldIn.isRemote) {
                if(target == Blocks.IRON_BLOCK) {
                    ItemHelper.dropAsEntity(worldIn, pos, new ItemStack(Items.IRON_INGOT, 9));
                    worldIn.setBlockToAir(pos);
                    worldIn.notifyNeighborsOfStateChange(pos, target);
                    return EnumActionResult.SUCCESS;
                }

                if(target == Blocks.GOLD_BLOCK) {
                    ItemHelper.dropAsEntity(worldIn, pos, new ItemStack(Items.GOLD_INGOT, 9));
                    worldIn.setBlockToAir(pos);
                    worldIn.notifyNeighborsOfStateChange(pos, target);
                    return EnumActionResult.SUCCESS;
                }

                if(target == Blocks.DIAMOND_BLOCK) {
                    ItemHelper.dropAsEntity(worldIn, pos, new ItemStack(Items.DIAMOND, 9));
                    worldIn.setBlockToAir(pos);
                    worldIn.notifyNeighborsOfStateChange(pos, target);
                    return EnumActionResult.SUCCESS;
                }
            }

            if (ToolLevelHandler.hasLevelTags(stack) && !ToolLevelHandler.isMaxToolLevel(stack, NBTHelper.getTagCompound(stack, ToolLevelHandler.SMTOOL_TAG))) {
                stack.damageItem(1, playerIn);
                return EnumActionResult.SUCCESS;
            }

            return EnumActionResult.FAIL;
        }

        return super.onItemUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
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

    @Override
    public RayTraceResult rayTrace(World worldIn, EntityPlayer playerIn, boolean useLiquids) {
        return super.rayTrace(worldIn, playerIn, useLiquids);
    }

    public ImmutableList<BlockPos> getAOEBlocks(ItemStack stack, World world, EntityPlayer player, BlockPos origin) {
        if(!ItemHelper.isToolEffective2(stack, world.getBlockState(origin))) {
            return ImmutableList.of();
        }

        return ItemHelper.calcAOEBlocks(stack, world, player, origin, 3, 3, 1);
    }


    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player) {
        if(ItemHelper.isToolEffective2(itemstack, player.worldObj.getBlockState(pos))) {
            for(BlockPos extraPos : (this).getAOEBlocks(itemstack, player.worldObj, player, pos)) {
                ItemHelper.breakExtraBlock(itemstack, player.worldObj, player, extraPos, pos);
            }
        }

        return super.onBlockStartBreak(itemstack, pos, player);
    }
}
