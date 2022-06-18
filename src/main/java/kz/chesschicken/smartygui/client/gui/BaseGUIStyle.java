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

import kz.chesschicken.smartygui.client.gui.button.ButtonTransparent;
import kz.chesschicken.smartygui.common.SmartyGUI;
import net.minecraft.client.gui.screen.ScreenBase;
import net.minecraft.client.gui.widgets.Button;

public class BaseGUIStyle extends ScreenBase {

	protected final SmartyGUI instance;
	
	public BaseGUIStyle(SmartyGUI smartygui) {
		this.instance = smartygui;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void init() {
		this.buttons.add(new ButtonTransparent(900, 2, this.height - 10, "Credits..."));
	}

	@Override
	protected void buttonClicked(Button var1) {
		if(!var1.active || !var1.visible)
			return;
		switch(var1.id) {
		case 900: {
			minecraft.openScreen(new GuiCredits());
			break;
		}
		}
	}

	@Override
	public void render(int a, int b, float f) {
		this.fillGradient(0, 0, this.width, this.height, -1072689136, -804253680);
		super.render(a, b, f);
	}

	public void drawSuper(int a, int b, float f) {
		super.render(a, b, f);
	}
}
