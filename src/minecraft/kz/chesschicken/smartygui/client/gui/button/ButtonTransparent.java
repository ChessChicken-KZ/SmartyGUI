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

import kz.chesschicken.smartygui.commonloader.GameUtils;
import net.minecraft.client.Minecraft;

public class ButtonTransparent extends ButtonBase {

	public ButtonTransparent(int id, int x, int y, String t) {
		super(id, x, y, t);
		this.width =  GameUtils.getMC().fontRenderer.getStringWidth(t);
		this.height = 8;
	}

	@Override
	public void drawButton(Minecraft mc, int a, int b) {
		if(this.enabled2) {
            this.mouseDragged(mc, a, b);
            this.drawCenteredString(mc.fontRenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, (!this.enabled) ? -6250336 : ((a >= this.xPosition && b >= this.yPosition && a < this.xPosition + this.width && b < this.yPosition + this.height) ? 16777120 : 14737632));
		}
	}
	
}
