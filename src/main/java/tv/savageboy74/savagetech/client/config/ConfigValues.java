package tv.savageboy74.savagetech.client.config;

/*
 * ConfigValues.java
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

import net.minecraftforge.common.config.Configuration;
import tv.savageboy74.savagetech.client.config.values.ConfigBlacklist;
import tv.savageboy74.savagetech.client.config.values.ConfigBooleanValues;
import tv.savageboy74.savagetech.client.config.values.ConfigIntegerValues;
import tv.savageboy74.savagetech.client.config.values.ConfigStringListValues;

public class ConfigValues
{
    public static ConfigIntegerValues[] integerConfig = ConfigIntegerValues.values();
    public static ConfigBooleanValues[] booleanConfig = ConfigBooleanValues.values();
    public static ConfigBlacklist[] blacklistConfig = ConfigBlacklist.values();
    //public static ConfigStringListValues[] stringListConfig = ConfigStringListValues.values();

    public static void initConfigValues(Configuration config) {
        for(ConfigIntegerValues currentConf : integerConfig) {
            currentConf.currentValue = config.get(currentConf.category, currentConf.name, currentConf.defaultValue, currentConf.desc, currentConf.min, currentConf.max).getInt();
        }

        for(ConfigBooleanValues currentConf : booleanConfig) {
            currentConf.currentValue = config.get(currentConf.category, currentConf.name, currentConf.defaultValue, currentConf.desc).getBoolean();
        }

        for(ConfigBlacklist currentConf : blacklistConfig) {
            currentConf.currentValues = config.getStringList(currentConf.name, currentConf.category, currentConf.defaultValues, currentConf.desc);
        }

//        for(ConfigStringListValues currentConf : stringListConfig) {
//            currentConf.currentValues = config.getStringList(currentConf.name, currentConf.category, currentConf.defaultValues, currentConf.desc);
//        }
    }
}