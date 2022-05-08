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

import org.lwjgl.opengl.GL11;

import kz.chesschicken.smartygui.common.ModuleRender;
import kz.chesschicken.smartygui.common.RenderUtils;
import kz.chesschicken.smartygui.common.SmartyGUI;
import kz.chesschicken.smartygui.common.plugins.event.EnumEventTypes;
import kz.chesschicken.smartygui.common.plugins.event.IAdditionalEntityDescription;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityList;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.RenderHelper;
import net.minecraft.src.RenderManager;

public class ModuleEntityRenderer extends ModuleRender {
    private String stringEntityCoordinates;
    private String stringEntityName;
    private String stringEntityID;
    private Entity entityBase;

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
    public void updateEntity(Entity entityBase) {
    	this.entityBase = entityBase;
        this.stringEntityCoordinates = "X: " + (int) entityBase.posX +
            " Y: " + (int) entityBase.posY +
            " Z: " + (int) entityBase.posZ;

        this.stringEntityName = entityBase instanceof EntityPlayer ? "Player: " + ((EntityPlayer)entityBase).username : "Entity: " + EntityList.getEntityString(entityBase);
        this.stringEntityID = "ID: " + entityBase.entityId;
    }
    
    public void __updateEntityDebug() {
    	updateEntity((Entity) minecraft.theWorld.getLoadedEntityList().get(0));
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
    	if(this.entityBase == null || RenderManager.instance.options == null)
    		return;
        
    	 GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
         GL11.glEnable(2903 /*GL_COLOR_MATERIAL*/);
         GL11.glPushMatrix();
         GL11.glTranslatef((float)(_x), (float)(_y), 50.0F);
         GL11.glScalef(-23.0F, 23.0F, 23.0F);
         GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
         
         float rYO_a = 0.0F;
         if(this.entityBase instanceof EntityLiving) rYO_a = ((EntityLiving)this.entityBase).renderYawOffset;
         float rY_a = this.entityBase.rotationYaw;
         
         float r_x = (float)(_x);
         float r_y = (float)(_y - 50);
         
         GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
         RenderHelper.enableStandardItemLighting();
         
         GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(((float)Math.atan((double)(r_y / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
         
         if(this.entityBase instanceof EntityLiving) ((EntityLiving)this.entityBase).renderYawOffset = (float)Math.atan((double)(r_x / 40.0F)) * 20.0F;
         this.entityBase.rotationYaw = (!(this.entityBase instanceof EntityLiving)) ? -225.0F : this.entityBase.rotationYaw % 90.0F;
         this.entityBase.entityBrightness = 1.0F;
         
         GL11.glTranslatef(0.0F, this.minecraft.thePlayer.yOffset, 0.0F);
         RenderManager.instance.renderEntityWithPosYaw(this.entityBase, 0.0D, 0.0D, 0.0D, this.entityBase.rotationYaw, 1.0F);
         
         this.entityBase.entityBrightness = 0.0F;
         if(this.entityBase instanceof EntityLiving) ((EntityLiving)this.entityBase).renderYawOffset = rYO_a;
         this.entityBase.rotationYaw = rY_a;
         
         GL11.glPopMatrix();
         RenderHelper.disableStandardItemLighting();
         GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
    }
    
    /**
     * Method for getting the health string or null for health info text.
     * @return Health string, otherwise null.
     */
    String getEntityBaseHealth() {
        return (this.entityBase instanceof EntityLiving) ? ("Health: " + ((EntityLiving)this.entityBase).health) : null;
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
        	realXY = RenderUtils.modernRenderByAnchor(x, y, Math.max(textRenderer.getStringWidth(stringEntityCoordinates), textRenderer.getStringWidth(stringEntityName)) + 43, 35 + (f * 10), anchor);
        else
        	realXY = RenderUtils.gradientRenderByAnchor(x, y,
                    Math.max(textRenderer.getStringWidth(stringEntityCoordinates), textRenderer.getStringWidth(stringEntityName)) + 46,
                    38 + (f * 10),
                    config.showBlockRGB[0],
                    config.showBlockRGB[1],
                    anchor, config.transparency);
    	
        this.renderEntityDoll(realXY[0] + 20, realXY[1] + ((h != null) ? 73 : 55));
        
        textRenderer.drawString(stringEntityCoordinates, realXY[0] + 5 + 35, realXY[1] + 5, config.showBlockRGB[2]);
        textRenderer.drawString(stringEntityName, realXY[0] + 5 + 35, realXY[1] + 15, config.showBlockRGB[2]);
        textRenderer.drawString(stringEntityID, realXY[0] + 5 + 35, realXY[1] + 25, config.showBlockRGB[2]);
        if(h != null)
        	textRenderer.drawString(h, realXY[0] + 5 + 35, realXY[1] + 35, config.showBlockRGB[2]);
        
        if(!_debug) {
	        f = (byte) (h != null ? 1 : 0);
	        for(int q = 0; q < vals.length; q++) {
	        	if(vals[q] == null)
	        		continue;
	        	for(String he : vals[q]) {
	                textRenderer.drawString(he, realXY[0] + 25, realXY[1] + 25 + (f * 10), config.showBlockRGB[2]);
	                f++;
	        	}
	        }
        }
    }
}
