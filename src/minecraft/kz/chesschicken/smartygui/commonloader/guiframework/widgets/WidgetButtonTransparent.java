package kz.chesschicken.smartygui.commonloader.guiframework.widgets;

import kz.chesschicken.smartygui.commonloader.BinaryIntFunction;
import kz.chesschicken.smartygui.commonloader.RenderUtils;
import kz.chesschicken.smartygui.commonloader.guiframework.ValueXY;

public class WidgetButtonTransparent extends WidgetButtonA {

    public WidgetButtonTransparent(String text, BinaryIntFunction<ValueXY> f, Action1 a) {
        super(text, RenderUtils.getStringSize(text), 8, f, a);
    }

    @Override
    public void render(int a, int b, float d) {
        RenderUtils.renderShadowCenteredString(this.x + this.width / 2, this.y + (this.height - 8) / 2, (!this.active) ? -6250336 : (isHovered(a, b) ? 16777120 : 14737632), this.text);
    }
}
