package kz.chesschicken.smartygui.common.guiframework;

public abstract class IComponent {
	
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
