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
import net.minecraft.client.render.entity.ItemRenderer;
import net.minecraft.client.util.ScreenScaler;
import net.minecraft.item.ItemInstance;

public class ModuleArmorRender extends ModuleRender {
	
    private boolean leftOrRight;
    private boolean topOrBottom;

    private final ItemRenderer itemRenderer;

    public ModuleArmorRender(Minecraft minecraft, SmartyGUI config) {
        super(minecraft, config);
        itemRenderer = new ItemRenderer();
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
    public void clean() { }

    /**
     * Render code for armor status HUD.
     */
    public void doArmorStatusRender() {
        ScreenScaler sr = new ScreenScaler(minecraft.options, minecraft.actualWidth, minecraft.actualHeight);
        int scaledWidth = leftOrRight ? sr.getScaledWidth() - 17 : 1;

        byte q = 0;
        if(minecraft.player.inventory.getHeldItem() != null) {
            ItemInstance a = minecraft.player.inventory.getHeldItem();
            String output = a.getDurability() != 0 ? a.getDurability() - a.getDamage() + "" : "";
            RenderUtils.renderItem(itemRenderer, textRenderer, minecraft.textureManager, a, scaledWidth,
                    topOrBottom ? 2 : sr.getScaledHeight() - 18);
            textRenderer.drawTextWithShadow(
                    output, leftOrRight ? scaledWidth - textRenderer.getTextWidth(output) : scaledWidth + 17,
                    topOrBottom ? 6 : sr.getScaledHeight() - 14, 16777215);
            q++;
        }
        
        for(int f = 0; f < minecraft.player.inventory.armour.length; f++) {
        	int ag = topOrBottom ? 3 - f : f;
            if(minecraft.player.inventory.armour[ag] != null) {
                int damage = minecraft.player.inventory.armour[ag].getDurability() - minecraft.player.inventory.armour[ag].getDamage();
                
                RenderUtils.renderItem(itemRenderer, textRenderer, minecraft.textureManager, minecraft.player.inventory.armour[ag], scaledWidth,
                        topOrBottom ? (15 * q) + 2 : sr.getScaledHeight() - 19 - (15 * q));

                textRenderer.drawTextWithShadow(String.valueOf(damage),
                        leftOrRight ? scaledWidth - textRenderer.getTextWidth(String.valueOf(damage)) : scaledWidth + 17,
                        topOrBottom ? (15 * q) + 7 : sr.getScaledHeight() - 15 - (15 * q), 16777215);
                
                q++;
            }
        }

    }
}
