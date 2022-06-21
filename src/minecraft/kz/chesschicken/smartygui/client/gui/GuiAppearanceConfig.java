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
import kz.chesschicken.smartygui.client.gui.button.ButtonEnum;
import kz.chesschicken.smartygui.client.gui.button.ButtonToggle;
import kz.chesschicken.smartygui.common.EnumTheme;
import kz.chesschicken.smartygui.common.SmartyGUI;
import kz.chesschicken.smartygui.common.SmartyGuiConfig;
import kz.chesschicken.smartygui.common.guiframework.GuiScreenExtended;
import net.minecraft.src.GuiButton;

public class GuiAppearanceConfig extends BaseGUIStyle {
	
	private static final String[] a_ash1 = new String[] { "Bottom Right", "Bottom Left", "Top Right", "Top Left" };
	
	private ButtonEnum<EnumTheme> themeButton;
	private boolean a_darkTheme;
	private boolean a_mmdebug;
	private boolean a_modernStyle;
	private boolean a_transparency;
	private byte a_ashmode;

	public GuiAppearanceConfig(SmartyGUI a) {
		super(a);
		this.a_darkTheme = SmartyGuiConfig.darkTheme;
		this.a_modernStyle = instance.CONFIG.showBlockModernStyle;
		this.a_transparency = instance.CONFIG.transparency;
		this.a_mmdebug = instance.CONFIG.enableDebugF3;
		this.a_ashmode = (byte) instance.CONFIG.armorStatusHUDmode;
	}
	
	void __setASH_String(GuiButton a) {
		a.displayString = "Armor HUD: " + a_ash1[a_ashmode];
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		super.initGui();
		this.controlList.add(themeButton = new ButtonEnum<EnumTheme>(50, this.width / 2 - 91, this.height / 4 + 8, 182, 20, "Icon Theme"));
		themeButton.changeEnum(EnumTheme.valueOf(SmartyGuiConfig.iconTheme));
		
		//Dark theme
		this.controlList.add(new ButtonToggle(51, this.width / 2 - 91, this.height / 4 + 32, 90, 20, "Dark Theme"));
		((ButtonToggle)this.controlList.get(2)).updateState(this.a_darkTheme);
		
		//MM Info
		this.controlList.add(new ButtonToggle(52, this.width / 2 + 1, this.height / 4 + 32, 90, 20, "F3 Extended"));
		((ButtonToggle)this.controlList.get(3)).updateState(this.a_mmdebug);
		
		//Modern ShowBlock
		this.controlList.add(new ButtonToggle(53, this.width / 2 - 91, this.height / 4 + 56, 90, 20, "Modern Style"));
		((ButtonToggle)this.controlList.get(4)).updateState(this.a_modernStyle);

		//Transparency
		this.controlList.add(new ButtonToggle(54, this.width / 2 + 1, this.height / 4 + 56, 90, 20, "Transparency"));
		((ButtonToggle)this.controlList.get(5)).updateState(this.a_transparency);
		
		//ArmorStatus HUD Mode
		this.controlList.add(new ButtonBase(55, this.width / 2 - 91, this.height / 4 + 80, 182, 20, ""));
		__setASH_String((GuiButton)this.controlList.get(6));
		
		this.controlList.add(new ButtonBase(150, this.width / 2 - 91, this.height / 4 + 104, 182, 20, "Save and Close"));
	}

	@Override
	protected void actionPerformed(GuiButton var1) {
		super.actionPerformed(var1);
		if(!var1.enabled || !var1.enabled2)
			return;
		switch(var1.id) {
		
		case 50: {
			themeButton.changeEnum(EnumTheme.values()[(themeButton.currentEnum.ordinal() + 1 < EnumTheme.values().length) ? themeButton.currentEnum.ordinal() + 1 : 0]);
			break;
		}
		
		case 51: {
			a_darkTheme = !a_darkTheme;
			((ButtonToggle)var1).updateState(a_darkTheme);
			break;
		}
		
		case 52: {
			a_mmdebug = !a_mmdebug;
			((ButtonToggle)var1).updateState(a_mmdebug);
			break;
		}
		
		case 53: {
			a_modernStyle = !a_modernStyle;
			((ButtonToggle)var1).updateState(a_modernStyle);
			break;
		}
		
		case 54: {
			a_transparency = !a_transparency;
			((ButtonToggle)var1).updateState(a_transparency);
			break;
		}
		
		case 55: {
			a_ashmode = (byte) ((a_ashmode + 1 < a_ash1.length) ? a_ashmode + 1 : 0);
			__setASH_String(var1);
			break;
		}
		
		case 150: {
			SmartyGuiConfig.iconTheme = themeButton.currentEnum.name();
			SmartyGuiConfig.darkTheme = a_darkTheme;
			instance.CONFIG.showBlockModernStyle = a_modernStyle;
			instance.CONFIG.transparency = a_transparency;
			instance.CONFIG.enableDebugF3 = a_mmdebug;
			instance.CONFIG.armorStatusHUDmode = a_ashmode;
			instance.CONFIG.forceSave();
			instance.updateASHUD();
			mc.displayGuiScreen(new GuiScreenExtended(GuiHome.HOME_GUI.apply(instance)));
			break;
		}
		}
	}
	
	@Override
	public void drawScreen(int a, int b, float f) {
		super.drawScreen(a, b, f);
		this.drawCenteredString(this.mc.fontRenderer, "Appearance Options", this.width / 2, 40, 0xFFFFFF);
	}
	
}
