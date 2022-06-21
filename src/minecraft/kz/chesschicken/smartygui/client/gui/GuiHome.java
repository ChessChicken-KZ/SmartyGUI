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

import kz.chesschicken.smartygui.common.SmartyGUI;
import kz.chesschicken.smartygui.commonloader.GameUtils;
import kz.chesschicken.smartygui.commonloader.RenderUtils;
import kz.chesschicken.smartygui.commonloader.guiframework.BasePanel;
import kz.chesschicken.smartygui.commonloader.guiframework.ValueXY;
import kz.chesschicken.smartygui.commonloader.guiframework.widgets.WidgetButtonA;

public class GuiHome {
	
	public static Function<SmartyGUI, BasePanel> HOME_GUI = (instance) -> new BasePanel(gui -> {
		gui.add(new WidgetButtonA("Modules", 90, 20, (w, h) -> new ValueXY(w / 2 - 91, h / 4 + 32), () -> GameUtils.open(new GuiModules(instance))));
		gui.add(new WidgetButtonA("Location Settings", 182, 20, (w, h) -> new ValueXY(w / 2 - 91, h / 4 + 8), () -> GameUtils.open(new GuiDragInterface(instance))));
		gui.add(new WidgetButtonA("Appearance", 90, 20, (w, h) -> new ValueXY(w / 2 + 1, h / 4 + 32), () -> GameUtils.open(new GuiAppearanceConfig(instance))));
		gui.add(new WidgetButtonA("Colour Settings", 182, 20, (w, h) -> new ValueXY(w / 2 - 91, h / 4 + 56), () -> GameUtils.open(new GuiColourConfig(instance))));
		gui.add(new WidgetButtonA("Save and Close", 182, 20, (w, h) -> new ValueXY(w / 2 - 91, h / 4 + 104), () -> GameUtils.open(null)));
	}) {
		@Override
		public void render(int q, int w, float e) {
            RenderUtils.gradientRender(0, 0, this.width, this.height, -1072689136, -804253680);
			super.render(q, w, e);
			RenderUtils.renderShadowCenteredString(this.width / 2, 40, 0xFFFFFF, "SmartyGUI Options");
		}
	};

}
