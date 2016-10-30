package tv.savageboy74.savagetech.handler;

/*
 * GuiHandler.java
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

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import tv.savageboy74.savagetech.blocks.autofertilizer.ContainerAutoFertilizer;
import tv.savageboy74.savagetech.blocks.autofertilizer.GuiAutoFertilizer;
import tv.savageboy74.savagetech.blocks.autofertilizer.TileEntityAutoFertilizer;
import tv.savageboy74.savagetech.blocks.infusion.ContainerSoulInfuser;
import tv.savageboy74.savagetech.blocks.infusion.GuiSoulInfuser;
import tv.savageboy74.savagetech.blocks.infusion.TileEntitySoulInfuser;
import tv.savageboy74.savagetech.blocks.lootbox.ContainerLootBox;
import tv.savageboy74.savagetech.blocks.lootbox.GuiLootBox;
import tv.savageboy74.savagetech.blocks.lootbox.TileEntityLootBox;
import tv.savageboy74.savagetech.items.lootbag.ContainerLootBag;
import tv.savageboy74.savagetech.items.lootbag.GuiLootBag;
import tv.savageboy74.savagetech.items.lootbag.InventoryLootBag;
import tv.savageboy74.savagetech.util.reference.Names;

public class GuiHandler implements IGuiHandler
{
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);

        switch (ID) {
            case Names.Gui.FERTILIZER_GUI:
                return new ContainerAutoFertilizer(player.inventory, (TileEntityAutoFertilizer) world.getTileEntity(pos));
            case Names.Gui.LOOT_BAG_GUI:
                return new ContainerLootBag(player, new InventoryLootBag(player.getHeldItemMainhand()));
            case Names.Gui.LOOT_BOX_GUI:
                return new ContainerLootBox(player, (TileEntityLootBox) world.getTileEntity(pos));
            case Names.Gui.SOUL_INFUSER_GUI:
                return new ContainerSoulInfuser(player, (TileEntitySoulInfuser) world.getTileEntity(pos));
//            case Names.Gui.SOLAR_GENERATOR_GUI:
//                return new ContainerSolarGenerator(player, (TileEntitySolarGenerator) world.getTileEntity(pos));
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (world instanceof WorldClient) {
            BlockPos pos = new BlockPos(x, y, z);

            switch (ID) {
                case Names.Gui.FERTILIZER_GUI:
                    return new GuiAutoFertilizer(player.inventory, (TileEntityAutoFertilizer) world.getTileEntity(pos));
                case Names.Gui.LOOT_BAG_GUI:
                    return new GuiLootBag(player, new InventoryLootBag(player.getHeldItemMainhand()));
                case Names.Gui.LOOT_BOX_GUI:
                    return new GuiLootBox(player, (TileEntityLootBox) world.getTileEntity(pos));
                case Names.Gui.SOUL_INFUSER_GUI:
                    return new GuiSoulInfuser(player, (TileEntitySoulInfuser) world.getTileEntity(pos));
//                case Names.Gui.SOLAR_GENERATOR_GUI:
//                    return new GuiSolarGenerator(player, (TileEntitySolarGenerator) world.getTileEntity(pos));
            }
        }

        return null;
    }
}
