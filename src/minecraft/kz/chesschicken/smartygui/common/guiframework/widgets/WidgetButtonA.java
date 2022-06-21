package kz.chesschicken.smartygui.common.guiframework.widgets;

import kz.chesschicken.smartygui.common.BinaryIntConsumer;

public class WidgetButtonA extends WidgetButton {
	public static interface Action1 {
		void onActivate();
	}
	
	protected final Action1 action;
	
	public WidgetButtonA(String text, BinaryIntConsumer f, Action1 a) {
		super(text, f);
		this.action = a;
	}

	@Override
	public void onActivate() {
		if(this.action != null)
			this.action.onActivate();
	}

}
