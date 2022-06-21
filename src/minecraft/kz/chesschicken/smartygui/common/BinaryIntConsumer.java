package kz.chesschicken.smartygui.common;

import java.util.Objects;

/* Let it treat as only functional interface. */
@FunctionalInterface
public interface BinaryIntConsumer {
	void accept(int a, int b);

	default BinaryIntConsumer andThen(BinaryIntConsumer q) {
		Objects.requireNonNull(q);
		return (a, b) -> {
			this.accept(a, b);
			q.accept(a, b);
		};
	}
}
