package tv.savageboy74.savagetech.inventory.crafting;

/*
 * FurnaceFuelRegistry.java
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
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import tv.savageboy74.savagetech.util.helper.ItemHelper;
import tv.savageboy74.savagetech.util.xml.XMLBuilder;
import tv.savageboy74.savagetech.util.xml.XMLNode;
import tv.savageboy74.savagetech.util.xml.XMLParser;

import java.util.List;

public class FurnaceFuelRegistry extends RecipeRegistry<FurnaceFuelRegistry.FuelEntry>
{
    public static final FurnaceFuelRegistry INSTANCE = new FurnaceFuelRegistry();

    FurnaceFuelRegistry() {
        super("UpgradableFurnaceFuels");
    }

    @Override
    protected XMLNode toXML(FuelEntry recipe) {
        XMLBuilder builder = new XMLBuilder("entry");

        builder.makeEntry("fuel", recipe.fuel);
        builder.makeEntry("burnTime", recipe.burnTime);

        return builder.toNode();
    }

    @Override
    protected FuelEntry makeRecipe(XMLNode node) {
        return new FuelEntry(XMLParser.parseNode(node.getNode("fuel")), Integer.parseInt(node.getNode("burnTime").getValue()));
    }

    @Override
    protected void addDefaultRecipes() {
        addFuel(new ItemStack(Items.COAL, 1, 0), 1600);
        addFuel(new ItemStack(Items.COAL, 1, 1), 1600);
        addFuel(new ItemStack(Blocks.LOG, 1, 0), 300);
        addFuel(new ItemStack(Blocks.LOG2, 1, 0), 300);
        addFuel(new ItemStack(Blocks.COAL_BLOCK), 16000);
        addFuel(new ItemStack(Items.LAVA_BUCKET), 20000);
        addFuel(new ItemStack(Items.STICK), 100);
        addFuel(new ItemStack(Items.BLAZE_ROD), 2400);
    }

    public int getBurnTime(ItemStack stack) {
        if (stack == null)
            return 0;
        for (FuelEntry entry : getRecipes())
            if (areStacksTheSame(entry.fuel, stack))
                return entry.burnTime;

        return 0;
    }

    public void addFuel(Object fuel, int burnTime) {
        addRecipe(new FuelEntry(fuel, burnTime));
    }

    public static class FuelEntry
    {

        final Object fuel;
        final int burnTime;

        FuelEntry(Object fuel, int burnTime) {
            if (fuel instanceof Item)
                this.fuel = new ItemStack((Item) fuel);
            else if (fuel instanceof Block)
                this.fuel = new ItemStack((Block) fuel);
            else
                this.fuel = fuel;

            this.burnTime = burnTime;
        }

        public Object getFuel() {
            return fuel;
        }
    }

    private boolean areStacksTheSame(Object obj, ItemStack target) {
        if (obj instanceof ItemStack)
            return ItemHelper.equalsIgnoreStackSize((ItemStack) obj, target);
        else if (obj instanceof String) {
            List<ItemStack> list = OreDictionary.getOres((String) obj);
            for (ItemStack stack : list)
                if (ItemHelper.equalsIgnoreStackSize(stack, target))
                    return true;
        }

        return false;
    }
}
