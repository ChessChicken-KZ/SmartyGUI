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

import java.util.ArrayList;
import java.util.List;

public class Group extends ConfigPart {
	
    private final List<Property> properties;
    
    Group(String s) {
        super(s);
        properties = new ArrayList<>();
    }

    public void add(Property property) {
        properties.add(property);
    }

    public void add(Property property, String commentary) {
        property.setCommentary(commentary);
        properties.add(property);
    }

    public Property getProperty(String propertyName) {
        for(Property p : properties) {
            if(p.getName().equalsIgnoreCase(propertyName))
                return p;
        }
        return null;
    }

    public List<Property> getProperties() {
        return this.properties;
    }

    public static Group createGroup(String s) {
        return new Group(s);
    }
}