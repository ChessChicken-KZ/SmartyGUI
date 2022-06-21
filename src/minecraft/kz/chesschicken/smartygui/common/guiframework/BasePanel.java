package kz.chesschicken.smartygui.common.guiframework;

import java.util.ArrayList;
import java.util.List;

public class BasePanel extends IComponent implements IContainer, IUpdateOnResize {

	public final List<IComponent> components = new ArrayList<>();
	
	@Override
	public void updateOnResize(int newWidth, int newHeight) {
		this.width = newWidth;
		this.height = newHeight;
		for(IComponent i : this.components) {
        	if(i instanceof IUpdateOnResize) {
        		((IUpdateOnResize)i).updateOnResize(newWidth, newHeight);
        	}
        }
	}

	@Override
	public void render(int q, int w, float e) {
		for(IComponent i : components) {
			if(i.shouldDraw()) {
				i.render(q, w, e);
			}
		}
	}

	@Override
	public void add(IComponent i) {
		this.components.add(i);
	}

	@Override
	public List<IComponent> getComponents() {
		return this.components;
	}
	
}
