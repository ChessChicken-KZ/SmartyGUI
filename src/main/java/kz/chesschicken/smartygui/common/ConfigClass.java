package kz.chesschicken.smartygui.common;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

/* Lombok :O */
public class ConfigClass {
    public static boolean enableMainMenuDebug = true;
    public static boolean enableShowBlock = true;
    public static boolean enableArmorStatusHUD = true;
    public static boolean enableInGameToolTip = true;
    public static boolean enableExtendedFurnaceInfo = true;
    public static boolean enablePlayerList = true;
    public static boolean showBlockModernStyle = false;

    public static int[] showblockRGB = new int[6] /* Should return 0 if nothing */;


    private static final File configFile = new File(FabricLoader.getInstance().getGameDirectory(), "smartygui.config");


    /**
     * Use this when Initialization!
     */
    @SneakyThrows
    public static void checkConfig()
    {
        if(!configFile.exists())
        {
            configFile.createNewFile();
            saveConfig();
        }else readConfig();
    }

    @SneakyThrows
    public static void saveConfig()
    {
        Map<String, Object> map = new HashMap<>();
        FileWriter fileWriter = new FileWriter(configFile);

        map.put("enableMainMenuDebug", enableMainMenuDebug);
        map.put("enableShowBlock", enableShowBlock);
        map.put("enableArmorStatusHUD", enableArmorStatusHUD);
        map.put("enableInGameToolTip", enableInGameToolTip);
        map.put("enableExtendedFurnaceInfo", enableExtendedFurnaceInfo);
        map.put("enablePlayerList", enablePlayerList);
        map.put("showBlockModernStyle", showBlockModernStyle);

        map.put("showblockRGB_R_1", showblockRGB[0]);
        map.put("showblockRGB_G_1", showblockRGB[1]);
        map.put("showblockRGB_B_1", showblockRGB[2]);
        map.put("showblockRGB_R_2", showblockRGB[3]);
        map.put("showblockRGB_G_2", showblockRGB[4]);
        map.put("showblockRGB_B_2", showblockRGB[5]);


        new Gson().toJson(map, fileWriter);
        fileWriter.close();

    }

    @SneakyThrows
    public static void readConfig()
    {
        FileReader fileReader = new FileReader(configFile);
        Map<?, ?> map = (new Gson()).fromJson(fileReader, Map.class);

        enableMainMenuDebug = Boolean.parseBoolean((String) map.get("enableMainMenuDebug"));
        enableShowBlock = Boolean.parseBoolean((String) map.get("enableShowBlock"));
        enableArmorStatusHUD = Boolean.parseBoolean((String) map.get("enableArmorStatusHUD"));
        enableInGameToolTip = Boolean.parseBoolean((String) map.get("enableInGameToolTip"));
        enableExtendedFurnaceInfo = Boolean.parseBoolean((String) map.get("enableExtendedFurnaceInfo"));
        enablePlayerList = Boolean.parseBoolean((String) map.get("enablePlayerList"));
        showBlockModernStyle = Boolean.parseBoolean((String) map.get("showBlockModernStyle"));

        int tempValue;

        tempValue = Integer.parseInt((String) map.get("showblockRGB_R_1"));
        showblockRGB[0] = ((tempValue < 256 && tempValue >= 0) ? tempValue : 0);
        tempValue = Integer.parseInt((String) map.get("showblockRGB_G_1"));
        showblockRGB[1] = ((tempValue < 256 && tempValue >= 0) ? tempValue : 0);
        tempValue = Integer.parseInt((String) map.get("showblockRGB_B_1"));
        showblockRGB[2] = ((tempValue < 256 && tempValue >= 0) ? tempValue : 0);
        tempValue = Integer.parseInt((String) map.get("showblockRGB_R_2"));
        showblockRGB[3] = ((tempValue < 256 && tempValue >= 0) ? tempValue : 0);
        tempValue = Integer.parseInt((String) map.get("showblockRGB_G_2"));
        showblockRGB[4] = ((tempValue < 256 && tempValue >= 0) ? tempValue : 0);
        tempValue = Integer.parseInt((String) map.get("showblockRGB_B_2"));
        showblockRGB[5] = ((tempValue < 256 && tempValue >= 0) ? tempValue : 0);

        fileReader.close();
    }

}
