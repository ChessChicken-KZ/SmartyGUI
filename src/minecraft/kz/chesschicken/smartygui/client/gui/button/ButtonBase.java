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

import java.util.function.BiConsumer;

import org.lwjgl.opengl.GL11;

import kz.chesschicken.smartygui.common.SmartyGuiConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiButton;

public class ButtonBase extends GuiButton {
	
	public boolean FF_1 = false;
	
	private byte tickA = 0;
	public String tooltipText = null;
	public BiConsumer<Integer, Integer> resizeFunc;
	
	public ButtonBase(int id, int x, int y, int w, int h, String t) {
		super(id, x, y, w, h, t);
	}
	
	public ButtonBase(int id, int x, int y, int w, int h, String t, String f) {
		super(id, x, y, w, h, t);
		this.tooltipText = f;
	}
	
	public ButtonBase(int id, int x, int y, String t) {
        super(id, x, y, t);
    }
	
	public ButtonBase(int id, int w, int h, BiConsumer<Integer, Integer> r, String t) {
		super(id, 0, 0, w, h, t);
		this.resizeFunc = r;
	}
	
	public ButtonBase(int id, int w, int h, BiConsumer<Integer, Integer> r, String t, String f) {
		super(id, 0, 0, w, h, t);
		this.resizeFunc = r;
		this.tooltipText = f;
	}
	
	public ButtonBase(int id, BiConsumer<Integer, Integer> r, String t) {
		super(id, 0, 0, t);
		this.resizeFunc = r;
	}
	
	public void drawModern(Minecraft mc, int a, int b) {
        if (this.enabled2) {
            FontRenderer var4 = mc.fontRenderer;
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            boolean var5 = a >= this.xPosition && b >= this.yPosition && a < this.xPosition + this.width && b < this.yPosition + this.height;
            
            this.drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, 0x000000);
            
            this.mouseDragged(mc, a, b);
            this.drawCenteredString(var4, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, !this.enabled ? -6250336 : (var5 ? 16777120 : 14737632));
        }
	}
	
	private boolean isHovered(int a, int b) {
        return a >= this.xPosition && b >= this.yPosition && a < this.xPosition + this.width && b < this.yPosition + this.height;
	}
	
	public void setToolTipText(String a) {
		this.tooltipText = a;
	}
	
	@Override
	public void drawButton(Minecraft mc, int a, int b) {
		if(SmartyGuiConfig.darkTheme)
			this.drawModern(mc, a, b);
		else
			super.drawButton(mc, a, b);
	}
	
	public void drawTooltip(Minecraft mc, int a, int b) {
		if(tooltipText != null) {
			if(isHovered(a, b)) {
				if(tickA < 1) {
					this.drawRect(a + 2, b + 2, a + 8 + mc.fontRenderer.getStringWidth(this.tooltipText), b + 14, 0x000000);
					this.drawString(mc.fontRenderer, this.tooltipText, a + 5, b + 4, 0xFFFFFF);
				} else tickA--;
			}else if(tickA != 60) tickA = 60;
		}
	}
}
