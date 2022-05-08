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

import kz.chesschicken.smartygui.common.configapi.ConfigInstance;
import kz.chesschicken.smartygui.common.configapi.instance.Group;
import kz.chesschicken.smartygui.common.configapi.instance.Property;

/**
 * A config instance implementation for the SmartyGUI modification.
 */
public class SmartyGuiConfig extends ConfigInstance {

    public boolean enableVersionCheck = true;
    public boolean enableDebugF3 = true;
    
    public boolean enableShowBlock = true;
    public boolean enableArmorStatusHUD = true;
    public boolean enableInGameToolTip = true;
    
    public boolean showBlockModernStyle = false;
    public boolean transparency = false;
    public static boolean darkTheme = false;
    
    public int tickToolTip = 200;
    
    public int factorX = 15;
    public int factorY = 15;
    
    /**
     * Keys for anchor:
     * 0 - LEFT;
     * 1 - CENTER;
     * 2 - RIGHT;
     */
    public int factorAnchor = 0;
    
    /**
     * Keys for theme:
     * default_theme;
     * dark_theme;
     * See: {@link kz.chesschicken.smartygui.common.EnumTheme}
     */
    public static String iconTheme = "default_theme";

    /**
     * Keys for mode:
     * 0 - Bottom Right
     * 1 - Bottom Left
     * 2 - Top Right
     * 3 - Top Left
     */
    public int armorStatusHUDmode = 0;

    public int[] showBlockRGB = new int[3] /* Should return 0 if nothing */;

    public SmartyGuiConfig() {
        super("smartygui");
        showBlockRGB[0] = showBlockRGB[1] = RenderUtils.getIntFromRGBA(0, 0, 0, 255);
        showBlockRGB[2] = 16777215;
    }

    @Override
    public void saveConfig() {
        Group general = Group.createGroup("general");
        general.add(Property.createProperty("enableVersionCheck", enableVersionCheck), "Enable version checking. (WIP)");
        general.add(Property.createProperty("enableDebugF3", enableDebugF3), "Additional data in F3.");
        general.add(Property.createProperty("iconTheme", iconTheme), "ID Theme for the icons GUI.");
        general.add(Property.createProperty("darkTheme", darkTheme), "Switch for the dark theme.");

        Group modules = Group.createGroup("modules");
        modules.add(Property.createProperty("enableShowBlock", enableShowBlock), "Enable ShowBlock module. (a.k.a. Waila)");
        modules.add(Property.createProperty("enableArmorStatusHUD", enableArmorStatusHUD), "Enable ArmorStatusHUD module.");
        modules.add(Property.createProperty("enableInGameToolTip", enableInGameToolTip), "Enable in game tooltip module.");

        Group hud_preferences = Group.createGroup("hud_preferences");
        hud_preferences.add(Property.createProperty("showBlockModernStyle", showBlockModernStyle), "Use modern style for ShowBlock.");
        hud_preferences.add(Property.createProperty("armorStatusHUDmode", armorStatusHUDmode), "Modes for ArmorStatusHUD. \n 0 - Bottom Right \n 1 - Bottom Left \n 2 - Top Right \n 3 - Top Left");
        hud_preferences.add(Property.createProperty("transparency", transparency), "Transparency mode for ShowBlock.");
        hud_preferences.add(Property.createProperty("factorX", factorX), "X coordinate on the display by factor value.");
        hud_preferences.add(Property.createProperty("factorY", factorY), "Y coordinate on the display by factor value.");
        hud_preferences.add(Property.createProperty("factorAnchor", factorAnchor), "Anchor type.");
        hud_preferences.add(Property.createProperty("tickToolTip", tickToolTip), "Amount of ticks before Tool-Tip dissapearing.");

        Group rgbShowBlock = Group.createGroup("rgbShowBlock");
        rgbShowBlock.setCommentary("RGB Values for gradient of ShowBlock. (if modern style is turned off)");

        rgbShowBlock.add(Property.createProperty("showBlockG1", showBlockRGB[0]), "First Gradient Colour.");
        rgbShowBlock.add(Property.createProperty("showBlockG2", showBlockRGB[1]), "Second Gradient Colour.");
        rgbShowBlock.add(Property.createProperty("showBlockT", showBlockRGB[2]), "Text Colour.");

        
        instance.add(general);
        instance.add(modules);
        instance.add(hud_preferences);
        instance.add(rgbShowBlock);
    }

    @Override
    public void applyConfig() {
        enableVersionCheck = (boolean) getValue("general", "enableVersionCheck");
        enableDebugF3 = (boolean) getValue("general", "enableDebugF3");
        iconTheme = (String) getValue("general", "iconTheme");
        darkTheme = (boolean) getValue("general", "darkTheme");
        
        enableShowBlock = (boolean) getValue("modules", "enableShowBlock");
        enableArmorStatusHUD = (boolean) getValue("modules", "enableArmorStatusHUD");
        enableInGameToolTip = (boolean) getValue("modules", "enableInGameToolTip");

        showBlockModernStyle = (boolean) getValue("hud_preferences", "showBlockModernStyle");
        armorStatusHUDmode = (int) getValue("hud_preferences", "armorStatusHUDmode");
        transparency = (boolean) getValue("hud_preferences", "transparency");
        factorX = (int) getValue("hud_preferences", "factorX");
        factorY = (int) getValue("hud_preferences", "factorY");
        factorAnchor = (int) getValue("hud_preferences", "factorAnchor");
        tickToolTip = (int) getValue("hud_preferences", "tickToolTip");
        
        showBlockRGB[0] = (int) getValue("rgbShowBlock", "showBlockG1");
        showBlockRGB[1] = (int) getValue("rgbShowBlock", "showBlockG2");
        showBlockRGB[2] = (int) getValue("rgbShowBlock", "showBlockT");
    }
}
