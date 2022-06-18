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

import net.minecraft.block.BlockBase;
import net.minecraft.client.render.entity.ItemRenderer;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.Record;
import net.minecraft.level.Level;
import net.minecraft.tileentity.TileEntityJukebox;
import net.minecraft.tileentity.TileEntitySign;
import org.lwjgl.input.Keyboard;

import kz.chesschicken.smartygui.common.RenderUtils;
import kz.chesschicken.smartygui.common.SmartyGUI;
import kz.chesschicken.smartygui.common.plugins.AbstractSmartyPlugin;
import kz.chesschicken.smartygui.common.plugins.event.EnumEventTypes;
import kz.chesschicken.smartygui.common.plugins.event.GetClassifiedEvents;
import kz.chesschicken.smartygui.common.plugins.event.IAdditionalBlockDescription;
import kz.chesschicken.smartygui.common.plugins.event.IOverrideBlockRender;
import kz.chesschicken.smartygui.commonloader.GameUtils;
import net.minecraft.client.Minecraft;

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
	public String[] getAdditionalBlockDescription(int id, int meta, Level world, int x, int y, int z) {
		if(BlockBase.CROPS.id == id) { return new String[] {"Age: " + (Math.round((meta * 100 / 7) * 100) / 100) + "%"}; }

		if((BlockBase.WALL_SIGN.id == id || BlockBase.STANDING_SIGN.id == id) && Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			TileEntitySign sign = (TileEntitySign) world.getTileEntity(x, y, z);
			byte q = 0;
			for(String a : sign.lines)
				if(a.length() > 0) q++;
			if(q == 0) return null;
			return sign.lines;
		}
		
		if(BlockBase.JUKEBOX.id == id) {
            TileEntityJukebox jukebox = (TileEntityJukebox) world.getTileEntity(x, y, z);
            String f = "Playing nothing.";
            
            if(ItemBase.byId[jukebox.recordId] instanceof Record)
            	f = "Playing - " + ((Record)ItemBase.byId[jukebox.recordId]).title;
            
			return new String[] { f };
		}
		
		return null;
	}

	@Override
	public boolean overrideHUDItemRenderer(int id, int meta, Level world, ItemRenderer render, int renderX, int renderY, int x, int y, int z) {
		if(this.mc == null)
			this.mc = GameUtils.getMC();
		
		if(BlockBase.WALL_SIGN.id == id || BlockBase.STANDING_SIGN.id == id) {
			RenderUtils.renderItem(render, mc.textRenderer, mc.textureManager, new ItemInstance(ItemBase.sign), renderX, renderY);
			return true;
		}

		if(BlockBase.WOOD_DOOR.id == id) {
			RenderUtils.renderItem(render, mc.textRenderer, mc.textureManager, new ItemInstance(ItemBase.woodDoor), renderX, renderY);
			return true;
		}
		
		if(BlockBase.IRON_DOOR.id == id) {
			RenderUtils.renderItem(render, mc.textRenderer, mc.textureManager, new ItemInstance(ItemBase.ironDoor), renderX, renderY);
			return true;
		}
		
		return false;
	}
	
}
