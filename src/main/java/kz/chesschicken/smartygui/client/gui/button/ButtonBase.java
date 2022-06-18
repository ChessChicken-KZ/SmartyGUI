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

import net.minecraft.client.gui.widgets.Button;
import net.minecraft.client.render.TextRenderer;
import org.lwjgl.opengl.GL11;

import kz.chesschicken.smartygui.common.SmartyGUIConfig;
import net.minecraft.client.Minecraft;

public class ButtonBase extends Button {
	
	public final int STACK_X, STACK_Y;
	public boolean FF_1 = false;
	
	private byte tickA = 0;
	public String tooltipText = null;
	
	public ButtonBase(int id, int x, int y, int w, int h, String t) {
		super(id, x, y, w, h, t);
		this.STACK_X = x;
		this.STACK_Y = y;
	}
	
	public ButtonBase(int id, int x, int y, int w, int h, String t, String f) {
		super(id, x, y, w, h, t);
		this.STACK_X = x;
		this.STACK_Y = y;
		this.tooltipText = f;
	}
	
	public ButtonBase(int id, int x, int y, String t) {
        super(id, x, y, t);
		this.STACK_X = x;
		this.STACK_Y = y;
    }
	
	public void drawModern(Minecraft mc, int a, int b) {
        if (this.visible) {
            TextRenderer var4 = mc.textRenderer;
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            boolean var5 = a >= this.x && b >= this.y && a < this.x + this.width && b < this.y + this.height;
            
            this.fill(this.x, this.y, this.x + this.width, this.y + this.height, 0x000000);
            
            this.postRender(mc, a, b);
            this.drawTextWithShadowCentred(var4, this.text, this.x + this.width / 2, this.y + (this.height - 8) / 2, !this.active ? -6250336 : (var5 ? 16777120 : 14737632));
        }
	}
	
	private boolean isHovered(int a, int b) {
        return a >= this.x && b >= this.y && a < this.x + this.width && b < this.y + this.height;
	}
	
	public void setToolTipText(String a) {
		this.tooltipText = a;
	}
	
	@Override
	public void render(Minecraft mc, int a, int b) {
		if(SmartyGUIConfig.darkTheme)
			this.drawModern(mc, a, b);
		else
			super.render(mc, a, b);
	}
	
	public void drawTooltip(Minecraft mc, int a, int b) {
		if(tooltipText != null) {
			if(isHovered(a, b)) {
				if(tickA < 1) {
					this.fill(a + 2, b + 2, a + 8 + mc.textRenderer.getTextWidth(this.tooltipText), b + 14, 0x000000);
					this.drawTextWithShadow(mc.textRenderer, this.tooltipText, a + 5, b + 4, 0xFFFFFF);
				} else tickA--;
			}else if(tickA != 60) tickA = 60;
		}
	}
	
	
	

}
