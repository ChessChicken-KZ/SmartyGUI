package kz.chesschicken.smartygui.common;

import kz.chesschicken.smartygui.common.configapi.ConfigInstance;
import kz.chesschicken.smartygui.common.configapi.instance.Group;
import kz.chesschicken.smartygui.common.configapi.instance.Property;

public class SmartyGuiConfig extends ConfigInstance{

    public boolean enableVersion = true;
    public boolean enableMainMenuDebug = true;
    public boolean enableShowBlock = true;
    public boolean enableArmorStatusHUD = true;
    public boolean enableInGameToolTip = true;
    public boolean enableExtendedFurnaceInfo = true;
    public boolean enablePlayerList = true;
    public boolean showBlockModernStyle = true;

    /**
     * Modes:
     * 0 - Bottom Right
     * 1 - Bottom Left
     * 2 - Top Right
     * 3 - Top Left
     */
    public int armorStatusHUDmode = 0;

    public int[] showBlockRGB = new int[6] /* Should return 0 if nothing */;

    private SmartyGuiConfig() {
        super("smartygui");
    }

    @Override
    public void saveConfig() {
        Group general = Group.createGroup("general");
        general.add(Property.createProperty("enableVersion", enableVersion), "Enable version checking. (WIP)");
        general.add(Property.createProperty("enableMainMenuDebug", enableMainMenuDebug), "Show RAM and Injector data in main menu.");
        general.add(Property.createProperty("enableExtendedFurnaceInfo", enableExtendedFurnaceInfo), "Show extended info inside furnace gui.");

        Group modules = Group.createGroup("modules");
        modules.add(Property.createProperty("enableShowBlock", enableShowBlock), "Enable ShowBlock module. (a.k.a. Waila)");
        modules.add(Property.createProperty("enableArmorStatusHUD", enableArmorStatusHUD), "Enable ArmorStatusHUD module.");
        modules.add(Property.createProperty("enableInGameToolTip", enableInGameToolTip), "Enable in game tooltip module.");
        modules.add(Property.createProperty("enablePlayerList", enablePlayerList), "Enable player list module. (REQUIRES StAPI + ADDON)");

        Group accessibility = Group.createGroup("accessibility");
        accessibility.add(Property.createProperty("showBlockModernStyle", showBlockModernStyle), "Use modern style for ShowBlock.");
        accessibility.add(Property.createProperty("armorStatusHUDmode", armorStatusHUDmode), "Modes for ArmorStatusHUD. \n 0 - Bottom Right \n 1 - Bottom Left \n 2 - Top Right \n 3 - Top Left");

        Group rgbShowBlock = Group.createGroup("rgbShowBlock");
        rgbShowBlock.setCommentary("RGB Values for gradient of ShowBlock. (if modern style is turned off)");

        rgbShowBlock.add(Property.createProperty("showBlockRGB_R_1", showBlockRGB[0]), "Red First Gradient Colour.");
        rgbShowBlock.add(Property.createProperty("showBlockRGB_G_1", showBlockRGB[1]), "Green First Gradient Colour.");
        rgbShowBlock.add(Property.createProperty("showBlockRGB_B_1", showBlockRGB[2]), "Blue First Gradient Colour.");
        rgbShowBlock.add(Property.createProperty("showBlockRGB_R_2", showBlockRGB[3]), "Red Second Gradient Colour.");
        rgbShowBlock.add(Property.createProperty("showBlockRGB_G_2", showBlockRGB[4]), "Green Second Gradient Colour.");
        rgbShowBlock.add(Property.createProperty("showBlockRGB_B_2", showBlockRGB[5]), "Blue Second Gradient Colour.");

        instance.add(general);
        instance.add(modules);
        instance.add(accessibility);
        instance.add(rgbShowBlock);
    }

    @Override
    public void applyConfig() {
        enableVersion = (boolean) getValue("general","enableVersion");
        enableMainMenuDebug = (boolean) getValue("general","enableMainMenuDebug");
        enableExtendedFurnaceInfo = (boolean) getValue("general","enableExtendedFurnaceInfo");

        enableShowBlock = (boolean) getValue("modules","enableShowBlock");
        enableArmorStatusHUD = (boolean) getValue("modules","enableArmorStatusHUD");
        enableInGameToolTip = (boolean) getValue("modules","enableInGameToolTip");
        enablePlayerList = (boolean) getValue("modules","enablePlayerList");

        showBlockModernStyle = (boolean) getValue("accessibility","showBlockModernStyle");
        armorStatusHUDmode = (int) getValue("accessibility","armorStatusHUDmode");

        showBlockRGB[0] = (int) getValue("rgbShowBlock","showBlockRGB_R_1");
        showBlockRGB[1] = (int) getValue("rgbShowBlock","showBlockRGB_G_1");
        showBlockRGB[2] = (int) getValue("rgbShowBlock","showBlockRGB_B_1");
        showBlockRGB[3] = (int) getValue("rgbShowBlock","showBlockRGB_R_2");
        showBlockRGB[4] = (int) getValue("rgbShowBlock","showBlockRGB_G_2");
        showBlockRGB[5] = (int) getValue("rgbShowBlock","showBlockRGB_B_2");
    }

    public static SmartyGuiConfig INSTANCE = new SmartyGuiConfig();
}
