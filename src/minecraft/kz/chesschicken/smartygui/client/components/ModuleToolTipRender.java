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

import net.minecraft.client.Minecraft;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.StringTranslate;
import org.lwjgl.opengl.GL11;

import kz.chesschicken.smartygui.common.ModuleRender;
import kz.chesschicken.smartygui.common.SmartyGUI;

public class ModuleToolTipRender extends ModuleRender {
    private short tickRender;
    
    private int currentID;
    private int currentMeta;
    private String output;

    public ModuleToolTipRender(Minecraft minecraft, SmartyGUI config) {
        super(minecraft, config);
    }

    @Override
    public void clean() {
    	//not really
    }

    /**
     * Render code for Tool-tip HUD.
     * @param item Item instance to be rendered.
     */
    public void doTooltipRender(ItemStack item) {
		
        if(item == null) {
        	this.currentID = 0;
        	this.currentMeta = 0;
        	this.tickRender = (short) config.tickToolTip;
        	return;
        }

        if (item.itemID == this.currentID && item.getItemDamage() == this.currentMeta) {
        	this.tickRender++;
        } else {
        	this.tickRender = 0;
        	this.currentID = item.itemID;
        	this.currentMeta = item.getItemDamage();

            if(item.getMaxDamage() < 1)
            	this.output = StringTranslate.getInstance().translateNamedKey(item.getItemName());
            else
            	this.output = StringTranslate.getInstance().translateNamedKey(item.getItemName()) + " | " + (item.getMaxDamage() - item.getItemDamage()) + "/" + item.getMaxDamage();

            if(item.itemID == Item.pocketSundial.shiftedIndex)
            	this.output = StringTranslate.getInstance().translateNamedKey(item.getItemName()) + " | " + (this.minecraft.theWorld.isDaytime() ? "Day" : "Night");
        }

        if(this.tickRender <= config.tickToolTip) {
        	double d = 10D * (1.0D - tickRender / ((double)config.tickToolTip));
			if (d < 0.0D) d = 0.0D;
			if (d > 1.0D) d = 1.0D;
            ScaledResolution screenScaler = (new ScaledResolution(this.minecraft.gameSettings, this.minecraft.displayWidth, this.minecraft.displayHeight));
            GL11.glEnable(3042 /* GL_BLEND */);
            this.textRenderer.drawStringWithShadow(this.output, screenScaler.getScaledWidth() / 2 - (this.textRenderer.getStringWidth(output) / 2), screenScaler.getScaledHeight() - 50, 0xFFFFFF + (((int)(255D * d * d)) << 24));
            GL11.glDisable(3042 /* GL_BLEND */);
        }
    }
}
