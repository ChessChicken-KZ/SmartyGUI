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
package kz.chesschicken.smartygui.client.components;

import java.util.Random;

import kz.chesschicken.smartygui.common.ModuleRender;
import kz.chesschicken.smartygui.common.RenderUtils;
import kz.chesschicken.smartygui.common.SmartyGUI;
import kz.chesschicken.smartygui.common.plugins.event.EnumEventTypes;
import kz.chesschicken.smartygui.common.plugins.event.IAdditionalBlockDescription;
import kz.chesschicken.smartygui.common.plugins.event.IOverrideBlockRender;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;
import net.minecraft.src.RenderItem;
import net.minecraft.src.StringTranslate;

public class ModuleBlockRender extends ModuleRender {
	
    private int currentBlockID;
    private int currentBlockMeta;
    private String stringBlockCoordinates;
    private String stringBlockProperties;
    private String stringBlockHardness;
    private byte colourType;

    private boolean _debug = false;
    
    private final RenderItem itemRenderer;
    private final IAdditionalBlockDescription[] blockDescPlugins;
    private final IOverrideBlockRender[] renderOverridePlugins;

    public ModuleBlockRender(Minecraft minecraft, SmartyGUI config) {
        super(minecraft, config);
        this.itemRenderer = new RenderItem();
        this.blockDescPlugins = this.pluginManager.__getEventsReady(EnumEventTypes.ADDITIONAL_BLOCK_DESCRIPTION, new IAdditionalBlockDescription[0], IAdditionalBlockDescription.class);
        this.renderOverridePlugins = this.pluginManager.__getEventsReady(EnumEventTypes.OVERRIDE_BLOCK_RENDER, new IOverrideBlockRender[0], IOverrideBlockRender.class);
    }
    
    /**
     * A kind of safe way to try-get a block's name.
     * @param id Block ID.
     * @param meta Block Metadata.
     * @return The name, otherwise "null" string.
     */
    String getSafeItemName(int id, int meta) {
    	try {
    		return new ItemStack(id, 1, meta).getItemName();
    	}catch (NullPointerException e) {
    		try {
    			return new ItemStack(id, 1, 0).getItemName();
    		}catch (NullPointerException e1) {
    			return "null";
    		}
    	}
    }

    public void updateBlock(int blockX, int blockY, int blockZ) {
        this.currentBlockID = this.minecraft.theWorld.getBlockId(blockX, blockY, blockZ);
        this.currentBlockMeta = this.minecraft.theWorld.getBlockMetadata(blockX, blockY, blockZ);

        this.stringBlockCoordinates = "X: " + blockX + " Y: " + blockY + " Z: " + blockZ;
        this.stringBlockProperties = StringTranslate.getInstance().translateNamedKey(getSafeItemName(currentBlockID, currentBlockMeta)).trim() +  " " + currentBlockID + ":" + currentBlockMeta + " ";
        this.stringBlockHardness = "H: " + Block.blocksList[currentBlockID].getHardness();
        
        this.colourType = getColorByHardness(Block.blocksList[currentBlockID].getHardness());
    }
    
    public void __updateBlockDebug() {
    	Random random = new Random();
    	
    	this.currentBlockID = Block.cloth.blockID;
    	this.currentBlockMeta = random.nextInt(16);
    	
    	this.stringBlockCoordinates = "X: " + ((random.nextBoolean() ? -1 : 1) * random.nextInt(4096)) + " Y: " + random.nextInt(128) + " Z: " + ((random.nextBoolean() ? -1 : 1) * random.nextInt(4096));
    	this.stringBlockProperties = StringTranslate.getInstance().translateNamedKey(getSafeItemName(currentBlockID, currentBlockMeta)).trim() + " " + currentBlockID + ":" + currentBlockMeta + " ";
    	this.stringBlockHardness = "H: " + Block.blocksList[currentBlockID].getHardness();
    	
    	this.colourType = getColorByHardness(Block.blocksList[currentBlockID].getHardness());
        
    	this._debug = true;
    }

    @Override
    public void clean() {
        this.currentBlockID = 0;
        this.currentBlockMeta = 0;
        this.stringBlockCoordinates = "";
        this.stringBlockProperties = "";
    }
    
    byte getColorByHardness(float f) {
        if(f < 0.0F)
            return 0; //'b'
        else if(f <= 1.0F)
            return 1; //'f'
        else if(f > 1.0F && f <= 3.0F)
            return 2; //'e'
        return 3; //'c'
    }

    /**
     * Render code for Block viewer HUD.
     * @param x X coordinate.
     * @param y Y coordinate.
     * @param anchor ANCHOR value.
     */
    public void doBlockRendering(int x, int y, int anchor) {
        if(currentBlockID == 0 || Block.blocksList[currentBlockID] == null)
            return;

        int[] realXY = new int[] {0, 0};
        
        String[][] vals = null;
        byte f = 0;
        
        if(!_debug) {
	        vals = new String[blockDescPlugins.length][];
	        for(int q = 0; q < vals.length; q++)
	        	vals[q] = blockDescPlugins[q].getAdditionalBlockDescription(currentBlockID, currentBlockMeta, minecraft.theWorld, minecraft.objectMouseOver.blockX, minecraft.objectMouseOver.blockY, minecraft.objectMouseOver.blockZ);
	        
	        for(String[] q : vals) {
	        	if(q != null) f += q.length;
	        }
        }
        
        if(config.showBlockModernStyle && !config.transparency)
            realXY = RenderUtils.modernRenderByAnchor(x, y, Math.max(textRenderer.getStringWidth(stringBlockCoordinates), textRenderer.getStringWidth(stringBlockProperties + stringBlockHardness)) + 33, 23 + (f * 10), anchor);
        else
        	realXY = RenderUtils.gradientRenderByAnchor(x, y, Math.max(textRenderer.getStringWidth(stringBlockCoordinates), textRenderer.getStringWidth(stringBlockProperties + stringBlockHardness)) + 36, 26 + (f * 10), config.showBlockRGB[0], config.showBlockRGB[1], anchor, config.transparency);

        boolean e = false;
        if(!_debug) {
	        for(IOverrideBlockRender g : renderOverridePlugins) {
	        	boolean j = g.overrideHUDItemRenderer(currentBlockID, currentBlockMeta, minecraft.theWorld, itemRenderer, realXY[0] + 5, realXY[1] + 5, minecraft.objectMouseOver.blockX, minecraft.objectMouseOver.blockY, minecraft.objectMouseOver.blockZ);
	        	if(!e) e = j;
	        }
        }
        
        if(!e)
        	RenderUtils.renderItem(itemRenderer,textRenderer, minecraft.renderEngine, new ItemStack(this.currentBlockID, 1, this.currentBlockMeta), realXY[0] + 5, realXY[1] + 5);
        
        textRenderer.drawString(stringBlockCoordinates, realXY[0] + 25, realXY[1] + 5, config.showBlockRGB[2]);
        textRenderer.drawString(stringBlockProperties, realXY[0] + 25, realXY[1] + 15, config.showBlockRGB[2]);;
        textRenderer.drawString(stringBlockHardness, realXY[0] + 25 + textRenderer.getStringWidth(stringBlockProperties), realXY[1] + 15, colourType == 0 ? 0x55FFFF : (colourType == 1 ? 0x55FF55 : (colourType == 2 ? 0xFFFF00 : 0xFF0000)));
        
        if(!_debug) {
	        f = 0;
	        for(int q = 0; q < vals.length; q++) {
	        	if(vals[q] == null)
	        		continue;
	        	for(String h : vals[q]) {
	                textRenderer.drawString(h, realXY[0] + 25, realXY[1] + 25 + (f * 10), config.showBlockRGB[2]);
	                f++;
	                
	                if(f > 254)
	                	break;
	        	}
	        }
        }
    }
}
