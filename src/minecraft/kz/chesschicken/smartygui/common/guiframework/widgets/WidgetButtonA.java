package kz.chesschicken.smartygui.common.guiframework.widgets;

import java.util.function.BiConsumer;

public class WidgetButtonA extends WidgetButton {
	public static interface Action1 {
		void onActivate();
	}
	
	protected final Action1 action;
	
	public WidgetButtonA(int id, String text, BiConsumer<Integer, Integer> f, Action1 a) {
		super(id, text, f);
		this.action = a;
	}

	@Override
	public void onActivate() {
		if(this.action != null)
			this.action.onActivate();
	}

}
