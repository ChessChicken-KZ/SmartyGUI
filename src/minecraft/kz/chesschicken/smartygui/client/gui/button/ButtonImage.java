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

import kz.chesschicken.smartygui.common.EnumTheme;
import kz.chesschicken.smartygui.common.SmartyGuiConfig;
import net.minecraft.client.Minecraft;

public class ButtonImage extends ButtonBase {
	
	protected int u, v;

    public ButtonImage(int id, int x, int y, int u, int v) {
        super(id, x, y, 20, 20, "");
        this.u = u;
        this.v = v;
    }
    
    public ButtonImage(int id, int x, int y, int u, int v, String f) {
        super(id, x, y, 20, 20, "");
        this.u = u;
        this.v = v;
        this.stringA = f;
    }
    
    @Override
    public void drawButton(Minecraft mc, int a, int b) {
    	super.drawButton(mc, a, b);
    	if (this.enabled2) {
            GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, mc.renderEngine.getTexture(EnumTheme.valueOf(SmartyGuiConfig.iconTheme).theme_gui));
            this.drawTexturedModalRect(this.xPosition + 2, this.yPosition + 2, this.u, this.v, 16, 16);
        }
    }
    
}
