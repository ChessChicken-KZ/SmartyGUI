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
    public boolean enableMCVersion = true;
    
    public boolean showBlockModernStyle = false;
    public boolean transparency = false;
    public String mcVersionRS = "Minecraft Beta 1.7.3";
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

    public int[] showBlockRGB = {-16777216, -16777216, 16777215};

    public SmartyGuiConfig() {
        super("smartygui");
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
        modules.add(Property.createProperty("enableInGameToolTip", enableInGameToolTip), "Enable in-game tooltip module.");
        modules.add(Property.createProperty("enableMCVersion", enableMCVersion), "Enable in-game \"Minecraft Beta 1.7.3\" label.");

        Group hud_preferences = Group.createGroup("hud_preferences");
        hud_preferences.add(Property.createProperty("showBlockModernStyle", showBlockModernStyle), "Use modern style for ShowBlock.");
        hud_preferences.add(Property.createProperty("armorStatusHUDmode", armorStatusHUDmode), "Modes for ArmorStatusHUD. \n 0 - Bottom Right \n 1 - Bottom Left \n 2 - Top Right \n 3 - Top Left");
        hud_preferences.add(Property.createProperty("replaceVersion", mcVersionRS), "Replace in-game \"Minecraft Beta 1.7.3\" label with custom one.");
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
        enableVersionCheck = getSafeValue("general", "enableVersionCheck", true);
        enableDebugF3 = getSafeValue("general", "enableDebugF3", true);
        iconTheme = getSafeValue("general", "iconTheme", "default_theme");
        darkTheme = getSafeValue("general", "darkTheme", false);
        
        enableShowBlock = getSafeValue("modules", "enableShowBlock", true);
        enableArmorStatusHUD = getSafeValue("modules", "enableArmorStatusHUD", true);
        enableInGameToolTip = getSafeValue("modules", "enableInGameToolTip", true);
        enableMCVersion = getSafeValue("modules", "enableMCVersion", true);

        showBlockModernStyle = getSafeValue("hud_preferences", "showBlockModernStyle", false);
        armorStatusHUDmode = getSafeValue("hud_preferences", "armorStatusHUDmode", 0);
        mcVersionRS = getSafeValue("hud_preferences", "replaceVersion", "Minecraft Beta 1.7.3");
        transparency = getSafeValue("hud_preferences", "transparency", false);
        factorX = getSafeValue("hud_preferences", "factorX", 15);
        factorY = getSafeValue("hud_preferences", "factorY", 15);
        factorAnchor = getSafeValue("hud_preferences", "factorAnchor", 0);
        tickToolTip = getSafeValue("hud_preferences", "tickToolTip", 200);

        showBlockRGB[0] = getSafeValue("rgbShowBlock", "showBlockG1", -16777216);
        showBlockRGB[1] = getSafeValue("rgbShowBlock", "showBlockG2", -16777216);
        showBlockRGB[2] = getSafeValue("rgbShowBlock", "showBlockT", 16777215);
    }
}
