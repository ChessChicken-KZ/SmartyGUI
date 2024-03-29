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

import kz.chesschicken.smartygui.client.components.ModuleBlockRender;
import kz.chesschicken.smartygui.client.gui.button.ButtonBase;
import kz.chesschicken.smartygui.common.SmartyGUI;
import net.minecraft.client.gui.screen.ScreenBase;
import net.minecraft.client.gui.widgets.Button;

public class GuiPresets extends ScreenBase {

	private final SmartyGUI instance;
	private final GuiDragInterface homeScreen;
	
	private boolean shouldRevert = true;
	private int[] axy = new int[3];
	private ModuleBlockRender debug1;
	
	public GuiPresets(SmartyGUI smartygui, GuiDragInterface homeScreen) {
		this.instance = smartygui;
		this.homeScreen = homeScreen;
		this.axy[0] = homeScreen.backupA;
		this.axy[1] = homeScreen.backupX;
		this.axy[2] = homeScreen.backupY;
	}
	
	@Override
	public void onClose() {
		if(shouldRevert) {
			this.instance.applyNewAnchor(this.axy[0]);
			this.instance.applyNewXY(this.axy[1], this.axy[2]);
		}
	}
	
	/* Presets mode. */
	@SuppressWarnings("unchecked")
	@Override
	public void init() {
		this.debug1 = new ModuleBlockRender(this.minecraft, instance);
		this.debug1.__updateBlockDebug();
		
		this.buttons.add(new ButtonBase(99, this.width / 2 - 10, this.height / 2 - 10, 20, 20, "X"));
		
		this.buttons.add(new ButtonBase(100, 10, 10, 80, 20, "LEFT UP"));
		this.buttons.add(new ButtonBase(101, this.width / 2 - 40, 10, 80, 20, "TOP UP"));
		this.buttons.add(new ButtonBase(102, this.width - 90, 10, 80, 20, "RIGHT UP"));
		
		this.buttons.add(new ButtonBase(103, 10, this.height - 60, 80, 20, "LEFT DOWN"));
		this.buttons.add(new ButtonBase(104, this.width / 2 - 40, this.height * 6 / 8, 80, 20, "UPPER INV"));
		this.buttons.add(new ButtonBase(105, this.width - 90, this.height - 60, 80, 20, "RIGHT DOWN"));
	}

	@Override
	protected void buttonClicked(Button var1) {
		switch(var1.id) {
		
		case 99: {
			shouldRevert = false;
			minecraft.openScreen(homeScreen);
			break;
		}
		
		case 100:
		case 101:
		case 102:
		case 103:
		case 104:
		case 105: {
			if(var1.id == 100 || var1.id == 103) {
				this.instance.applyNewAnchor(0);
				this.instance.applyNewXY(var1.x - 2, var1.y);
			}
			if(var1.id == 101 || var1.id == 104) {
				this.instance.applyNewAnchor(1);
				this.instance.applyNewXY(var1.x + 40, var1.y + 10);
			}
			if(var1.id == 102 || var1.id == 105) {
				this.instance.applyNewAnchor(2);
				this.instance.applyNewXY(var1.x + 80, var1.y);
			}
			break;
		}
		}
	}
	
	@Override
	public void render(int a, int b, float f) {
		super.render(a, b, f);
		debug1.doBlockRendering(instance.getX(), instance.getY(), instance.getAnchor());
	}

}
