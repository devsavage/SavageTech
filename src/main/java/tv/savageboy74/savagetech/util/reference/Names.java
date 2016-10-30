package tv.savageboy74.savagetech.util.reference;

/*
 * Names.java
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

import net.minecraft.block.SoundType;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;

public class Names
{
    public static final class Blocks
    {
        public static final String autoFertilizer = "autoFertilizer";
        public static final String upgradableFurnace = "upgradableFurnace";
        public static final String lootBox = "blockLootBox";
        public static final String soulFurnace = "soulFurnace";
        public static final String generator = "solarGenerator";
        public static final String oreRawSoulMatter = "rawSoulMatterOre";
        public static final String soulInfuser = "soulInfuser";
    }

    public static final class Items
    {
        public static final String emeraldRod = "emeraldRod";
        public static final String soulMatter = "soulMatter";
        public static final String rawSoulMatter = "rawSoulMatter";
        public static final String bonemeal = "bonemealBundle";
        public static final String soulSword = "soulSword";
        public static final String soulAxe = "soulAxe";
        public static final String soulPick = "soulPick";
        public static final String soulHammer = "soulHammer";
        public static final String soulShovel = "soulShovel";
        public static final String soulHoe = "soulHoe";
        public static final String lootBag = "lootBag";
        public static final String magnet = "magnet";
        public static final String soulEnergizer = "soulEnergizer";

        //Soul
        public static final String soulMatterHelmet = "soulMatterHelmet";
        public static final String soulMatterChest= "soulMatterChest";
        public static final String soulMatterLegs = "soulMatterLegs";
        public static final String soulMatterBoots = "soulMatterBoots";
    }

    public static final class NBT
    {
        public static final String ITEMS = "Items";
        public static final String UUID_MOST_SIG = "UUIDMostSig";
        public static final String UUID_LEAST_SIG = "UUIDLeastSig";
        public static final String OWNER = "Owner";
        public static final String OWNER_NAME = "OwnerName";
        public static final String OWNER_UUID_MOST_SIG = "OwnerUUIDMostSig";
        public static final String OWNER_UUID_LEAST_SIG = "OwnerUUIDLeastSig";
        public static final String CUSTOM_NAME = "CustomName";
        public static final String DIRECTION = "TEDirection";
        public static final String STATE = "TEState";
        public static final String SLOT = "Slot";
        public static final String DEVICE_COOK_TIME = "DeviceCookTime";
        public static final String FUEL_BURN_TIME = "FuelBurnTime";
        public static final String ITEM_COOK_TIME = "ItemCookTime";
        public static final String LOOT_BAG_GUI_OPEN = "LootBagGuiOpen";
        public static final String ENERGY = "STEnergy";
        public static final String CAPACITY = "STCapacity";
        public static final String MAX_TRANSFER_RECEIVE = "STMaxTransferReceive";
        public static final String MAX_TRANSFER_EXTRACT = "STMaxTransferExtract";
        public static final String MAXIMUM_ENERGY_GENERATION = "STMaxEnergyGeneration";
        public static final String ROTATION = "Rotation";
        public static final String ROTATION_PACKET = "r";
        public static final String OWNER_PACKET = "o";
    }

    public static final class Gui
    {
        public static final int FERTILIZER_GUI = 0;
        public static final int FURNACE_GUI = 1;
        public static final int LOOT_BAG_GUI = 2;
        public static final int LOOT_BOX_GUI = 3;
        public static final int SOLAR_GENERATOR_GUI = 4;
        public static final int SOUL_INFUSER_GUI = 5;
    }

    public static final class Containers
    {
        public static final String LOOT_BAG = "container." + ModReference.MOD_DOMAIN + Items.lootBag;

        public static final String VANILLA_INVENTORY = "container.inventory";
    }

    public static final class Sounds
    {
        public static final SoundEvent CHEST_OPEN = SoundEvents.BLOCK_CHEST_OPEN;
        public static final SoundEvent CHEST_CLOSE = SoundEvents.BLOCK_CHEST_CLOSE;
    }

    public static final class Keys
    {
        public static final String category = "key.categories.st2";
        public static final String flightSpeed = "key.flightSpeed";
    }
}
