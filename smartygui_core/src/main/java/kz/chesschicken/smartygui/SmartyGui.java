package kz.chesschicken.smartygui;


import kz.chesschicken.smartygui.common.APIDetector;
import kz.chesschicken.smartygui.common.SmartyGuiConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SmartyGui implements ClientModInitializer
{
    public static String getFullOperatingSystemName()
    {
        String name = System.getProperty("os.name");
        if(name == null) return "Unknown";

        return name + " " + System.getProperty("os.version");
    }

    public static String getFormattedOperatingSystemName()
    {

        StringBuilder osstring = new StringBuilder();
        try
        {
            String name = System.getProperty("os.name");
            if (name == null) throw new Exception("os.name not found");

            name = name.toLowerCase(Locale.ENGLISH);
            //windows
            if (name.contains("windows")) {
                osstring.append("Windows");

                //unix os
            } else if (name.contains("mpe/ix")
                    || name.contains("freebsd")
                    || name.contains("irix")
                    || name.contains("digital unix")
                    || name.contains("unix")) {
                osstring.append("Unix");

                //linux
            } else if(name.contains("linux")){
                osstring.append("Linux");

                //macos
            } else if (name.contains("mac os")) {
                osstring.append("Mac OS");

                //other unix
            } else if (name.contains("sun os")
                    || name.contains("sunos")
                    || name.contains("solaris")
                    || name.contains("hp-ux")
                    || name.contains("aix")) {
                osstring.append("Posix Unix");
            } else {
                osstring.append(name.toUpperCase());
            }

        } catch (Exception ex) {
            osstring.append("UNKNOWN");
        } finally {
            osstring.append(" ").append(System.getProperty("os.version"));
        }
        return osstring.toString();
    }

    public static String getProcessorInfo()
    {
        return System.getenv("PROCESSOR_IDENTIFIER");
    }


    public static void logMessageAs(String sender, String string) {
        System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] [" + sender + "] " + string);
    }

    public static boolean moduleStAPI;
    @Override
    public void onInitializeClient() {
        SmartyGuiConfig.INSTANCE.start();
        APIDetector.INSTANCE.checkAPI();
        moduleStAPI = FabricLoader.getInstance().isModLoaded("smartyguistapi");
    }
}
