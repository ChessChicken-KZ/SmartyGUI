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
package kz.chesschicken.smartygui.commonloader.guiframework;

import kz.chesschicken.smartygui.commonloader.GameUtils;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Mouse;

import java.awt.*;

public class WindowTheme {

    public static Point calculateMouseLocation() {
        Minecraft minecraft = GameUtils.getMC();
        int scale = minecraft.gameSettings.guiScale;
        if(scale == 0) scale = 1000;
        int scaleFactor = 0;
        while(scaleFactor < scale && minecraft.displayWidth / (scaleFactor + 1) >= 320 && minecraft.displayHeight / (scaleFactor + 1) >= 240)
            scaleFactor++;
        return new Point(Mouse.getX() / scaleFactor, minecraft.displayHeight / scaleFactor - Mouse.getY() / scaleFactor - 1);
    }

    public static int[][] getWindowsProps(int renderX, int renderY, int width, int height, boolean f) {
        return new int[][] {
                new int[] {
                        renderX, renderY, renderX + width, renderY + height,
                        0x000000
                },
                new int[] {
                        renderX, renderY, renderX + width - 1, renderY + height - 1,
                        0xC0C7C8
                },
                new int[] {
                        renderX + 1, renderY + 1, renderX + width - 1, renderY + height - 1,
                        0x87888F
                },
                new int[] {
                        renderX + 1, renderY + 1, renderX + width - 2, renderY + height - 2,
                        0xFFFFFF
                },
                new int[] {
                        renderX + 2, renderY + 2, renderX + width - 3, renderY + height - 3,
                        0xC0C7C8
                },
                //Blue thing.
                new int[] {
                        renderX + 3, renderY + 3, renderX + width - 4, renderY + 3 + 9,
                        f ? 0x0000A8 : 0x87888F
                },
        };
    }
}
