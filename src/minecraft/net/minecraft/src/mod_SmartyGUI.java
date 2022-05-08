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
import kz.chesschicken.smartygui.modloader.IDeFabricated;
import kz.chesschicken.smartygui.modloader.IModMenuDesc;
import net.minecraft.client.Minecraft;

public class mod_SmartyGUI extends BaseMod implements IModMenuDesc {
    @Override
    public String Version() {
        return "ML-2.2";
    }

    private static IDeFabricated<mod_SmartyGUI> modInstance;

    public mod_SmartyGUI() {
        modInstance = new SmartyGUI();
        modInstance.onInitializeClient(this);
        ModLoader.SetInGameHook(this, true, false);
        ModLoader.SetInGUIHook(this, true, false);
    }
    
    public static void addPlugin(AbstractSmartyPlugin plugin) {
    	((SmartyGUI)modInstance).PLUGINS.registerPlugin(plugin);
    }

    @Override
    public boolean OnTickInGame(Minecraft game) {
        modInstance.onTickGame(game);
        return true;
    }

    @Override
    public boolean OnTickInGUI(Minecraft game, GuiScreen gui) {
        modInstance.onTickInGUI(game, gui);
        return true;
    }
    
    @Override
    public void KeyboardEvent(KeyBinding event) {
    	modInstance.keyPressed(event);
    }

	@Override
	public String Name() {
		return null;
	}

	@Override
	public String Description() {
		return "";
	}

	@Override
	public String Icon() {
		// TODO Auto-generated method stub
		return null;
	}

}
