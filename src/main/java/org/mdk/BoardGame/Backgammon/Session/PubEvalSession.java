package org.mdk.BoardGame.Backgammon.Session;

import org.mdk.BoardGame.SessionResult;
import org.mdk.BoardGame.Backgammon.Player.BackgammonPlayer;
import org.mdk.BoardGame.Backgammon.Player.PubEvalPlayer;

public class PubEvalSession extends BackgammonSession {
	public PubEvalSession(boolean verbose) {
		super(verbose);
	}
	
	@Override
	public SessionResult play(BackgammonPlayer player, int numGames) {
		PubEvalPlayer opponent = new PubEvalPlayer();
		return play(player,opponent, numGames);
	}

}
