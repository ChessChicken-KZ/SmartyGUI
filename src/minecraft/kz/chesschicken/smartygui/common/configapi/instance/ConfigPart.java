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

public class ConfigPart {
	
    private String commentary = "";
    private final String name;

    ConfigPart(String s) {
        this.name = s;
    }

    public void setCommentary(String s) {
        this.commentary = s;
    }

    public boolean isCommentaryPresent() {
        return this.commentary.length() > 0;
    }

    public String getCommentary() {
        return this.commentary;
    }

    public String getName() {
        return this.name;
    }
}
