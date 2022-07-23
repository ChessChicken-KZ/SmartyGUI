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
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;

public class GuiPresets extends GuiScreen {

	private final SmartyGUI instance;
	private final GuiDragInterface homeScreen;
	
	private boolean shouldRevert = true;
	private final int[] axy = new int[3];
	private ModuleBlockRender debug1;
	
	public GuiPresets(SmartyGUI smartygui, GuiDragInterface homeScreen) {
		this.instance = smartygui;
		this.homeScreen = homeScreen;
		this.axy[0] = homeScreen.backupA;
		this.axy[1] = homeScreen.backupX;
		this.axy[2] = homeScreen.backupY;
	}
	
	@Override
	public void onGuiClosed() {
		if(shouldRevert) {
			this.instance.applyNewAnchor(this.axy[0]);
			this.instance.applyNewXY(this.axy[1], this.axy[2]);
		}
	}
	
	/* Presets mode. */
	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		this.debug1 = new ModuleBlockRender(this.mc, instance);
		this.debug1.__updateBlockDebug();
		
		this.controlList.add(new ButtonBase(99, this.width / 2 - 10, this.height / 2 - 10, 20, 20, "X"));
		
		this.controlList.add(new ButtonBase(100, 10, 10, 80, 20, "LEFT UP"));
		this.controlList.add(new ButtonBase(101, this.width / 2 - 40, 10, 80, 20, "TOP UP"));
		this.controlList.add(new ButtonBase(102, this.width - 90, 10, 80, 20, "RIGHT UP"));
		
		this.controlList.add(new ButtonBase(103, 10, this.height - 60, 80, 20, "LEFT DOWN"));
		this.controlList.add(new ButtonBase(104, this.width / 2 - 40, this.height * 6 / 8, 80, 20, "UPPER INV"));
		this.controlList.add(new ButtonBase(105, this.width - 90, this.height - 60, 80, 20, "RIGHT DOWN"));
	}

	@Override
	protected void actionPerformed(GuiButton var1) {
		switch(var1.id) {
		
		case 99: {
			shouldRevert = false;
			this.mc.displayGuiScreen(homeScreen);
			break;
		}
		
		case 100: {
			this.instance.applyNewAnchor(5);
			this.instance.applyNewXY(var1.xPosition - 2, var1.yPosition);
			break;
		}

		case 103: {
			this.instance.applyNewAnchor(2);
			this.instance.applyNewXY(var1.xPosition - 2, var1.yPosition + 20);
			break;
		}

		case 104: {
			this.instance.applyNewAnchor(1);
			this.instance.applyNewXY(var1.xPosition + 40, var1.yPosition + 20);
			break;
		}

		case 101: {
			this.instance.applyNewAnchor(4);
			this.instance.applyNewXY(var1.xPosition + 40, var1.yPosition);
			break;
		}

		case 102: {
			this.instance.applyNewAnchor(3);
			this.instance.applyNewXY(var1.xPosition + 80, var1.yPosition);
			break;
		}
		
		case 105: {
			this.instance.applyNewAnchor(0);
			this.instance.applyNewXY(var1.xPosition + 80, var1.yPosition + 20);
			break;
		}

		}
	}
	
	@Override
	public void drawScreen(int a, int b, float f) {
		super.drawScreen(a, b, f);
		debug1.doBlockRendering(instance.getX(), instance.getY(), instance.getAnchor());
	}

}
