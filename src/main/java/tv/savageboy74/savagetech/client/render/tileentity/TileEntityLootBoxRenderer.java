package tv.savageboy74.savagetech.client.render.tileentity;

/*
 * TileEntityLootBoxRenderer.java
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
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import tv.savageboy74.savagetech.blocks.lootbox.BlockLootBox;
import tv.savageboy74.savagetech.blocks.lootbox.TileEntityLootBox;
import tv.savageboy74.savagetech.client.model.ModelLootBox;
import tv.savageboy74.savagetech.util.reference.ModReference;

@SideOnly(Side.CLIENT)
public class TileEntityLootBoxRenderer extends TileEntitySpecialRenderer<TileEntityLootBox>
{
    private ModelLootBox modelLootBox = new ModelLootBox();
    private ResourceLocation model = new ResourceLocation(ModReference.MOD_DOMAIN + "textures/blocks/blockLootBox.png");

    private void renderTileEntityLootBoxAt(TileEntityLootBox lootBox, double x, double y, double z, float partialTicks, int destroyStage) {
        GlStateManager.enableDepth();
        GlStateManager.depthFunc(515);
        GlStateManager.depthMask(true);

        int i;

        if (!lootBox.hasWorldObj()) {
            i = 0;
        } else {
            Block block = lootBox.getBlockType();
            i = lootBox.getBlockMetadata();

            if (block instanceof BlockLootBox && i == 0) {
                i = lootBox.getBlockMetadata();
            }
        }

        ModelLootBox modelLootBox1 = modelLootBox;

        if (destroyStage >= 0)
        {
            this.bindTexture(DESTROY_STAGES[destroyStage]);
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.scale(4.0F, 4.0F, 1.0F);
            GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
            GlStateManager.matrixMode(5888);
        } else {
            bindTexture(model);
        }
//
//        bindTexture(model);

        GlStateManager.pushMatrix();
        GlStateManager.enableRescaleNormal();

        if (destroyStage < 0) {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        }

        GlStateManager.translate((float)x, (float)y + 1.0F, (float)z + 1.0F);
        GlStateManager.scale(1.0F, -1.0F, -1.0F);
        GlStateManager.translate(0.5F, 0.5F, 0.5F);

        int j = 0;

        if (i == 2)
        {
            j = 180;
        }

        if (i == 3)
        {
            j = 0;
        }

        if (i == 4)
        {
            j = 90;
        }

        if (i == 5)
        {
            j = -90;
        }

        GlStateManager.rotate((float)j, 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(-0.5F, -0.5F, -0.5F);

        float f = lootBox.prevLidAngle + (lootBox.lidAngle - lootBox.prevLidAngle) * partialTicks;

        f = 1.0F - f;
        f = 1.0F - f * f * f;

        modelLootBox1.chestLid.rotateAngleX = -(f * ((float)Math.PI / 2F));
        modelLootBox1.renderAll();
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        if (destroyStage >= 0) {
            GlStateManager.matrixMode(5890);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
        }

//        GL11.glPushMatrix();
//        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
//        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
//        GL11.glTranslatef((float) x, (float) y + 1.0F, (float) z + 1.0F);
//        GL11.glScalef(1.0F, -1.0F, -1.0F);
//        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
//        short short1 = 0;
//
//        if (i == 2) {
//            short1 = 180;
//        }
//
//        if (i == 3) {
//            short1 = 0;
//        }
//
//        if (i == 4) {
//            short1 = 90;
//        }
//
//        if (i == 5) {
//            short1 = -90;
//        }
//
//        GL11.glRotatef(short1, 0.0F, 1.0F, 0.0F);
//        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
//        float f1 = lootBox.prevLidAngle
//                       + (lootBox.lidAngle - lootBox.prevLidAngle)
//                             * partialTicks;
//
//        f1 = 1.0F - f1;
//        f1 = 1.0F - f1 * f1 * f1;
//        modelLootBox1.chestLid.rotateAngleX = -(f1 * (float) Math.PI / 2.0F);
//        modelLootBox1.renderAll();
//        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
//        GL11.glPopMatrix();
//        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public void renderTileEntityAt(TileEntityLootBox te, double x, double y, double z, float partialTicks, int destroyStage) {
        renderTileEntityLootBoxAt((TileEntityLootBox) te, x, y, z, partialTicks, destroyStage);
    }
}
