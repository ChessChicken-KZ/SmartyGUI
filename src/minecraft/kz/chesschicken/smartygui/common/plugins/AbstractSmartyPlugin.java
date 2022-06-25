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

public abstract class AbstractSmartyPlugin implements Comparable<AbstractSmartyPlugin> {
	
	public abstract void methodInitPlugin(SmartyGUI instance);
	
	public abstract String getPluginName();
	
	public String getPluginDescription() {
		return "";
	}

	@Override
	public int compareTo(AbstractSmartyPlugin o) {
		return this.getPluginName().compareTo(o.getPluginName());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.getPluginDescription() == null) ? 0 : this.getPluginDescription().hashCode());
		result = prime * result + ((this.getPluginName() == null) ? 0 : this.getPluginName().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if (obj == null || this.getClass() != obj.getClass()) 
			return false;
		AbstractSmartyPlugin other = (AbstractSmartyPlugin) obj;
		return other.getPluginName().equals(this.getPluginName());
	}
	
}
