package kz.chesschicken.smartygui.commonloader.guiframework.widgets;

import kz.chesschicken.smartygui.commonloader.BinaryIntFunction;
import kz.chesschicken.smartygui.commonloader.GameUtils;
import kz.chesschicken.smartygui.commonloader.guiframework.ValueXY;

import java.util.function.Consumer;

public class WidgetButtonActionB extends WidgetButton {

    protected final Consumer<WidgetButtonActionB> action;

    public WidgetButtonActionB(String text, BinaryIntFunction<ValueXY> f, Consumer<WidgetButtonActionB> a) {
        super(text, f);
        this.action = a;
    }

    public WidgetButtonActionB(String text, int w, int h, BinaryIntFunction<ValueXY> f, Consumer<WidgetButtonActionB> a) {
        super(text, w, h, f);
        this.action = a;
    }

    public WidgetButtonActionB(int w, int h, String text, BinaryIntFunction<ValueXY> f, Consumer<WidgetButtonActionB> a) {
        super(text, f);
        this.action = a;
    }

    @Override
    public void typeKey(char c, int i) {

    }

    @Override
    public void clickMouse(int mX, int mY, int mEvent) {
        if(isHovered(mX, mY) && mEvent == 0 && this.action != null) {
            GameUtils.playSoundFX("random.click", 1.0F, 1.0F);
            this.action.accept(this);
        }
    }

}
