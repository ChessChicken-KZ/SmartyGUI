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
package kz.chesschicken.smartygui.client.gui;

import kz.chesschicken.smartygui.client.gui.button.ButtonTransparent;
import kz.chesschicken.smartygui.common.SmartyGUI;
import kz.chesschicken.smartygui.commonloader.guiframework.IUpdateOnResize;
import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiParticle;
import net.minecraft.src.GuiScreen;

public class BaseGUIStyle extends GuiScreen {

	protected final SmartyGUI instance;
	
	public BaseGUIStyle(SmartyGUI smartygui) {
		this.instance = smartygui;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		this.controlList.add(new ButtonTransparent(900, 2, this.height - 10, "Credits..."));
	}

	@Override
	protected void actionPerformed(GuiButton var1) {
		if(!var1.enabled || !var1.enabled2)
			return;
	}

	@Override
	public void drawScreen(int a, int b, float f) {
		this.drawGradientRect(0, 0, this.width, this.height, -1072689136, -804253680);
		super.drawScreen(a, b, f);
	}
	
	@Override
	public void setWorldAndResolution(Minecraft g, int w, int h) {
		this.guiParticles = new GuiParticle(g);
        this.mc = g;
        this.fontRenderer = g.fontRenderer;
        this.width = w;
        this.height = h;
        //Resize function.
        for(Object a : this.controlList) {
        	if(a instanceof IUpdateOnResize) {
        		((IUpdateOnResize)a).updateOnResize(this.width, this.height);
        	}
        }
	}

	public void drawSuper(int a, int b, float f) {
		super.drawScreen(a, b, f);
	}
}
