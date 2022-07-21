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
package kz.chesschicken.smartygui.common.configapi;

public abstract class ConfigInstance {

    protected final Configuration instance;

    public ConfigInstance(String name) {
        instance = new Configuration(name);
    }

    public void start() {
        if(!instance.exists()) {
            saveConfig();
            instance.save();
        } else {
            instance.load();
            applyConfig();
        }
    }
    
    public void forceSave() {
    	instance.destroyFile();
    	instance.cleanup();
    	saveConfig();
        instance.save();
    }

    @SuppressWarnings("unchecked")
    public <T> T getValue(String group, String property) {
        return (T) instance.getGroup(group).getProperty(property).getValue();
    }

    public <T> T getSafeValue(String group, String property, T defValue) {
        try {
            return getValue(group, property);
        } catch (NullPointerException e) {
            System.out.println("[SmartyGUI] Failed to load property [" + group + "/" + property + "], using default value. Perhaps, the user updated modification?");
            return defValue;
        }
    }

    public abstract void saveConfig();
    public void applyConfig() { }
}
