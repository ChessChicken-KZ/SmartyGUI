package kz.chesschicken.smartygui.common;


import lombok.SneakyThrows;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.util.Properties;

public class ModConfig {
    private final File configFile = new File(FabricLoader.getInstance().getGameDirectory(), "smartygui.json");
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
    public byte armorStatusHUDmode = 0;

    public byte[] showBlockRGB = new byte[6] /* Should return 0 if nothing */;

    public boolean exists()
    {
        return this.configFile.exists();
    }

    @SneakyThrows
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void createNewFile()
    {
        this.configFile.createNewFile();
    }

    @SneakyThrows
    public void saveConfig() {
        OutputStream o = new FileOutputStream(configFile);
        Properties p = new Properties();

        p.setProperty("enableVersion", String.valueOf(enableVersion));

        p.setProperty("enableVersion", String.valueOf(enableVersion));
        p.setProperty("enableMainMenuDebug", String.valueOf(enableMainMenuDebug));
        p.setProperty("enableShowBlock", String.valueOf(enableShowBlock));
        p.setProperty("enableArmorStatusHUD", String.valueOf(enableArmorStatusHUD));
        p.setProperty("enableInGameToolTip", String.valueOf(enableInGameToolTip));
        p.setProperty("enableExtendedFurnaceInfo", String.valueOf(enableExtendedFurnaceInfo));
        p.setProperty("enablePlayerList", String.valueOf(enablePlayerList));
        p.setProperty("showBlockModernStyle", String.valueOf(showBlockModernStyle));

        p.setProperty("armorStatusHUDmode", String.valueOf(armorStatusHUDmode));

        p.setProperty("showBlockRGB_R_1", String.valueOf(showBlockRGB[0]));
        p.setProperty("showBlockRGB_G_1", String.valueOf(showBlockRGB[1]));
        p.setProperty("showBlockRGB_B_1", String.valueOf(showBlockRGB[2]));
        p.setProperty("showBlockRGB_R_2", String.valueOf(showBlockRGB[3]));
        p.setProperty("showBlockRGB_G_2", String.valueOf(showBlockRGB[4]));
        p.setProperty("showBlockRGB_B_2", String.valueOf(showBlockRGB[5]));


        p.store(o, "Hello!");

        o.close();
        p.clear();

    }

    @SneakyThrows
    public void readConfig() {
        InputStream o = new FileInputStream(configFile);
        Properties p = new Properties();

        p.load(o);

        enableVersion = Boolean.parseBoolean(p.getProperty("enableVersion"));
        enableMainMenuDebug = Boolean.parseBoolean(p.getProperty("enableMainMenuDebug"));
        enableShowBlock = Boolean.parseBoolean(p.getProperty("enableShowBlock"));
        enableArmorStatusHUD = Boolean.parseBoolean(p.getProperty("enableArmorStatusHUD"));
        enableInGameToolTip = Boolean.parseBoolean(p.getProperty("enableInGameToolTip"));
        enableExtendedFurnaceInfo = Boolean.parseBoolean(p.getProperty("enableExtendedFurnaceInfo"));
        enablePlayerList = Boolean.parseBoolean(p.getProperty("enablePlayerList"));
        showBlockModernStyle = Boolean.parseBoolean(p.getProperty("showBlockModernStyle"));
        armorStatusHUDmode = Byte.parseByte(p.getProperty("armorStatusHUDmode"));

        showBlockRGB[0] = getNormal(p.getProperty("showBlockRGB_R_1"));
        showBlockRGB[1] = getNormal(p.getProperty("showBlockRGB_G_1"));
        showBlockRGB[2] = getNormal(p.getProperty("showBlockRGB_B_1"));
        showBlockRGB[3] = getNormal(p.getProperty("showBlockRGB_R_2"));
        showBlockRGB[4] = getNormal(p.getProperty("showBlockRGB_G_2"));
        showBlockRGB[5] = getNormal(p.getProperty("showBlockRGB_B_2"));

        o.close();
        p.clear();
    }

    private byte getNormal(String s) {
        return (byte) (Integer.parseInt(s) + 128);
    }

    private static final ModConfig INSTANCE = new ModConfig();
    public static ModConfig getInstance()
    {
        return INSTANCE;
    }

}
