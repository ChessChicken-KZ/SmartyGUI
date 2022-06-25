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
package kz.chesschicken.smartygui.commonloader.guiframework.window;

import kz.chesschicken.smartygui.commonloader.RenderUtils;
import kz.chesschicken.smartygui.commonloader.guiframework.WindowTheme;
import kz.chesschicken.smartygui.commonloader.guiframework.api.*;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BaseWindow extends AbstractComponent implements IContainer, IUpdateOnResize, ITickUpdate, IFocus, IControllerInput {

    protected final List<AbstractComponent> components = new ArrayList<>();

    protected boolean isFocused = false;
    protected int preX, preY;
    protected int[][] render;
    private Point dragOffset;

    public boolean isDragging() {
        return dragOffset != null;
    }

    public void setDragging(boolean dragging) {
        if(dragging) {
            Point mouseLocation = WindowTheme.calculateMouseLocation();
            dragOffset = new Point(mouseLocation.x - this.x, mouseLocation.y - this.y);
        } else
            dragOffset = null;
    }

    public boolean insideSpecBox(int boxX, int boxY, int boxWidth, int boxHeight, int var2, int var3) {
        return var2 >= boxX && var3 >= boxY && var2 < boxX + boxWidth && var3 < boxY + boxHeight;
    }

    public BaseWindow(int w, int h, int x, int y) {
        this.width = w;
        this.height = h;
        this.x = x;
        this.y = y;
        this.render = WindowTheme.getWindowsProps(getContX(), getContY(), width, height, isFocused);
    }

    public BaseWindow(int w, int h, int x, int y, Consumer<? super BaseWindow> a) {
        this(w, h, x, y);
        a.accept(this);
    }

    @Override
    public void render(int a, int b, float d) {
        if(isDragging()) {
            if(Mouse.isButtonDown(0)) {
                Point mouseLocation = WindowTheme.calculateMouseLocation();
                this.x = (mouseLocation.x - dragOffset.x);
                this.y = (mouseLocation.y - dragOffset.y);
                this.render = WindowTheme.getWindowsProps(getContX(), getContY(), width, height, isFocused);
            } else
                setDragging(false);
        }

        GL11.glPushMatrix();
        GL11.glTranslatef(0, 0, 50.0F);
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
        GL11.glPopMatrix();
    }

    @Override
    public void onClose() {
        for(AbstractComponent c : components)
            c.onClose();
    }

    @Override
    public void add(AbstractComponent i) {
        i.updateContXY(x, y);
        this.components.add(i);
    }

    @Override
    public List<AbstractComponent> getComponents() {
        return this.components;
    }

    @Override
    public void updateOnResize(int newWidth, int newHeight) {
        for(AbstractComponent i : this.components) {
            if(i instanceof IUpdateOnResize) {
                ((IUpdateOnResize)i).updateOnResize(newWidth, newHeight);
            }
        }
    }

    @Override
    public void update() {
        this.preX = getContX();
        this.preY = getContY();

        for(AbstractComponent c : components) {
            if(c instanceof ITickUpdate)
                ((ITickUpdate)c).update();
        }
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
        if(insideSpecBox(getContX() + 3, getContY() + 3, this.width - 4, 12, mX, mY)) {
            setDragging(true);
        }
        for(AbstractComponent i : components) {
            if(mEvent == 0 && i instanceof IFocus)
                ((IFocus)i).setFocused(i.isHovered(mX, mY));
            if(i instanceof IControllerInput)
                ((IControllerInput)i).clickMouse(mX, mY, mEvent);
        }
    }
}
