package kz.chesschicken.smartygui;


import net.minecraft.client.render.RenderHelper;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.client.render.entity.ItemRenderer;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.item.ItemInstance;
import net.modificationstation.stationloader.api.common.config.Category;
import net.modificationstation.stationloader.api.common.config.Property;
import net.modificationstation.stationloader.api.common.mod.StationMod;
import org.lwjgl.opengl.GL11;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SmartyGui implements StationMod
{

    public static boolean options_mainmenudebug;
    public static boolean options_showwaila;
    public static boolean options_armorstatus;
    public static boolean options_ingametooltip;
    public static boolean options_guifurnaceextended;
    public static boolean options_nearplayerlist;
    public static boolean options_furnaceinfo;

    public static boolean test1 = false;

    public static int[] waila_rgbvalues = new int[6];

    public static String joinString(String[] strings)
    {
        String s = "";
        for(int i = 0; i < strings.length; i++) { s = s + " " + strings[i]; }
        return s.trim();
    }




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
                    || osName.contains("solaris")) {
                osstring.append("POSIX UNIX");
            } else if (osName.contains("hp-ux")
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

    public static void gradientRender(int x1, int y1, int x2, int y2, int startColour, int endColour) {
        float var7 = (float)(startColour >> 24 & 255) / 255.0F;
        float var8 = (float)(startColour >> 16 & 255) / 255.0F;
        float var9 = (float)(startColour >> 8 & 255) / 255.0F;
        float var10 = (float)(startColour & 255) / 255.0F;
        float var11 = (float)(endColour >> 24 & 255) / 255.0F;
        float var12 = (float)(endColour >> 16 & 255) / 255.0F;
        float var13 = (float)(endColour >> 8 & 255) / 255.0F;
        float var14 = (float)(endColour & 255) / 255.0F;
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glDisable(3008);
        GL11.glBlendFunc(770, 771);
        GL11.glShadeModel(7425);
        Tessellator var15 = Tessellator.INSTANCE;
        var15.start();
        var15.colour(var8, var9, var10, var7);
        var15.pos(x2, y1, 0.0D);
        var15.pos(x1, y1, 0.0D);
        var15.colour(var12, var13, var14, var11);
        var15.pos(x1, y2, 0.0D);
        var15.pos(x2, y2, 0.0D);
        var15.draw();
        GL11.glShadeModel(7424);
        GL11.glDisable(3042);
        GL11.glEnable(3008);
        GL11.glEnable(3553);
    }

    public static void renderItem(ItemRenderer ir, TextRenderer fr, TextureManager textureManager, ItemInstance itemInstance, int x, int y) {
        GL11.glPushMatrix();
        GL11.glRotatef(120.0F, 1.0F, 0.0F, 0.0F);
        RenderHelper.enableLighting();
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(32826);
        ir.method_1487(fr, textureManager, itemInstance, x, y);
        ir.method_1488(fr, textureManager, itemInstance, x, y);
        GL11.glDisable(32826);
        RenderHelper.disableLighting();
        GL11.glPopMatrix();
    }

    public static void logMessageAs(String sender, String string) {
        System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] [" + sender + "] " + string);
    }

    @Override
    public void preInit() {
        getDefaultConfig().load();

        Category allConfig = getDefaultConfig().getCategory("Main");
        Property opt_mmd = allConfig.getProperty("optionsMainmenudebug", true);
        Property opt_sw = allConfig.getProperty("optionsShowwaila", true);
        Property opt_ash = allConfig.getProperty("optionsArmorstatus", true);
        Property opt_igt = allConfig.getProperty("optionsIngametooltip", true);
        Property opt_gfe = allConfig.getProperty("optionsExtendedFurnaceGui", true);
        Property opt_npl = allConfig.getProperty("optionsNearPlayerList", true);
        Property opt_fui = allConfig.getProperty("optionsFurnaceInfo", true);
        Category gradientWaila = getDefaultConfig().getCategory("Waila");
        Property rgbS_r = gradientWaila.getProperty("startGradientR",0);
        Property rgbS_g = gradientWaila.getProperty("startGradientG",0);
        Property rgbS_b = gradientWaila.getProperty("startGradientB",0);
        Property rgbE_r = gradientWaila.getProperty("endGradientR",0);
        Property rgbE_g = gradientWaila.getProperty("endGradientG",0);
        Property rgbE_b = gradientWaila.getProperty("endGradientB",0);

        getDefaultConfig().save();

        options_mainmenudebug = opt_mmd.getBooleanValue();
        options_showwaila = opt_sw.getBooleanValue();
        options_armorstatus = opt_ash.getBooleanValue();
        options_ingametooltip = opt_igt.getBooleanValue();
        options_guifurnaceextended = opt_gfe.getBooleanValue();
        options_nearplayerlist = opt_npl.getBooleanValue();
        options_furnaceinfo = opt_fui.getBooleanValue();

        waila_rgbvalues[0] = ((rgbS_r.getIntValue() < 256 && rgbS_r.getIntValue() >= 0) ? rgbS_r.getIntValue() : 0);
        waila_rgbvalues[1] = ((rgbS_g.getIntValue() < 256 && rgbS_g.getIntValue() >= 0) ? rgbS_g.getIntValue() : 0);
        waila_rgbvalues[2] = ((rgbS_b.getIntValue() < 256 && rgbS_b.getIntValue() >= 0) ? rgbS_b.getIntValue() : 0);
        waila_rgbvalues[3] = ((rgbE_r.getIntValue() < 256 && rgbE_r.getIntValue() >= 0) ? rgbE_r.getIntValue() : 0);
        waila_rgbvalues[4] = ((rgbE_g.getIntValue() < 256 && rgbE_g.getIntValue() >= 0) ? rgbE_g.getIntValue() : 0);
        waila_rgbvalues[5] = ((rgbE_b.getIntValue() < 256 && rgbE_b.getIntValue() >= 0) ? rgbE_b.getIntValue() : 0);

    }
}
