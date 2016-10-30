package tv.savageboy74.savagetech.blocks.autofertilizer;

/*
 * GuiAutoFertilizer.java
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

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import tv.savageboy74.savagetech.network.handler.PacketHandler;
import tv.savageboy74.savagetech.util.reference.ModReference;

import java.io.IOException;

public class GuiAutoFertilizer extends GuiContainer
{
    private TileEntityAutoFertilizer tileEntityAutoFertilizer;

    public GuiAutoFertilizer(InventoryPlayer inventoryPlayer, TileEntityAutoFertilizer tileEntityAutoFertilizer) {
        super(new ContainerAutoFertilizer(inventoryPlayer, tileEntityAutoFertilizer));
        this.tileEntityAutoFertilizer = tileEntityAutoFertilizer;

        this.xSize = 176;
        this.ySize = 166;
    }

    @Override
    public void initGui() {
        super.initGui();

        FontRenderer fontRenderer = this.fontRendererObj;

        int uX0 = guiLeft + 105;
        int uY0 = guiTop + 33;

        int dX0 = guiLeft + 50;
        int dY0 = guiTop + 33;

        int btnSizeW = 20;
        int btnSizeH = 20;


        GuiButton buttonUp = new GuiButton(1, uX0, uY0, btnSizeW, btnSizeH, "+");
        GuiButton buttonDown = new GuiButton(2, dX0, dY0, btnSizeW, btnSizeH, "-");
        buttonList.add(buttonUp);
        buttonList.add(buttonDown);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 1:
                PacketHandler.sendToServer(PacketHandler.AutoFertilizerButton, tileEntityAutoFertilizer, tileEntityAutoFertilizer.getArea() + 1, true);
                break;
            case 2:
                PacketHandler.sendToServer(PacketHandler.AutoFertilizerButton, tileEntityAutoFertilizer, tileEntityAutoFertilizer.getArea() - 1, true);
                break;
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glColor4f(1F, 1F, 1F, 1F);

        this.mc.getTextureManager().bindTexture(new ResourceLocation(ModReference.MOD_DOMAIN + "textures/gui/GuiFertilizer.png"));

        int xStart = (width - xSize) / 2;
        int yStart = (height - ySize) / 2;

        drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String containerName = this.tileEntityAutoFertilizer.getName();
        short area = tileEntityAutoFertilizer.getArea();

        this.fontRendererObj.drawString(area + "x" + area, 79, 58, 4210752);
        this.fontRendererObj.drawString(containerName, this.xSize / 2 - this.fontRendererObj.getStringWidth(containerName) / 2, 6, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
    }
}
