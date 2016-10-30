package tv.savageboy74.savagetech.network.handler;

/*
 * PacketHandler.java
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

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import tv.savageboy74.savagetech.SavageTech;
import tv.savageboy74.savagetech.network.handler.ServerPacketHandler.SavageTechMessage;

public class PacketHandler
{
    public static final short AutoFertilizerButton = 0;

    public static void sendToServer(IMessage message) {
        SavageTech.networkWrapper.sendToServer(message);
    }

    public static void sendToServer(short packet, TileEntity te, Object... args) {
        sendToServer(new SavageTechMessage(packet, te, args));
    }

    public static void sendTo(IMessage message, EntityPlayerMP playerMP) {
        SavageTech.networkWrapper.sendTo(message, playerMP);
    }
}
