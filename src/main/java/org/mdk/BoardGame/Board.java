package org.mdk.BoardGame;

import java.util.List;


//import java.util.List;

public interface Board<T extends Board<T,K>,K extends Roll> {
	enum Player { UNKNOWN, PLAYER, OPPONENT }
//	enum Status { INITIAL, ACTIVE, FINISHED }
	
	T flip();
	boolean hasWinner();
	Player getWinner();
	List<T> getBoards(K roll);
	void setPlayerName(String name);
	void setOpponenName(String name);
	
//	List<Move> getLegalMoves();
//	boolean executeMove(Move m);
//	
//	Player getActivePlayer();
//	Status getStatus();
//	
//	void start(Player p);
//	void next();
}
