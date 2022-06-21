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
package kz.chesschicken.smartygui.common.guiframework;

public abstract class AbstractComponent {
	
	protected int x, y, width, height;
	protected boolean visible = true;
	
	public abstract void render(int i, int i1, float d);
	
	public boolean shouldDraw() {
		return this.visible;
	}

	public int getX() {
		return this.x;
	}
	
	public void setX(int i) {
		this.x = i;
	}
	
	public int getY() {
		return this.x;
	}
	
	public void setY(int i) {
		this.y = i;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public void setWidth(int i) {
		this.width = i;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public void setHeight(int i) {
		this.height = i;
	}
	
	public boolean isVisible() {
		return this.visible;
	}
	
	public void setVisible(boolean a) {
		this.visible = a;
	}
}
