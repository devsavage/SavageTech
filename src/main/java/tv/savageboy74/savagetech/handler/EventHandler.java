package tv.savageboy74.savagetech.handler;

/*
 * EventHandler.java
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

import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tv.savageboy74.savagetech.init.ModItems;

import java.util.Random;

public class EventHandler
{
    @SubscribeEvent
    public void onEntityDrop(LivingDropsEvent event) {
        if(event.getSource().getEntity() instanceof EntityPlayer) {
            double rand = Math.random();
            if(event.getEntity() instanceof EntityEnderman) {
                if(rand < 0.3D) {
                    event.getEntityLiving().dropItem(ModItems.rawSoulMatter, 2);
                } else if(rand < 0.15D) {
                    event.getEntityLiving().dropItem(ModItems.rawSoulMatter, 3);
                }
            }
        }
    }

    @SubscribeEvent
    public void onItemPickup(PlayerEvent.ItemPickupEvent event) {

    }

    public static class ExtraBlockBreak extends Event
    {
        public final ItemStack stack;
        public final EntityPlayer player;

        public int width;
        public int height;
        public int depth;
        public int distance;

        public ExtraBlockBreak(ItemStack stack, EntityPlayer player) {
            this.stack = stack;
            this.player = player;
        }

        public static ExtraBlockBreak fireEvent(ItemStack itemStack, EntityPlayer player, int width, int height, int depth, int distance) {
            ExtraBlockBreak event = new ExtraBlockBreak(itemStack, player);
            event.width = width;
            event.height = height;
            event.depth = depth;
            event.distance = distance;

            MinecraftForge.EVENT_BUS.post(event);
            return event;
        }
    }
}
