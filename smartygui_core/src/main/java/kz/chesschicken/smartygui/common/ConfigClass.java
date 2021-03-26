package kz.chesschicken.smartygui.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.SneakyThrows;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

/* Lombok :O */
public class ConfigClass {
    public static boolean enableVersion = true;
    public static boolean enableMainMenuDebug = true;
    public static boolean enableShowBlock = true;
    public static boolean enableArmorStatusHUD = true;
    public static boolean enableInGameToolTip = true;
    public static boolean enableExtendedFurnaceInfo = true;
    public static boolean enablePlayerList = true;
    public static boolean showBlockModernStyle = false;

    /**
     * Modes:
     * 0 - Bottom Right
     * 1 - Bottom Left
     * 2 - Top Right
     * 3 - Top Left
     */
    public static int armorStatusHUDmode = 0;

    public static int[] showBlockRGB = new int[6] /* Should return 0 if nothing */;


    private static final File configFile = new File(FabricLoader.getInstance().getGameDirectory(), "smartygui.json");


    /**
     * Use this when Initialization!
     */
    @SneakyThrows
    public static void checkConfig()
    {
        System.out.println(configFile.getAbsolutePath());
        if(!configFile.exists())
            configFile.createNewFile();
        else readConfig();

        saveConfig();
    }

    @SneakyThrows
    public static void saveConfig()
    {
        Map<String, Object> map = new HashMap<>();
        FileWriter fileWriter = new FileWriter(configFile);

        map.put("enableVersion", enableVersion);
        map.put("enableMainMenuDebug", enableMainMenuDebug);
        map.put("enableShowBlock", enableShowBlock);
        map.put("enableArmorStatusHUD", enableArmorStatusHUD);
        map.put("enableInGameToolTip", enableInGameToolTip);
        map.put("enableExtendedFurnaceInfo", enableExtendedFurnaceInfo);
        map.put("enablePlayerList", enablePlayerList);
        map.put("showBlockModernStyle", showBlockModernStyle);

        map.put("armorStatusHUDmode", armorStatusHUDmode);

        map.put("showBlockRGB_R_1", showBlockRGB[0]);
        map.put("showBlockRGB_G_1", showBlockRGB[1]);
        map.put("showBlockRGB_B_1", showBlockRGB[2]);
        map.put("showBlockRGB_R_2", showBlockRGB[3]);
        map.put("showBlockRGB_G_2", showBlockRGB[4]);
        map.put("showBlockRGB_B_2", showBlockRGB[5]);


        new GsonBuilder().setPrettyPrinting().create().toJson(map, fileWriter);
        fileWriter.close();

    }

    @SneakyThrows
    public static void readConfig()
    {
        FileReader fileReader = new FileReader(configFile);
        Map<?, Object> map = (new Gson()).fromJson(fileReader, Map.class);

        enableVersion = (boolean) map.get("enableVersion");
        enableMainMenuDebug = (boolean) map.get("enableMainMenuDebug");
        enableShowBlock = (boolean) map.get("enableShowBlock");
        enableArmorStatusHUD = (boolean) map.get("enableArmorStatusHUD");
        enableInGameToolTip = (boolean) map.get("enableInGameToolTip");
        enableExtendedFurnaceInfo = (boolean) map.get("enableExtendedFurnaceInfo");
        enablePlayerList = (boolean) map.get("enablePlayerList");
        showBlockModernStyle = (boolean) map.get("showBlockModernStyle");
        armorStatusHUDmode = getInt((double) map.get("armorStatusHUDmode"));

        int tempValue;

        tempValue = getInt((double) map.get("showBlockRGB_R_1"));
        showBlockRGB[0] = ((tempValue < 256 && tempValue >= 0) ? tempValue : 0);
        tempValue = getInt((double) map.get("showBlockRGB_G_1"));
        showBlockRGB[1] = ((tempValue < 256 && tempValue >= 0) ? tempValue : 0);
        tempValue = getInt((double) map.get("showBlockRGB_B_1"));
        showBlockRGB[2] = ((tempValue < 256 && tempValue >= 0) ? tempValue : 0);
        tempValue = getInt((double) map.get("showBlockRGB_R_2"));
        showBlockRGB[3] = ((tempValue < 256 && tempValue >= 0) ? tempValue : 0);
        tempValue = getInt((double) map.get("showBlockRGB_G_2"));
        showBlockRGB[4] = ((tempValue < 256 && tempValue >= 0) ? tempValue : 0);
        tempValue = getInt((double) map.get("showBlockRGB_B_2"));
        showBlockRGB[5] = ((tempValue < 256 && tempValue >= 0) ? tempValue : 0);

        fileReader.close();
    }

    /*
     * Idk why but this works only
     */
    private static int getInt(double d)
    {
        return (int) d;
    }

}
