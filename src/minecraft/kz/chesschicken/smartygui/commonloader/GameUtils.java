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

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;

import net.minecraft.client.Minecraft;
import net.minecraft.src.ModLoader;
import sun.misc.Unsafe;

public class GameUtils {

    public static boolean isDeveloperInstance() {
        return IS_SANDBOX_MODE;
    }

    public static Object getObject(Class<?> c, Object i, String name) {
        try {
        	Field f = c.getDeclaredField(name);
        	return UNSAFE_INSTANCE.getObject(i, UNSAFE_INSTANCE.objectFieldOffset(f));
        } catch (NoSuchFieldException e) {
            return null;
        }
    }
    
    public static Object getStaticObject(Class<?> c, Object i, String name) {
        try {
        	Field f = c.getDeclaredField(name);
        	return UNSAFE_INSTANCE.getObject(i == null ? UNSAFE_INSTANCE.staticFieldBase(f) : i, UNSAFE_INSTANCE.staticFieldOffset(f));
        } catch (NoSuchFieldException e) {
            return null;
        }
    }
    
    public static <T extends net.minecraft.src.GuiScreen> void open(T a) {
    	getMC().displayGuiScreen(a);
    }
    
    public static Minecraft getMC() {
    	if(MC_INSTANCE == null) 
    		MC_INSTANCE = ModLoader.getMinecraftInstance();
    	return MC_INSTANCE;
    }

    static Minecraft MC_INSTANCE;
    
    public final static boolean IS_SANDBOX_MODE;
    public static Unsafe UNSAFE_INSTANCE;
    public static MethodHandles.Lookup IMPL_LOOKUP_INSTANCE;
    
    static {
    	//Getting Unsafe field.
		try {
	    	Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
	    	UNSAFE_INSTANCE = (Unsafe) f.get(null);
	    	
            Field a = MethodHandles.Lookup.class.getDeclaredField("IMPL_LOOKUP");
            IMPL_LOOKUP_INSTANCE = (MethodHandles.Lookup) UNSAFE_INSTANCE.getObject(UNSAFE_INSTANCE.staticFieldBase(a), UNSAFE_INSTANCE.staticFieldOffset(a));
		} catch (NoSuchFieldException | IllegalAccessException exp) {
			exp.printStackTrace();
		}
    	
    	//Accessing "net.minecraft.src.Block" as a kind of test.
    	boolean a = false;
    	try {
            Class.forName("net.minecraft.src.Block");
            a = true;
        } catch (ClassNotFoundException e) {
        	a = false;
        }
    	IS_SANDBOX_MODE = a;
    }
}
