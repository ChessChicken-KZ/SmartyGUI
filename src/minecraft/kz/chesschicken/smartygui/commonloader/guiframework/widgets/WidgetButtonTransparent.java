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
package kz.chesschicken.smartygui.commonloader.guiframework.widgets;

import kz.chesschicken.smartygui.commonloader.BinaryIntFunction;
import kz.chesschicken.smartygui.commonloader.RenderUtils;
import kz.chesschicken.smartygui.commonloader.guiframework.ValueXY;

public class WidgetButtonTransparent extends WidgetButtonAction {

    public WidgetButtonTransparent(String text, BinaryIntFunction<ValueXY> f, IAction a) {
        super(text, RenderUtils.getStringSize(text), 8, f, a);
    }

    @Override
    public void render(int a, int b, float d) {
        RenderUtils.renderShadowCenteredString(getContX() + this.width / 2, getContY() + (this.height - 8) / 2, (!this.active) ? -6250336 : (isHovered(a, b) ? 16777120 : 14737632), this.text);
    }
}
