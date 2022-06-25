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

import java.util.function.Function;
import java.util.function.Supplier;

import kz.chesschicken.smartygui.client.components.ModuleBlockRender;
import kz.chesschicken.smartygui.common.SmartyGUI;
import kz.chesschicken.smartygui.commonloader.GameUtils;
import kz.chesschicken.smartygui.commonloader.RenderUtils;
import kz.chesschicken.smartygui.commonloader.guiframework.*;
import kz.chesschicken.smartygui.commonloader.guiframework.api.AbstractComponent;
import kz.chesschicken.smartygui.commonloader.guiframework.api.BasePanel;
import kz.chesschicken.smartygui.commonloader.guiframework.api.IRunQ1W9M;
import kz.chesschicken.smartygui.commonloader.guiframework.widgets.*;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;

public class LambdaPanels {

	public static Supplier<BasePanel> CREDITS_GUI = () -> new BasePanel(gui -> {
		gui.add(new WidgetButtonA("Close", 182, 20, (w, h) -> new ValueXY(w / 2 - 91, h / 4 + 104), GameUtils::closeGUI));
		gui.add(new WidgetButtonA("GitHub", 59, 20, (w, h) -> new ValueXY(w / 2 - 91, h / 4 + 126), () -> Sys.openURL("https://github.com/ChessChicken-KZ/SmartyGUI")));
		gui.add(new WidgetButtonA("Twitter", 59, 20, (w, h) -> new ValueXY(w / 2 - 91, h / 4 + 126), () -> Sys.openURL("https://twitter.com/ChessChicken_KZ")));
		gui.add(new WidgetButtonA("A Button :)", 59, 20, (w, h) -> new ValueXY(w / 2 + 32, h / 4 + 126), () -> { /* TODO: Add something here... */ }));
	}) {
		private final String[] authors = new String[] {
				"ChessChicken - author of the main idea and lead developer.",
				"WaterfallFlower - bug and code maintainer."
		};

		@Override
		public void render(int q, int w, float e) {
			RenderUtils.gradientRender(0, 0, this.width, this.height, -1072689136, -804253680);
			super.render(q, w, e);
			RenderUtils.renderShadowCenteredString(this.width / 2, 40, 0xFFFFFF, "SmartyGUi Credits");

			for(int i = 0; i < authors.length; i++) {
				RenderUtils.renderShadowCenteredString(this.width / 2, 55 + (12 * i), 0xFFFF00, authors[i]);
			}
		}
	};

	public static class COLOR_CONFIG extends BasePanel implements IRunQ1W9M {

		private final WidgetTextFieldNum[] textFields1 = new WidgetTextFieldNum[4];

		/** 0 - text. 1 - 1st colour. 2 - 2nd colour. */
		private byte mode = -1;
		/** Debug instance of ModuleBlockRender. */
		private ModuleBlockRender d;

		private int[] guiColours;
		private int[] backupA;
		private final WidgetButton[] buttonsMove = new WidgetButton[8];
		private boolean eskerty;
		private boolean updateS;

		private final SmartyGUI instance;

		void inner$501_502_503(int id) {
			this.mode = (byte) id;
			for(AbstractComponent o : this.components)
				if(o instanceof WidgetButton)
					((WidgetButton)o).active = true;
			__checkOptions();

			if(this.mode > -1) {
				for(int i = 0; i < 4; i++) {
					this.textFields1[i].isEnabled = true;
					this.textFields1[i].setText(String.valueOf(this.guiColours[i + (4 * mode)]));
				}
				for(WidgetButton b : buttonsMove)
					b.active = true;
			}
		}

		void inner$revert() {
			this.instance.CONFIG.showBlockRGB[0] = this.backupA[0];
			this.instance.CONFIG.showBlockRGB[1] = this.backupA[1];
			this.instance.CONFIG.showBlockRGB[2] = this.backupA[2];
			__updateValues();
			for(int i = 0; i < 4; i++) {
				this.textFields1[i].setText(String.valueOf(this.guiColours[i + (4 * mode)]));
			}
		}

		void inner$510_511_512_513_514_515_516_517(int id) {
			if(id < 4)
				this.textFields1[id].setText(__parseNumString(this.textFields1[id].getText(), -1));
			else
				this.textFields1[id - 4].setText(__parseNumString(this.textFields1[id - 4].getText(), 1));
			runQ1W9M();
		}

		public COLOR_CONFIG(SmartyGUI smartygui) {
			this.instance = smartygui;
			__updateValues();
			this.backupA = new int[] { smartygui.CONFIG.showBlockRGB[0], smartygui.CONFIG.showBlockRGB[1], smartygui.CONFIG.showBlockRGB[2] };

			Keyboard.enableRepeatEvents(true);

			this.d = new ModuleBlockRender(GameUtils.getMC(), instance);
			this.d.__updateBlockDebug();

			add(new WidgetButtonA("1st Colour", 59, 20, (w, h) -> new ValueXY(w / 2 - 91, h / 4 + 8), () -> inner$501_502_503(0)));
			add(new WidgetButtonA("2nd Colour", 59, 20, (w, h) -> new ValueXY(w / 2 - 30, h / 4 + 8), () -> inner$501_502_503(1)));
			add(new WidgetButtonA("Text", 59, 20, (w, h) -> new ValueXY(w / 2 + 31, h / 4 + 8), () -> inner$501_502_503(2)));

			add(new WidgetButtonA("Save", 59, 20, (w, h) -> new ValueXY(w / 2 - 30, h / 4 + 8 - 22), () -> this.instance.CONFIG.forceSave()));
			add(new WidgetButtonA("Revert", 59, 20, (w, h) -> new ValueXY(w / 2 - 91, h / 4 + 8 - 22), this::inner$revert));
			add(new WidgetButtonA("Reload", 59, 20, (w, h) -> new ValueXY(w / 2 + 31, h / 4 + 8 - 22), this::__updateValues));

			for(byte b = 0; b < 4; b++) {
				byte finalB = b;
				add(textFields1[b] = new WidgetTextFieldNum(50, 20, (w, h) -> new ValueXY(w / 2 - 25, h / 4 + 32 + (finalB * 24)), this));
				textFields1[b].setMaxLength(3);
				textFields1[b].isEnabled = false;
				/* 510 511 512 513 */ add(buttonsMove[b] = new WidgetButtonA("-", 20, 20, (w, h) -> new ValueXY(w / 2 + 27, h / 4 + 32 + (24 * finalB)), () -> inner$510_511_512_513_514_515_516_517(finalB)));
				/* 514 515 516 517 */ add(buttonsMove[b + 4] = new WidgetButtonA("+", 20, 20, (w, h) -> new ValueXY(this.width / 2 - 47, this.height / 4 + 32 + (24 * finalB)), () -> inner$510_511_512_513_514_515_516_517(finalB + 4)));
			}

			for(WidgetButton b : buttonsMove)
				b.active = false;

			__checkOptions();
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
			((WidgetButton)this.components.get(1)).active = ((WidgetButton)this.components.get(2)).active = !eskerty;
		}

		String __parseNumString(String d, int a) {
			if(d.length() < 1) d = "0";
			int q = (Integer.parseInt(d) + a);
			if(q > 255) return "255";
			if(q < 0) return "0";
			return String.valueOf(q);
		}

		@Override
		public void runQ1W9M() {
			for(int i = 0; i < 4; i++) {
				if(this.textFields1[i].getText().length() < 1)
					this.textFields1[i].setText("0");
				this.guiColours[i + (4 * mode)] = Integer.parseInt(this.textFields1[i].getText());
			}
			this.instance.CONFIG.showBlockRGB[0] = RenderUtils.getIntFromRGBA(this.guiColours[0], this.guiColours[1], this.guiColours[2], this.guiColours[3]);
			this.instance.CONFIG.showBlockRGB[1] = RenderUtils.getIntFromRGBA(this.guiColours[4], this.guiColours[5], this.guiColours[6], this.guiColours[7]);
			this.instance.CONFIG.showBlockRGB[2] = RenderUtils.getIntFromRGBA(this.guiColours[8], this.guiColours[9], this.guiColours[10], this.guiColours[11]);
		}

		@Override
		public void render(int a, int b, float f) {
			RenderUtils.gradientRender(0, 0, this.width, this.height, -1072689136, -804253680);
			if(mode > -1) {
				RenderUtils.gradientRender(this.width / 2 - 90, this.height / 4 + 30, this.width / 2 + 90, this.height / 4 + 32 + (24 * 4), this.instance.CONFIG.showBlockRGB[mode], this.instance.CONFIG.showBlockRGB[mode]);
				for(WidgetTextFieldNum t : textFields1)
					t.render(a, b, f);
			}
			super.render(a, b, f);
			RenderUtils.renderShadowCenteredString(this.width / 2, 40, 0xFFFFFF, "Colour Settings");
			if(eskerty) {
				RenderUtils.renderShadowCenteredString(this.width / 2, 15, 0xFF0000, "Please turn off Transparency or/and Modern Style");
				RenderUtils.renderShadowCenteredString(this.width / 2, 25, 0xFF0000, "in order to manage background colour options!");
			}

			if(updateS)
				RenderUtils.renderShadowString(5, 5, 0xFFD800, "Changes have been saved!");

			d.doBlockRendering(this.width / 2, this.height * 6 / 8 + 20, 1);
		}

		@Override
		public void onClose() {
			Keyboard.enableRepeatEvents(false);
		}
	}
	
	public static Function<SmartyGUI, BasePanel> MODULES_GUI = (instance) -> new BasePanel(gui -> {
		gui.add(new WidgetButtonTransparent("Credits...", (w, h) -> new ValueXY(2, h - 10), () -> GameUtils.openPanel(CREDITS_GUI)));

		gui.add(new WidgetButtonBoolean("Block/Entity Viewer HUD", 182, 20, (w, h) -> new ValueXY(w / 2 - 91, h / 4 + 8),
				instance.CONFIG.enableShowBlock, aBoolean -> instance.CONFIG.enableShowBlock = aBoolean));
		gui.add(new WidgetButtonBoolean("Armor Status HUD", 182, 20, (w, h) -> new ValueXY(w / 2 - 91, h / 4 + 32),
				instance.CONFIG.enableArmorStatusHUD, aBoolean -> instance.CONFIG.enableArmorStatusHUD = aBoolean));
		gui.add(new WidgetButtonBoolean("In-Game ToolTip", 182, 20, (w, h) -> new ValueXY(w / 2 - 91, h / 4 + 56),
				instance.CONFIG.enableInGameToolTip, aBoolean -> instance.CONFIG.enableInGameToolTip = aBoolean));
		gui.add(new WidgetButtonA("Save and Close", 182, 20, (w, h) -> new ValueXY(w / 2 - 91, h / 4 + 104), () -> {
			gui.getComponents().stream().filter(c -> c instanceof IRunQ1W9M).forEach(a -> ((IRunQ1W9M)a).runQ1W9M());
			instance.CONFIG.forceSave();
			instance.updateASHUD();
			GameUtils.openPanel(LambdaPanels.HOME_GUI.apply(instance));
		}));
	}) {
		@Override
		public void render(int q, int w, float e) {
            RenderUtils.gradientRender(0, 0, this.width, this.height, -1072689136, -804253680);
			super.render(q, w, e);
			RenderUtils.renderShadowCenteredString(this.width / 2, 40, 0xFFFFFF, "Modules Options");
		}
	};

	public static Function<SmartyGUI, BasePanel> HOME_GUI = (instance) -> new BasePanel(gui -> {
		gui.add(new WidgetButtonTransparent("Credits...", (w, h) -> new ValueXY(2, h - 10), () -> GameUtils.openPanel(CREDITS_GUI)));

		gui.add(new WidgetButtonA("Modules", 90, 20, (w, h) -> new ValueXY(w / 2 - 91, h / 4 + 32), () -> GameUtils.openPanel(MODULES_GUI.apply(instance))));
		gui.add(new WidgetButtonA("Location Settings", 182, 20, (w, h) -> new ValueXY(w / 2 - 91, h / 4 + 8), () -> GameUtils.open(new GuiDragInterface(instance))));
		gui.add(new WidgetButtonA("Appearance", 90, 20, (w, h) -> new ValueXY(w / 2 + 1, h / 4 + 32), () -> GameUtils.open(new GuiAppearanceConfig(instance))));
		gui.add(new WidgetButtonA("Colour Settings", 182, 20, (w, h) -> new ValueXY(w / 2 - 91, h / 4 + 56), () -> GameUtils.openPanel(new COLOR_CONFIG(instance))));
		gui.add(new WidgetButtonA("Save and Close", 182, 20, (w, h) -> new ValueXY(w / 2 - 91, h / 4 + 104), GameUtils::closeGUI));
	}) {
		@Override
		public void render(int q, int w, float e) {
            RenderUtils.gradientRender(0, 0, this.width, this.height, -1072689136, -804253680);
			super.render(q, w, e);
			RenderUtils.renderShadowCenteredString(this.width / 2, 40, 0xFFFFFF, "SmartyGUI Options");
			RenderUtils.renderShadowString(2, 2, 0xFFFFFF, "Instance: " + instance);
		}
	};
}
