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
import kz.chesschicken.smartygui.commonloader.guiframework.api.IUpdateOnResize;
import org.lwjgl.opengl.GL11;

public abstract class WidgetButton extends AbstractComponent implements IUpdateOnResize, IControllerInput {
	
	protected String text;
	public boolean active = true;
	protected final BinaryIntFunction<ValueXY> resizeFunc;
	
	public WidgetButton(String text, BinaryIntFunction<ValueXY> f) {
		this(text, 200, 20, f);
	}
	
	public WidgetButton(String text, int w, int h, BinaryIntFunction<ValueXY> f) {
		this.text = text;
		this.resizeFunc = f;
		this.width = w;
		this.height = h;
	}

	public void setText(String s) {
		this.text = s;
	}

	@Override
	public void render(int var2, int var3, float d) {
        RenderUtils.bindTexture("/gui/gui.png");
        boolean isHovered = this.isHovered(var2, var3);
        byte renderState = (byte) (!this.active ? 0 : (isHovered ? 2 : 1));
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderUtils.renderTexture(getContX(), getContY(), 0, 46 + renderState * 20, this.width / 2, this.height);
        RenderUtils.renderTexture(getContX() + this.width / 2, getContY(), 200 - this.width / 2, 46 + renderState * 20, this.width / 2, this.height);
    	RenderUtils.renderShadowCenteredString(getContX() + this.width / 2, getContY() + (this.height - 8) / 2, !this.active ? -6250336 : (isHovered ? 16777120 : 14737632), this.text);
	}

	@Override
	public void updateOnResize(int newWidth, int newHeight) {
		this.setXY(this.resizeFunc.apply(newWidth, newHeight));
	}

	@Override
	public void onClose() {

	}
}
