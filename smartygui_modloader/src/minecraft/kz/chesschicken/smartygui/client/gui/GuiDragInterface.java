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
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.ScaledResolution;

public class GuiDragInterface extends GuiScreen {
	
	private final SmartyGUI instance;
	
	public int backupX, backupY, backupA;
	private int factorMove = 1;
	private boolean saveState = false;
	
	/** Moving factor control buttons. */
	private final ButtonBase[] m = new ButtonBase[3];
	
	/** General control buttons. */
	private final ButtonBase[] g = new ButtonBase[12];
	
	/** Debug instance of ModuleBlockRender. */
	private ModuleBlockRender s;
	
	boolean __anyChanges() {
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
		
		this.s = new ModuleBlockRender(this.mc, instance);
		this.s.__updateBlockDebug();
		
		int bX_base = 5;
		int bY_base = 15;
		
		/* CENTER */ this.controlList.add(g[0] = new ButtonImage(0, bX_base, bY_base, 80, 0, "Center the UI"));
		/* UP */ this.controlList.add(g[1] = new ButtonImage(1, bX_base + 22, bY_base, 16, 0, "Move Up"));
		/* Presets */ this.controlList.add(g[2] = new ButtonImage(7, bX_base + 44, bY_base, 96, 0, "Revert Changes"));
		
		/* < */ this.controlList.add(g[3] = new ButtonImage(2, bX_base, bY_base + 22, 0, 0, "Move Left"));
		/* FIX */ this.controlList.add(g[4] = new ButtonBase(3, bX_base + 22, bY_base + 22, 20, 20, "FIX", "WIP"));
		/* > */ this.controlList.add(g[5] = new ButtonImage(4, bX_base + 44, bY_base + 22, 32, 0, "Move Right"));
		
		/* SAVE */ this.controlList.add(g[6] = new ButtonImage(5, bX_base, bY_base + 44, 64, 0, "Save to Config"));
		/* DOWN */ this.controlList.add(g[7] = new ButtonImage(6, bX_base + 22, bY_base + 44, 48, 0, "Move Down"));
		/* RESET */ this.controlList.add(g[8] = new ButtonImage(14, bX_base + 44, bY_base + 44, 112, 0, "Load Presets"));
		
		this.controlList.add(m[0] = new ButtonBase(8, bX_base, bY_base + 66, 20, 20, "1x", "1x Moving Factor"));
		m[0].enabled = false;
		this.controlList.add(m[1] = new ButtonBase(9, bX_base + 22, bY_base + 66, 20, 20, "5x", "5x Moving Factor"));
		this.controlList.add(m[2] = new ButtonBase(10, bX_base + 44, bY_base + 66, 20, 20, "10x", "10x Moving Factor"));
		
		/* ANCHLEFT */ this.controlList.add(g[9] = new ButtonImage(11, bX_base + 66, bY_base, 48, 16, "Select previous anchor"));
		/* ANCHCENTER */ this.controlList.add(g[10] = new ButtonBase(12, bX_base + 66, bY_base + 22, 20, 20, String.valueOf(this.instance.CONFIG.factorAnchor)));
		/* ANCHRIGHT */ this.controlList.add(g[11] = new ButtonImage(13, bX_base + 66, bY_base + 44, 64, 16, "Select next anchor"));
		
		
		updateScreen();
	}
	
	@Override
	public void updateScreen() {
		((GuiButton)this.controlList.get(6)).enabled = __anyChanges();
	}
	
	@Override
	protected void actionPerformed(GuiButton var1) {
		if(!var1.enabled || !var1.enabled2)
			return;
		switch(var1.id) {
		
		case 8:
		case 9:
		case 10: {
			this.factorMove = var1.id == 8 ? 1 : var1.id == 9 ? 5 : 10;
			for(GuiButton a : m)
				a.enabled = true;
			m[var1.id - 8].enabled = false;
			break;
		}
		
		case 11:
		case 13: {
			byte a = (byte) instance.getAnchor();
			if(var1.id == 11) a--;
			if(var1.id == 13) a++;
			if(a >= 0 && a <= 8) {
				instance.applyNewAnchor(a);
				g[10].displayString = String.valueOf(instance.getAnchor());
			}
			break;
		}
		
		case 0: {
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
		if(__anyChanges()) {
			instance.applyNewXY(backupX, backupY);
			instance.applyNewAnchor(backupA);
		}
    }
	
	@Override
	public void drawScreen(int var1, int var2, float var3) {
		super.drawScreen(var1, var2, var3);

		this.mc.fontRenderer.drawStringWithShadow("Move, Drag & Setup", 2, 2, 0xFFFFFF);
		
		if(__anyChanges() || saveState) {
			this.mc.fontRenderer.drawStringWithShadow("From: " + backupX + " " + backupY, 2, this.height - 30, 0xFFFFFF);
			this.mc.fontRenderer.drawStringWithShadow("To: " + instance.getX() + " " + instance.getY() , 2, this.height - 20, 0xFFFFFF);
			this.mc.fontRenderer.drawStringWithShadow("Delta: " + (instance.getX() - backupX) + " " + (instance.getY() - backupY) , 2, this.height - 10, 0xFFFFFF);
		}

		if(saveState)
			this.mc.fontRenderer.drawStringWithShadow("Changes have been saved!", 5 + this.mc.fontRenderer.getStringWidth("Move, Drag & Setup"), 2, 0xFFD800);
		
		for(Object f : this.controlList)
			((ButtonBase) f).drawTooltip(this.mc, var1, var2);

		s.doBlockRendering(instance.getX(), instance.getY(), instance.getAnchor());
	}
	
}
