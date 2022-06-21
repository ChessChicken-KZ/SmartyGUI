package kz.chesschicken.smartygui.commonloader;

import java.util.function.Supplier;

public class LateGetter<T> {
	private Supplier<T> supplier;
	private T value;

	public LateGetter(Supplier<T> delegate) {
		this.supplier = delegate;
	}
	
	public T get() {
		Supplier<T> supplier = this.supplier;
		if (supplier != null) {
			this.value = supplier.get();
			this.supplier = null;
		}
		return this.value;
	}
}
