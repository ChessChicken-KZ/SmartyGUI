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
package net.minecraft.src;

import smartygui.plugins.vanilla.PluginShowBreaking;
import smartygui.plugins.vanilla.PluginVanilla;

public class mod_SmartyGUI_VPl extends BaseMod {

	public mod_SmartyGUI_VPl() {
	}
	
	@Override
	public void ModsLoaded() {
		mod_SmartyGUI.addPlugin(new PluginShowBreaking());
		mod_SmartyGUI.addPlugin(new PluginVanilla());
	}
	
	@Override
	public String Version() {
		return "0.1v - for SmartyGUI ML2.2";
	}

}
