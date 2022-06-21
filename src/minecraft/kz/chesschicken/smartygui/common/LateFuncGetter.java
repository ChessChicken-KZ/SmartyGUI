package kz.chesschicken.smartygui.common;

import java.util.function.Function;

public class LateFuncGetter<K, T> {
	private Function<K, T> supplier;
	private T value;

	public LateFuncGetter(Function<K, T> delegate) {
		this.supplier = delegate;
	}
	
	public T get(K k) {
		Function<K, T> supplier = this.supplier;
		if (supplier != null) {
			this.value = supplier.apply(k);
			this.supplier = null;
		}
		return this.value;
	}
}
