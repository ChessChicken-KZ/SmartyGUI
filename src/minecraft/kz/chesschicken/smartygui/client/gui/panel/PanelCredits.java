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
package kz.chesschicken.smartygui.client.gui.panel;

import kz.chesschicken.smartygui.commonloader.GameUtils;
import kz.chesschicken.smartygui.commonloader.RenderUtils;
import kz.chesschicken.smartygui.commonloader.guiframework.ValueXY;
import kz.chesschicken.smartygui.commonloader.guiframework.api.BasePanel;
import kz.chesschicken.smartygui.commonloader.guiframework.widgets.WidgetButtonAction;
import org.lwjgl.Sys;

public class PanelCredits extends BasePanel {
    private static final String[] authors = new String[]{
            "ChessChicken - author of the main idea and lead developer.",
            "WaterfallFlower - bug and code maintainer."
    };

    public PanelCredits() {
        super();
        add(new WidgetButtonAction("Close", 182, 20, (w, h) -> new ValueXY(w / 2 - 91, h / 4 + 104), GameUtils::closeGUI));
        add(new WidgetButtonAction("GitHub", 59, 20, (w, h) -> new ValueXY(w / 2 - 91, h / 4 + 126), () -> Sys.openURL("https://github.com/ChessChicken-KZ/SmartyGUI")));
        add(new WidgetButtonAction("Twitter", 59, 20, (w, h) -> new ValueXY(w / 2 - 91, h / 4 + 126), () -> Sys.openURL("https://twitter.com/ChessChicken_KZ")));
        add(new WidgetButtonAction("A Button :)", 59, 20, (w, h) -> new ValueXY(w / 2 + 32, h / 4 + 126), () -> { /* TODO: Add something here... */ }));
    }

    @Override
    public void render(int q, int w, float e) {
        RenderUtils.gradientRender(0, 0, this.width, this.height, -1072689136, -804253680);
        super.render(q, w, e);
        RenderUtils.renderShadowCenteredString(this.width / 2, 40, 0xFFFFFF, "SmartyGUi Credits");

        for (int i = 0; i < authors.length; i++) {
            RenderUtils.renderShadowCenteredString(this.width / 2, 55 + (12 * i), 0xFFFF00, authors[i]);
        }
    }
}
