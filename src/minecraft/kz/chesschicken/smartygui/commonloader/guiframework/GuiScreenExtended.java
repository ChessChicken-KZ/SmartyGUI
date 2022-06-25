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

import kz.chesschicken.smartygui.commonloader.guiframework.api.BasePanel;
import kz.chesschicken.smartygui.commonloader.guiframework.api.IPauseGame;
import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiParticle;
import net.minecraft.src.GuiScreen;

public class GuiScreenExtended<T extends BasePanel> extends GuiScreen {
	
	public final T mainPanel;
	
	public GuiScreenExtended(T panel) {
		this.mainPanel = panel;
	}
	
	@Override
	public void drawScreen(int var1, int var2, float var3) {
		mainPanel.render(var1, var2, var3);
	}
	
	@Override
	protected void mouseClicked(int var1, int var2, int var3) {
		mainPanel.clickMouse(var1, var2, var3);
	}

	@Override
	protected void keyTyped(char var1, int var2) {
		super.keyTyped(var1, var2);
		mainPanel.typeKey(var1, var2);
	}

	@Override
	protected void mouseMovedOrUp(int var1, int var2, int var3) {
		//FIXME: I think use it?
	}

	@Override
	protected void actionPerformed(GuiButton var1) {
		//Hardcoded to not accept it in any way.
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return !(mainPanel instanceof IPauseGame) || ((IPauseGame) mainPanel).shouldPauseGame();
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
        this.controlList.clear();
        this.initGui();
	}


	@Override
	public void initGui() {}

	@Override
	public void updateScreen() {
		mainPanel.update();
	}

	@Override
	public void onGuiClosed() {
	}
}
