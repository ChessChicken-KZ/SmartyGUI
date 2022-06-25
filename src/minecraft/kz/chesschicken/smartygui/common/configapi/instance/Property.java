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
package kz.chesschicken.smartygui.common.configapi.instance;

import kz.chesschicken.smartygui.common.configapi.EnumPropertyType;

public class Property extends ConfigPart {
	
    private EnumPropertyType type;
    private Object value;
    
    public Property(String s, Object o) {
        super(s);
        value = o;
        type = EnumPropertyType.parseObject(o);
    }

    public void setOther(Object o, boolean f) {
        value = o;
        if(f) type = EnumPropertyType.parseObject(o);
    }

    public Object getValue() {
        return value;
    }

    public EnumPropertyType getType() {
        return this.type;
    }

    public static Property createProperty(String s, Object instance) {
        return new Property(s, instance);
    }
}