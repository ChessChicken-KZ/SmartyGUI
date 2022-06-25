package kz.chesschicken.smartygui.commonloader.guiframework.window;

import kz.chesschicken.smartygui.commonloader.BinaryIntFunction;
import kz.chesschicken.smartygui.commonloader.RenderUtils;
import kz.chesschicken.smartygui.commonloader.guiframework.ValueXY;
import kz.chesschicken.smartygui.commonloader.guiframework.WindowTheme;
import kz.chesschicken.smartygui.commonloader.guiframework.api.*;

import java.util.ArrayList;
import java.util.List;

public class BaseWindow extends AbstractComponent implements IContainer, IUpdateOnResize, ITickUpdate, IFocus, IControllerInput {

    protected final List<AbstractComponent> components = new ArrayList<>();
    protected final BinaryIntFunction<ValueXY> resizeFunc;

    protected boolean isFocused = false;
    protected int preX, preY;
    protected int[][] render;

    public BaseWindow(int w, int h, BinaryIntFunction<ValueXY> r) {
        this.width = w;
        this.height = h;
        this.resizeFunc = r;
        this.render = WindowTheme.getWindowsProps(getContX(), getContY(), width, height, isFocused);
    }

    @Override
    public void render(int a, int b, float d) {
        for(int[] vals : render) {
            RenderUtils.gradientRenderRGB(vals[0], vals[1], vals[2], vals[3], vals[4]);
        }
        for(AbstractComponent i : components) {
            if(inner$isMoved() && i instanceof IUpdateOnResize) {
                ((IUpdateOnResize)i).updateOnResize(this.width, this.height);
                i.updateContXY(x, y);
            }
            if(i.shouldDraw()) {
                i.render(a, b, d);
            }
        }
    }

    @Override
    public void add(AbstractComponent i) {
        this.components.add(i);
    }

    @Override
    public List<AbstractComponent> getComponents() {
        return this.components;
    }

    @Override
    public void updateOnResize(int newWidth, int newHeight) {
        this.setXY(this.resizeFunc.apply(newWidth, newHeight));
    }

    @Override
    public void update() {
        this.preX = getContX();
        this.preY = getContY();
    }

    boolean inner$isMoved() {
        return this.preX != getContX() || this.preY != getContY();
    }

    @Override
    public boolean isFocused() {
        return isFocused;
    }

    @Override
    public void setFocused(boolean a) {
        this.isFocused = a;
        this.render = WindowTheme.getWindowsProps(getContX(), getContY(), width, height, isFocused);
    }

    @Override
    public void typeKey(char c, int i) {
        if(!isFocused)
            return;
        for(AbstractComponent f : components) {
            if(f instanceof IControllerInput)
                ((IControllerInput)f).typeKey(c, i);
        }
    }

    @Override
    public void clickMouse(int mX, int mY, int mEvent) {
        if(!isFocused)
            return;
        for(AbstractComponent i : components) {
            if(mEvent != 0 && i instanceof IFocus)
                ((IFocus)i).setFocused(i.isHovered(mX, mY));
            if(i instanceof IControllerInput)
                ((IControllerInput)i).clickMouse(mX, mY, mEvent);
        }
    }
}
