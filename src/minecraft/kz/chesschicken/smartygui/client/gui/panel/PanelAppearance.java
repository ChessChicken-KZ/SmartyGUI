package kz.chesschicken.smartygui.client.gui.panel;

import kz.chesschicken.smartygui.common.EnumTheme;
import kz.chesschicken.smartygui.common.SmartyGUI;
import kz.chesschicken.smartygui.common.SmartyGuiConfig;
import kz.chesschicken.smartygui.commonloader.GameUtils;
import kz.chesschicken.smartygui.commonloader.RenderUtils;
import kz.chesschicken.smartygui.commonloader.guiframework.ValueXY;
import kz.chesschicken.smartygui.commonloader.guiframework.api.BasePanel;
import kz.chesschicken.smartygui.commonloader.guiframework.api.IRunQ1W9M;
import kz.chesschicken.smartygui.commonloader.guiframework.widgets.WidgetButtonAction;
import kz.chesschicken.smartygui.commonloader.guiframework.widgets.WidgetButtonActionB;
import kz.chesschicken.smartygui.commonloader.guiframework.widgets.WidgetButtonBoolean;
import kz.chesschicken.smartygui.commonloader.guiframework.widgets.WidgetButtonEnum;

import java.util.function.Consumer;

public class PanelAppearance extends BasePanel {
    private final SmartyGUI instance;

    private static final String[] a_ash1 = new String[] { "Bottom Right", "Bottom Left", "Top Right", "Top Left" };

    private byte a_ashmode;

    public PanelAppearance(SmartyGUI smartyGUI) {
        this.instance = smartyGUI;
        this.a_ashmode = (byte) instance.CONFIG.armorStatusHUDmode;

        WidgetButtonEnum<EnumTheme> themeButton;
        add(themeButton = new WidgetButtonEnum<>("Icon Theme", 182, 20, (w, h) -> new ValueXY(w / 2 - 91, h / 4 + 8), (a, b) ->
                a.changeEnum(EnumTheme.values()[(b.ordinal() + 1 < EnumTheme.values().length) ? b.ordinal() + 1 : 0])));
        themeButton.changeEnum(EnumTheme.valueOf(SmartyGuiConfig.iconTheme));

        //Dark theme
        add(new WidgetButtonBoolean("Dark Theme", 90, 20, (w, h) -> new ValueXY(w / 2 - 91, h / 4 + 32),
                SmartyGuiConfig.darkTheme, b -> SmartyGuiConfig.darkTheme = b));

        //MM Info
        add(new WidgetButtonBoolean("F3 Extended", 90, 20, (w, h) -> new ValueXY(w / 2 + 1, h / 4 + 32),
                instance.CONFIG.enableDebugF3, b -> instance.CONFIG.enableDebugF3 = b));

        //Modern ShowBlock
        add(new WidgetButtonBoolean("Modern Style", 90, 20, (w, h) -> new ValueXY(w / 2 - 91, h / 4 + 56),
                instance.CONFIG.showBlockModernStyle, b -> instance.CONFIG.showBlockModernStyle = b));

        //Transparency
        add(new WidgetButtonBoolean("Transparency", 90, 20, (w, h) -> new ValueXY(w / 2 + 1, h / 4 + 56),
                instance.CONFIG.transparency, b -> instance.CONFIG.transparency = b));

        //ArmorStatus HUD Mode
        add(new WidgetButtonActionB("Armor HUD: " + a_ash1[a_ashmode], 182, 20, (w, h) -> new ValueXY(w / 2 - 91, h / 4 + 80), new Consumer<WidgetButtonActionB>() {
            @Override
            public void accept(WidgetButtonActionB w) {
                a_ashmode = (byte) ((a_ashmode + 1 < a_ash1.length) ? a_ashmode + 1 : 0);
                w.setText("Armor HUD: " + a_ash1[a_ashmode]);
            }
        }));

        add(new WidgetButtonAction("Save and Close", 182, 20, (w, h) -> new ValueXY(w / 2 - 91, h / 4 + 104), () -> {
            getComponents().stream().filter(c -> c instanceof IRunQ1W9M).forEach(a -> ((IRunQ1W9M) a).runQ1W9M());
            instance.CONFIG.armorStatusHUDmode = a_ashmode;
            instance.CONFIG.forceSave();
            instance.updateASHUD();
            GameUtils.openPanel(new PanelHome(instance));
        }));
    }

    @Override
    public void render(int q, int w, float e) {
        RenderUtils.gradientRender(0, 0, this.width, this.height, -1072689136, -804253680);
        super.render(q, w, e);
        RenderUtils.renderShadowCenteredString(this.width / 2, 40, 0xFFFFFF, "Appearance Options");
    }
}
