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
package kz.chesschicken.smartygui.common.plugins;

import kz.chesschicken.smartygui.common.SmartyGUI;
import kz.chesschicken.smartygui.common.plugins.event.EnumEventTypes;
import kz.chesschicken.smartygui.common.plugins.event.GetClassifiedEvents;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class SmartyGuiPlugins {
	
	private final SmartyGUI instance;
	private List<AbstractSmartyPlugin> pluginList = new ArrayList<>();
	
	private EnumMap<EnumEventTypes, List<AbstractSmartyPlugin>> eventPluginMap = new EnumMap<>(EnumEventTypes.class);
	
	public SmartyGuiPlugins(SmartyGUI smartygui) {
		this.instance = smartygui;
		for(EnumEventTypes b : EnumEventTypes.values())
			eventPluginMap.put(b, new ArrayList<>());
	}

	public void registerPlugin(AbstractSmartyPlugin plugin) {
		if(pluginList.contains(plugin)) {
			System.out.println("Plugin with name: " + plugin.getPluginName() + " is already registered!");
			return;
		}
		if(plugin.getClass().isAnnotationPresent(GetClassifiedEvents.class)) {
			for(EnumEventTypes b : plugin.getClass().getAnnotation(GetClassifiedEvents.class).value())
				eventPluginMap.get(b).add(plugin);
		}
		
		plugin.methodInitPlugin(instance);
		pluginList.add(plugin);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T[] __getEventsReady(EnumEventTypes e, T[] a, Class<T> clazz) {
		T[] lists = (T[]) Array.newInstance(clazz, eventPluginMap.get(e).size());
		for(int i = 0; i < lists.length; i++)
			lists[i] = (T) eventPluginMap.get(e).get(i);
		return lists;
	}

}
