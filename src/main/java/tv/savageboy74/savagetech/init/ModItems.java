package tv.savageboy74.savagetech.init;

/*
 * ModItems.java
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

import com.google.gson.JsonObject;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import tv.savageboy74.savagetech.SavageTech;
import tv.savageboy74.savagetech.client.config.values.ConfigBlacklist;
import tv.savageboy74.savagetech.items.*;
import tv.savageboy74.savagetech.items.lootbag.ItemLootBag;
import tv.savageboy74.savagetech.items.soul.armor.ItemSoulArmor;
import tv.savageboy74.savagetech.items.soul.tools.*;
import tv.savageboy74.savagetech.util.helper.LogHelper;
import tv.savageboy74.savagetech.util.reference.ModReference;
import tv.savageboy74.savagetech.util.reference.Names;

import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;

public class ModItems
{
    public static final Item emeraldRod = new ItemEmeraldRod();
    public static final Item soulMatter = new ItemSoulMatter();
    public static final Item rawSoulMatter = new ItemRawSoulMatter();
    public static final Item bonemealBundle = new ItemBonemealBundle();
    public static final Item lootBag = new ItemLootBag();
    public static final Item magnet = new ItemMagnet();
    public static final Item soulEnergizer = new ItemSoulEnergizer();

    public static final Item soulHelmet = new ItemSoulArmor(EntityEquipmentSlot.HEAD);
    public static final Item soulChest = new ItemSoulArmor(EntityEquipmentSlot.CHEST);
    public static final Item soulLegs = new ItemSoulArmor(EntityEquipmentSlot.LEGS);
    public static final Item soulBoots = new ItemSoulArmor(EntityEquipmentSlot.FEET);

    public static final Item soulSword = new ItemSoulMatterSword();
    public static final Item soulShovel = new ItemSoulMatterShovel();
    public static final Item soulPick = new ItemSoulMatterPickaxe();
    public static final Item soulHammer = new ItemSoulMatterHammer();
    public static final Item soulHoe = new ItemSoulMatterHoe();
    public static final Item soulAxe = new ItemSoulMatterAxe();

    public static void init() {
        registerItem(emeraldRod, Names.Items.emeraldRod);
        registerItem(soulMatter, Names.Items.soulMatter);
        registerItem(rawSoulMatter, Names.Items.rawSoulMatter);
        registerItem(bonemealBundle, Names.Items.bonemeal);
        registerItem(lootBag, Names.Items.lootBag);
        registerItem(magnet, Names.Items.magnet);
        registerItem(soulEnergizer, Names.Items.soulEnergizer);

        registerItem(soulHelmet, Names.Items.soulMatterHelmet);
        registerItem(soulChest, Names.Items.soulMatterChest);
        registerItem(soulLegs, Names.Items.soulMatterLegs);
        registerItem(soulBoots, Names.Items.soulMatterBoots);

        registerItem(soulSword, Names.Items.soulSword);
        registerItem(soulShovel, Names.Items.soulShovel);
        registerItem(soulPick, Names.Items.soulPick);
        registerItem(soulHammer, Names.Items.soulHammer);
        registerItem(soulHoe, Names.Items.soulHoe);
        registerItem(soulAxe, Names.Items.soulAxe);
    }

    private static void registerItem(Item item, String name) {
        if(Arrays.asList(ConfigBlacklist.ITEM_BLACKLIST.getCurrentValues()).contains(name))
            return;

        item.setRegistryName(new ResourceLocation(ModReference.MOD_ID, name));
        GameRegistry.register(item);

        if(SavageTech.developmentEnvironment)
            createModelFile(name);
    }

    private static void createModelFile(String unlocalizedName) {
        JsonObject obj = new JsonObject();
        JsonObject layers = new JsonObject();

        obj.addProperty("parent", "savagetech:item/ItemModelBase");
        layers.addProperty("layer0", "savagetech:items/" + unlocalizedName);
        obj.add("textures", layers);

        try {
            File file = new File("../src/main/resources/assets/savagetech/models/item/" + unlocalizedName + ".json");

            if(file.exists())
                file.delete();

            file.getParentFile().mkdir();
            file.createNewFile();

            FileWriter writer = new FileWriter(file);
            writer.write(obj.toString());

            writer.flush();
            writer.close();
        } catch(Exception e) {
            LogHelper.error("Error Creating Model File: " + e.getMessage());
        }
    }
}
