package tv.savageboy74.savagetech.util;

/*
 * UpdateChecker.java
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

import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import net.minecraftforge.common.MinecraftForge;
import tv.savageboy74.savagetech.SavageTech;
import tv.savageboy74.savagetech.util.helper.LogHelper;
import tv.savageboy74.savagetech.util.reference.ModReference;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class UpdateChecker
{

    public static Status status = Status.PENDING;
    public static String newestVersion = "";

    public static void checkForUpdate() throws IOException {
        try {
            getNewestVersion();
        } catch (IOException e) {
            LogHelper.error("Error checking for update!", true);
            System.out.println(e);
        }
    }

    public static void getNewestVersion() throws IOException {
        URL url = new URL("https://api.savageboy74.tv/v1/minecraft/mods/savagetech.json");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 Safari/535.11");

        InputStream stream = connection.getInputStream();

        String data = new String(ByteStreams.toByteArray(stream));

        connection.disconnect();

        Map<String, Object> json = new Gson().fromJson(data, Map.class);
        Map<String, String> versions = (Map<String, String>)json.get("versions");
        String latestVersion = versions.get(MinecraftForge.MC_VERSION + "-latest");
        String versionForNewest = versions.get(MinecraftForge.MC_VERSION + "-full");

        if(latestVersion != null) {
            String currentVersion = SavageTech.developmentEnvironment ? getStrippedVersion(MinecraftForge.MC_VERSION + "-1.0.0") : getStrippedVersion(ModReference.MOD_VERSION);
            int currentVersionNumber = convertToNumber(currentVersion);
            int latestVersionNumber = convertToNumber(latestVersion);

            if(latestVersionNumber > currentVersionNumber) {
                status = Status.OUTDATED;
            } else if(latestVersionNumber == currentVersionNumber) {
                status = Status.UP_TO_DATE;
            } else if(currentVersionNumber > latestVersionNumber) {
                status = Status.AHEAD;
            }

            newestVersion = versionForNewest;
        } else {
            status = Status.FAILED;
        }
    }

    private static String getStrippedVersion(String string) {
        return string.split("-")[1];
    }

    private static int convertToNumber(String string) {
        return Integer.parseInt(string.replace(".", ""));
    }

    public static enum Status
    {
        PENDING,
        FAILED,
        UP_TO_DATE,
        OUTDATED,
        AHEAD
    }
}
