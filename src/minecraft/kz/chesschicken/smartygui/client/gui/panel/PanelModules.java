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

import kz.chesschicken.smartygui.common.SmartyGUI;
import kz.chesschicken.smartygui.commonloader.GameUtils;
import kz.chesschicken.smartygui.commonloader.RenderUtils;
import kz.chesschicken.smartygui.commonloader.guiframework.ValueXY;
import kz.chesschicken.smartygui.commonloader.guiframework.api.BasePanel;
import kz.chesschicken.smartygui.commonloader.guiframework.api.IRunQ1W9M;
import kz.chesschicken.smartygui.commonloader.guiframework.widgets.WidgetButtonAction;
import kz.chesschicken.smartygui.commonloader.guiframework.widgets.WidgetButtonBoolean;
import kz.chesschicken.smartygui.commonloader.guiframework.widgets.WidgetButtonTransparent;

public class PanelModules extends BasePanel {
    private final SmartyGUI instance;

    public PanelModules(SmartyGUI smartyGUI) {
        this.instance = smartyGUI;

        add(new WidgetButtonTransparent("Credits...", (w, h) -> new ValueXY(2, h - 10), () -> GameUtils.openPanel(new PanelCredits())));
        add(new WidgetButtonBoolean("Block/Entity Viewer HUD", 182, 20, (w, h) -> new ValueXY(w / 2 - 91, h / 4 + 8),
                instance.CONFIG.enableShowBlock, aBoolean -> instance.CONFIG.enableShowBlock = aBoolean));
        add(new WidgetButtonBoolean("Armor Status HUD", 182, 20, (w, h) -> new ValueXY(w / 2 - 91, h / 4 + 32),
                instance.CONFIG.enableArmorStatusHUD, aBoolean -> instance.CONFIG.enableArmorStatusHUD = aBoolean));
        add(new WidgetButtonBoolean("In-Game ToolTip", 182, 20, (w, h) -> new ValueXY(w / 2 - 91, h / 4 + 56),
                instance.CONFIG.enableInGameToolTip, aBoolean -> instance.CONFIG.enableInGameToolTip = aBoolean));
        add(new WidgetButtonAction("Save and Close", 182, 20, (w, h) -> new ValueXY(w / 2 - 91, h / 4 + 104), () -> {
            getComponents().stream().filter(c -> c instanceof IRunQ1W9M).forEach(a -> ((IRunQ1W9M) a).runQ1W9M());
            instance.CONFIG.forceSave();
            instance.updateASHUD();
            GameUtils.openPanel(new PanelHome(instance));
        }));
    }

    @Override
    public void render(int q, int w, float e) {
        RenderUtils.gradientRender(0, 0, this.width, this.height, -1072689136, -804253680);
        super.render(q, w, e);
        RenderUtils.renderShadowCenteredString(this.width / 2, 40, 0xFFFFFF, "Modules Options");
    }
}
