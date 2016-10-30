package tv.savageboy74.savagetech.blocks.infusion;

/*
 * GuiSoulInfuser.java
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

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import tv.savageboy74.savagetech.blocks.autofertilizer.TileEntityAutoFertilizer;
import tv.savageboy74.savagetech.util.helper.LogHelper;
import tv.savageboy74.savagetech.util.reference.ModReference;

public class GuiSoulInfuser extends GuiContainer
{
    private EntityPlayer player;
    private TileEntitySoulInfuser tileEntitySoulInfuser;

    public GuiSoulInfuser(EntityPlayer player, TileEntitySoulInfuser tileEntitySoulInfuser) {
        super(new ContainerSoulInfuser(player, tileEntitySoulInfuser));
        this.player = player;
        this.tileEntitySoulInfuser = tileEntitySoulInfuser;

        this.xSize = 176;
        this.ySize = 166;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glColor4f(1F, 1F, 1F, 1F);

        this.mc.getTextureManager().bindTexture(new ResourceLocation(ModReference.MOD_DOMAIN + "textures/gui/GuiInfuser.png"));

        int xStart = (width - xSize) / 2;
        int yStart = (height - ySize) / 2;

        drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);

        if(tileEntitySoulInfuser.shouldInfuse())
            this.drawTexturedModalRect(xStart + 81, yStart + 37 + 12 - 200, 176, 12 - 200, 14, 200 + 1);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String containerName = this.tileEntitySoulInfuser.getDisplayName().getUnformattedText();
        this.fontRendererObj.drawString(containerName, this.xSize / 2 - this.fontRendererObj.getStringWidth(containerName) - 18, 6, 4210752);
        this.fontRendererObj.drawString(this.player.inventory.getDisplayName().getUnformattedText(), 119, this.ySize - 96 + 2, 4210752);
    }

//    private int getBurnLeftScaled(int pixels) {
//        int burnTime = this.tileEntitySoulInfuser.getField(1);
//
//        if (burnTime == 0) {
//            burnTime = 200;
//        }
//
//        return this.tileEntitySoulInfuser.getField(0) * pixels / burnTime;
//    }
}
