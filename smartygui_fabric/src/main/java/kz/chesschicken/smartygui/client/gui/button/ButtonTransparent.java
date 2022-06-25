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
		this.width =  GameUtils.getMC().textRenderer.getTextWidth(t);
		this.height = 8;
	}

	@Override
	public void render(Minecraft mc, int a, int b) {
		if(this.visible) {
            this.postRender(mc, a, b);
            this.drawTextWithShadowCentred(mc.textRenderer, this.text, this.x + this.width / 2, this.y + (this.height - 8) / 2, (!this.active) ? -6250336 : ((a >= this.x && b >= this.y && a < this.x + this.width && b < this.y + this.height) ? 16777120 : 14737632));
		}
	}
	
}
