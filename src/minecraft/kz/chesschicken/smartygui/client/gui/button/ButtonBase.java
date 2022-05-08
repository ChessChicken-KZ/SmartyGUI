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
package kz.chesschicken.smartygui.client.gui.button;

import org.lwjgl.opengl.GL11;

import com.sun.prism.paint.Color;

import kz.chesschicken.smartygui.common.SmartyGuiConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiButton;

public class ButtonBase extends GuiButton {
	
	public final int STACK_X, STACK_Y;
	public boolean FF_1 = false;
	
	private byte tickA = 0;
	public String stringA = null;
	
	public ButtonBase(int id, int x, int y, int w, int h, String t) {
		super(id, x, y, w, h, t);
		this.STACK_X = x;
		this.STACK_Y = y;
	}
	
	public ButtonBase(int id, int x, int y, int w, int h, String t, String f) {
		super(id, x, y, w, h, t);
		this.STACK_X = x;
		this.STACK_Y = y;
		this.stringA = f;
	}
	
	public ButtonBase(int id, int x, int y, String t) {
        super(id, x, y, t);
		this.STACK_X = x;
		this.STACK_Y = y;
    }
	
	public void drawModern(Minecraft mc, int a, int b) {
        if (this.enabled2) {
            FontRenderer var4 = mc.fontRenderer;
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            boolean var5 = a >= this.xPosition && b >= this.yPosition && a < this.xPosition + this.width && b < this.yPosition + this.height;
            
            this.drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, Color.BLACK.getIntArgbPre());
            
            this.mouseDragged(mc, a, b);
            this.drawCenteredString(var4, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, !this.enabled ? -6250336 : (var5 ? 16777120 : 14737632));
        }
	}
	
	private boolean isHovered(int a, int b) {
        return a >= this.xPosition && b >= this.yPosition && a < this.xPosition + this.width && b < this.yPosition + this.height;
	}
	
	public void setStringA(String a) {
		this.stringA = a;
	}

	
	@Override
	public void drawButton(Minecraft mc, int a, int b) {
		if(SmartyGuiConfig.darkTheme)
			this.drawModern(mc, a, b);
		else
			super.drawButton(mc, a, b);
	}
	
	public void drawTooltip(Minecraft mc, int a, int b) {
		if(stringA != null) {
			if(isHovered(a, b)) {
				if(tickA < 1) {
					this.drawRect(a + 2, b + 2, a + 8 + mc.fontRenderer.getStringWidth(this.stringA), b + 14, Color.BLACK.getIntArgbPre());
					this.drawString(mc.fontRenderer, this.stringA, a + 5, b + 4, 0xFFFFFF);
				} else tickA--;
			}else if(tickA != 60) tickA = 60;
		}
	}
	
	
	

}
