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
import kz.chesschicken.smartygui.client.gui.button.ButtonImage;
import kz.chesschicken.smartygui.common.SmartyGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.ModLoader;
import net.minecraft.src.ScaledResolution;

public class GuiDragInterface extends GuiScreen {
	
	private final SmartyGUI instance;
	public int backupX, backupY, backupA;
	private boolean saveState = false;
	
	private int factorMove = 1;
	private ButtonBase[] f = new ButtonBase[3];
	private ButtonBase[] m = new ButtonBase[12];

	private ModuleBlockRender debug1;
	
	private boolean anyChanges() {
		return instance.CONFIG.factorX != instance.getX() || instance.CONFIG.factorY != instance.getY();
	}
	
	public GuiDragInterface(SmartyGUI a) {
		this.instance = a;
		this.backupX = a.getX();
		this.backupY = a.getY();
		this.backupA = a.getAnchor();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		this.debug1 = new ModuleBlockRender(this.mc, instance);
		this.debug1.__updateBlockDebug();
		
		int bX_base = 5;
		int bY_base = 15;
		
		/* CENTER */ this.controlList.add(m[0] = new ButtonImage(0, bX_base, bY_base, 80, 0, "Center the UI"));
		/* UP */ this.controlList.add(m[1] = new ButtonImage(1, bX_base + 22, bY_base, 16, 0, "Move Up"));
		/* Presets */ this.controlList.add(m[2] = new ButtonImage(7, bX_base + 44, bY_base, 96, 0, "Revert Changes"));
		
		/* < */ this.controlList.add(m[3] = new ButtonImage(2, bX_base, bY_base + 22, 0, 0, "Move Left"));
		/* FIX */ this.controlList.add(m[4] = new ButtonBase(3, bX_base + 22, bY_base + 22, 20, 20, "FIX", "WIP"));
		/* > */ this.controlList.add(m[5] = new ButtonImage(4, bX_base + 44, bY_base + 22, 32, 0, "Move Right"));
		
		/* SAVE */ this.controlList.add(m[6] = new ButtonImage(5, bX_base, bY_base + 44, 64, 0, "Save to Config"));
		/* DOWN */ this.controlList.add(m[7] = new ButtonImage(6, bX_base + 22, bY_base + 44, 48, 0, "Move Down"));
		/* RESET */ this.controlList.add(m[8] = new ButtonImage(14, bX_base + 44, bY_base + 44, 112, 0, "Load Presets"));
		
		this.controlList.add(f[0] = new ButtonBase(8, bX_base, bY_base + 66, 20, 20, "1x", "1x Moving Factor"));
		f[0].enabled = false;
		this.controlList.add(f[1] = new ButtonBase(9, bX_base + 22, bY_base + 66, 20, 20, "5x", "5x Moving Factor"));
		this.controlList.add(f[2] = new ButtonBase(10, bX_base + 44, bY_base + 66, 20, 20, "10x", "10x Moving Factor"));
		
		/* ANCHLEFT */ this.controlList.add(m[9] = new ButtonImage(11, bX_base + 66, bY_base, 0, 16, "Set Anchor to the left side"));
		/* ANCHCENTER */ this.controlList.add(m[10] = new ButtonImage(12, bX_base + 66, bY_base + 22, 16, 16, "Set Anchor to the center"));
		/* ANCHRIGHT */ this.controlList.add(m[11] = new ButtonImage(13, bX_base + 66, bY_base + 44, 32, 16, "Set Anchor to the right side"));
		
		
		updateScreen();
	}
	
	@Override
	public void updateScreen() {
		((GuiButton)this.controlList.get(6)).enabled = anyChanges();
	}
	
	@Override
	protected void actionPerformed(GuiButton var1) {
		if(!var1.enabled || !var1.enabled2)
			return;
		
		switch(var1.id) {
		
		case 8:
		case 9:
		case 10: {
			this.factorMove = var1.id == 8 ? 1 : (var1.id == 9 ? 5 : (var1.id == 10 ? 10 : 0));
			for(GuiButton a : f)
				a.enabled = true;
			f[var1.id - 8].enabled = false;
			break;
		}
		
		case 11:
		case 12:
		case 13: {
			instance.applyNewAnchor(var1.id - 11);
			break;
		}
		
		case 0: {
			Minecraft mc = ModLoader.getMinecraftInstance();
			ScaledResolution scaled = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
			instance.applyNewXY(scaled.getScaledWidth() / 2, scaled.getScaledHeight() / 2);
			instance.CONFIG.saveConfig();
	    	break;
		}
		
		case 5: {
			instance.CONFIG.factorX = backupX = instance.getX();
			instance.CONFIG.factorY = backupY = instance.getY();
			instance.CONFIG.factorAnchor = backupA = instance.getAnchor();
			instance.CONFIG.forceSave();
			saveState = true;
			break;
		}
		
		case 6:
		case 1: {
			instance.applyNewXY(instance.getX(), instance.getY() + (factorMove * (var1.id == 6 ? 1 : -1)));
			break;
		}
		
		case 2:
		case 4: {
			instance.applyNewXY(instance.getX() + (factorMove * (var1.id == 4 ? 1 : -1)), instance.getY());
			break;
		}
		
		case 7: {
			instance.applyNewXY(backupX, backupY);
			instance.applyNewAnchor(backupA);
			break;
		}
		
		case 14: {
			this.mc.displayGuiScreen(new GuiPresets(this.instance, this));
			break;
		}
		}
		
		saveState = var1.id == 5;
    }
	
	@Override
    public void onGuiClosed() {
		if(anyChanges()) {
			instance.applyNewXY(backupX, backupY);
			instance.applyNewAnchor(backupA);
		}
    }

	@Override
	public void drawScreen(int var1, int var2, float var3) {
		super.drawScreen(var1, var2, var3);

		this.mc.fontRenderer.drawStringWithShadow("Move, Drag & Setup", 2, 2, 0xFFFFFF);
		
		if(anyChanges() || saveState) {
			this.mc.fontRenderer.drawStringWithShadow("From: " + backupX + " " + backupY, 2, this.height - 30, 0xFFFFFF);
			this.mc.fontRenderer.drawStringWithShadow("To: " + instance.getX() + " " + instance.getY() , 2, this.height - 20, 0xFFFFFF);
			this.mc.fontRenderer.drawStringWithShadow("Delta: " + (instance.getX() - backupX) + " " + (instance.getY() - backupY) , 2, this.height - 10, 0xFFFFFF);
		}

		if(saveState)
			this.mc.fontRenderer.drawStringWithShadow("Сhanges have been saved!", 5 + this.mc.fontRenderer.getStringWidth("Move, Drag & Setup"), 2, 0xFFD800);
		
		for(Object f : this.controlList)
			((ButtonBase) f).drawTooltip(this.mc, var1, var2);

		debug1.doBlockRendering(instance.getX(), instance.getY(), instance.getAnchor());
	}
	
}
