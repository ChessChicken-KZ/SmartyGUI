package kz.chesschicken.smartygui;


import kz.chesschicken.smartygui.common.SmartyGuiConfig;
import lombok.SneakyThrows;
import net.fabricmc.api.ClientModInitializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SmartyGui implements ClientModInitializer
{

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
        return System.getenv("PROCESSOR_IDENTIFIER");
    }


    public static void logMessageAs(String sender, String string) {
        System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] [" + sender + "] " + string);
    }

    @SneakyThrows
    @Override
    public void onInitializeClient() {
        SmartyGuiConfig.INSTANCE.start();
    }
}
