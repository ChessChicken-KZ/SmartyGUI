package kz.chesschicken.smartygui.common.guiframework;

import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiParticle;
import net.minecraft.src.GuiScreen;

public class GuiScreenExtended extends GuiScreen {
	
	public final BasePanel mainPanel = new BasePanel();
	
	@Override
	public void drawScreen(int var1, int var2, float var3) {
		mainPanel.render(var1, var2, var3);
	}

	@Override
	public void setWorldAndResolution(Minecraft g, int w, int h) {
		this.guiParticles = new GuiParticle(g);
        this.mc = g;
        this.fontRenderer = g.fontRenderer;
        this.width = w;
        this.height = h;
        //Resize function.
        mainPanel.updateOnResize(this.width, this.height);
	}
}
