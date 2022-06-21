package kz.chesschicken.smartygui.common.guiframework.widgets;

import java.util.function.BiConsumer;

import org.lwjgl.opengl.GL11;

import kz.chesschicken.smartygui.common.RenderUtils;
import kz.chesschicken.smartygui.common.guiframework.IComponent;
import kz.chesschicken.smartygui.common.guiframework.IInteractive;
import kz.chesschicken.smartygui.common.guiframework.IUpdateOnResize;

public abstract class WidgetButton extends IComponent implements IUpdateOnResize, IInteractive {
	
	protected final int id;
	protected String text;
	protected boolean active;
	protected final BiConsumer<Integer, Integer> resizeFunc;
	
	public WidgetButton(int id, String text, BiConsumer<Integer, Integer> f) {
		this.id = id;
		this.text = text;
		this.resizeFunc = f;
	}

	@Override
	public void render(int var2, int var3, float d) {
        GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, RenderUtils.texture("/gui/gui.png"));
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        boolean isHovered = this.isHovered(var2, var3);
        byte renderState = (byte) (!this.active ? 0 : (isHovered ? 2 : 0));
        RenderUtils.renderTexture(this.x, this.y, 0, 46 + renderState * 20, this.width / 2, this.height);
        RenderUtils.renderTexture(this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + renderState * 20, this.width / 2, this.height);
    	RenderUtils.renderShadowCenteredString(this.x + this.width / 2, this.y + (this.height - 8) / 2, !this.active ? -6250336 : (isHovered ? 16777120 : 14737632), this.text);
	}

	@Override
	public void updateOnResize(int newWidth, int newHeight) {
		this.resizeFunc.accept(newWidth, newHeight);
	}

	@Override
	public boolean isHovered(int a, int b) {
        return a >= this.x && b >= this.y && a < this.x + this.width && b < this.y + this.height;
	}

}
