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
package kz.chesschicken.smartygui.modloader;

import java.util.Locale;

public class SystemUtils {
	
    public static String getFullOperatingSystemName() {
        String name = System.getProperty("os.name");
        if(name == null)
        	return "Unknown";
        return name + " " + System.getProperty("os.version");
    }

    public static String getFormattedOperatingSystemName() {
        StringBuilder osstring = new StringBuilder();
        try {
            String name = System.getProperty("os.name");
            if (name == null)
            	throw new Exception("os.name not found");
            EnumOSList os = EnumOSList.getByName(name.toLowerCase(Locale.ENGLISH));
            osstring.append(os == EnumOSList.UNKNOWN ? name.toUpperCase() : os.formattedName);
        } catch (Exception ex) {
            osstring.append(EnumOSList.UNKNOWN.formattedName);
        } finally {
            osstring.append(" ").append(System.getProperty("os.version"));
        }
        return osstring.toString();
    }

    public static String getProcessorInfo() {
        return System.getenv("PROCESSOR_IDENTIFIER");
    }

}
