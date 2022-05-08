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

import kz.chesschicken.smartygui.common.ModuleRender;
import kz.chesschicken.smartygui.common.RenderUtils;
import kz.chesschicken.smartygui.common.SmartyGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.src.RenderItem;
import net.minecraft.src.ScaledResolution;

public class ModuleArmorRender extends ModuleRender {
	
    private boolean leftOrRight;
    private boolean topOrBottom;

    private final RenderItem itemRenderer;

    public ModuleArmorRender(Minecraft minecraft, SmartyGUI config) {
        super(minecraft, config);
        itemRenderer = new RenderItem();
        this.update();
    }
    
    /**
     * Method for updating HUD state.
     */
    public void update() {
        leftOrRight = config.armorStatusHUDmode == 0 || config.armorStatusHUDmode == 2;
        topOrBottom = config.armorStatusHUDmode == 2 || config.armorStatusHUDmode == 3;
    }

    @Override
    public void clean() {
        //nothing really
    }

    /**
     * Render code for armor status HUD.
     */
    public void doArmorStatusRender() {
        ScaledResolution sr = new ScaledResolution(minecraft.gameSettings, minecraft.displayWidth, minecraft.displayHeight);
        int scaledWidth = leftOrRight ? sr.getScaledWidth() - 17 : 1;

        int q = 0;
        if(minecraft.thePlayer.inventory.getCurrentItem() != null) {
            String output = minecraft.thePlayer.inventory.getCurrentItem().getMaxDamage() != 0 ? minecraft.thePlayer.inventory.getCurrentItem().getMaxDamage() - minecraft.thePlayer.inventory.getCurrentItem().getItemDamage() + "" : "";
            RenderUtils.renderItem(itemRenderer, textRenderer, minecraft.renderEngine, minecraft.thePlayer.inventory.getCurrentItem(), scaledWidth,
                    topOrBottom ? 2 : sr.getScaledHeight() - 18);
            textRenderer.drawStringWithShadow(
                    output, leftOrRight ? scaledWidth - textRenderer.getStringWidth(output) : scaledWidth + 17,
                    topOrBottom ? 6 : sr.getScaledHeight() - 14, 16777215);
            q++;
        }
        
        for(int f = 0; f < minecraft.thePlayer.inventory.armorInventory.length; f++) {
        	int ag = topOrBottom ? 3 - f : f;
            if(minecraft.thePlayer.inventory.armorInventory[ag] != null) {
                int damage = minecraft.thePlayer.inventory.armorInventory[ag].getMaxDamage() - minecraft.thePlayer.inventory.armorInventory[ag].getItemDamage();
                
                RenderUtils.renderItem(itemRenderer, textRenderer, minecraft.renderEngine, minecraft.thePlayer.inventory.armorInventory[ag], scaledWidth,
                        topOrBottom ? (15 * q) + 2 : sr.getScaledHeight() - 19 - (15 * q));

                textRenderer.drawStringWithShadow(String.valueOf(damage),
                        leftOrRight ? scaledWidth - textRenderer.getStringWidth(String.valueOf(damage)) : scaledWidth + 17,
                        topOrBottom ? (15 * q) + 7 : sr.getScaledHeight() - 15 - (15 * q), 16777215);
                
                q++;
            }
        }

    }
}
