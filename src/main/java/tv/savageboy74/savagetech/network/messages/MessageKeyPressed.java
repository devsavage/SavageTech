package tv.savageboy74.savagetech.network.messages;

/*
 * MessageKeyPressed.java
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

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import tv.savageboy74.savagetech.util.IKeyBound;
import tv.savageboy74.savagetech.util.reference.Key;

public class MessageKeyPressed implements IMessage
{
    private Key keyPressed;

    public MessageKeyPressed() {

    }

    public MessageKeyPressed(Key keyPressed) {
        if(keyPressed != null) {
            this.keyPressed = keyPressed;
        } else {
            this.keyPressed = Key.unknown;
        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.keyPressed = Key.getKey(buf.readByte());
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeByte((byte) keyPressed.ordinal());
    }

    public static class MessageHandler implements IMessageHandler<MessageKeyPressed, IMessage> {

        @Override
        public IMessage onMessage(MessageKeyPressed message, MessageContext ctx) {
            EntityPlayer entityPlayer = ctx.getServerHandler().playerEntity;

            if (entityPlayer != null) {
                ItemStack boots = entityPlayer.getItemStackFromSlot(EntityEquipmentSlot.FEET);
                ItemStack legs = entityPlayer.getItemStackFromSlot(EntityEquipmentSlot.LEGS);
                ItemStack chest = entityPlayer.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
                ItemStack helm = entityPlayer.getItemStackFromSlot(EntityEquipmentSlot.HEAD);

                if(boots != null && legs != null && chest != null && helm != null) {
                    if(boots.getItem() instanceof IKeyBound && legs.getItem() instanceof IKeyBound && chest.getItem() instanceof IKeyBound && helm.getItem() instanceof IKeyBound) {
                        //We only need to handle the chest's keybinding action for now
                        ((IKeyBound) chest.getItem()).doKeyBindingAction(entityPlayer, chest, message.keyPressed);
                    }
                }
            }

            return null;
        }
    }
}
