package tv.savageboy74.savagetech.client.init;

/*
 * ItemModels.java
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

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import tv.savageboy74.savagetech.client.config.values.ConfigBlacklist;
import tv.savageboy74.savagetech.init.ModItems;
import tv.savageboy74.savagetech.util.reference.ModReference;
import tv.savageboy74.savagetech.util.reference.Names;

import java.util.Arrays;

public class ItemModels
{
    public static void register() {
        registerItemModel(ModItems.rawSoulMatter, Names.Items.rawSoulMatter);
        registerItemModel(ModItems.emeraldRod, Names.Items.emeraldRod);
        registerItemModel(ModItems.bonemealBundle, Names.Items.bonemeal);
        registerItemModel(ModItems.magnet, Names.Items.magnet);
        registerItemModel(ModItems.soulEnergizer, Names.Items.soulEnergizer);
        registerItemModel(ModItems.soulMatter, Names.Items.soulMatter);
        registerItemModel(ModItems.lootBag, Names.Items.lootBag);
        registerItemModel(ModItems.soulHelmet, Names.Items.soulMatterHelmet);
        registerItemModel(ModItems.soulChest, Names.Items.soulMatterChest);
        registerItemModel(ModItems.soulLegs, Names.Items.soulMatterLegs);
        registerItemModel(ModItems.soulBoots, Names.Items.soulMatterBoots);
        registerItemModel(ModItems.soulSword, Names.Items.soulSword);
        registerItemModel(ModItems.soulHammer, Names.Items.soulHammer);
        registerItemModel(ModItems.soulPick, Names.Items.soulPick);
        registerItemModel(ModItems.soulShovel, Names.Items.soulShovel);
        registerItemModel(ModItems.soulAxe, Names.Items.soulAxe);
        registerItemModel(ModItems.soulHoe, Names.Items.soulHoe);
    }

    private static void registerItemModelForAllVariants(Item item, String resourceName, ItemMeshDefinition itemMeshDefinition) {
        if(Arrays.asList(ConfigBlacklist.ITEM_BLACKLIST.getCurrentValues()).contains(resourceName))
            return;

        resourceName = ModReference.MOD_DOMAIN + resourceName;

        ModelBakery.registerItemVariants(item, new ResourceLocation(resourceName));

        ModelLoader.setCustomMeshDefinition(item, itemMeshDefinition);
    }

    private static void registerItemModel(Item item, String resourceName) {
        if(Arrays.asList(ConfigBlacklist.ITEM_BLACKLIST.getCurrentValues()).contains(resourceName))
            return;

        registerItemModel(item, resourceName, 0, false);
    }

    private static void registerItemModel(Item item, String resourceName, int meta, boolean hasSubtypes) {
        if(hasSubtypes) {
            resourceName = resourceName + "_" + meta;
        }

        resourceName = ModReference.MOD_DOMAIN + resourceName;

        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(resourceName, "inventory"));
    }
}
