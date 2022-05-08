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
import kz.chesschicken.smartygui.common.SmartyGUI;
import net.minecraft.src.GuiButton;

public class GuiHome extends BaseGUIStyle {
	

	public GuiHome(SmartyGUI mod) {
		super(mod);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		super.initGui();
		this.controlList.add(new ButtonBase(20, this.width / 2 - 91, this.height / 4 + 32, 90, 20, "Modules", "Modules toggling menu."));

		this.controlList.add(new ButtonBase(22, this.width / 2 - 91, this.height / 4 + 8, /* 90 */ 182, 20, "Location Settings", "BEVHUD location menu."));
		this.controlList.add(new ButtonBase(23, this.width / 2 + 1, this.height / 4 + 32, 90, 20, "Appearance", "General appearance settings."));
		this.controlList.add(new ButtonBase(24, this.width / 2 - 91, this.height / 4 + 56, /* 90 */ 182, 20, "Colour Settings", "BEVHUD colour settings."));
		
		this.controlList.add(new ButtonBase(70, this.width / 2 - 91, this.height / 4 + 104, 182, 20, "Save and Close", "Save settings and exit to game."));
	}
	
	@Override
	protected void actionPerformed(GuiButton var1) {
		super.actionPerformed(var1);
		if(!var1.enabled || !var1.enabled2)
			return;
		switch(var1.id) {
		
		case 20: {
			mc.displayGuiScreen(new GuiModules(instance));
			break;
		}
		
		case 22: {
			mc.displayGuiScreen(new GuiDragInterface(instance));
			break;
		}
		
		case 23: {
			mc.displayGuiScreen(new GuiAppearanceConfig(instance));
			break;
		}
		
		case 24: {
			mc.displayGuiScreen(new GuiColourConfig(instance));
			break;
		}
		
		case 70: {
			mc.displayGuiScreen(null);
			break;
		}
		
		
		}
	}
	
	@Override
	public void drawScreen(int a, int b, float f) {
		super.drawScreen(a, b, f);
		this.drawCenteredString(this.mc.fontRenderer, "SmartyGUI Options", this.width / 2, 40, 0xFFFFFF);
	}

}
