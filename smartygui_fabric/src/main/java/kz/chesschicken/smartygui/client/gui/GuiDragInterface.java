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
import net.minecraft.client.gui.screen.ScreenBase;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.client.util.ScreenScaler;

public class GuiDragInterface extends ScreenBase {
	
	private final SmartyGUI instance;
	
	public int backupX, backupY, backupA;
	private int factorMove = 1;
	private boolean saveState = false;
	
	/** Moving factor control buttons. */
	private ButtonBase[] m = new ButtonBase[3];
	
	/** General control buttons. */
	private ButtonBase[] g = new ButtonBase[12];
	
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
	public void init() {
		
		this.s = new ModuleBlockRender(this.minecraft, instance);
		this.s.__updateBlockDebug();
		
		int bX_base = 5;
		int bY_base = 15;
		
		/* CENTER */ this.buttons.add(g[0] = new ButtonImage(0, bX_base, bY_base, 80, 0, "Center the UI"));
		/* UP */ this.buttons.add(g[1] = new ButtonImage(1, bX_base + 22, bY_base, 16, 0, "Move Up"));
		/* Presets */ this.buttons.add(g[2] = new ButtonImage(7, bX_base + 44, bY_base, 96, 0, "Revert Changes"));
		
		/* < */ this.buttons.add(g[3] = new ButtonImage(2, bX_base, bY_base + 22, 0, 0, "Move Left"));
		/* FIX */ this.buttons.add(g[4] = new ButtonBase(3, bX_base + 22, bY_base + 22, 20, 20, "FIX", "WIP"));
		/* > */ this.buttons.add(g[5] = new ButtonImage(4, bX_base + 44, bY_base + 22, 32, 0, "Move Right"));
		
		/* SAVE */ this.buttons.add(g[6] = new ButtonImage(5, bX_base, bY_base + 44, 64, 0, "Save to Config"));
		/* DOWN */ this.buttons.add(g[7] = new ButtonImage(6, bX_base + 22, bY_base + 44, 48, 0, "Move Down"));
		/* RESET */ this.buttons.add(g[8] = new ButtonImage(14, bX_base + 44, bY_base + 44, 112, 0, "Load Presets"));
		
		this.buttons.add(m[0] = new ButtonBase(8, bX_base, bY_base + 66, 20, 20, "1x", "1x Moving Factor"));
		m[0].active = false;
		this.buttons.add(m[1] = new ButtonBase(9, bX_base + 22, bY_base + 66, 20, 20, "5x", "5x Moving Factor"));
		this.buttons.add(m[2] = new ButtonBase(10, bX_base + 44, bY_base + 66, 20, 20, "10x", "10x Moving Factor"));
		
		/* ANCHLEFT */ this.buttons.add(g[9] = new ButtonImage(11, bX_base + 66, bY_base, 0, 16, "Set Anchor to the left side"));
		/* ANCHCENTER */ this.buttons.add(g[10] = new ButtonImage(12, bX_base + 66, bY_base + 22, 16, 16, "Set Anchor to the center"));
		/* ANCHRIGHT */ this.buttons.add(g[11] = new ButtonImage(13, bX_base + 66, bY_base + 44, 32, 16, "Set Anchor to the right side"));


		tick();
	}
	
	@Override
	public void tick() {
		((Button)this.buttons.get(6)).active = __anyChanges();
	}
	
	@Override
	protected void buttonClicked(Button var1) {
		if(!var1.active || !var1.visible)
			return;
		switch(var1.id) {
		
		case 8:
		case 9:
		case 10: {
			this.factorMove = var1.id == 8 ? 1 : (var1.id == 9 ? 5 : (var1.id == 10 ? 10 : 0));
			for(Button a : m)
				a.active = true;
			m[var1.id - 8].active = false;
			break;
		}
		
		case 11:
		case 12:
		case 13: {
			instance.applyNewAnchor(var1.id - 11);
			break;
		}
		
		case 0: {
			ScreenScaler scaled = new ScreenScaler(minecraft.options, minecraft.actualWidth, minecraft.actualHeight);
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
			minecraft.openScreen(new GuiPresets(this.instance, this));
			break;
		}
		}
		
		saveState = var1.id == 5;
    }
	
	@Override
    public void onClose() {
		if(__anyChanges()) {
			instance.applyNewXY(backupX, backupY);
			instance.applyNewAnchor(backupA);
		}
    }
	
	@Override
	public void render(int var1, int var2, float var3) {
		super.render(var1, var2, var3);

		minecraft.textRenderer.drawTextWithShadow("Move, Drag & Setup", 2, 2, 0xFFFFFF);
		
		if(__anyChanges() || saveState) {
			minecraft.textRenderer.drawTextWithShadow("From: " + backupX + " " + backupY, 2, this.height - 30, 0xFFFFFF);
			minecraft.textRenderer.drawTextWithShadow("To: " + instance.getX() + " " + instance.getY() , 2, this.height - 20, 0xFFFFFF);
			minecraft.textRenderer.drawTextWithShadow("Delta: " + (instance.getX() - backupX) + " " + (instance.getY() - backupY) , 2, this.height - 10, 0xFFFFFF);
		}

		if(saveState)
			minecraft.textRenderer.drawTextWithShadow("Changes have been saved!", 5 + minecraft.textRenderer.getTextWidth("Move, Drag & Setup"), 2, 0xFFD800);
		
		for(Object f : this.buttons)
			((ButtonBase) f).drawTooltip(this.minecraft, var1, var2);

		s.doBlockRendering(instance.getX(), instance.getY(), instance.getAnchor());
	}
	
}
