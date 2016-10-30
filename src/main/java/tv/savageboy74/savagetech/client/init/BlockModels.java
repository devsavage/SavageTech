package tv.savageboy74.savagetech.client.init;

/*
 * BlockModels.java
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
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.FMLCommonHandler;
import tv.savageboy74.savagetech.client.config.values.ConfigBlacklist;
import tv.savageboy74.savagetech.init.ModBlocks;
import tv.savageboy74.savagetech.util.reference.ModReference;
import tv.savageboy74.savagetech.util.reference.Names;

import java.util.Arrays;

public class BlockModels
{
    public static void register() {
        registerBlockModel(ModBlocks.blockAutoFertilizer, Names.Blocks.autoFertilizer);
        registerBlockModel(ModBlocks.blockOreRawSoulMatter, Names.Blocks.oreRawSoulMatter);
        registerBlockModel(ModBlocks.blockSoulInfuser, Names.Blocks.soulInfuser);
    }

    public static void registerSpecial() {
        registerInternalModel(ModBlocks.blockLootBox, Names.Blocks.lootBox);
    }

    private static void registerBlockModel(Block block, String resourceName) {
        if(Arrays.asList(ConfigBlacklist.BLOCK_BLACKLIST.getCurrentValues()).contains(resourceName))
            return;

        ResourceLocation resourceLocation = new ResourceLocation(ModReference.MOD_DOMAIN + resourceName);

        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(resourceLocation, "inventory"));
    }

    private static void registerInternalModel(Block block, String resourceName) {
        if(FMLCommonHandler.instance().getEffectiveSide().isClient())
            Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getModelManager().getBlockModelShapes().registerBuiltInBlocks(block);

        ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
        mesher.register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(ModReference.MOD_DOMAIN + resourceName, "inventory"));
    }
}
