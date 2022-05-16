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

import org.lwjgl.input.Keyboard;

import kz.chesschicken.smartygui.common.RenderUtils;
import kz.chesschicken.smartygui.common.SmartyGUI;
import kz.chesschicken.smartygui.common.plugins.AbstractSmartyPlugin;
import kz.chesschicken.smartygui.common.plugins.event.EnumEventTypes;
import kz.chesschicken.smartygui.common.plugins.event.GetClassifiedEvents;
import kz.chesschicken.smartygui.common.plugins.event.IAdditionalBlockDescription;
import kz.chesschicken.smartygui.common.plugins.event.IOverrideBlockRender;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemRecord;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.RenderItem;
import net.minecraft.src.TileEntityRecordPlayer;
import net.minecraft.src.TileEntitySign;
import net.minecraft.src.World;

@GetClassifiedEvents({EnumEventTypes.ADDITIONAL_BLOCK_DESCRIPTION, EnumEventTypes.OVERRIDE_BLOCK_RENDER})
public class PluginVanilla extends AbstractSmartyPlugin implements IAdditionalBlockDescription, IOverrideBlockRender {
	
	private Minecraft mc;

	@Override
	public void methodInitPlugin(SmartyGUI instance) {
		System.out.println("Vanilla module initializaing... magic happening...");
	}
	
	@Override
	public String getPluginName() {
		return "vanilla";
	}
	
	@Override
	public String[] getAdditionalBlockDescription(int id, int meta, World world, int x, int y, int z) {
		if(Block.crops.blockID == id) {
			return new String[] {"Age: " + (Math.round((meta * 100 / 7) * 100) / 100) + "%"};
		}

		if((Block.signPost.blockID == id || Block.signWall.blockID == id) && Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			TileEntitySign sign = (TileEntitySign) world.getBlockTileEntity(x, y, z);
			byte q = 0;
			for(String a : sign.signText)
				if(a.length() > 0) q++;
			if(q == 0) return null;
			return sign.signText;
		}
		
		if(Block.jukebox.blockID == id) {

            TileEntityRecordPlayer jukebox = (TileEntityRecordPlayer)world.getBlockTileEntity(x, y, z);
            String f = "Playing nothing.";
            
            if(Item.itemsList[jukebox.record] instanceof ItemRecord) {
            	f = "Playing - " + ((ItemRecord)Item.itemsList[jukebox.record]).recordName;
            }
            
			return new String[] { f };
		}
		
		return null;
	}

	@Override
	public boolean overrideHUDItemRenderer(int id, int meta, World world, RenderItem render, int renderX, int renderY, int x, int y, int z) {
		if(this.mc == null)
			this.mc = ModLoader.getMinecraftInstance();
		
		if(Block.signPost.blockID == id || Block.signWall.blockID == id) {
			RenderUtils.renderItem(render, mc.fontRenderer, mc.renderEngine, new ItemStack(Item.sign), renderX, renderY);
			return true;
		}
		return false;
	}
	
}
