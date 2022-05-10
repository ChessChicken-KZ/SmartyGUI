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

import net.minecraft.src.ModLoader;

public class ModLoaderUtils {
    private boolean isDev;

    public boolean isDeveloperInstance() {
        return isDev;
    }

    public Object getInstance(Class<?> c, Object i, String nameObf, String nameReal) {
        try {
            return ModLoader.getPrivateValue(c, i, isDev ? nameReal : nameObf);
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    private ModLoaderUtils() {}
    private static ModLoaderUtils INSTANCE;
    public static ModLoaderUtils getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new ModLoaderUtils();

            try {
                Class.forName("net.minecraft.src.ModLoader");
                INSTANCE.isDev = true;
            } catch (ClassNotFoundException e) {
                INSTANCE.isDev = false;
            }

        }

        return INSTANCE;
    }
}
