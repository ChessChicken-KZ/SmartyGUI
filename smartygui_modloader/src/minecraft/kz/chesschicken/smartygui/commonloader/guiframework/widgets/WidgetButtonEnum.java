package kz.chesschicken.smartygui.commonloader.guiframework.widgets;

import kz.chesschicken.smartygui.commonloader.BinaryIntFunction;
import kz.chesschicken.smartygui.commonloader.GameUtils;
import kz.chesschicken.smartygui.commonloader.guiframework.ValueXY;

import java.util.function.BiConsumer;

public class WidgetButtonEnum<T extends Enum<T>> extends WidgetButton {

    protected T currentEnum;
    protected final String text1;
    protected final BiConsumer<WidgetButtonEnum<T>, T> enumProcessor;

    public WidgetButtonEnum(String text, BinaryIntFunction<ValueXY> f, BiConsumer<WidgetButtonEnum<T>, T> c) {
        super(text, f);
        this.text1 = text;
        this.enumProcessor = c;
    }

    public WidgetButtonEnum(String text, int w, int h, BinaryIntFunction<ValueXY> f, BiConsumer<WidgetButtonEnum<T>, T> c) {
        super(text, w, h, f);
        this.text1 = text;
        this.enumProcessor = c;
    }

    public void changeEnum(T e) {
        this.currentEnum = e;
        this.text = text1 + ": " + this.currentEnum.toString();
    }

    public T getCurrentEnum() {
        return this.currentEnum;
    }

    @Override
    public void typeKey(char c, int i) {

    }

    @Override
    public void clickMouse(int mX, int mY, int mEvent) {
        if(isHovered(mX, mY) && mEvent == 0 && this.enumProcessor != null) {
            GameUtils.playSoundFX("random.click", 1.0F, 1.0F);
            this.enumProcessor.accept(this, currentEnum);
        }
        //changeEnum(T.values()[(currentEnum.ordinal() + 1 < EnumTheme.values().length) ? currentEnum.ordinal() + 1 : 0]);
    }
}
