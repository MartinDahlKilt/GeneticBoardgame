package org.mdk.BoardGame;


public interface GameSession<T extends Player<?,?>> {
	SessionResult play(T player, int numGames);
	SessionResult play(T player, T opponent, int numGames);
}
