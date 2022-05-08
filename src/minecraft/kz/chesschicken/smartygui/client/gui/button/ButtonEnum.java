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
package kz.chesschicken.smartygui.client.gui.button;

public class ButtonEnum<T extends Enum<T>> extends ButtonBase {
	
	public T currentEnum;
	public String afString;

	public ButtonEnum(int id, int x, int y, String t) {
		super(id, x, y, t);
		this.afString = t;
	}

	public ButtonEnum(int id, int x, int y, int w, int h, String t) {
		super(id, x, y, w, h, t);
		this.afString = t;
	}
	
	public void changeEnum(T e) {
		this.currentEnum = e;
		this.displayString = afString + ": " + this.currentEnum.toString();
	}

}
