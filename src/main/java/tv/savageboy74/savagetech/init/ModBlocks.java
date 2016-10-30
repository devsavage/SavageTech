package tv.savageboy74.savagetech.init;

/*
 * ModBlocks.java
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

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import tv.savageboy74.savagetech.blocks.autofertilizer.BlockAutoFertilizer;
import tv.savageboy74.savagetech.blocks.autofertilizer.TileEntityAutoFertilizer;
import tv.savageboy74.savagetech.blocks.base.STItemBlock;
import tv.savageboy74.savagetech.blocks.infusion.BlockSoulInfuser;
import tv.savageboy74.savagetech.blocks.infusion.TileEntitySoulInfuser;
import tv.savageboy74.savagetech.blocks.lootbox.BlockLootBox;
import tv.savageboy74.savagetech.blocks.lootbox.TileEntityLootBox;
import tv.savageboy74.savagetech.blocks.ores.BlockOreRawSoulMatter;
import tv.savageboy74.savagetech.client.config.values.ConfigBlacklist;
import tv.savageboy74.savagetech.util.reference.ModReference;
import tv.savageboy74.savagetech.util.reference.Names;

import java.util.Arrays;

public class ModBlocks
{
    public static final Block blockAutoFertilizer = new BlockAutoFertilizer();
    public static final Block blockLootBox = new BlockLootBox();
    public static final Block blockOreRawSoulMatter = new BlockOreRawSoulMatter();
    public static final Block blockSoulInfuser = new BlockSoulInfuser();


    public static void init() {
        registerBlock(blockAutoFertilizer, new STItemBlock(blockAutoFertilizer), Names.Blocks.autoFertilizer);
        registerBlock(blockLootBox, new STItemBlock(blockLootBox), Names.Blocks.lootBox);
        registerBlock(blockOreRawSoulMatter, new STItemBlock(blockOreRawSoulMatter), Names.Blocks.oreRawSoulMatter);
        registerBlock(blockSoulInfuser, new STItemBlock(blockSoulInfuser), Names.Blocks.soulInfuser);
    }

    public static void initTileEntities() {
        registerTileEntity(TileEntityAutoFertilizer.class, Names.Blocks.autoFertilizer);
        registerTileEntity(TileEntityLootBox.class, Names.Blocks.lootBox);
        registerTileEntity(TileEntitySoulInfuser.class, Names.Blocks.soulInfuser);
    }

    private static void registerTileEntity(Class clazz, String name) {
        if(Arrays.asList(ConfigBlacklist.BLOCK_BLACKLIST.getCurrentValues()).contains(name))
            return;

        GameRegistry.registerTileEntity(clazz, ModReference.MOD_ID + "." + name);
    }

    private static void registerBlock(Block block, ItemBlock itemBlock, String name) {
        if(Arrays.asList(ConfigBlacklist.BLOCK_BLACKLIST.getCurrentValues()).contains(name))
            return;

        block.setRegistryName(new ResourceLocation(ModReference.MOD_ID, name));
        GameRegistry.register(block);
        GameRegistry.register(itemBlock.setRegistryName(block.getRegistryName()));
    }

    private static void registerItemBlock(Block block, ItemBlock itemBlock, String name) {
        if(Arrays.asList(ConfigBlacklist.BLOCK_BLACKLIST.getCurrentValues()).contains(name))
            return;

        block.setRegistryName(new ResourceLocation(ModReference.MOD_ID, name));
        GameRegistry.register(block);
        GameRegistry.register(itemBlock.setRegistryName(block.getRegistryName()));
    }
}
