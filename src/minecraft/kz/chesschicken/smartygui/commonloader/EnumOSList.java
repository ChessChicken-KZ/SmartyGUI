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

public enum EnumOSList {
	WIN("Windows", "windows"),
	UNIX("Unix", "mpe/ix", "freebsd", "irix", "digital unix", "unix"),
	LINUX("Linux", "linux"),
	OSX("Mac OS", "osx", "mac os"),
	POSIX("Posix Unix", "sun os", "sunos", "solaris", "hp-ux", "aix"),
	UNKNOWN("Unknown");
	
	public final String formattedName;
	private final String[] uFList;
	
	EnumOSList(String a_f, String... f) {
		this.formattedName = a_f;
		this.uFList = f;
	}
	
	/**
	 * Return an enum with OS by the OS name.
	 * @param s OS Name.
	 * @return OS Enum, otherwise {@link EnumOSList#UNKNOWN}
	 */
	public static EnumOSList getByName(String s) {
		for(EnumOSList a : values())
			for(String g : a.uFList)
				if(g.equals(s)) return a;
		
		return UNKNOWN;
	}
}
