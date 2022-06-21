package kz.chesschicken.smartygui.commonloader.guiframework;

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
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class BasePanel extends AbstractComponent implements IContainer, IUpdateOnResize {

	public final List<AbstractComponent> components = new ArrayList<>();
	
	public BasePanel(Consumer<? super BasePanel> init) {
		init.accept(this);
	}
	
	public BasePanel() {
	}
	
	@Override
	public void updateOnResize(int newWidth, int newHeight) {
		this.width = newWidth;
		this.height = newHeight;
		for(AbstractComponent i : this.components) {
        	if(i instanceof IUpdateOnResize) {
        		((IUpdateOnResize)i).updateOnResize(newWidth, newHeight);
        	}
        }
	}

	@Override
	public void render(int q, int w, float e) {
		for(AbstractComponent i : components) {
			if(i.shouldDraw()) {
				i.render(q, w, e);
			}
		}
	}

	@Override
	public void add(AbstractComponent i) {
		this.components.add(i);
	}

	@Override
	public List<AbstractComponent> getComponents() {
		return this.components;
	}

	@Override
	public void onInteractWithComponents(int mX, int mY, int mEvent) {
		if(mEvent != 0) //If not left-click - cancel.
			return;
		for(AbstractComponent i : components) {
			if(!(i instanceof IInteractive) || !i.isVisible())
				continue;
			if(((IInteractive)i).isHovered(mX, mY)) {
				((IInteractive)i).onActivate();
			}
		}
	}
	
}
