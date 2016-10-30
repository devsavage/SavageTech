package tv.savageboy74.savagetech.blocks.solargenerator;

/*
 * GuiSolarGenerator.java
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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import tv.savageboy74.savagetech.util.reference.ModReference;

import java.text.NumberFormat;

//@SideOnly(Side.CLIENT)
//public class GuiSolarGenerator extends GuiContainer
//{
//    private TileEntitySolarGenerator tileGenerator;
//    private String displayName;
//
//    public GuiSolarGenerator(EntityPlayer player, TileEntitySolarGenerator generator) {
//        super(new ContainerSolarGenerator(player, generator));
//        this.tileGenerator = generator;
//        this.xSize = 176;
//        this.ySize = 166;
//        initDisplay();
//    }
//
//    private void initDisplay() {
//        this.displayName = "Solar Generator";
//    }
//
//    @Override
//    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
//        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
//
//        this.mc.getTextureManager().bindTexture(new ResourceLocation(ModReference.MOD_DOMAIN + "textures/gui/GuiGenerator.png"));
//
//        int xStart = (width - xSize) / 2;
//        int yStart = (height - ySize) / 2;
//        this.drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
//
//        int bufferScale = this.tileGenerator.getEnergyScaled(76);
//        drawTexturedModalRect(xStart + 154, yStart + 80 - bufferScale + 1, 0, 242 - bufferScale, 12, bufferScale);
//    }
//
//    @Override
//    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
//        String storedEnergy = NumberFormat.getIntegerInstance().format(tileGenerator.getEnergyStored(null));
//        String maxEnergy = NumberFormat.getIntegerInstance().format(tileGenerator.getMaxEnergyStored());
//        this.fontRendererObj.drawString(displayName, 86 - this.fontRendererObj.getStringWidth(displayName) / 2, 6, 4210752);
//        this.fontRendererObj.drawString(storedEnergy + " / " + maxEnergy + " RF", 13, 38, 4210752);
//    }
//
//    @Override
//    public void updateScreen() {
//        super.updateScreen();
//    }
//}
