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
import kz.chesschicken.smartygui.commonloader.guiframework.ValueXY;

public class WidgetButtonA extends WidgetButton {
	
	@FunctionalInterface
	public static interface Action1 {
		void onActivate();
	}
	
	protected final Action1 action;
	
	public WidgetButtonA(String text, BinaryIntFunction<ValueXY> f, Action1 a) {
		super(text, f);
		this.action = a;
	}
	
	public WidgetButtonA(String text, int w, int h, BinaryIntFunction<ValueXY> f, Action1 a) {
		super(text, w, h, f);
		this.action = a;
	}
	
	public WidgetButtonA(int w, int h, String text, BinaryIntFunction<ValueXY> f, Action1 a) {
		super(text, f);
		this.action = a;
	}

	@Override
	public void onActivate() {
		if(this.action != null)
			this.action.onActivate();
	}

}
