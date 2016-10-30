package tv.savageboy74.savagetech.command;

/*
 * CommandLevelSoulTool.java
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

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import tv.savageboy74.savagetech.items.soul.tools.handler.ToolLevelHandler;
import tv.savageboy74.savagetech.util.helper.NBTHelper;

import java.util.List;

public class CommandLevelSoulTool implements ISubCommand
{
    public static CommandLevelSoulTool instance = new CommandLevelSoulTool();

    @Override
    public String getCommandName() {
        return "maxlevel";
    }

    @Override
    public int getPermissionLevel() {
        return 2;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] arguments) {
        if (sender.getEntityWorld().getPlayerEntityByName(sender.getName()).getHeldItemMainhand() != null) {
            ItemStack currentItem = sender.getEntityWorld().getPlayerEntityByName(sender.getName()).getHeldItemMainhand();
            EntityPlayer player = sender.getEntityWorld().getPlayerEntityByName(sender.getName());
            if (sender.getEntityWorld().getPlayerEntityByName(sender.getName()).getHeldItemMainhand().hasTagCompound()) {
                if (currentItem.getTagCompound().hasKey(ToolLevelHandler.SMTOOL_TAG)) {
                    if (player.capabilities.isCreativeMode) {
                        if (!ToolLevelHandler.isMaxToolLevel(currentItem, NBTHelper.getTagCompound(currentItem, ToolLevelHandler.SMTOOL_TAG))) {
                            ToolLevelHandler.setMaxLevel(player, currentItem);
                        } else {
                            sender.addChatMessage(new TextComponentString(I18n.translateToLocal("info.savagetech.command.maxlevel.max")));
                        }
                    } else {
                        sender.addChatMessage(new TextComponentString(I18n.translateToLocal("info.savagetech.command.maxlevel.creative")));
                    }
                } else {
                    sender.addChatMessage(new TextComponentString(I18n.translateToLocal("info.savagetech.command.maxlevel.hand")));
                }
            }
        }
    }

    @Override
    public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args) {
        return null;
    }
}
