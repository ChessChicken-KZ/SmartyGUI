package kz.chesschicken.smartygui.common;

import net.minecraft.client.render.RenderHelper;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.client.render.entity.ItemRenderer;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.item.ItemInstance;
import org.lwjgl.opengl.GL11;

public class RenderUtils {
    public static void gradientRender(int x1, int y1, int x2, int y2, int startColour, int endColour) {
        float var7 = (float)(startColour >> 24 & 255) / 255.0F;
        float var8 = (float)(startColour >> 16 & 255) / 255.0F;
        float var9 = (float)(startColour >> 8 & 255) / 255.0F;
        float var10 = (float)(startColour & 255) / 255.0F;
        float var11 = (float)(endColour >> 24 & 255) / 255.0F;
        float var12 = (float)(endColour >> 16 & 255) / 255.0F;
        float var13 = (float)(endColour >> 8 & 255) / 255.0F;
        float var14 = (float)(endColour & 255) / 255.0F;
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glDisable(3008);
        GL11.glBlendFunc(770, 771);
        GL11.glShadeModel(7425);
        Tessellator var15 = Tessellator.INSTANCE;
        var15.start();
        var15.colour(var8, var9, var10, var7);
        var15.addVertex(x2, y1, 0.0D);
        var15.addVertex(x1, y1, 0.0D);
        var15.colour(var12, var13, var14, var11);
        var15.addVertex(x1, y2, 0.0D);
        var15.addVertex(x2, y2, 0.0D);
        var15.draw();
        GL11.glShadeModel(7424);
        GL11.glDisable(3042);
        GL11.glEnable(3008);
        GL11.glEnable(3553);
    }

    public static void renderItem(ItemRenderer ir, TextRenderer fr, TextureManager textureManager, ItemInstance itemInstance, int x, int y) {
        GL11.glPushMatrix();
        GL11.glRotatef(120.0F, 1.0F, 0.0F, 0.0F);
        RenderHelper.enableLighting();
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(32826);
        ir.method_1487(fr, textureManager, itemInstance, x, y);
        ir.method_1488(fr, textureManager, itemInstance, x, y);
        GL11.glDisable(32826);
        RenderHelper.disableLighting();
        GL11.glPopMatrix();
    }
}
