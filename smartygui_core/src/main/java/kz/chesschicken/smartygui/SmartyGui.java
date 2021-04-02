package kz.chesschicken.smartygui;


import lombok.SneakyThrows;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

public class SmartyGui implements ClientModInitializer
{
    public static byte enableVersion = 1;
    public static byte enableMainMenuDebug = 1;
    public static byte enableShowBlock = 1;
    public static byte enableArmorStatusHUD = 1;
    public static byte enableInGameToolTip = 1;
    public static byte enableExtendedFurnaceInfo = 1;
    public static byte enablePlayerList = 1;
    public static byte showBlockModernStyle = 0;

    /**
     * Modes:
     * 0 - Bottom Right
     * 1 - Bottom Left
     * 2 - Top Right
     * 3 - Top Left
     */
    public static byte armorStatusHUDmode = 0;

    public static byte[] showBlockRGB = new byte[6] /* Should return 0 if nothing */;


    private static final File configFile = new File(FabricLoader.getInstance().getGameDirectory(), "smartygui.json");

    public static String getOSNAME()
    {

        StringBuilder osstring = new StringBuilder();
        try
        {
            String osName = System.getProperty("os.name");
            if (osName == null) {
                throw new IOException("os.name not found");
            }
            osName = osName.toLowerCase(Locale.ENGLISH);
            if (osName.contains("windows")) {
                osstring.append("WINDOWS");
            } else if (osName.contains("mpe/ix")
                    || osName.contains("freebsd")
                    || osName.contains("irix")
                    || osName.contains("digital unix")
                    || osName.contains("unix")) {
                osstring.append("UNIX");
            } else if(osName.contains("linux")){
                osstring.append("LINUX");
            } else if (osName.contains("mac os")) {
                osstring.append("MAC OS");
            } else if (osName.contains("sun os")
                    || osName.contains("sunos")
                    || osName.contains("solaris")
                    || osName.contains("hp-ux")
                    || osName.contains("aix")) {
                osstring.append("POSIX UNIX");
            } else {
                osstring.append("UNKNOWN");
            }

        } catch (Exception ex) {
            osstring.append("UNKNOWN");
        } finally {
            osstring.append(" ").append(System.getProperty("os.version"));
        }
        return osstring.toString();
    }

    public static String getCPUINFO()
    {
        try {
            String osName = System.getProperty("os.name");
            if (osName == null) {
                throw new IOException("os.name not found");
            }
            osName = osName.toLowerCase(Locale.ENGLISH);
            if(osName.contains("linux"))
                return Files.lines(Paths.get("/proc/cpuinfo"))
                        .filter(line -> line.startsWith("model name"))
                        .map(line -> line.replaceAll(".*: ", ""))
                        .findFirst().orElse("");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void logMessageAs(String sender, String string) {
        System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] [" + sender + "] " + string);
    }


    @SneakyThrows
    @Override
    public void onInitializeClient() {
        System.out.println(configFile.getAbsolutePath());
        if(!configFile.exists())
            configFile.createNewFile();
        else readConfig();

        saveConfig();
    }

    @SneakyThrows
    public void saveConfig()
    {
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
    public static void readConfig()
    {
        InputStream o = new FileInputStream(configFile);
        Properties p = new Properties();

        p.load(o);

        enableVersion = Byte.parseByte(p.getProperty("enableVersion"));
        enableMainMenuDebug = Byte.parseByte(p.getProperty("enableMainMenuDebug"));
        enableShowBlock = Byte.parseByte(p.getProperty("enableShowBlock"));
        enableArmorStatusHUD = Byte.parseByte(p.getProperty("enableArmorStatusHUD"));
        enableInGameToolTip = Byte.parseByte(p.getProperty("enableInGameToolTip"));
        enableExtendedFurnaceInfo = Byte.parseByte(p.getProperty("enableExtendedFurnaceInfo"));
        enablePlayerList = Byte.parseByte(p.getProperty("enablePlayerList"));
        showBlockModernStyle = Byte.parseByte(p.getProperty("showBlockModernStyle"));
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

    private static byte getNormal(String s)
    {
        return (byte) (Integer.parseInt(s) + 128);
    }
}
