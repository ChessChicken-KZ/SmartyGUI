/**
 * Copyright 2022 ChessChicken-KZ
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package kz.chesschicken.smartygui.common;

import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;

public class RenderUtils {
	public static int[] gradientRenderByAnchor(int x1, int y1, int width, int height, int startColour, int endColour, int anchor, boolean transparency) {
		if(transparency)
			return anchor == 0 ? new int [] { x1, y1 } : (anchor == 1 ? new int[] { x1 - (width / 2), y1 - (height / 2) } : (anchor == 2 ? new int[] { x1 - width, y1 } : new int[0]));
		if(anchor == 0)
			return gradientRender(x1, y1, x1 + width, y1 + height, startColour, endColour);
		if(anchor == 1)
			return gradientRender(x1 - (width / 2), y1 - (height / 2), x1 + width / 2, y1 + height / 2, startColour, endColour);
		if(anchor == 2)
			return gradientRender(x1 - width, y1, x1, y1 + height, startColour, endColour);
		return new int[0];
	}
	
    public static int[] gradientRender(int x1, int y1, int width, int height, int startColour, int endColour) {
    	
    	float vA = (float)(startColour >> 24 & 255) / 255.0F;
        float vR = (float)(startColour >> 16 & 255) / 255.0F;
        float vG = (float)(startColour >> 8 & 255) / 255.0F;
        float vB = (float)(startColour & 255) / 255.0F;
        
        float vA2 = (float)(endColour >> 24 & 255) / 255.0F;
        float vR2 = (float)(endColour >> 16 & 255) / 255.0F;
        float vG2 = (float)(endColour >> 8 & 255) / 255.0F;
        float vB2 = (float)(endColour & 255) / 255.0F;
        
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glDisable(3008);
        GL11.glBlendFunc(770, 771);
        GL11.glShadeModel(7425);
       
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_F(vR, vG, vB, vA);
        tessellator.addVertex(width, y1, 0.0D);
        tessellator.addVertex(x1, y1, 0.0D);
        tessellator.setColorRGBA_F(vR2, vG2, vB2, vA2);
        tessellator.addVertex(x1, height, 0.0D);
        tessellator.addVertex(width, height, 0.0D);
        tessellator.draw();
        
        GL11.glShadeModel(7424);
        GL11.glDisable(3042);
        GL11.glEnable(3008);
        GL11.glEnable(3553);
    	
        return new int[] {x1, y1};
    }

    public static void renderItem(RenderItem ir, FontRenderer fr, RenderEngine textureManager, ItemStack itemInstance, int x, int y) {
        GL11.glPushMatrix();
        GL11.glRotatef(120.0F, 1.0F, 0.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(32826);
        try {
    	   ir.renderItemIntoGUI(fr, textureManager, itemInstance, x, y);
           ir.renderItemOverlayIntoGUI(fr, textureManager, itemInstance, x, y);
        }catch(NullPointerException ignored) {
        }
        GL11.glDisable(32826);
        RenderHelper.disableStandardItemLighting();
        GL11.glPopMatrix();
    }
    
    public static int[] modernRenderByAnchor(int x1, int y1, int w, int h, int anchor) {
    	if(anchor == 0)
    		return modernRender(x1, y1, w, h);
    	if(anchor == 1)
    		return modernRenderCentered(x1, y1, w, h);
    	if(anchor == 2)
    		return modernRenderRight(x1, y1, w, h);
    	return new int[0];
    }
    
    public static int[] modernRenderRight(int x1, int y1, int length, int var4) {
    	x1 = x1 - length;
		return modernRender(x1, y1, length, var4);
    }

    
    public static int[] modernRenderCentered(int x1, int y1, int w, int h) {
    	x1 = x1 - (w / 2);
		y1 = y1 - (h / 2);
		return modernRender(x1, y1, w, h);
    }

    public static int[] modernRender(int x1, int y1, int w, int h) {
        y1 = y1 + 2;
        
        gradientRender(x1 - 3, y1 - 4, x1 + w + 3, (y1 - 3), -267386864, -267386864);
        gradientRender(x1 - 3, y1 + h + 3, x1 + w + 3, (y1 + h + 4), -267386864, -267386864);
        gradientRender(x1 - 3, y1 - 3, x1 + w + 3, (y1 + h + 3), -267386864, -267386864);
        gradientRender(x1 - 4, y1 - 3, x1 - 3, (y1 + h + 3), -267386864, -267386864);
        gradientRender(x1 + w + 3, y1 - 3, x1 + w + 4, (y1 + h + 3), -267386864, -267386864);
        
        gradientRender(x1 - 3, y1 - 3 + 1, x1 - 3 + 1, (y1 + h + 3 - 1), 1347420415, 1344798847);
        gradientRender(x1 + w + 2, y1 - 3 + 1, x1 + w + 3, (y1 + h + 3 - 1), 1347420415, 1344798847);
        gradientRender(x1 - 3, y1 - 3, x1 + w + 3,(y1 - 3 + 1), 1347420415, 1347420415);
        gradientRender(x1 - 3, y1 + h + 2, x1 + w + 3, (y1 + h + 3), 1344798847, 1344798847);

        return new int[] {x1, y1 - 2};
    }

    public static int getIntFromRGB(int r, int g, int b) {
    	return ((r & 0x0FF) << 16) | ((g & 0x0FF) << 8) | (b & 0x0FF);
    }
    
    public static int getIntFromRGBA(int r, int g, int b, int a) {
    	return (a << 24) | ((r & 0x0FF) << 16) | ((g & 0x0FF) << 8) | (b & 0x0FF);
    }
    
    public static int[] getRGBAFromInt(int i) {
    	return new int[] {
    			(i & 0xff0000) >> 16,
    			(i & 0xff00) >> 8,
    			i & 0xff,
    			(i & 0xff000000) >>> 24,
    	};
    }
    
    public static float[] getRGBAFromIntF(int i) {
    	return new float[] {
    			1.0F * ((i & 0xff0000) >> 16) / 255,
    			1.0F * ((i & 0xff00) >> 8) / 255,
    			1.0F * (i & 0xff) / 255,
    			1.0F * ((i & 0xff000000) >>> 24) / 255,
    	};
    }
    
    
    public static float[] getARGBColour(int a) {
    	return new float[] {
    	    	 (float)(a >> 24 & 255) / 255.0F,
    	         (float)(a >> 16 & 255) / 255.0F,
    	         (float)(a >> 8 & 255) / 255.0F,
    	         (float)(a & 255) / 255.0F
    	};
    }

}
