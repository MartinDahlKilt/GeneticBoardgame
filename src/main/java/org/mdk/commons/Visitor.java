package org.mdk.commons;

public interface Visitor<T> {
	void visit(T objectToVisit);
}
