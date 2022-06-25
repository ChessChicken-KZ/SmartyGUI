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
package kz.chesschicken.smartygui.commonloader.guiframework.api;

import kz.chesschicken.smartygui.commonloader.guiframework.ValueXY;

public abstract class AbstractComponent {

	protected int contX = 0, contY = 0;
	protected int x, y, width, height;
	protected boolean visible = true;
	
	public abstract void render(int a, int b, float d);
	
	public boolean shouldDraw() {
		return this.visible;
	}

	public void updateContXY(int a, int b) {
		this.contX = a;
		this.contY = b;
	}

	public int getContX() {
		return this.x + this.contX;
	}

	public int getContY() {
		return this.y + this.contY;
	}

	public void setXY(ValueXY n) {
		this.x = n.a;
		this.y = n.b;
	}

	public boolean isHovered(int a, int b) {
		return a >= getContX() && b >= getContY() && a < getContX() + this.width && b < getContY() + this.height;
	}
}
