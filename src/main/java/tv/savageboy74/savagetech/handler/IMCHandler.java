package tv.savageboy74.savagetech.handler;

/*
 * IMCHandler.java
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

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import tv.savageboy74.savagetech.inventory.crafting.FurnaceFuelRegistry;

public class IMCHandler
{
    public static void processIMC(FMLInterModComms.IMCEvent event) {
        for (FMLInterModComms.IMCMessage message : event.getMessages()) {
            if (message.key.equals(IMCKeys.FURNACE_FUEL)) {
                addFurnaceFuel(message);
            }
        }

    }

    private static void addFurnaceFuel(FMLInterModComms.IMCMessage message) {
        NBTTagCompound nbt = message.getNBTValue();
        if (nbt == null)
            return;

        if (!nbt.hasKey("fuel") || !nbt.hasKey("burnTime"))
            return;

        Object fuel = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("fuel"));
        if (fuel == null)
            fuel = nbt.getString("fuel");

        int burnTime = nbt.getInteger("burnTime");

        FurnaceFuelRegistry.INSTANCE.addFuel(fuel, burnTime);
    }

    public static class IMCKeys
    {
        public static final String FURNACE_FUEL = "furnaceFuel";
    }
}
