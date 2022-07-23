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
package kz.chesschicken.smartygui.client.gui.panel;

import kz.chesschicken.smartygui.client.components.ModuleBlockRender;
import kz.chesschicken.smartygui.common.SmartyGUI;
import kz.chesschicken.smartygui.commonloader.GameUtils;
import kz.chesschicken.smartygui.commonloader.RenderUtils;
import kz.chesschicken.smartygui.commonloader.guiframework.ValueXY;
import kz.chesschicken.smartygui.commonloader.guiframework.api.AbstractComponent;
import kz.chesschicken.smartygui.commonloader.guiframework.api.BasePanel;
import kz.chesschicken.smartygui.commonloader.guiframework.api.IRunQ1W9M;
import kz.chesschicken.smartygui.commonloader.guiframework.widgets.WidgetButton;
import kz.chesschicken.smartygui.commonloader.guiframework.widgets.WidgetButtonAction;
import kz.chesschicken.smartygui.commonloader.guiframework.widgets.WidgetTextFieldNum;
import org.lwjgl.input.Keyboard;

public class PanelColorConfig extends BasePanel implements IRunQ1W9M {
    private final SmartyGUI instance;

    private final WidgetTextFieldNum[] textFields1 = new WidgetTextFieldNum[4];
    private final WidgetButton[] buttonsMove = new WidgetButton[8];

    /**
     * Debug instance of ModuleBlockRender.
     */
    private final ModuleBlockRender debugRenderer;
    /**
     * Backup values for reverting.
     */
    private final int[] backupA;

    /**
     * 0 - text. 1 - 1st colour. 2 - 2nd colour.
     */
    private byte mode = -1;

    private int[] guiColours;
    private boolean eskerty;
    private boolean updateS;

    void buttonChooseType(byte id) {
        this.mode = id;
        for (AbstractComponent o : this.components)
            if (o instanceof WidgetButton)
                ((WidgetButton) o).active = true;
        shouldDeny();

        if (this.mode > -1) {
            for (int i = 0; i < 4; i++) {
                this.textFields1[i].isEnabled = true;
                this.textFields1[i].setText(String.valueOf(this.guiColours[i + (4 * mode)]));
            }
            for (WidgetButton b : buttonsMove)
                b.active = true;
        }
    }

    void buttonRevert() {
        this.instance.CONFIG.showBlockRGB[0] = this.backupA[0];
        this.instance.CONFIG.showBlockRGB[1] = this.backupA[1];
        this.instance.CONFIG.showBlockRGB[2] = this.backupA[2];
        setGuiColours();
        for (int i = 0; i < 4; i++) {
            this.textFields1[i].setText(String.valueOf(this.guiColours[i + (4 * mode)]));
        }
    }

    void buttonPlusMinus(int id) {
        if (id < 4)
            this.textFields1[id].setText(utilParseText(this.textFields1[id].getText(), -1));
        else
            this.textFields1[id - 4].setText(utilParseText(this.textFields1[id - 4].getText(), 1));
        runQ1W9M();
    }

    void setGuiColours() {
        int[] text1 = RenderUtils.getRGBAFromInt(instance.CONFIG.showBlockRGB[0]);
        int[] c1 = RenderUtils.getRGBAFromInt(instance.CONFIG.showBlockRGB[1]);
        int[] c2 = RenderUtils.getRGBAFromInt(instance.CONFIG.showBlockRGB[2]);
        this.guiColours = new int[]{
                text1[0], text1[1], text1[2], text1[3],
                c1[0], c1[1], c1[2], c1[3],
                c2[0], c2[1], c2[2], c2[3],
        };
    }

    void shouldDeny() {
        eskerty = this.instance.CONFIG.transparency || this.instance.CONFIG.showBlockModernStyle;
        ((WidgetButton) this.components.get(0)).active = ((WidgetButton) this.components.get(1)).active = !eskerty;
    }

    public PanelColorConfig(SmartyGUI smartygui) {
        Keyboard.enableRepeatEvents(true);

        this.instance = smartygui;
        this.backupA = new int[]{smartygui.CONFIG.showBlockRGB[0], smartygui.CONFIG.showBlockRGB[1], smartygui.CONFIG.showBlockRGB[2]};

        this.debugRenderer = new ModuleBlockRender(GameUtils.getMC(), instance);
        this.debugRenderer.__updateBlockDebug();

        setGuiColours();

        add(new WidgetButtonAction("1st Colour", 59, 20, (w, h) -> new ValueXY(w / 2 - 91, h / 4 + 8), () -> buttonChooseType((byte) 0)));
        add(new WidgetButtonAction("2nd Colour", 59, 20, (w, h) -> new ValueXY(w / 2 - 30, h / 4 + 8), () -> buttonChooseType((byte) 1)));
        add(new WidgetButtonAction("Text", 59, 20, (w, h) -> new ValueXY(w / 2 + 31, h / 4 + 8), () -> buttonChooseType((byte) 2)));

        add(new WidgetButtonAction("Save", 59, 20, (w, h) -> new ValueXY(w / 2 - 30, h / 4 + 8 - 22), () -> {
            this.instance.CONFIG.forceSave();
            updateS = true;
        }));
        add(new WidgetButtonAction("Revert", 59, 20, (w, h) -> new ValueXY(w / 2 - 91, h / 4 + 8 - 22), this::buttonRevert));
        add(new WidgetButtonAction("Reload", 59, 20, (w, h) -> new ValueXY(w / 2 + 31, h / 4 + 8 - 22), this::setGuiColours));

        for (byte b = 0; b < 4; b++) {
            final byte finalB = b;
            add(textFields1[b] = new WidgetTextFieldNum(50, 20, (w, h) -> new ValueXY(w / 2 - 25, h / 4 + 32 + (finalB * 24)), this));
            textFields1[b].setMaxLength(3);
            textFields1[b].isEnabled = false;
            /* 510 511 512 513 */
            add(buttonsMove[b] = new WidgetButtonAction("-", 20, 20, (w, h) -> new ValueXY(w / 2 + 27, h / 4 + 32 + (24 * finalB)), () -> buttonPlusMinus(finalB)));
            /* 514 515 516 517 */
            add(buttonsMove[b + 4] = new WidgetButtonAction("+", 20, 20, (w, h) -> new ValueXY(this.width / 2 - 47, this.height / 4 + 32 + (24 * finalB)), () -> buttonPlusMinus(finalB + 4)));
        }

        for (WidgetButton b : buttonsMove)
            b.active = false;

        shouldDeny();
    }

    @Override
    public void runQ1W9M() {
        for (int i = 0; i < 4; i++) {
            if (this.textFields1[i].getText().length() < 1)
                this.textFields1[i].setText("0");
            this.guiColours[i + (4 * mode)] = Integer.parseInt(this.textFields1[i].getText());
        }
        this.instance.CONFIG.showBlockRGB[0] = RenderUtils.getIntFromRGBA(this.guiColours[0], this.guiColours[1], this.guiColours[2], this.guiColours[3]);
        this.instance.CONFIG.showBlockRGB[1] = RenderUtils.getIntFromRGBA(this.guiColours[4], this.guiColours[5], this.guiColours[6], this.guiColours[7]);
        this.instance.CONFIG.showBlockRGB[2] = RenderUtils.getIntFromRGBA(this.guiColours[8], this.guiColours[9], this.guiColours[10], this.guiColours[11]);
    }

    @Override
    public void render(int a, int b, float f) {
        RenderUtils.gradientRender(0, 0, this.width, this.height, -1072689136, -804253680);
        if (mode > -1) {
            RenderUtils.gradientRender(this.width / 2 - 90, this.height / 4 + 30, this.width / 2 + 90, this.height / 4 + 32 + (24 * 4), this.instance.CONFIG.showBlockRGB[mode], this.instance.CONFIG.showBlockRGB[mode]);
        }
        super.render(a, b, f);
        RenderUtils.renderShadowCenteredString(this.width / 2, 40, 0xFFFFFF, "Colour Settings");
        if (eskerty) {
            RenderUtils.renderShadowCenteredString(this.width / 2, 15, 0xFF0000, "Please turn off Transparency or/and Modern Style");
            RenderUtils.renderShadowCenteredString(this.width / 2, 25, 0xFF0000, "in order to manage background colour options!");
        }
        if (updateS)
            RenderUtils.renderShadowString(5, 5, 0xFFD800, "Changes have been saved!");
        debugRenderer.doBlockRendering(this.width / 2, this.height * 6 / 8 + 20, 4);
    }

    @Override
    public void onClose() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    public void clickMouse(int mX, int mY, int mEvent) {
        if (updateS) updateS = false;
        super.clickMouse(mX, mY, mEvent);
    }

    String utilParseText(String d, int a) {
        if (d.length() < 1) d = "0";
        int q = (Integer.parseInt(d) + a);
        if (q > 255) return "255";
        if (q < 0) return "0";
        return String.valueOf(q);
    }
}
