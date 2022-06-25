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
import kz.chesschicken.smartygui.commonloader.GameUtils;
import kz.chesschicken.smartygui.commonloader.guiframework.ValueXY;

public class WidgetButtonAction extends WidgetButton {

	@FunctionalInterface
	public interface IAction {
		void onActivate();
	}
	
	protected final IAction action;
	
	public WidgetButtonAction(String text, BinaryIntFunction<ValueXY> f, IAction a) {
		super(text, f);
		this.action = a;
	}
	
	public WidgetButtonAction(String text, int w, int h, BinaryIntFunction<ValueXY> f, IAction a) {
		super(text, w, h, f);
		this.action = a;
	}
	
	public WidgetButtonAction(int w, int h, String text, BinaryIntFunction<ValueXY> f, IAction a) {
		super(text, f);
		this.action = a;
	}

	@Override
	public void typeKey(char c, int i) {

	}

	@Override
	public void clickMouse(int mX, int mY, int mEvent) {
		if(isHovered(mX, mY) && mEvent == 0 && this.action != null && this.active) {
			GameUtils.playSoundFX("random.click", 1.0F, 1.0F);
			this.action.onActivate();
		}
	}

}
