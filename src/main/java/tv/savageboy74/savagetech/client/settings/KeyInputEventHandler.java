package tv.savageboy74.savagetech.client.settings;

/*
 * KeyInputEventHandler.java
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
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tv.savageboy74.savagetech.items.soul.armor.ItemSoulArmor;
import tv.savageboy74.savagetech.network.handler.PacketHandler;
import tv.savageboy74.savagetech.network.messages.MessageKeyPressed;
import tv.savageboy74.savagetech.util.IKeyBound;
import tv.savageboy74.savagetech.util.helper.LogHelper;
import tv.savageboy74.savagetech.util.reference.Key;

@SideOnly(Side.CLIENT)
public class KeyInputEventHandler
{
    private static Key getPressedKeyBinding() {
        if(KeyBindings.flightSpeed.isKeyDown()) {
            return Key.flightSpeed;
        }

        return Key.unknown;
    }

    @SubscribeEvent
    public void handleKeyPressedEvent(InputEvent.KeyInputEvent event) {
        if(getPressedKeyBinding() == Key.unknown) {
            return;
        }

        if(FMLClientHandler.instance().getClient().inGameHasFocus) {
            if(FMLClientHandler.instance().getClientPlayerEntity() != null) {
                EntityPlayer player = FMLClientHandler.instance().getClientPlayerEntity();

                if(player.getHeldItemMainhand() != null) {
                    ItemStack currentlyEquippedItem = player.getHeldItemMainhand();

                    // Wow
                    if(currentlyEquippedItem.getItem() instanceof IKeyBound) {
                        if(player.worldObj.isRemote) {
                            PacketHandler.sendToServer(new MessageKeyPressed(getPressedKeyBinding()));
                        } else {
                            ((IKeyBound) currentlyEquippedItem.getItem()).doKeyBindingAction(player, currentlyEquippedItem, getPressedKeyBinding());
                        }
                    }
                }

                ItemStack boots = player.getItemStackFromSlot(EntityEquipmentSlot.FEET);
                ItemStack legs = player.getItemStackFromSlot(EntityEquipmentSlot.LEGS);
                ItemStack chest = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
                ItemStack helm = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD);

                if(boots != null && legs != null && chest != null && helm != null) {
                    if(boots.getItem() instanceof IKeyBound && legs.getItem() instanceof IKeyBound && chest.getItem() instanceof IKeyBound && helm.getItem() instanceof IKeyBound) {
                        if(player.worldObj.isRemote) {
                            PacketHandler.sendToServer(new MessageKeyPressed(getPressedKeyBinding()));
                        } else {
                            //I only handle the chest in ItemSoulArmor->doKeyBindingAction so I will only handle it here too.
                            ((IKeyBound) chest.getItem()).doKeyBindingAction(player, chest, getPressedKeyBinding());
                        }
                    }
                }
            }
        }
    }
}
