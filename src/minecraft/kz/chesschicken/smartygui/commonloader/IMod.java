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
package kz.chesschicken.smartygui.commonloader;

import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.KeyBinding;

public interface IMod<T> {

    void onInitializeClient();
    
    void onPostInitClient();

    default void onTickGame(Minecraft minecraft) { }

    default void onTickInGUI(Minecraft minecraft, GuiScreen current) { }

    default void keyPressed(int key) { }

    default String getVersion(Class<?> instance) {
    	if(instance.isAnnotationPresent(ModDescription.class)) {
    		return instance.getDeclaredAnnotation(ModDescription.class).version();
    	}
    	return "DEF_MOD_VERSION";
    }
    
    default String getName(Class<?> instance) {
    	if(instance.isAnnotationPresent(ModDescription.class)) {
    		return instance.getDeclaredAnnotation(ModDescription.class).name();
    	}
    	return "DEF_MOD_NAME";
    }
    
    default String getDescription(Class<?> instance) {
    	if(instance.isAnnotationPresent(ModDescription.class)) {
    		return instance.getDeclaredAnnotation(ModDescription.class).description();
    	}
    	return "DEF_MOD_DESC";
    }
    
    default String getIcon(Class<?> instance) {
    	if(instance.isAnnotationPresent(ModDescription.class)) {
    		return instance.getDeclaredAnnotation(ModDescription.class).icon();
    	}
    	return "/pack.png";
    }
}
