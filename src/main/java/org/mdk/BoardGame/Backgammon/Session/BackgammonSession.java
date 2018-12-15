package org.mdk.BoardGame.Backgammon.Session;

import java.util.Random;

import org.mdk.BoardGame.GameSession;
import org.mdk.BoardGame.SessionResult;
import org.mdk.BoardGame.Backgammon.BackgammonBoard;
import org.mdk.BoardGame.Backgammon.BackgammonRoll;
import org.mdk.BoardGame.Backgammon.Player.BackgammonPlayer;

public class BackgammonSession implements GameSession<BackgammonPlayer> {
	private boolean mVerbose;
	
	public BackgammonSession(boolean verbose) {
		mVerbose = verbose;
	}
	
	public BackgammonBoard getBoard() {
		return new BackgammonBoard();
	}
	
	public BackgammonRoll getRoll(Random rand) {
		return new BackgammonRoll(rand);
	}
	
	@Override
	public SessionResult play(BackgammonPlayer player, BackgammonPlayer opponent, int numGames) {
		Random rand = new Random();
		SessionResult res = new SessionResult();
		for(int idx=0; idx < numGames; idx++) {
			BackgammonBoard board = getBoard();
			board.setPlayerName(player.getName());
			board.setOpponenName(opponent.getName());
			boolean finished = false;
			BackgammonRoll roll = null;
			// Roll initial roll - can't be a double
			do {
				roll = getRoll(rand);
			} while(roll.isDouble());

			// Determine who gets to start
			boolean playerTurn = true;
			if(roll.get(0)<roll.get(1)) {
				playerTurn = false;
			}
			while(!finished) {
				if(playerTurn) {
					board = player.move(board, roll);
					if(mVerbose) {
						System.out.println(board);
					}
				} else {
					board = opponent.move(board, roll);
				}
				if(board.hasWinner()) {
					finished = true;
					// Only the current player can win
					if(playerTurn) {
						res.addWin();
					} else {
						res.addLoss();
					}
				}
				playerTurn = !playerTurn;
				board = board.flip();
				roll = getRoll(rand);
			}
		}
		return res;
	}

	
	@Override
	public SessionResult play(BackgammonPlayer player, int numGames)  {
		return play(player,player, numGames);
	}

}
