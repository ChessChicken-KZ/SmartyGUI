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
package kz.chesschicken.smartygui.client.gui.button;

import kz.chesschicken.smartygui.client.gui.GuiColourConfig;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiTextField;

public class TextFieldNum extends GuiTextField {
	
	private GuiColourConfig guiscreen;

	public TextFieldNum(GuiColourConfig var1, FontRenderer var2, int var3, int var4, int var5, int var6, String var7) {
		super(var1, var2, var3, var4, var5, var6, var7);
		this.guiscreen = var1;
	}
	
	boolean __isNum(char a) {
		return a == '0' || a == '1' || a == '2' || a == '3' || a == '4' || a == '5' || a == '6' || a == '7' || a == '8' || a == '9' || a == '\b';
	}

	@Override
	public void textboxKeyTyped(char var1, int var2) {
        if (this.isEnabled && this.isFocused) {
            if(__isNum(var1)) {
            	super.textboxKeyTyped(var1, var2);
            	if(this.getText().length() > 0)
                	this.guiscreen.updateColours();
            }
        }
    }
	
	


}
