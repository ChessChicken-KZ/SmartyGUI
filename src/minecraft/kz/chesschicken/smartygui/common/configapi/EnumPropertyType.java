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

public enum EnumPropertyType {
    BOOLEAN(Boolean.class),
    STRING(String.class),
    INTEGER(Integer.class),
    DOUBLE(Double.class),
    FLOAT(Float.class),
    STRING_ARRAY(String.class);

    private final Class<?> extending;
    EnumPropertyType(Class<?> c)
    {
        this.extending = c;
    }

    public Class<?> getType()
    {
        return this.extending;
    }

    public static EnumPropertyType parseObject(Object o)
    {
        if(o instanceof Boolean)
            return EnumPropertyType.BOOLEAN;
        if(o instanceof String)
            return EnumPropertyType.STRING;
        if(o instanceof Integer || o instanceof Byte)
            return EnumPropertyType.INTEGER;
        if(o instanceof Double)
            return EnumPropertyType.DOUBLE;
        if(o instanceof Float)
            return EnumPropertyType.FLOAT;

        return EnumPropertyType.STRING;
    }
}
