package tv.savageboy74.savagetech;

/*
 * SavageTech.java
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

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import tv.savageboy74.savagetech.client.config.values.ConfigBooleanValues;
import tv.savageboy74.savagetech.handler.*;
import tv.savageboy74.savagetech.init.ModBlocks;
import tv.savageboy74.savagetech.init.ModItems;
import tv.savageboy74.savagetech.init.ModRecipes;
import tv.savageboy74.savagetech.init.crafting.RecipeSoulTools;
import tv.savageboy74.savagetech.network.handler.ServerPacketHandler;
import tv.savageboy74.savagetech.network.messages.MessageKeyPressed;
import tv.savageboy74.savagetech.proxy.CommonProxy;
import tv.savageboy74.savagetech.util.UpdateChecker;
import tv.savageboy74.savagetech.util.helper.LogHelper;
import tv.savageboy74.savagetech.util.reference.ModReference;
import tv.savageboy74.savagetech.world.gen.EndWorldGenerator;

import java.io.File;
import java.io.IOException;

@Mod(modid = ModReference.MOD_ID, name = ModReference.MOD_NAME, version = ModReference.MOD_VERSION, guiFactory = ModReference.MOD_GUI_FACTORY)
public class SavageTech
{
    public static boolean developmentEnvironment = (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");

    public static SimpleNetworkWrapper networkWrapper = null;

    private File configDirectory;

    public static CreativeTabs tabSavageTech = new CreativeTabs(ModReference.MOD_ID + ".creativeTab")
    {
        @Override
        public Item getTabIconItem() {
            return ModItems.soulMatter;
        }
    };

    @SidedProxy(serverSide = ModReference.MOD_COMMON_PROXY, clientSide = ModReference.MOD_CLIENT_PROXY)
    public static CommonProxy proxy;

    @Mod.Instance(ModReference.MOD_ID)
    public static SavageTech instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        configDirectory = new File(event.getModConfigurationDirectory(), "SavageTech");
        ConfigHandler.init(new File(getConfigDirectory(), "SavageTech.cfg"));

        networkWrapper = new SimpleNetworkWrapper(ModReference.MOD_ID);
        networkWrapper.registerMessage(ServerPacketHandler.class, ServerPacketHandler.SavageTechMessage.class, 0, Side.SERVER);
        networkWrapper.registerMessage(MessageKeyPressed.MessageHandler.class, MessageKeyPressed.class, 1, Side.SERVER);
        //networkWrapper.registerMessage(MessageSolarGenerator.class, MessageSolarGenerator.class, 2, Side.CLIENT);

        ModBlocks.init();
        ModBlocks.initTileEntities();
        ModItems.init();

        GameRegistry.registerWorldGenerator(new EndWorldGenerator(), 0);

        proxy.preInit();

        MinecraftForge.EVENT_BUS.register(this);

        if(ConfigBooleanValues.ALLOW_UPDATE_CHECKING.isEnabled()) {
            try {
                LogHelper.info("Checking for updates... (You can disable this in the config)", true);
                UpdateChecker.checkForUpdate();
                if(UpdateChecker.status == UpdateChecker.Status.OUTDATED) {
                    LogHelper.info("A new version of SavageTech is available!", true);
                } else if(UpdateChecker.status == UpdateChecker.Status.UP_TO_DATE) {
                    LogHelper.info("You are running the latest version of SavageTech!", true);
                } else if(UpdateChecker.status == UpdateChecker.Status.FAILED) {
                    LogHelper.info("The version check failed, this may be due to SavageTech not supporting your current Forge version.", true);
                } else if(UpdateChecker.status == UpdateChecker.Status.AHEAD) {
                    LogHelper.info("You are running a newer version of SavageTech!", true);
                }
            } catch (IOException e) {
                LogHelper.error("Error checking for update!", true);
                System.out.println(e);
            }
        }

        LogHelper.info("Pre-Initialization Complete", true);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ModRecipes.init();
        (new RecipeSoulTools()).addRecipes();

        NetworkRegistry.INSTANCE.registerGuiHandler(SavageTech.instance, new GuiHandler());

        proxy.init();

        LogHelper.info("Initialization Complete", true);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit();

        CraftingHandler.initRegistries();

        LogHelper.info("Post-Initialization Complete", true);
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        CommandHandler.initCommands(event);
        LogHelper.info("Server Starting Event Complete", true);
    }

    @Mod.EventHandler
    public void processIMCRequests(FMLInterModComms.IMCEvent event) {
        IMCHandler.processIMC(event);
    }

    private File getConfigDirectory() {
        return this.configDirectory;
    }

    @SubscribeEvent
    public void updateCheck(PlayerEvent.PlayerLoggedInEvent event) {
        if(UpdateChecker.status == UpdateChecker.Status.OUTDATED) {
            String outdatedText = TextFormatting.AQUA + "[SavageTech] " + TextFormatting.RESET + "is " + TextFormatting.RED + "outdated! " + TextFormatting.RESET + "Newest Version: " + TextFormatting.GOLD + UpdateChecker.newestVersion + TextFormatting.RESET + " Current Version: " + TextFormatting.RED + ModReference.MOD_VERSION;
            String downloadText = "Download";
            String update_url = "https://savageboy74.tv/mods/savagetech/download/" + UpdateChecker.newestVersion;

            event.player.addChatComponentMessage(ITextComponent.Serializer.jsonToComponent("[{\"text\":\"" + outdatedText + "\"}," + "{\"text\":\" " + TextFormatting.WHITE + "[" + TextFormatting.GREEN + downloadText + TextFormatting.WHITE + "]\"," + "\"color\":\"green\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":" + "{\"text\":\"Click to download the latest version\",\"color\":\"yellow\"}}," + "\"clickEvent\":{\"action\":\"open_url\",\"value\":\"" + update_url + "\"}}]"));
        }
    }
}
