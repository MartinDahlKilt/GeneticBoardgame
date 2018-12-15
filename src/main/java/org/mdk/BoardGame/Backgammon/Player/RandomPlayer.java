package org.mdk.BoardGame.Backgammon.Player;

import java.util.List;
import java.util.Random;

import org.mdk.BoardGame.Backgammon.BackgammonBoard;
import org.mdk.BoardGame.Backgammon.BackgammonRoll;

public class RandomPlayer extends BackgammonPlayer {
	private Random mRand = new Random();
	
	@Override
	public BackgammonBoard move(BackgammonBoard board, BackgammonRoll roll) {
		BackgammonBoard m = board;
		List<BackgammonBoard> configs = board.getBoards(roll);
		int size = configs.size();
		if(size>0) {
			m = configs.get(mRand.nextInt(size));
		}
		return m;

	}

	@Override
	public String getName() {
		return "RandomPlayer";
	}

}
