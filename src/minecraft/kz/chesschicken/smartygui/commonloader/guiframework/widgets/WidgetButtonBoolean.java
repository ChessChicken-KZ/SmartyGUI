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
import kz.chesschicken.smartygui.commonloader.guiframework.IRunQ1W9M;
import kz.chesschicken.smartygui.commonloader.guiframework.ValueXY;

import java.util.function.Consumer;

public class WidgetButtonBoolean extends WidgetButton implements IRunQ1W9M {
	
	protected boolean value;
	protected final String newText;
	protected final Consumer<Boolean> config;

	public WidgetButtonBoolean(String text, BinaryIntFunction<ValueXY> f, boolean a, Consumer<Boolean> q) {
		super("", f);
		this.value = a;
		this.newText = text;
		this.config = q;
		this.text = formatName();
	}
	
	public WidgetButtonBoolean(String text, int w, int h, BinaryIntFunction<ValueXY> f, boolean a, Consumer<Boolean> q) {
		super("", w, h, f);
		this.value = a;
		this.newText = text;
		this.config = q;
		this.text = formatName();
	}

	@Override
	public void onActivate() {
		this.value = !this.value;
		this.text = formatName();
	}
	
	public String formatName() {
		return this.newText + ": " + (value ? "ON" : "OFF");
	}

	@Override
	public void runQ1W9M() {
		this.config.accept(this.value);
	}
}
