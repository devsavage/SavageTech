package tv.savageboy74.savagetech.proxy;

/*
 * ClientProxy.java
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

import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tv.savageboy74.savagetech.blocks.lootbox.TileEntityLootBox;
import tv.savageboy74.savagetech.client.init.BlockModels;
import tv.savageboy74.savagetech.client.init.ItemModels;
import tv.savageboy74.savagetech.client.render.BlockModelInventoryRenderer;
import tv.savageboy74.savagetech.client.render.tileentity.TileEntityLootBoxRenderer;
import tv.savageboy74.savagetech.client.settings.KeyBindings;
import tv.savageboy74.savagetech.client.settings.KeyInputEventHandler;
import tv.savageboy74.savagetech.handler.ClientHandler;
import tv.savageboy74.savagetech.handler.ConfigHandler;
import tv.savageboy74.savagetech.handler.EventHandler;
import tv.savageboy74.savagetech.init.ModBlocks;
import tv.savageboy74.savagetech.init.ModItems;
import tv.savageboy74.savagetech.util.reference.ModReference;
import tv.savageboy74.savagetech.world.gen.EndWorldGenerator;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy
{
    private ClientHandler clientHandler;

    @Override
    public void preInit() {
        super.preInit();

        clientHandler = new ClientHandler();
    }

    @Override
    public void init() {
        super.init();
        clientHandler.init();

        BlockModels.registerSpecial();
    }

    @Override
    public void initRenderers() {
        super.initRenderers();

        BlockModels.register();
        ItemModels.register();

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLootBox.class, new TileEntityLootBoxRenderer());
        TileEntityItemStackRenderer.instance = new BlockModelInventoryRenderer();
    }

    @Override
    public void postInit() {
        super.postInit();
    }

    @Override
    public void initKeyBindings() {
        super.initKeyBindings();
        ClientRegistry.registerKeyBinding(KeyBindings.flightSpeed);
    }

    @Override
    public void initEventHandlers() {
        super.initEventHandlers();
        MinecraftForge.EVENT_BUS.register(new KeyInputEventHandler());
        MinecraftForge.EVENT_BUS.register(new ConfigHandler());
        MinecraftForge.EVENT_BUS.register(new EventHandler());
    }
}
