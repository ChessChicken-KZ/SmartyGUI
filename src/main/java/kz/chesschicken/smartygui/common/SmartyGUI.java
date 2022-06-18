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
package kz.chesschicken.smartygui.common;

import kz.chesschicken.smartygui.client.components.ModuleArmorRender;
import kz.chesschicken.smartygui.client.components.ModuleBlockRender;
import kz.chesschicken.smartygui.client.components.ModuleEntityRenderer;
import kz.chesschicken.smartygui.client.components.ModuleToolTipRender;
import kz.chesschicken.smartygui.client.gui.GuiHome;
import kz.chesschicken.smartygui.common.plugins.SmartyGuiPlugins;
import kz.chesschicken.smartygui.commonloader.GameUtils;
import kz.chesschicken.smartygui.commonloader.IMod;
import kz.chesschicken.smartygui.commonloader.ModDescription;
import kz.chesschicken.smartygui.commonloader.SystemUtils;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.options.KeyBinding;

import net.minecraft.client.render.TextRenderer;
import net.minecraft.client.util.ScreenScaler;
import net.minecraft.util.hit.HitType;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import smartygui.plugins.vanilla.PluginShowBreaking;
import smartygui.plugins.vanilla.PluginVanilla;

/**
 * Basically a modification instance.
 */
@ModDescription(
		name = "SmartyGUI",
		version = "CF-1.1.0",
		description = "SmartyGUI - Block/Entity viewer, armor status and tooltip HUD.",
		icon = "/assets/smartygui/icon.png"
		)
public class SmartyGUI implements IMod<SmartyGUI> {
	
	public static final KeyBinding openConfigKeyBind = new KeyBinding("openConfigKeyBind", Keyboard.KEY_NUMPAD0);
	
	public SmartyGUIConfig CONFIG;
	public static SmartyGuiPlugins PLUGINS;

    private ModuleBlockRender renderBlock;
    private ModuleEntityRenderer renderEntity;
    private ModuleArmorRender renderStatus;
    private ModuleToolTipRender renderToolTip;

    private int xV, yV, aV;

    @Override
    public void onInitializeClient() {
        CONFIG = new SmartyGUIConfig();
        CONFIG.start();
        PLUGINS = new SmartyGuiPlugins(this);
        xV = CONFIG.factorX;
        yV = CONFIG.factorY;
        aV = CONFIG.factorAnchor;
    }
    
    /**
     * Set new coordinates for HUD.
     * @param x X coordinate.
     * @param y Y coordinate.
     */
    public void applyNewXY(int x, int y) {
    	this.xV = x;
    	this.yV = y;
    }
    
    /**
     * Set new anchor value for HUD.
     * @param a
     */
    public void applyNewAnchor(int a) {
    	this.aV = a;
    }
    
    /**
     * Method for getting x coordinate of HUD.
     * @return HUD's X coordinate.
     */
    public int getX() {
    	return this.xV;
    }
    
    /**
     * Method for getting y coordinate of HUD.
     * @return HUD's Y coordinate.
     */
    public int getY() {
    	return this.yV;
    }
    
    /**
     * Method for getting anchor value of HUD.
     * @return HUD's ANCHOR value.
     */
    public int getAnchor() {
    	return this.aV;
    }
    
    public void updateASHUD() {
    	this.renderStatus.update();
    }

    @Override
    public void onTickGame(Minecraft minecraft) {
        if(renderBlock == null) renderBlock = new ModuleBlockRender(minecraft, this);
        if(renderEntity == null) renderEntity = new ModuleEntityRenderer(minecraft, this);
        if(renderStatus == null) renderStatus = new ModuleArmorRender(minecraft, this);
        if(renderToolTip == null) renderToolTip = new ModuleToolTipRender(minecraft, this);
        
        /* InGame ToolTip Part */
        if(CONFIG.enableInGameToolTip && !minecraft.paused && minecraft.currentScreen == null) {
            renderToolTip.doTooltipRender(minecraft.player.inventory.getHeldItem());
            renderToolTip.clean();
        }
        
        /* ArmorStatusHUD Part */
        if(CONFIG.enableArmorStatusHUD && minecraft.currentScreen == null && !minecraft.paused && !minecraft.options.debugHud && !minecraft.options.hideHud) {
            renderStatus.doArmorStatusRender();
            renderStatus.clean();
        }

        /* ShowBlock Part */
        if (CONFIG.enableShowBlock && minecraft.hitResult != null && minecraft.currentScreen == null && !minecraft.paused && !Minecraft.isDebugHudEnabled() && !minecraft.options.hideHud) {
            if (minecraft.hitResult.type == HitType.field_789) {
                renderBlock.updateBlock(minecraft.hitResult.x, minecraft.hitResult.y, minecraft.hitResult.z);
                renderBlock.doBlockRendering(xV, yV, aV);
                renderBlock.clean();
            } else {
                renderEntity.updateEntity(minecraft.hitResult.field_1989);
                renderEntity.doEntityRendering(xV, yV, aV);
                renderEntity.clean();
            }
        }

        /* F3 Extended Output */
        if (minecraft.options.debugHud && CONFIG.enableDebugF3) {
            TextRenderer fr = minecraft.textRenderer;
            String stringToSent;
            int scaledWidth = (new ScreenScaler(minecraft.options, minecraft.actualWidth, minecraft.actualHeight)).getScaledWidth();

            fr.drawTextWithShadow("yaw: " + minecraft.player.yaw, 2, 96, 14737632);
            fr.drawTextWithShadow("Biome: " + minecraft.player.level.getBiomeSource().getBiome((int) minecraft.player.x, (int) minecraft.player.z).biomeName, 2, 104, 14737632);
            fr.drawTextWithShadow("World Seed: " + minecraft.player.level.getSeed(), 2, 112, 14737632);
            fr.drawTextWithShadow("World Time: " + minecraft.player.level.getLevelTime(), 2, 120, 14737632);
            fr.drawTextWithShadow("World Name: " + minecraft.player.level.getProperties().getName(), 2, 128, 14737632);

            stringToSent = "OS: " + SystemUtils.getFullOperatingSystemName();
            fr.drawTextWithShadow(stringToSent, scaledWidth - fr.getTextWidth(stringToSent) - 2, 26, 14737632);
            if(SystemUtils.getProcessorInfo() != null)
            {
                stringToSent = "CPU: " + SystemUtils.getProcessorInfo();
                fr.drawTextWithShadow(stringToSent, scaledWidth - fr.getTextWidth(stringToSent) - 2, 36, 14737632);
            }
            stringToSent = "GPU: " + GL11.glGetString(GL11.GL_RENDERER);
            fr.drawTextWithShadow(stringToSent, scaledWidth - fr.getTextWidth(stringToSent) - 2, 46, 14737632);
            stringToSent = GL11.glGetString(GL11.GL_VERSION) + " " + GL11.glGetString(GL11.GL_VENDOR);
            fr.drawTextWithShadow(stringToSent, scaledWidth - fr.getTextWidth(stringToSent) - 2, 56, 14737632);


            stringToSent = "Loaded Fabric Mods: " + FabricLoader.getInstance().getAllMods().size();
            fr.drawTextWithShadow(stringToSent, scaledWidth - fr.getTextWidth(stringToSent) - 2, 76, 14737632);

        }
    }

    @Override
    public void keyPressed(int key) {
    	if(key == SmartyGUI.openConfigKeyBind.key) {
    		GameUtils.getMC().openScreen(new GuiHome(this));
    	}
    }

	@Override
	public void onPostInitClient() {
        PLUGINS.registerPlugin(new PluginVanilla());
        PLUGINS.registerPlugin(new PluginShowBreaking());
	}
}
