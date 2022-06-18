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
package kz.chesschicken.smartygui.commonloader.modloader;

import kz.chesschicken.smartygui.commonloader.IMod;
import net.minecraft.client.Minecraft;
import net.minecraft.src.BaseMod;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.KeyBinding;
import net.minecraft.src.ModLoader;

public abstract class BaseModExtended<T extends IMod<T>> extends BaseMod {

	protected T instance;
	
	public BaseModExtended(T t_holder) {
		this.instance = t_holder;
		this.instance.onInitializeClient();
        ModLoader.SetInGameHook(this, true, false);
        ModLoader.SetInGUIHook(this, true, false);
	}
	
    @Override
    public boolean OnTickInGame(Minecraft game) {
    	instance.onTickGame(game);
        return true;
    }

    @Override
    public boolean OnTickInGUI(Minecraft game, GuiScreen gui) {
    	instance.onTickInGUI(game, gui);
        return true;
    }
    
    @Override
    public void KeyboardEvent(KeyBinding event) {
    	instance.keyPressed(event.keyCode);
    }

	@Override
	public void ModsLoaded() {
		instance.onPostInitClient();
	}

	/* Description methods. */
	
    @Override
    public String Version() {
        return instance.getVersion(instance.getClass());
    }
    
	public String Name() {
		return instance.getName(instance.getClass());
	}

	public String Description() {
		return instance.getDescription(instance.getClass());
	}

	public String Icon() {
		return instance.getIcon(instance.getClass());
	}

}
