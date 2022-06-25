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
package smartygui.plugins.vanilla;

import kz.chesschicken.smartygui.commonloader.IMod;
import kz.chesschicken.smartygui.commonloader.ModDescription;
import net.minecraft.src.mod_SmartyGUI;

@ModDescription(
		name = "SmartyGUI - Vanilla Plugin Pack",
		description = "A set of plugins for vanilla to enchance the gameplay with SmartyGUI.",
		version = "0.1 - for SmartyGUI ML-2.3",
		icon = "/smartygui/icon.png"
		)
public class SmartyGUIVanilla implements IMod<SmartyGUIVanilla> {

	@Override
	public void onInitializeClient() {
	}

	@Override
	public void onPostInitClient() {
		mod_SmartyGUI.addPlugin(new PluginShowBreaking());
		mod_SmartyGUI.addPlugin(new PluginVanilla());
	}

}
