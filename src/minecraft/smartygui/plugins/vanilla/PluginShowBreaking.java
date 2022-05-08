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

import kz.chesschicken.smartygui.common.SmartyGUI;
import kz.chesschicken.smartygui.common.plugins.AbstractSmartyPlugin;
import kz.chesschicken.smartygui.common.plugins.event.EnumEventTypes;
import kz.chesschicken.smartygui.common.plugins.event.GetClassifiedEvents;
import kz.chesschicken.smartygui.common.plugins.event.IAdditionalBlockDescription;
import net.minecraft.client.Minecraft;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;

@GetClassifiedEvents({EnumEventTypes.ADDITIONAL_BLOCK_DESCRIPTION})
public class PluginShowBreaking extends AbstractSmartyPlugin implements IAdditionalBlockDescription {
	
	private Minecraft mc;
	
	@Override
	public void methodInitPlugin(SmartyGUI instance) {
	}

	@Override
	public String getPluginName() {
		return "vanilla_breaking";
	}
	
	@Override
	public String[] getAdditionalBlockDescription(int id, int meta, World world, int x, int y, int z) {
		if(this.mc == null)
			this.mc = ModLoader.getMinecraftInstance();

		if(this.mc.ingameGUI.damageGuiPartialTime > 0.0F) {
			return new String[] {"Breaking: " + Math.round(this.mc.ingameGUI.damageGuiPartialTime * 100) + "%"};
		}
		
		return null;
	}


}
