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
package kz.chesschicken.smartygui.common;

/**
 * Enum for themes.
 * Themes should be hardcoded, NO any flexible ways to add custom content things.
 */
public enum EnumTheme {
	default_theme("Pixel 8-BIT (Default)", "/smartygui/theme/default/gui.png"),
	dark_theme("Dark Theme", "/smartygui/theme/dark/gui.png");
	
	public final String theme_name;
	public final String theme_gui;
	
	EnumTheme(String a, String b) {
		this.theme_name = a;
		this.theme_gui = b;
	}
	
	@Override
	public String toString() {
		return this.theme_name;
	}
}
