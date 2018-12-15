package org.mdk.BoardGame;


public interface Player<T extends Board<T,K>, K extends Roll> {
	T move(T board, K roll);
	String getName();
}
