package kz.chesschicken.smartygui.common.guiframework;

import java.util.List;

public interface IContainer {
	void add(IComponent i);
	
	List<IComponent> getComponents();
}
