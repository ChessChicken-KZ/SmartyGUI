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
package kz.chesschicken.smartygui.client.gui;

import kz.chesschicken.smartygui.client.gui.button.ButtonBase;
import net.minecraft.client.gui.screen.ScreenBase;
import net.minecraft.client.gui.widgets.Button;
import org.lwjgl.Sys;

public class GuiCredits extends ScreenBase {
	
	private String[] authors = new String[] {
			"ChessChicken - author of the main idea and lead developer.",
			"WaterfallFlower - bug and code maintainer."
	};

	@Override
	protected void buttonClicked(Button var1) {
		if(!var1.active || !var1.visible)
			return;
		switch(var1.id) {
		case 901: {
			minecraft.openScreen(null);
			break;
		}
		
		case 902: {
			Sys.openURL("https://github.com/ChessChicken-KZ/SmartyGUI");
			break;
		}
		
		case 903: {
			Sys.openURL("https://twitter.com/ChessChicken_KZ");
			break;
		}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void init() {
		this.buttons.add(new ButtonBase(901, this.width / 2 - 91, this.height / 4 + 104, 182, 20, "Close", "Close the screen."));
		
		this.buttons.add(new ButtonBase(902, this.width / 2 - 91, this.height / 4 + 126, 59, 20, "Github", "Repositories."));
		this.buttons.add(new ButtonBase(903, this.width / 2 - 29, this.height / 4 + 126, 59, 20, "Twitter", "A... uhm.. social network?."));
		this.buttons.add(new ButtonBase(904, this.width / 2 + 32, this.height / 4 + 126, 59, 20, "A Button :)", "Does nothing."));
	}

	@Override
	public void render(int var1, int var2, float var3) {
		this.fillGradient(0, 0, this.width, this.height, -1072689136, -804253680);
		super.render(var1, var2, var3);
		this.drawTextWithShadowCentred(minecraft.textRenderer, "SmartyGUI Credits", this.width / 2, 40, 0xFFFFFF);
		
		for(int i = 0; i < authors.length; i++) {
			this.drawTextWithShadowCentred(minecraft.textRenderer, authors[i], this.width / 2, 55 + (12 * i), 0xFFFF00);
		}
	}
	
}
