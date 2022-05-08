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

import kz.chesschicken.smartygui.common.SmartyGuiConfig;
import net.minecraft.client.Minecraft;

public class ButtonSlider256 extends ButtonBase {
    public int sliderValue = 255;
    public boolean dragging = false;
    protected final String stringFormat;

    public ButtonSlider256(int id, int x, int y, String text, int basicValue) {
        this(id, x, y, 150, 20, text, basicValue);
    }
    
    public ButtonSlider256(int id, int x, int y, int width, int height, String text, int basicValue) {
        super(id, x, y, width, height, text);
        this.stringFormat = text;
        this.sliderValue = basicValue;
    }

    protected int getHoverState(boolean var1) {
        return 0;
    }

    protected void mouseDragged(Minecraft var1, int a, int b) {
        if (this.enabled2) {
            if (this.dragging) {
                this.sliderValue = (int)((float)(a - (this.xPosition + 4)) / (float)(this.width - 8) * 255 / 1.0F);
                if (this.sliderValue < 0)
                    this.sliderValue = 0;
                if (this.sliderValue > 255)
                	this.sliderValue = 255;
                this.displayString = this.stringFormat + ": " + this.sliderValue;
            }

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            if(SmartyGuiConfig.darkTheme) {
            	this.drawRect(this.xPosition + (int)((this.sliderValue * 1.0F / 255) * (float)(this.width - 8)), this.yPosition, 4, 20, 0xFFFFFF);
                this.drawRect(this.xPosition + (int)((this.sliderValue * 1.0F / 255) * (float)(this.width - 8) + 4), this.yPosition, 4, 20, 0xFFFFFF);
                return;
            }
            this.drawTexturedModalRect(this.xPosition + (int)((this.sliderValue * 1.0F / 255) * (float)(this.width - 8)), this.yPosition, 0, 66, 4, 20);
            this.drawTexturedModalRect(this.xPosition + (int)((this.sliderValue * 1.0F / 255) * (float)(this.width - 8) + 4), this.yPosition, 196, 66, 4, 20);
        }
    }

    public boolean mousePressed(Minecraft game, int a, int b) {
        if (super.mousePressed(game, a, b)) {
            this.sliderValue = (int)((float)(a - (this.xPosition + 4)) / (float)(this.width - 8) * 255 / 1.0F);
            if (this.sliderValue < 0)
                this.sliderValue = 0;
            if (this.sliderValue > 255)
                this.sliderValue = 255;
            

            this.displayString = this.stringFormat + ": " + this.sliderValue;
            this.dragging = true;
            return true;
        } else {
            return false;
        }
    }

    public void mouseReleased(int a, int b) {
        this.dragging = false;
    }
}