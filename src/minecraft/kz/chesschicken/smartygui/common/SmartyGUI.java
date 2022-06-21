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
import kz.chesschicken.smartygui.commonloader.guiframework.GuiScreenExtended;
import net.minecraft.client.Minecraft;
import net.minecraft.src.EnumMovingObjectType;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.KeyBinding;
import net.minecraft.src.ModLoader;
import net.minecraft.src.ScaledResolution;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

/**
 * Basically a modification instance.
 */
@ModDescription(
		name = "SmartyGUI",
		version = "ML-2.3",
		description = "SmartyGUI - Block/Entity viewer, armor status and tooltip HUD.",
		icon = "/smartygui/icon.png"
		)
public class SmartyGUI implements IMod<SmartyGUI> {
	
	public static final KeyBinding openConfigKeyBind = new KeyBinding("key.openconfigkeybind", Keyboard.KEY_NUMPAD0);
	public static final KeyBinding toggleGUI = new KeyBinding("key.togglegui", Keyboard.KEY_NUMPAD1);
	
	public SmartyGuiConfig CONFIG;
	public SmartyGuiPlugins PLUGINS;

    private ModuleBlockRender renderBlock;
    private ModuleEntityRenderer renderEntity;
    private ModuleArmorRender renderStatus;
    private ModuleToolTipRender renderToolTip;

    private int xV, yV, aV;
    private boolean keytoggle;

    @Override
    public void onInitializeClient() {
        CONFIG = new SmartyGuiConfig();
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
    	if(keytoggle)
    		return;
        if(renderBlock == null) renderBlock = new ModuleBlockRender(minecraft, this);
        if(renderEntity == null) renderEntity = new ModuleEntityRenderer(minecraft, this);
        if(renderStatus == null) renderStatus = new ModuleArmorRender(minecraft, this);
        if(renderToolTip == null) renderToolTip = new ModuleToolTipRender(minecraft, this);
        
        /* InGame ToolTip Part */
        if(CONFIG.enableInGameToolTip && !minecraft.isGamePaused && minecraft.currentScreen == null) {
            renderToolTip.doTooltipRender(minecraft.thePlayer.inventory.getCurrentItem());
            renderToolTip.clean();
        }
        
        /* ArmorStatusHUD Part */
        if(CONFIG.enableArmorStatusHUD && minecraft.currentScreen == null && !minecraft.isGamePaused && !minecraft.gameSettings.showDebugInfo && !minecraft.gameSettings.hideGUI) {
            renderStatus.doArmorStatusRender();
            renderStatus.clean();
        }

        /* ShowBlock Part */
        if (CONFIG.enableShowBlock && minecraft.objectMouseOver != null && minecraft.currentScreen == null && !minecraft.isGamePaused && !Minecraft.isDebugInfoEnabled() && !minecraft.gameSettings.hideGUI) {
            if (minecraft.objectMouseOver.typeOfHit == EnumMovingObjectType.TILE) {
                renderBlock.updateBlock(minecraft.objectMouseOver.blockX, minecraft.objectMouseOver.blockY, minecraft.objectMouseOver.blockZ);
                renderBlock.doBlockRendering(xV, yV, aV);
                renderBlock.clean();
            } else {
                renderEntity.updateEntity(minecraft.objectMouseOver.entityHit);
                renderEntity.doEntityRendering(xV, yV, aV);
                renderEntity.clean();
            }
        }

        /* F3 Extended Output */
        if (minecraft.gameSettings.showDebugInfo && CONFIG.enableDebugF3) {
            FontRenderer fr = minecraft.fontRenderer;
            String stringToSent;
            int scaledWidth = (new ScaledResolution(minecraft.gameSettings, minecraft.displayWidth, minecraft.displayHeight)).getScaledWidth();

            fr.drawStringWithShadow("yaw: " + minecraft.thePlayer.rotationYaw, 2, 96, 14737632);
            fr.drawStringWithShadow("Biome: " + minecraft.thePlayer.worldObj.getWorldChunkManager().getBiomeGenAt((int) minecraft.thePlayer.posX, (int) minecraft.thePlayer.posZ).biomeName, 2, 104, 14737632);
            fr.drawStringWithShadow("World Seed: " + minecraft.thePlayer.worldObj.getRandomSeed(), 2, 112, 14737632);
            fr.drawStringWithShadow("World Time: " + minecraft.thePlayer.worldObj.getWorldTime(), 2, 120, 14737632);
            fr.drawStringWithShadow("World Name: " + minecraft.thePlayer.worldObj.getWorldInfo().getWorldName(), 2, 128, 14737632);

            stringToSent = "OS: " + SystemUtils.getFullOperatingSystemName();
            fr.drawStringWithShadow(stringToSent, scaledWidth - fr.getStringWidth(stringToSent) - 2, 26, 14737632);
            if(SystemUtils.getProcessorInfo() != null)
            {
                stringToSent = "CPU: " + SystemUtils.getProcessorInfo();
                fr.drawStringWithShadow(stringToSent, scaledWidth - fr.getStringWidth(stringToSent) - 2, 36, 14737632);
            }
            stringToSent = "GPU: " + GL11.glGetString(GL11.GL_RENDERER);
            fr.drawStringWithShadow(stringToSent, scaledWidth - fr.getStringWidth(stringToSent) - 2, 46, 14737632);
            stringToSent = GL11.glGetString(GL11.GL_VERSION) + " " + GL11.glGetString(GL11.GL_VENDOR);
            fr.drawStringWithShadow(stringToSent, scaledWidth - fr.getStringWidth(stringToSent) - 2, 56, 14737632);


            stringToSent = "Loaded ModLoader Mods: " + ModLoader.getLoadedMods().size();
            fr.drawStringWithShadow(stringToSent, scaledWidth - fr.getStringWidth(stringToSent) - 2, 76, 14737632);

        }
    }

    @Override
    public void keyPressed(int key) {
    	if(key == SmartyGUI.openConfigKeyBind.keyCode)
    		GameUtils.getMC().displayGuiScreen(new GuiScreenExtended(GuiHome.HOME_GUI.apply(this)));
    	if(key == SmartyGUI.toggleGUI.keyCode)
    		keytoggle = !keytoggle;
    }

	@Override
	public void onPostInitClient() {
	}
}
