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

import net.minecraft.client.render.RenderHelper;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.entity.EntityBase;
import net.minecraft.entity.EntityRegistry;
import net.minecraft.entity.Living;
import net.minecraft.entity.player.PlayerBase;
import org.lwjgl.opengl.GL11;

import kz.chesschicken.smartygui.common.ModuleRender;
import kz.chesschicken.smartygui.common.RenderUtils;
import kz.chesschicken.smartygui.common.SmartyGUI;
import kz.chesschicken.smartygui.common.plugins.event.EnumEventTypes;
import kz.chesschicken.smartygui.common.plugins.event.IAdditionalEntityDescription;
import net.minecraft.client.Minecraft;

public class ModuleEntityRenderer extends ModuleRender {
    private String stringEntityCoordinates;
    private String stringEntityName;
    private String stringEntityID;
    private EntityBase entityBase;

    private boolean _debug = false;
    
    private final IAdditionalEntityDescription[] entityDescPlugins;

    public ModuleEntityRenderer(Minecraft minecraft, SmartyGUI config) {
        super(minecraft, config);
        this.entityDescPlugins = this.pluginManager.__getEventsReady(EnumEventTypes.ADDITIONAL_ENTITY_DESCRIPTION, new IAdditionalEntityDescription[0], IAdditionalEntityDescription.class);
    }

    /**
     * Method for updating entity.
     * @param entityBase New entity instance.
     */
    public void updateEntity(EntityBase entityBase) {
    	this.entityBase = entityBase;
        this.stringEntityCoordinates = "X: " + (int) entityBase.x +
            " Y: " + (int) entityBase.y +
            " Z: " + (int) entityBase.z;

        this.stringEntityName = entityBase instanceof PlayerBase ? "Player: " + ((PlayerBase)entityBase).name : "Entity: " + EntityRegistry.getStringId(entityBase);
        this.stringEntityID = "ID: " + entityBase.entityId;
    }
    
    public void __updateEntityDebug() {
    	updateEntity((EntityBase) minecraft.level.getEntities().get(0));
        this._debug = true;
    }

    @Override
    public void clean() {
        this.stringEntityCoordinates = "";
        this.stringEntityName = "";
        this.stringEntityID = "";
        this.entityBase = null;
    }
    
    /**
     * Entity doll render code.
     * @param _x X coordinate for render.
     * @param _y Y coordinate for render.
     */
    void renderEntityDoll(int _x, int _y) {
    	if(this.entityBase == null || EntityRenderDispatcher.INSTANCE.options == null)
    		return;
        
    	 GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
         GL11.glEnable(2903 /*GL_COLOR_MATERIAL*/);
         GL11.glPushMatrix();
         GL11.glTranslatef((float)(_x), (float)(_y), 50.0F);
         GL11.glScalef(-23.0F, 23.0F, 23.0F);
         GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
         
         float rYO_a = 0.0F;
         if(this.entityBase instanceof Living) rYO_a = ((Living)this.entityBase).field_1012;
         float rY_a = this.entityBase.yaw;
         
         float r_x = (float)(_x);
         float r_y = (float)(_y - 50);
         
         GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
         RenderHelper.enableLighting();
         
         GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(((float)Math.atan((double)(r_y / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
         
         if(this.entityBase instanceof Living) ((Living)this.entityBase).field_1012 = (float)Math.atan((double)(r_x / 40.0F)) * 20.0F;
         this.entityBase.yaw = (!(this.entityBase instanceof Living)) ? -225.0F : this.entityBase.yaw % 90.0F;
         this.entityBase.field_1617 = 1.0F;
         
         GL11.glTranslatef(0.0F, this.minecraft.player.standingEyeHeight, 0.0F);
        EntityRenderDispatcher.INSTANCE.method_1920(this.entityBase, 0.0D, 0.0D, 0.0D, this.entityBase.yaw, 1.0F);
         
         this.entityBase.field_1617 = 0.0F;
         if(this.entityBase instanceof Living) ((Living)this.entityBase).field_1012 = rYO_a;
         this.entityBase.yaw = rY_a;
         
         GL11.glPopMatrix();
         RenderHelper.disableLighting();
         GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
    }
    
    /**
     * Method for getting the health string or null for health info text.
     * @return Health string, otherwise null.
     */
    String getEntityBaseHealth() {
        return (this.entityBase instanceof Living) ? ("Health: " + ((Living)this.entityBase).health) : null;
    }

    /**
     * Render code for entity viewer HUD.
     * @param x X coordinate.
     * @param y Y coordinate.
     * @param anchor ANCHOR value.
     */
    public void doEntityRendering(int x, int y, int anchor) {
    	int[] realXY = new int[] {0, 0};
    	String h = getEntityBaseHealth();
    	
    	String[][] vals = null;
        byte f = (byte) (h != null ? 1 : 0);
        if(!_debug) {
	        vals = new String[entityDescPlugins.length][];
	        for(int q = 0; q < vals.length; q++)
	        	vals[q] = entityDescPlugins[q].getAdditionalEntityDescription(this.entityBase);
	        
	        for(String[] q : vals) {
	        	if(q != null) f += q.length;
	        }
        }
    	
    	if(config.showBlockModernStyle && !config.transparency)
        	realXY = RenderUtils.modernRenderByAnchor(x, y, Math.max(textRenderer.getTextWidth(stringEntityCoordinates), textRenderer.getTextWidth(stringEntityName)) + 43, 35 + (f * 10), anchor);
        else
        	realXY = RenderUtils.gradientRenderByAnchor(x, y,
                    Math.max(textRenderer.getTextWidth(stringEntityCoordinates), textRenderer.getTextWidth(stringEntityName)) + 46,
                    38 + (f * 10),
                    config.showBlockRGB[0],
                    config.showBlockRGB[1],
                    anchor, config.transparency);
    	
        this.renderEntityDoll(realXY[0] + 20, realXY[1] + ((h != null) ? 73 : 55));
        
        textRenderer.drawText(stringEntityCoordinates, realXY[0] + 5 + 35, realXY[1] + 5, config.showBlockRGB[2]);
        textRenderer.drawText(stringEntityName, realXY[0] + 5 + 35, realXY[1] + 15, config.showBlockRGB[2]);
        textRenderer.drawText(stringEntityID, realXY[0] + 5 + 35, realXY[1] + 25, config.showBlockRGB[2]);
        if(h != null)
        	textRenderer.drawText(h, realXY[0] + 5 + 35, realXY[1] + 35, config.showBlockRGB[2]);
        
        if(!_debug) {
	        f = (byte) (h != null ? 1 : 0);
	        for(int q = 0; q < vals.length; q++) {
	        	if(vals[q] == null)
	        		continue;
	        	for(String he : vals[q]) {
	                textRenderer.drawText(he, realXY[0] + 25, realXY[1] + 25 + (f * 10), config.showBlockRGB[2]);
	                f++;
	                
	                if(f > 254)
	                	break;
	        	}
	        }
        }
    }
}
