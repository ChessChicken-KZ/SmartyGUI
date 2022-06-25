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
import kz.chesschicken.smartygui.commonloader.guiframework.api.AbstractComponent;
import kz.chesschicken.smartygui.commonloader.guiframework.api.IControllerInput;
import kz.chesschicken.smartygui.commonloader.guiframework.api.ITickUpdate;
import kz.chesschicken.smartygui.commonloader.guiframework.api.IUpdateOnResize;
import net.minecraft.src.ChatAllowedCharacters;
import net.minecraft.src.GuiScreen;

public class WidgetTextField extends AbstractComponent implements ITickUpdate, IControllerInput, IUpdateOnResize {
    public boolean isEnabled = true;
    protected boolean isFocused = false;
    protected int maxLength;

    protected int tick1;
    protected final BinaryIntFunction<ValueXY> resizeFunc;
    protected String text = "";

    public WidgetTextField(int w, int h, BinaryIntFunction<ValueXY> f) {
        this.resizeFunc = f;
        this.width = w;
        this.height = h;
    }

    public WidgetTextField(String t, int w, int h, BinaryIntFunction<ValueXY> f) {
        this(w, h, f);
        this.text = t;
    }

    @Override
    public void render(int i, int i1, float d) {
        RenderUtils.gradientRenderRGB(getContX() - 1, getContY() - 1, getContX() + this.width + 1, getContY() + this.height + 1, -6250336);
        RenderUtils.gradientRenderRGB(getContX(), getContY(), getContX() + this.width, getContY() + this.height, -16777216);
        RenderUtils.renderString(getContX() + 4, getContY() + (this.height - 8) / 2, this.isEnabled ? 14737632 : 7368816, this.isEnabled ? (this.text + (this.isFocused && this.tick1 / 6 % 2 == 0 ? "_" : "")) : (this.text));
    }

    @Override
    public void onClose() {
    }

    @Override
    public void update() {
        ++this.tick1;
    }

    @Override
    public void typeKey(char c, int i) {
        if (this.isEnabled && this.isFocused) {
            if (c == 22) {
                String clipboardString = GuiScreen.getClipboardString();
                if (clipboardString == null)
                    clipboardString = "";
                int check1 = 32 - this.text.length();
                if (check1 > clipboardString.length())
                    check1 = clipboardString.length();
                if (check1 > 0)
                    this.text = this.text + clipboardString.substring(0, check1);
            }
            if (c == 14 && this.text.length() > 0)
                this.text = this.text.substring(0, this.text.length() - 1);
            if (ChatAllowedCharacters.allowedCharacters.indexOf(c) >= 0 && (this.text.length() < this.maxLength || this.maxLength == 0))
                this.text = this.text + c;
        }
    }

    @Override
    public void clickMouse(int mX, int mY, int mEvent) {
        boolean event = this.isEnabled && isHovered(mX, mY);
        if(event && !this.isFocused)
            tick1 = 0;
        this.isFocused = event;
    }

    @Override
    public void updateOnResize(int newWidth, int newHeight) {
        this.setXY(this.resizeFunc.apply(newWidth, newHeight));
    }

    public void setMaxLength(int q) {
        this.maxLength = q;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String s) {
        this.text = s;
    }
}
