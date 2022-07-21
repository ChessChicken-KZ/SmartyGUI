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
package kz.chesschicken.smartygui.common;

import kz.chesschicken.smartygui.common.plugins.SmartyGuiPlugins;
import net.minecraft.client.Minecraft;
import net.minecraft.src.FontRenderer;

/**
 * Specific abstract class that supports being rendered in tick.
 */
public abstract class ModuleRender {
	
    protected final Minecraft minecraft;
    protected final SmartyGuiConfig config;
    protected final SmartyGuiPlugins pluginManager;
    protected final FontRenderer textRenderer;

    public ModuleRender(Minecraft minecraft, SmartyGUI config) {
        this.minecraft = minecraft;
        this.config = config.CONFIG;
        this.pluginManager = config.PLUGINS;
        this.textRenderer = minecraft.fontRenderer;
    }

    /**
     * Abstract method that usually called when the code needs to clean-up and update preferences.
     */
    public abstract void clean();

}
