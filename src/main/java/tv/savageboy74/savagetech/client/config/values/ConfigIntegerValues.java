package tv.savageboy74.savagetech.client.config.values;

/*
 * ConfigIntegerValues.java
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

import tv.savageboy74.savagetech.client.config.ConfigCategories;

public enum ConfigIntegerValues
{
    BONEMEAL_BUNDLE_MAX_USES("Bonemeal Bundle Uses", ConfigCategories.ITEMS, 256, 1, Integer.MAX_VALUE, "Change this value to set how many times the Bonemeal Bundle can be used. Requires Restart."),
    MAX_SOUL_MATTER_TOOL_LEVEL("Max Soul Matter Tool Level", ConfigCategories.ITEMS, 8, 2, Integer.MAX_VALUE, "This number handles the Soul Matter Tool max tool level. When the tool reaches this number, it will have max abilities."),
    MAGNET_AUTO_PULL("Magnetic Auto-Pull Distance", ConfigCategories.ITEMS, 5, 1, 15, "This value sets how far the Magnet can pull automatically (When activated)"),
    MAGNET_MANUAL_PULL("Magnetic Manual-Pull Distance", ConfigCategories.ITEMS, 5, 1, 15, "This value sets how far the Magnet can pull manually (When deactivated and holding right click)"),
    AUTO_FERTILIZER_RATE("Auto Fertilizer Rate", ConfigCategories.BLOCKS, 60, 20, 120, "This value sets how fast or slow the fertilizer will fertilize. Lower = faster");

    public final String name;
    public final String category;
    public final int defaultValue;
    public final int min;
    public final int max;
    public final String desc;

    public int currentValue;

    ConfigIntegerValues(String name, ConfigCategories category, int defaultValue, int min, int max, String desc) {
        this.name = name;
        this.category = category.name;
        this.defaultValue = defaultValue;
        this.min = min;
        this.max = max;
        this.desc = desc;
    }

    public int getValue(){
        return this.currentValue;
    }
}

