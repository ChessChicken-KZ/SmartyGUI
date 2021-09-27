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

    public static void gradientModern(int var1, int var2, int length, int var4, int var5, int multiplier, int widthSize)
    {
        int var9 = var1 - var4 + 12;
        int var10 = var2 - var5 - 12;
        int l1 = widthSize;
        if (length > l1) {
            l1 = length;
        }

        int j3 = 8 * multiplier;
        int k3 = -267386864;
        gradientRender(var9 - 3, var10 - 4, var9 + l1 + 3, ( var10 - 3), k3, k3);
        gradientRender(var9 - 3, var10 + j3 + 3, var9 + l1 + 3, ( var10 + j3 + 4), k3, k3);
        gradientRender(var9 - 3, var10 - 3, var9 + l1 + 3, ( var10 + j3 + 3), k3, k3);
        gradientRender(var9 - 4, var10 - 3, var9 - 3, ( var10 + j3 + 3), k3, k3);
        gradientRender(var9 + l1 + 3, var10 - 3, var9 + l1 + 4, ( var10 + j3 + 3), k3, k3);
        int l3 = 1347420415;
        int i4 = (l3 & 16711422) >> 1 | l3 & -16777216;
        gradientRender(var9 - 3, var10 - 3 + 1, var9 - 3 + 1, ( var10 + j3 + 3 - 1), l3, i4);
        gradientRender(var9 + l1 + 2, var10 - 3 + 1, var9 + l1 + 3, ( var10 + j3 + 3 - 1), l3, i4);
        gradientRender(var9 - 3, var10 - 3, var9 + l1 + 3,( var10 - 3 + 1), l3, l3);
        gradientRender(var9 - 3, var10 + j3 + 2, var9 + l1 + 3, (var10 + j3 + 3), i4, i4);

    }

    public static int convertRGBToInt(int r, int g, int b) {
        return 0xFF000000 | ((r << 16) & 0x00FF0000) | ((g << 8) & 0x0000FF00) | (b & 0x000000FF);
    }

    public static int[] convertIntToRGB(int i) {
        return new int[] {i >> 16, i >> 8 & 255, i & 255};
    }

    public static float[] convertIntToFloatRGB(int i) {
        return new float[] {((i >> 16 & 255) / 255.0F), ((i >> 8 & 255) / 255.0F), ((i & 255) / 255.0F)};
    }

    public static char getColorByHardness(float f)
    {
        if(f < 0f)
            return 'b';
        else if(f <= 1f)
            return 'a';
        else if(f > 1F && f <= 3f)
            return 'e';

        return 'c';
    }

}
