package tv.savageboy74.savagetech.client.render;

/*
 * FertilizerRenderer.java
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

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL12;
import tv.savageboy74.savagetech.blocks.autofertilizer.TileEntityAutoFertilizer;
import tv.savageboy74.savagetech.util.Coord;
import tv.savageboy74.savagetech.util.helper.LogHelper;

import static org.lwjgl.opengl.GL11.*;

public class FertilizerRenderer
{
    private Coord coord;
    private float maxWidth, maxHeight;
    private static final int DEFAULT_TEXT_COLOR = 255 + (255 << 8) + (255 << 16) + (170 << 24);

    public static final FertilizerRenderer INSTANCE = new FertilizerRenderer();

    @SubscribeEvent
    public void renderEvent(RenderWorldLastEvent event) {
        try {
            doRenderEvent();
        } catch (Exception e) {
            LogHelper.warn("There was an error while trying to render the display for the Fertilizer.");
            e.printStackTrace();
        }
    }

    private void doRenderEvent() {
        Minecraft minecraft = Minecraft.getMinecraft();

        if(minecraft.renderEngine == null || minecraft.getRenderManager() == null || minecraft.getRenderManager().getFontRenderer() == null)
            return;

        coord = new Coord(minecraft.theWorld.provider.getDimension(), minecraft.objectMouseOver);

        switch (minecraft.objectMouseOver.typeOfHit) {
            case BLOCK:
                TileEntity tile = minecraft.theWorld.getTileEntity(coord.blockPos);
                if(tile instanceof TileEntityAutoFertilizer) {
                    renderInfoBox((TileEntityAutoFertilizer) tile);
                }
        }
    }

    private void renderInfoBox(TileEntityAutoFertilizer tileEntityAutoFertilizer) {
        short area = tileEntityAutoFertilizer.getArea();

        if(tileEntityAutoFertilizer.hasFertilizer())
            doRenderInfoBox(tileEntityAutoFertilizer.getCustomName(), String.format("Active Area: %sx%s Fertilizer: %s", area, area, tileEntityAutoFertilizer.getFertilizerStackSize()), true);
        else
            doRenderInfoBox(tileEntityAutoFertilizer.getCustomName(), "No Fertilizer", false);
    }

    private void doRenderInfoBox(String customName, String info, boolean hasData) {
        glPushMatrix();

        moveAndRotate(-1);

        glEnable(GL12.GL_RESCALE_NORMAL);
        glDisable(GL_DEPTH_TEST);

        float blockScale = 1.0F;

        glTranslated(0F, maxHeight + blockScale / 1.25, 0F);

        glScaled(blockScale, blockScale, blockScale);
        glScalef(0.3f, 0.3f, 0.3f);
        glScalef(0.03f, 0.03f, 0.03f);
        glTranslated(2.5f * info.length(), 0f, 0f);
        glRotatef(180, 0.0F, 0.0F, 1.0F);
        glTranslatef(-1f, 1f, 0f);

        Minecraft.getMinecraft().getRenderManager().getFontRenderer().drawString(customName, Minecraft.getMinecraft().fontRendererObj.getStringWidth(info) / 2 - Minecraft.getMinecraft().fontRendererObj.getStringWidth(customName) / 2, 0, DEFAULT_TEXT_COLOR, true);
        Minecraft.getMinecraft().getRenderManager().getFontRenderer().drawString(info, 0, 12, DEFAULT_TEXT_COLOR, true);

        glDisable(GL12.GL_RESCALE_NORMAL);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_DEPTH_TEST);

        glPopMatrix();
    }

    private void moveAndRotate(double depth) {
        RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();


        double tX = coord.blockPos.getX() - renderManager.viewerPosX + 0.55D;
        double tY = coord.blockPos.getY() - renderManager.viewerPosY + 0.5D;
        double tZ = coord.blockPos.getZ() - renderManager.viewerPosZ + 0.5D;

        glTranslated(tX, tY, tZ);
        glRotatef(-renderManager.playerViewY, 0.0F, 0.5F, 0.0F);
        glRotatef(renderManager.playerViewX, 0.5F, 0.0F, 0.0F);
        glTranslated(0, 0, depth);
    }
}
