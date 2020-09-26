package kz.chesschicken.smartygui.api;

import kz.chesschicken.smartygui.SmartyGui;
import net.minecraft.block.BlockBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ScreenBase;
import net.minecraft.client.gui.screen.ingame.EditSign;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.hit.HitType;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class CommandHandler {
    public static void runCommand(Minecraft minecraft, String string)
    {
        String[] args = string.split(" ");
        if(args.length == 1)
        {
            if(args[0].equalsIgnoreCase("exit"))
                minecraft.openScreen(null);
            if(args[0].equalsIgnoreCase("clear"))
                GuiCheatInput.toshow.clear();
            if(args[0].equalsIgnoreCase("terminate"))
                minecraft.stop();
        }
        if(args.length == 2)
        {
            if(args[0].equalsIgnoreCase("sign"))
            {
                if(minecraft.hitResult != null) {
                    if (minecraft.hitResult.type == HitType.TILE) {
                        int ix = minecraft.hitResult.x;
                        int iy = minecraft.hitResult.y;
                        int iz = minecraft.hitResult.z;
                        if (minecraft.level.getTileId(ix, iy, iz) == BlockBase.STANDING_SIGN.id || minecraft.level.getTileId(ix, iy, iz) == BlockBase.WALL_SIGN.id) {
                            TileEntitySign entitySign = (TileEntitySign) minecraft.level.getTileEntity(ix, iy, iz);

                            if (args[1].equalsIgnoreCase("show")) {
                                for (int i = 0; i < entitySign.lines.length; i++) {
                                    GuiCheatInput.toshow.add(entitySign.lines[i]);
                                }
                            }

                            if (args[1].equalsIgnoreCase("copy")) {
                                Toolkit.getDefaultToolkit()
                                        .getSystemClipboard()
                                        .setContents(
                                                new StringSelection(SmartyGui.joinString(entitySign.lines)),
                                                null
                                        );
                            }

                            if(args[1].equalsIgnoreCase("edit")) {
                                minecraft.openScreen(new EditSign(entitySign));
                            }
                        }
                    }
                }
            }

            if(args[0].equalsIgnoreCase("color"))
            {
                GuiCheatInput.termcolor[0] = ((Integer.parseInt(args[1]) < 256 && (Integer.parseInt(args[1]) >= 0)) ? Integer.parseInt(args[1]) : 0);
                GuiCheatInput.termcolor[1] = ((Integer.parseInt(args[1]) < 256 && (Integer.parseInt(args[1]) >= 0)) ? Integer.parseInt(args[1]) : 0);
                GuiCheatInput.termcolor[2] = ((Integer.parseInt(args[1]) < 256 && (Integer.parseInt(args[1]) >= 0)) ? Integer.parseInt(args[1]) : 0);
            }


        }

        if(args.length == 4)
        {
            if(args[0].equalsIgnoreCase("color"))
            {
                GuiCheatInput.termcolor[0] = ((Integer.parseInt(args[1]) < 256 && (Integer.parseInt(args[1]) >= 0)) ? Integer.parseInt(args[1]) : 0);
                GuiCheatInput.termcolor[1] = ((Integer.parseInt(args[2]) < 256 && (Integer.parseInt(args[2]) >= 0)) ? Integer.parseInt(args[2]) : 0);
                GuiCheatInput.termcolor[2] = ((Integer.parseInt(args[3]) < 256 && (Integer.parseInt(args[3]) >= 0)) ? Integer.parseInt(args[3]) : 0);
            }
        }

        if(args.length > 1)
            if(args[0].equalsIgnoreCase("echo"))
            {
                String[] echo1 = string.split(" ", 2);
                GuiCheatInput.toshow.add(echo1[1]);
            }


    }
}
