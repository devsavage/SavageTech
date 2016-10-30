package tv.savageboy74.savagetech.items.soul.tools.handler;

/*
 * ToolLevelHandler.java
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
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import tv.savageboy74.savagetech.client.config.values.ConfigIntegerValues;
import tv.savageboy74.savagetech.handler.ConfigHandler;
import tv.savageboy74.savagetech.init.ModItems;
import tv.savageboy74.savagetech.util.helper.NBTHelper;

public class ToolLevelHandler
{
    public static final String SMTOOL_TAG = "SMTool";
    public static final String SMTOOL_XP = "ToolXP";
    public static final String SMTOOL_LEVEL = "ToolLevel";
    public static int maxLevel = ConfigIntegerValues.MAX_SOUL_MATTER_TOOL_LEVEL.getValue();

    public static int getToolLevel(ItemStack tool, NBTTagCompound tag) {
        return NBTHelper.getInt(tool, SMTOOL_LEVEL);
    }

    public static long getToolExperience(ItemStack tool, NBTTagCompound tag) {
        return NBTHelper.getLong(tool, SMTOOL_XP);
    }

    public static boolean hasLevel(ItemStack tool, NBTTagCompound tag) {
        return NBTHelper.hasTag(tool, SMTOOL_LEVEL);
    }

    public static boolean hasXp(ItemStack tool, NBTTagCompound tag) {
        return NBTHelper.hasTag(tool, SMTOOL_XP);
    }

    public static boolean isMaxToolLevel(ItemStack tool, NBTTagCompound tag) {
        return getToolLevel(tool, tag) >= maxLevel;
    }

    public static void addLevelTag(ItemStack tool) {
        NBTHelper.setInteger(tool, SMTOOL_LEVEL, 1);
        NBTHelper.setLong(tool, SMTOOL_XP, 0);
    }

    public static boolean hasLevelTags(ItemStack tool) {
        return NBTHelper.hasTag(tool, SMTOOL_LEVEL) && NBTHelper.hasTag(tool, SMTOOL_XP);
    }

    public static void updateToolXp(ItemStack tool, EntityPlayer player, long xp) {
        NBTTagCompound tag = tool.getTagCompound().getCompoundTag(SMTOOL_TAG);
        if (!hasLevel(tool, tag))
            return;

        int lvl = getToolLevel(tool, tag);

        boolean levelTool = false;

        if (xp >= 0 && hasXp(tool, tag) && lvl > 0 && !isMaxToolLevel(tool, tag)) {
            NBTHelper.setLong(tool, SMTOOL_XP, xp);

            if (xp >= getRequiredXp(tool, tag)) {
                levelUp(tool, player);
                levelTool = true;
            }
        }

        if ((levelTool) && !player.worldObj.isRemote) {
            int level = getToolLevel(tool, tag);
            // Does this work? or needed?
            tool.getItem().setDamage(tool, 1);

            player.addChatComponentMessage(new TextComponentString("You have leveled up your " + TextFormatting.DARK_AQUA + tool.getDisplayName() + TextFormatting.RESET + " to level " + TextFormatting.AQUA + level + TextFormatting.RESET + "!"));
            player.worldObj.playSound(player, player.getPosition(), SoundEvents.ENTITY_WITCH_HURT, SoundCategory.HOSTILE, 0.9F, 1.0F);
        }

        if ((levelTool) && !player.worldObj.isRemote && isMaxToolLevel(tool, NBTHelper.getTagCompound(tool, SMTOOL_TAG))) {
            triggerMaxLevel(tool, player);
        }
    }

    public static void triggerMaxLevel(ItemStack tool, EntityPlayer player) {
        player.addChatComponentMessage(new TextComponentString(TextFormatting.GOLD + "You have reached the max level for your " + TextFormatting.AQUA + tool.getDisplayName() + TextFormatting.GOLD + "!"));
        player.worldObj.playSound(player, player.getPosition(), SoundEvents.ENTITY_WITHER_DEATH, SoundCategory.HOSTILE, 0.9F, 1.0F);
    }

    public static void addXp(ItemStack tool, EntityPlayer player, long xp) {
        if (player.capabilities.isCreativeMode)
            return;
        if (tool == null || !tool.hasTagCompound())
            return;

        NBTTagCompound tag = tool.getTagCompound().getCompoundTag(SMTOOL_TAG);

        if (!hasLevel(tool, tag) || !hasXp(tool, tag))
            return;

        Long toolXp = -1L;
        if (hasXp(tool, tag)) {
            toolXp = getToolExperience(tool, tag) + xp;
        }

        updateToolXp(tool, player, toolXp);
    }

    public static int getRequiredXp(ItemStack tool, NBTTagCompound tag) {
        if (tag == null) tag = tool.getTagCompound().getCompoundTag(SMTOOL_TAG);

        float baseXp = 100F;

        if (tool.getItem() == ModItems.soulSword) baseXp *= 5.5F;
        if (tool.getItem() == ModItems.soulShovel) baseXp *= 3.5F;
        if (tool.getItem() == ModItems.soulAxe) baseXp *= 3.5F;
        if (tool.getItem() == ModItems.soulHoe) baseXp *= 4.5F;
        if (tool.getItem() == ModItems.soulPick) baseXp *= 4.5F;
        if (tool.getItem() == ModItems.soulHammer) baseXp *= 5.5F;

        baseXp *= 150 / 100F;

        int toolLevel = tag.getInteger(SMTOOL_LEVEL);
        if (toolLevel >= 1)
            baseXp *= Math.pow(1.15F, toolLevel - 1);

        return Math.round(baseXp);
    }

    public static void levelUp(ItemStack tool, EntityPlayer player) {
        NBTTagCompound tag = tool.getTagCompound().getCompoundTag(SMTOOL_TAG);

        int level = getToolLevel(tool, tag);
        level++;

        NBTHelper.setInteger(tool, SMTOOL_LEVEL, level);
        NBTHelper.setLong(tool, SMTOOL_XP, 0L);
    }

    public static void setMaxLevel(EntityPlayer player, ItemStack tool) {
        NBTHelper.setInteger(tool, SMTOOL_LEVEL, maxLevel);
        NBTHelper.setLong(tool, SMTOOL_XP, 0L);
        triggerMaxLevel(tool, player);
    }
}
