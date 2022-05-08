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
import kz.chesschicken.smartygui.client.gui.button.ButtonToggle;
import kz.chesschicken.smartygui.common.SmartyGUI;
import net.minecraft.src.GuiButton;

public class GuiModules extends BaseGUIStyle {
	
	private boolean a_BEVHUD;
	private boolean a_ASH;
	private boolean a_IGTT;
	
	public GuiModules(SmartyGUI a) {
		super(a);
		this.a_BEVHUD = instance.CONFIG.enableShowBlock;
		this.a_ASH = instance.CONFIG.enableArmorStatusHUD;
		this.a_IGTT = instance.CONFIG.enableInGameToolTip;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		super.initGui();
		this.controlList.add(new ButtonToggle(40, this.width / 2 - 91, this.height / 4 + 8, 182, 20, "Block/Entity Viewer HUD", "Enable BEVHUD."));
		((ButtonToggle)this.controlList.get(1)).updateState(this.a_BEVHUD);
		
		this.controlList.add(new ButtonToggle(41, this.width / 2 - 91, this.height / 4 + 32, 182, 20, "Armor Status HUD", "Enable Armor Status HUD."));
		((ButtonToggle)this.controlList.get(2)).updateState(this.a_ASH);
		
		this.controlList.add(new ButtonToggle(42, this.width / 2 - 91, this.height / 4 + 56, 182, 20, "In-Game ToolTip", "Enable In-Game Tooltip."));
		((ButtonToggle)this.controlList.get(3)).updateState(this.a_IGTT);
		
		this.controlList.add(new ButtonBase(50, this.width / 2 - 91, this.height / 4 + 104, 182, 20, "Save and Close", "Save and go back."));
	}



	@Override
	protected void actionPerformed(GuiButton var1) {
		super.actionPerformed(var1);
		if(!var1.enabled || !var1.enabled2)
			return;
		switch(var1.id) {
		
		case 40: {
			this.a_BEVHUD = !this.a_BEVHUD;
			((ButtonToggle)var1).updateState(this.a_BEVHUD);
			break;
		}
		
		case 41: {
			this.a_ASH = !this.a_ASH;
			((ButtonToggle)var1).updateState(this.a_ASH);
			break;
		}
		
		case 42: {
			this.a_IGTT = !this.a_IGTT;
			((ButtonToggle)var1).updateState(this.a_IGTT);
			break;
		}
		
		case 50: {
			instance.CONFIG.enableShowBlock = this.a_BEVHUD;
			instance.CONFIG.enableArmorStatusHUD = this.a_ASH;
			instance.CONFIG.enableInGameToolTip = this.a_IGTT;
			
			instance.CONFIG.forceSave();
			instance.update___ArS();
			mc.displayGuiScreen(new GuiHome(instance));
			break;
		}
		
		
		}
	}
	
	@Override
	public void drawScreen(int a, int b, float f) {
		super.drawScreen(a, b, f);
		this.drawCenteredString(this.mc.fontRenderer, "Modules Options", this.width / 2, 40, 0xFFFFFF);
	}

}
