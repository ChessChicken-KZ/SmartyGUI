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

import kz.chesschicken.smartygui.common.SmartyGUI;
import kz.chesschicken.smartygui.common.plugins.AbstractSmartyPlugin;
import kz.chesschicken.smartygui.commonloader.modloader.BaseModExtended;

public class mod_SmartyGUI extends BaseModExtended<SmartyGUI> {

	private static mod_SmartyGUI ML_INSTANCE;
	
    /**
     * A way to load a plugin into SmartyGUI
     * @param plugin Plugin Instance.
     */
    public static void addPlugin(AbstractSmartyPlugin plugin) {
    	if(plugin == null) return;
    	ML_INSTANCE.instance.PLUGINS.registerPlugin(plugin);
    }
    
    public mod_SmartyGUI() {
    	super(new SmartyGUI());
    	ModLoader.RegisterKey(this, SmartyGUI.openConfigKeyBind, false);
    	ModLoader.RegisterKey(this, SmartyGUI.toggleGUI, false);
    	ML_INSTANCE = this;
    }

	@Override
	public String Version() {
		return super.Version();
	}

	@Override
	public String Name() {
		return super.Name();
	}
	
	@Override
	public String Description() {
		return super.Description();
	}

	@Override
	public String Icon() {
		return super.Icon();
	}
}
