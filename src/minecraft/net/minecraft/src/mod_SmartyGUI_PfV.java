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

import kz.chesschicken.smartygui.modloader.IModMenuDesc;
import smartygui.plugins.vanilla.PluginShowBreaking;
import smartygui.plugins.vanilla.PluginVanilla;

public class mod_SmartyGUI_PfV extends BaseMod implements IModMenuDesc {

	public mod_SmartyGUI_PfV() {
	}
	
	@Override
	public void ModsLoaded() {
		mod_SmartyGUI.addPlugin(new PluginShowBreaking());
		mod_SmartyGUI.addPlugin(new PluginVanilla());
	}
	
	@Override
	public String Version() {
		return "0.1v - for SmartyGUI ML-2.2";
	}

	@Override
	public String Name() {
		return "SmartyGUI - Plugins for Vanilla";
	}

	@Override
	public String Description() {
		return "A set of plugins for vanilla to enchance the gameplay with SmartyGUI.";
	}

	@Override
	public String Icon() {
		return "/smartygui/icon.png";
	}

}
