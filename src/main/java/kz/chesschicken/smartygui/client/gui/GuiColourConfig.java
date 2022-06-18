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
import kz.chesschicken.smartygui.client.gui.button.TextFieldNum;
import kz.chesschicken.smartygui.common.RenderUtils;
import kz.chesschicken.smartygui.common.SmartyGUI;
import net.minecraft.client.gui.widgets.Button;
import org.lwjgl.input.Keyboard;

public class GuiColourConfig extends BaseGUIStyle {
	
	private TextFieldNum[] textFields1 = new TextFieldNum[4];

	/** 0 - text. 1 - 1st colour. 2 - 2nd colour. */
	private byte mode = -1;
	/** Debug instance of ModuleBlockRender. */
	private ModuleBlockRender d;
	
	private int[] guiColours;
	private int[] backupA;
	private ButtonBase[] buttonsMove;
	private boolean eskerty;
	private boolean updateS;
	
	public GuiColourConfig(SmartyGUI smartygui) {
		super(smartygui);
		__updateValues();
		this.backupA = new int[] { smartygui.CONFIG.showBlockRGB[0], smartygui.CONFIG.showBlockRGB[1], smartygui.CONFIG.showBlockRGB[2] };
		this.buttonsMove = new ButtonBase[8];
	}
	
	void __updateValues() {
		int[] text1 = RenderUtils.getRGBAFromInt(instance.CONFIG.showBlockRGB[0]);
		int[] c1 = RenderUtils.getRGBAFromInt(instance.CONFIG.showBlockRGB[1]);
		int[] c2 = RenderUtils.getRGBAFromInt(instance.CONFIG.showBlockRGB[2]);
		this.guiColours = new int[] {
				text1[0], text1[1], text1[2], text1[3],
				c1[0], c1[1], c1[2], c1[3],
				c2[0], c2[1], c2[2], c2[3],
		};
	}
	
	void __checkOptions() {
		eskerty = this.instance.CONFIG.transparency || this.instance.CONFIG.showBlockModernStyle;
		((Button)this.buttons.get(1)).active = ((Button)this.buttons.get(2)).active = !eskerty;
	}
	
	String __parseNumString(String d, int a) {
		if(d.length() < 1) d = "0";
		int q = (Integer.parseInt(d) + a);
		if(q > 255) return "255";
		if(q < 0) return "0";
		return String.valueOf(q);
	}
	
	public void updateColours() {
		for(int i = 0; i < 4; i++) {
			if(this.textFields1[i].getText().length() < 1)
				this.textFields1[i].setText("0");
			this.guiColours[i + (4 * mode)] = Integer.parseInt(this.textFields1[i].getText());
		}
		this.instance.CONFIG.showBlockRGB[0] = RenderUtils.getIntFromRGBA(this.guiColours[0], this.guiColours[1], this.guiColours[2], this.guiColours[3]);
		this.instance.CONFIG.showBlockRGB[1] = RenderUtils.getIntFromRGBA(this.guiColours[4], this.guiColours[5], this.guiColours[6], this.guiColours[7]);
		this.instance.CONFIG.showBlockRGB[2] = RenderUtils.getIntFromRGBA(this.guiColours[8], this.guiColours[9], this.guiColours[10], this.guiColours[11]);
	}
	
	/* Override methods. */

	@SuppressWarnings("unchecked")
	@Override
	public void init() {
		super.init();
		
		Keyboard.enableRepeatEvents(true);
		
		this.d = new ModuleBlockRender(this.minecraft, instance);
		this.d.__updateBlockDebug();
		
		this.buttons.add(new ButtonBase(501, this.width / 2 - 91, this.height / 4 + 8, 59, 20, "1st Colour", "First gradient colour of the BEVHUD."));
		this.buttons.add(new ButtonBase(502, this.width / 2 - 30, this.height / 4 + 8,  59, 20, "2nd Colour", "First gradient colour of the BEVHUD."));
		this.buttons.add(new ButtonBase(503, this.width / 2 + 31, this.height / 4 + 8,  59, 20, "Text", "Text colour of the BEVHUD."));
		
		this.buttons.add(new ButtonBase(504, this.width / 2 - 30, this.height / 4 + 8 - 22, 59, 20, "Save", "Save changes."));
		this.buttons.add(new ButtonBase(505, this.width / 2 - 91, this.height / 4 + 8 - 22, 59, 20, "Revert", "Revert changes."));
		this.buttons.add(new ButtonBase(506, this.width / 2 + 31, this.height / 4 + 8 - 22, 59, 20, "Reload", "Reload current."));
		
		for(byte b = 0; b < 4; b++) {
			textFields1[b] = new TextFieldNum(this, this.textManager, this.width / 2 - 25, this.height / 4 + 32 + (b * 24), 50, 20, "");
			textFields1[b].setMaxLength(3);
			/* 510 511 512 513 */ this.buttons.add(buttonsMove[b] = new ButtonBase(510 + b, this.width / 2 + 27, this.height / 4 + 32 + (24 * b), 20, 20, "-"));
			/* 514 515 516 517 */ this.buttons.add(buttonsMove[b + 4] = new ButtonBase(514 + b, this.width / 2 - 47, this.height / 4 + 32 + (24 * b), 20, 20, "+"));
		}
		
		for(Button b : buttonsMove)
			b.visible = false;
		
		__checkOptions();
	}
	
	@Override
	public void tick() {
        for(TextFieldNum a : textFields1)
        	a.tick();
    }
	
	@Override
	protected void keyPressed(char var1, int var2) {
		 for(TextFieldNum a : textFields1)
	        	a.keyPressed(var1, var2);
		 super.keyPressed(var1, var2);
	}

	@Override
	protected void mouseClicked(int var1, int var2, int var3) {
        super.mouseClicked(var1, var2, var3);
        for(TextFieldNum a : textFields1)
        	a.mouseClicked(var1, var2, var3);
    }
	
	@Override
	public void onClose() {
        Keyboard.enableRepeatEvents(false);
    }

	@Override
	protected void buttonClicked(Button var1) {
		super.buttonClicked(var1);
		if(!var1.active || !var1.visible)
			return;
		switch(var1.id) {
		case 501:
		case 502:
		case 503: {
			this.mode = (byte) (var1.id - 501);
			for(Object o : this.buttons)
				((Button)o).active = true;
			__checkOptions();
			var1.active = false;
			
			if(this.mode > -1) {
				for(int i = 0; i < 4; i++) {
					this.textFields1[i].setText(String.valueOf(this.guiColours[i + (4 * mode)]));
				}
				for(Button b : buttonsMove)
					b.visible = true;
			}
			
			break;
		}
		
		case 504: {
			
			this.instance.CONFIG.forceSave();
			break;
		}
		
		case 505: {
			this.instance.CONFIG.showBlockRGB[0] = this.backupA[0];
			this.instance.CONFIG.showBlockRGB[1] = this.backupA[1];
			this.instance.CONFIG.showBlockRGB[2] = this.backupA[2];
			__updateValues();
			for(int i = 0; i < 4; i++) {
				this.textFields1[i].setText(String.valueOf(this.guiColours[i + (4 * mode)]));
			}
			break;
		}
		
		case 506: {
			__updateValues();
			break;
		}
		
		case 510:
		case 511:
		case 512:
		case 513:
		case 514:
		case 515:
		case 516:
		case 517: {
			int code = var1.id - 510;
			if(code < 4)
				this.textFields1[code].setText(__parseNumString(this.textFields1[code].getText(), -1));
			else
				this.textFields1[code - 4].setText(__parseNumString(this.textFields1[code - 4].getText(), 1));
			updateColours();
			break;
		}
		}
		
		this.updateS = !(var1.id != 504);
	}

	@Override
	public void render(int a, int b, float f) {
		this.fillGradient(0, 0, this.width, this.height, -1072689136, -804253680);
		if(mode > -1) {
			 RenderUtils.gradientRender(this.width / 2 - 90, this.height / 4 + 30, this.width / 2 + 90, this.height / 4 + 32 + (24 * 4), this.instance.CONFIG.showBlockRGB[mode], this.instance.CONFIG.showBlockRGB[mode]);
			 for(TextFieldNum t : textFields1)
		        	t.draw();
		}
		this.drawSuper(a, b, f);
		this.drawTextWithShadowCentred(minecraft.textRenderer, "Colour Settings", this.width / 2, 40, 0xFFFFFF);
		if(eskerty) {
			this.drawTextWithShadowCentred(minecraft.textRenderer, "Please turn off Transparency or/and Modern Style", this.width / 2, 15, 0xFF0000);
			this.drawTextWithShadowCentred(minecraft.textRenderer, "in order to manage background colour options!", this.width / 2, 25, 0xFF0000);
		}
		
		if(updateS)
			minecraft.textRenderer.drawTextWithShadow("Changes have been saved!", 5, 5, 0xFFD800);
		
		d.doBlockRendering(this.width / 2, this.height * 6 / 8 + 20, 1);
	}
	
	

}
