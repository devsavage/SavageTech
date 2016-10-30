package tv.savageboy74.savagetech.items.soul.armor;

/*
 * ItemSoulArmor.java
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

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import tv.savageboy74.savagetech.SavageTech;
import tv.savageboy74.savagetech.init.ModItems;
import tv.savageboy74.savagetech.util.IKeyBound;
import tv.savageboy74.savagetech.util.helper.ItemHelper;
import tv.savageboy74.savagetech.util.reference.Key;
import tv.savageboy74.savagetech.util.reference.ModReference;

import java.util.List;

import static net.minecraft.inventory.EntityEquipmentSlot.*;

public class ItemSoulArmor extends ItemArmor implements IKeyBound
{
    //TODO: Implement ISpecialArmor and add another level to the armor for more abilities. Fix up the protection from mobs
    private static String[] types = {"helmet", "chest", "legs", "boots"};

    private boolean flySpeed = false;

    public ItemSoulArmor(EntityEquipmentSlot equipmentSlot) {
        super(ArmorMaterial.DIAMOND, 3, equipmentSlot);
        this.setCreativeTab(SavageTech.tabSavageTech);
        this.canRepair = true;
        this.setUnlocalizedName(ModReference.MOD_DOMAIN + "soulArmor.");
    }


    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        ItemStack boots = player.getItemStackFromSlot(FEET);
        ItemStack legs = player.getItemStackFromSlot(LEGS);
        ItemStack chest = player.getItemStackFromSlot(CHEST);
        ItemStack helm = player.getItemStackFromSlot(HEAD);

        if(boots != null && legs != null && chest != null && helm != null) {
            if(boots.getItem() instanceof ItemSoulArmor && legs.getItem() instanceof ItemSoulArmor && chest.getItem() instanceof ItemSoulArmor && helm.getItem() instanceof ItemSoulArmor) {
                //Chestplate Abilities
                player.capabilities.allowFlying = true;
                player.sendPlayerAbilities();

                //Boots Abilities
                player.fallDistance = 0.0F;

                //Legging Abilities
                player.stepHeight = 1.0F;

                //Helmet Abilities
                player.setAir(300);
            } else {
                resetAbilities(player);
            }
        } else {
            resetAbilities(player);
        }

        //LogHelper.info(types[armorType]);
    }

    private void resetAbilities(EntityPlayer player) {
        player.stepHeight = 0.5F;
        player.fallDistance = 0.5F;
        player.capabilities.isFlying = false;
        player.capabilities.setFlySpeed(0.05F);
        player.sendPlayerAbilities();
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        if(this.armorType == HEAD)
            tooltip.add("- Underwater Breathing");
        if(this.armorType == CHEST) {
            tooltip.add("- Flying");
            TextFormatting color = playerIn.capabilities.getFlySpeed() > 0.05F ? TextFormatting.GREEN : TextFormatting.RED;
            tooltip.add("- Fly Speed: " + color + playerIn.capabilities.getFlySpeed());
        }

        if(this.armorType == LEGS)
            tooltip.add("- Step Assist");

        if(this.armorType == FEET)
            tooltip.add("- No Fall Damage");

    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        if (this == ModItems.soulChest || this == ModItems.soulHelmet || this == ModItems.soulBoots) {
            return ModReference.MOD_DOMAIN + "textures/models/armor/soulArmor_layer_1.png";
        }

        if (this == ModItems.soulLegs) {
            return ModReference.MOD_DOMAIN + "textures/models/armor/soulArmor_layer_2.png";
        } else {
            return null;
        }
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return this == ModItems.soulHelmet && repair.getItem() == ModItems.soulMatter ||
                   this == ModItems.soulChest && repair.getItem() == ModItems.soulMatter ||
                   this == ModItems.soulLegs && repair.getItem() == ModItems.soulMatter ||
                   this == ModItems.soulBoots && repair.getItem() == ModItems.soulMatter;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        //Temp
        switch (armorType) {
            case HEAD:
                return super.getUnlocalizedName(stack) + types[0];
            case CHEST:
                return super.getUnlocalizedName(stack) + types[1];
            case LEGS:
                return super.getUnlocalizedName(stack) + types[2];
            case FEET:
                return super.getUnlocalizedName(stack) + types[3];
        }

        return super.getUnlocalizedName(stack);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }

    @Override
    public void doKeyBindingAction(EntityPlayer player, ItemStack itemStack, Key key) {
        ItemStack boots = player.getItemStackFromSlot(FEET);
        ItemStack legs = player.getItemStackFromSlot(LEGS);
        ItemStack chest = player.getItemStackFromSlot(CHEST);
        ItemStack helm = player.getItemStackFromSlot(HEAD);

        if(ItemHelper.equalsIgnoreStackSize(itemStack, chest)) {
            if(key == Key.flightSpeed) {
                if(boots != null && legs != null && helm != null) {
                    if(boots.getItem() instanceof ItemSoulArmor && legs.getItem() instanceof ItemSoulArmor && chest.getItem() instanceof ItemSoulArmor && helm.getItem() instanceof ItemSoulArmor) {
                        if(!flySpeed) {
                            player.capabilities.setFlySpeed(0.15F);
                            flySpeed = true;
                            player.addChatComponentMessage(new TextComponentString("Fly Speed" + TextFormatting.DARK_GREEN + " Enabled" + TextFormatting.RESET + " - Speed: " + TextFormatting.GREEN + Float.toString(player.capabilities.getFlySpeed())));
                        } else {
                            player.capabilities.setFlySpeed(0.05F);
                            flySpeed = false;
                            player.addChatComponentMessage(new TextComponentString("Fly Speed" + TextFormatting.DARK_RED + " Disabled" + TextFormatting.RESET + " - Speed: " + TextFormatting.RED + Float.toString(player.capabilities.getFlySpeed())));
                        }
                    }
                }
            }
        }
    }
}
