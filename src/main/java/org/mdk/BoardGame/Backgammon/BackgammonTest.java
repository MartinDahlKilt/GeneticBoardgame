package org.mdk.BoardGame.Backgammon;

import org.mdk.BoardGame.SessionResult;
import org.mdk.BoardGame.Backgammon.Player.MDKEvalPlayer;
import org.mdk.BoardGame.Backgammon.Player.PubEvalPlayer;
import org.mdk.BoardGame.Backgammon.Session.BackgammonSession;

public class BackgammonTest {
	public static void main(String[] args) throws Exception {
//		PubEvalSession game = new PubEvalSession(false);
		BackgammonSession game = new BackgammonSession(true);
		
//		RandomPlayer player1 = new RandomPlayer();
		MDKEvalPlayer player2 = new MDKEvalPlayer();
		PubEvalPlayer player3 = new PubEvalPlayer();
//		MDK2PlyEvalPlayer player2 = new MDK2PlyEvalPlayer();
//		RandomPlayer player = new RandomPlayer();
//		PubEvalPlayer player = new PubEvalPlayer();
		
		long start = System.currentTimeMillis();
		SessionResult sess = null;
//		sess = game.play(player1, player2, 10000);
//		System.out.println(sess);
//		sess = game.play(player1, player3, 10000);
//		System.out.println(sess);
		
		sess = game.play(player2, player3, 1);
		long end = System.currentTimeMillis();
		System.out.println(sess);
		System.out.println("In "+(end-start)+"ms");
//		sess = game.play(player3, player2, 40000);
//		System.out.println(sess);
	}
}
