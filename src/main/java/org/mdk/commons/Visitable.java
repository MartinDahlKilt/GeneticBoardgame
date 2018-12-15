package org.mdk.commons;

public interface Visitable {
	<T> void accept(Visitor<T> visitor);
}
