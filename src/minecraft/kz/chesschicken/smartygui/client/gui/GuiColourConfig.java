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

import org.lwjgl.input.Keyboard;

import kz.chesschicken.smartygui.client.components.ModuleBlockRender;
import kz.chesschicken.smartygui.client.gui.button.ButtonBase;
import kz.chesschicken.smartygui.client.gui.button.TextFieldNum;
import kz.chesschicken.smartygui.common.RenderUtils;
import kz.chesschicken.smartygui.common.SmartyGUI;
import net.minecraft.src.GuiButton;

public class GuiColourConfig extends BaseGUIStyle {
	
	private TextFieldNum[] textFields1 = new TextFieldNum[4];

	public GuiColourConfig(SmartyGUI smartygui) {
		super(smartygui);
		__updateVals1();
		this.backupA = new int[] {
				smartygui.CONFIG.showBlockRGB[0], smartygui.CONFIG.showBlockRGB[1], smartygui.CONFIG.showBlockRGB[2]
		};
		this.buttonsMove = new ButtonBase[8];
	}
	
	void __updateVals1() {
		int[] text1 = RenderUtils.getRGBAFromInt(instance.CONFIG.showBlockRGB[0]);
		int[] c1 = RenderUtils.getRGBAFromInt(instance.CONFIG.showBlockRGB[1]);
		int[] c2 = RenderUtils.getRGBAFromInt(instance.CONFIG.showBlockRGB[2]);
		this.guiColours = new int[] {
				text1[0], text1[1], text1[2], text1[3],
				c1[0], c1[1], c1[2], c1[3],
				c2[0], c2[1], c2[2], c2[3],
		};
	}
	
	/**
	 * 0 - Text
	 * 1 - 1st Colour
	 * 2 - 2nd Colour
	 */
	private byte mode = -1;
	private ModuleBlockRender debug1;
	
	private int[] guiColours;
	private int[] backupA;
	private ButtonBase[] buttonsMove;
	private boolean eskerty;
	private boolean updateS;

	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		this.debug1 = new ModuleBlockRender(this.mc, instance);
		Keyboard.enableRepeatEvents(true);
		this.debug1.__updateBlockDebug();
		super.initGui();
		this.controlList.add(new ButtonBase(501, this.width / 2 - 91, this.height / 4 + 8, 59, 20, "1st Colour", "First gradient colour of the BEVHUD."));
		this.controlList.add(new ButtonBase(502, this.width / 2 - 30, this.height / 4 + 8,  59, 20, "2nd Colour", "First gradient colour of the BEVHUD."));
		this.controlList.add(new ButtonBase(503, this.width / 2 + 31, this.height / 4 + 8,  59, 20, "Text", "Text colour of the BEVHUD."));
		

		this.controlList.add(new ButtonBase(504, this.width / 2 - 30, this.height / 4 + 8 - 22, 59, 20, "Save", "Save changes."));
		this.controlList.add(new ButtonBase(505, this.width / 2 - 91, this.height / 4 + 8 - 22, 59, 20, "Revert", "Revert changes."));
		this.controlList.add(new ButtonBase(506, this.width / 2 + 31, this.height / 4 + 8 - 22, 59, 20, "Reload", "Reload current."));
		
		for(int b = 0; b < 4; b++)
		{
			textFields1[b] = new TextFieldNum(this, this.fontRenderer, this.width / 2 - 25, this.height / 4 + 32 + (b * 24), 50, 20, "");
			textFields1[b].setMaxStringLength(3);
		}
		
		for(int b = 0; b < 4; b++)
		{
			//510 511 512 513
			this.controlList.add(buttonsMove[b] = new ButtonBase(510 + b, this.width / 2 + 27, this.height / 4 + 32 + (24 * b), 20, 20, "-"));
			//514 515 516 517
			this.controlList.add(buttonsMove[b + 4] = new ButtonBase(514 + b, this.width / 2 - 47, this.height / 4 + 32 + (24 * b), 20, 20, "+"));
		}
		
		for(GuiButton b : buttonsMove)
			b.enabled2 = false;
		
		__checkEskerty();
	}
	
	@Override
	public void updateScreen() {
        for(TextFieldNum a : textFields1)
        	a.updateCursorCounter();
    }
	
	@Override
	protected void keyTyped(char var1, int var2) {
		 for(TextFieldNum a : textFields1)
	        	a.textboxKeyTyped(var1, var2);
		 super.keyTyped(var1, var2);
	}

	@Override
	protected void mouseClicked(int var1, int var2, int var3) {
        super.mouseClicked(var1, var2, var3);
        for(TextFieldNum a : textFields1)
        	a.mouseClicked(var1, var2, var3);
    }

	
	@Override
	public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
	
	private void __checkEskerty() {
		eskerty = this.instance.CONFIG.transparency || this.instance.CONFIG.showBlockModernStyle;
		if(eskerty) {
			((GuiButton)this.controlList.get(1)).enabled = ((GuiButton)this.controlList.get(2)).enabled = false;
		}
	}

	@Override
	protected void actionPerformed(GuiButton var1) {
		super.actionPerformed(var1);
		if(!var1.enabled || !var1.enabled2)
			return;
		switch(var1.id) {
		case 501:
		case 502:
		case 503: {
			this.mode = (byte) (var1.id - 501);
			for(Object o : this.controlList)
				((GuiButton)o).enabled = true;
			__checkEskerty();
			var1.enabled = false;
			
			if(this.mode > -1)
			{
				for(int i = 0; i < 4; i++) {
					this.textFields1[i].setText(String.valueOf(this.guiColours[i + (4 * mode)]));
				}
				for(GuiButton b : buttonsMove)
					b.enabled2 = true;
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
			__updateVals1();
			for(int i = 0; i < 4; i++) {
				this.textFields1[i].setText(String.valueOf(this.guiColours[i + (4 * mode)]));
			}
			break;
		}
		
		case 506: {
			__updateVals1();
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
				this.textFields1[code].setText(__tF_Parse(this.textFields1[code].getText(), -1));
			else
				this.textFields1[code - 4].setText(__tF_Parse(this.textFields1[code - 4].getText(), 1));
			updateColours();
			break;
		}
		}
		
		this.updateS = !(var1.id != 504);
	}
	
	public void updateColours() {
		for(int i = 0; i < 4; i++) {
			if(this.textFields1[i].getText().length() < 1)
				this.textFields1[i].setText("0");
			this.guiColours[i + (4 * mode)] = Integer.parseInt(this.textFields1[i].getText());
			//this.textFields1[i].setText(String.valueOf(this.guiColours[i + (4 * mode)]));
		}
		this.instance.CONFIG.showBlockRGB[0] = RenderUtils.getIntFromRGBA(this.guiColours[0], this.guiColours[1], this.guiColours[2], this.guiColours[3]);
		this.instance.CONFIG.showBlockRGB[1] = RenderUtils.getIntFromRGBA(this.guiColours[4], this.guiColours[5], this.guiColours[6], this.guiColours[7]);
		this.instance.CONFIG.showBlockRGB[2] = RenderUtils.getIntFromRGBA(this.guiColours[8], this.guiColours[9], this.guiColours[10], this.guiColours[11]);
		
	}
	
	String __tF_Parse(String d, int a) {
		if(d.length() < 1)
			d = "0";
		int q = (Integer.parseInt(d) + a);
		if(q > 255)
			return "255";
		if(q < 0)
			return "0";
		return String.valueOf(q);
	}
	

	@Override
	public void drawScreen(int a, int b, float f) {
		this.drawGradientRect(0, 0, this.width, this.height, -1072689136, -804253680);
		if(mode > -1) {
			 RenderUtils.gradientRender(this.width / 2 - 90, this.height / 4 + 30, this.width / 2 + 90, this.height / 4 + 32 + (24 * 4), this.instance.CONFIG.showBlockRGB[mode], this.instance.CONFIG.showBlockRGB[mode]);
			 for(TextFieldNum t : textFields1)
		        	t.drawTextBox();
		}
		this.drawSuper(a, b, f);
		this.drawCenteredString(this.mc.fontRenderer, "Colour Settings", this.width / 2, 40, 0xFFFFFF);
		if(eskerty)
		{
			this.drawCenteredString(this.mc.fontRenderer, "Please turn off Transparency or/and Modern Style", this.width / 2, 15, 0xFF0000);
			this.drawCenteredString(this.mc.fontRenderer, "in order to manage last two options!", this.width / 2, 25, 0xFF0000);
		}
		
		if(updateS)
			this.mc.fontRenderer.drawStringWithShadow("Сhanges have been saved!", 5, 5, 0xFFD800);
		
		debug1.doBlockRendering(this.width / 2, this.height * 6 / 8 + 20, 1);
	}
	
	

}