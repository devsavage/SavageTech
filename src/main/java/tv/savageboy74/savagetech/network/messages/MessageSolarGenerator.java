package tv.savageboy74.savagetech.network.messages;

/*
 * MessageSolarGenerator.java
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
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

//public class MessageSolarGenerator implements IMessage, IMessageHandler<MessageSolarGenerator, IMessage>
//{
//    private int xCoord;
//    private int yCoord;
//    private int zCoord;
//    private int energyStored;
//
//    public MessageSolarGenerator(){}
//
//    public MessageSolarGenerator(int x, int y, int z, int storedEnergy) {
//        this.xCoord = x;
//        this.yCoord = y;
//        this.zCoord = z;
//        this.energyStored = storedEnergy;
//    }
//
//    @Override
//    public void fromBytes(ByteBuf buf) {
//        this.xCoord = buf.readInt();
//        this.yCoord = buf.readInt();
//        this.zCoord = buf.readInt();
//        this.energyStored = buf.readInt();
//    }
//
//    @Override
//    public void toBytes(ByteBuf buf) {
//        buf.writeInt(this.xCoord);
//        buf.writeInt(this.yCoord);
//        buf.writeInt(this.zCoord);
//        buf.writeInt(this.energyStored);
//    }
//
//    @Override
//    public IMessage onMessage(MessageSolarGenerator message, MessageContext ctx) {
//        TileEntity tileEntity = FMLClientHandler.instance().getWorldClient().getTileEntity(new BlockPos(message.xCoord, message.yCoord, message.zCoord));
//        if (tileEntity instanceof TileEntitySolarGenerator) {
//            ((TileEntitySolarGenerator) tileEntity).setEnergyStored(message.energyStored);
//        }
//
//        return null;
//    }
//}
