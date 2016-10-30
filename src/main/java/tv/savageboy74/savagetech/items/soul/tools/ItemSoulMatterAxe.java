package tv.savageboy74.savagetech.items.soul.tools;

/*
 * ItemSoulMatterAxe.java
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

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import gnu.trove.set.hash.THashSet;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import tv.savageboy74.savagetech.handler.EventHandler;
import tv.savageboy74.savagetech.init.ModItems;
import tv.savageboy74.savagetech.items.soul.tools.base.STItemSoulTool;
import tv.savageboy74.savagetech.items.soul.tools.handler.ToolLevelHandler;
import tv.savageboy74.savagetech.util.helper.ItemHelper;
import tv.savageboy74.savagetech.util.helper.NBTHelper;
import tv.savageboy74.savagetech.util.reference.ModReference;
import tv.savageboy74.savagetech.util.reference.Names;

import java.util.*;

@SuppressWarnings("Duplicates")
public class ItemSoulMatterAxe extends STItemSoulTool
{
    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(new Block[] {Blocks.PLANKS, Blocks.BOOKSHELF, Blocks.LOG, Blocks.LOG2, Blocks.CHEST, Blocks.PUMPKIN, Blocks.LIT_PUMPKIN, Blocks.MELON_BLOCK, Blocks.LADDER, Blocks.WOODEN_BUTTON, Blocks.WOODEN_PRESSURE_PLATE});

    public ItemSoulMatterAxe() {
        super("axe", 7, EFFECTIVE_ON);
        this.setUnlocalizedName(Names.Items.soulAxe);
    }

    @Override
    public int getHarvestLevel(ItemStack stack, String toolClass) {
        if(toolClass.equals("axe") && ToolLevelHandler.hasLevelTags(stack)) {
            return getToolMaterial().getHarvestLevel() * ToolLevelHandler.getToolLevel(stack, NBTHelper.getTagCompound(stack, ToolLevelHandler.SMTOOL_TAG));
        } else {
            return getToolMaterial().getHarvestLevel();
        }
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
        return toRepair.getItem() == ModItems.soulAxe && repair.getItem() == ModItems.soulMatter;
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

    /**
     *
     * Connected block breaking system.. A HUGE thanks to Tinkers' Construct
     *
     */


    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player) {
        if(ToolLevelHandler.isMaxToolLevel(itemstack, itemstack.getTagCompound()) && detectTree(player.worldObj, pos)) {
            return verifyAndBeginTreeChopping(itemstack, pos, player);
        }
        return super.onBlockStartBreak(itemstack, pos, player);
    }

    private static boolean isWood(World world, BlockPos pos) {
        return world.getBlockState(pos).getBlock().isWood(world, pos);
    }

    public static boolean detectTree(World world, BlockPos origin) {
        BlockPos pos = null;
        Stack<BlockPos> candidates = new Stack<BlockPos>();
        candidates.add(origin);

        while (!candidates.isEmpty()) {
            BlockPos candidate = candidates.pop();
            if ((pos == null || candidate.getY() > pos.getY()) && isWood(world, candidate)) {
                pos = candidate.up();
                while (isWood(world, pos)) {
                    pos = pos.up();
                }
                candidates.add(pos.north());
                candidates.add(pos.east());
                candidates.add(pos.south());
                candidates.add(pos.west());
            }
        }

        if (pos == null) {
            return false;
        }

        int d = 3;
        int o = -1;
        int leaves = 0;
        for (int x = 0; x < d; x++) {
            for (int y = 0; y < d; y++) {
                for (int z = 0; z < d; z++) {
                    BlockPos leaf = pos.add(o + x, o + y, o + z);
                    IBlockState state = world.getBlockState(leaf);
                    if (state.getBlock().isLeaves(state, world, leaf)) {
                        if (++leaves >= 5) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    private boolean verifyAndBeginTreeChopping(ItemStack stack, BlockPos blockPos, EntityPlayer player) {
        if(player.worldObj.isRemote) {
            return true;
        }

        EventHandler.ExtraBlockBreak event = EventHandler.ExtraBlockBreak.fireEvent(stack, player, 3, 3, 3, -1);
        int speed = Math.round((event.width * event.height * event.depth) / 27F);
        if(event.distance > 0) {
            speed = event.distance + 1;
        }

        MinecraftForge.EVENT_BUS.register(new TreeChopTask(stack, blockPos, player, speed));
        return true;
    }

    public static class TreeChopTask
    {
        public final World world;
        public final EntityPlayer player;
        public final ItemStack tool;
        public final int blocksPerTick;

        public Queue<BlockPos> blocks = Lists.newLinkedList();
        public Set<BlockPos> visited = new THashSet<BlockPos>();

        public TreeChopTask(ItemStack tool, BlockPos start, EntityPlayer player, int blocksPerTick) {
            this.world = player.getEntityWorld();
            this.player = player;
            this.tool = tool;
            this.blocksPerTick = blocksPerTick;

            this.blocks.add(start);
        }

        // Chop... chop ...... CHOP
        @SubscribeEvent
        public void chopChopChop(TickEvent.WorldTickEvent event) {
            if(event.side.isClient()) {
                finish();
                return;
            }

            int left = blocksPerTick;

            BlockPos pos;
            while(left > 0) {
                if(blocks.isEmpty()) {
                    finish();
                    return;
                }

                pos = blocks.remove();
                if(!visited.add(pos)) {
                    continue;
                }

                if(!isWood(world, pos) || !ItemHelper.isToolEffective2(tool, world.getBlockState(pos))) {
                    continue;
                }

                for(EnumFacing facing : new EnumFacing[]{EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST}) {
                    BlockPos pos2 = pos.offset(facing);
                    if(!visited.contains(pos2)) {
                        blocks.add(pos2);
                    }
                }

                for(int x = 0; x < 3; x++) {
                    for(int z = 0; z < 3; z++) {
                        BlockPos pos2 = pos.add(-1 + x, 1, -1 + z);
                        if(!visited.contains(pos2)) {
                            blocks.add(pos2);
                        }
                    }
                }

                ItemHelper.breakExtraBlock(tool, world, player, pos, pos);
                left--;
            }
        }

        private void finish() {
            MinecraftForge.EVENT_BUS.unregister(this);
        }
    }
}
