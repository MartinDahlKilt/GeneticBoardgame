package org.mdk.BoardGame.Backgammon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.mdk.BoardGame.Board;

public class BackgammonBoard implements Board<BackgammonBoard, BackgammonRoll> {
	static int PLAYER_OFF = 26;
	static int OPPONENT_OFF = 27;
	static int PLAYER_BAR = 25;
	static int OPPONENT_BAR = 0;
	
	static int PLAYER_COLOR = 1;
	static int OPPONENT_COLOR = -1;
	String mPlayerName;
	String mOpponentName;
			
	
/*
     pos[]  is an integer array of dimension 28 which
     should represent a legal final board state after
     the move. Elements 1-24 correspond to board locations
     1-24 from players point of view, i.e. players
     men move in the negative direction from 24 to 1, and
     opponent's men move in the positive direction from
     1 to 24. Players men are represented by positive
     integers, and opponents men are represented by negative
     integers. Element 25 represents players men on the
     bar (positive integer), and element 0 represents opponent
     men on the bar (negative integer). Element 26 represents
     players men off the board (positive integer), and
     element 27 represents opponents men off the board
     (negative integer).                                  */
 
	private int[] board;
	
	public BackgammonBoard() {
		board = new int[28];
		SetStartBoard();
	}
	
	public BackgammonBoard(BackgammonBoard b) {
		board = new int[28];
		set(b);
	}
	
	private void SetStartBoard() {
		board[1] = 2*OPPONENT_COLOR;
		board[6] = 5*PLAYER_COLOR;
		board[8] = 3*PLAYER_COLOR;
		board[12] = 5*OPPONENT_COLOR;
		board[13] = 5*PLAYER_COLOR;
		board[17] = 3*OPPONENT_COLOR;
		board[19] = 5*OPPONENT_COLOR;
		board[24] = 2*PLAYER_COLOR;
	}
	
	@Override
	public BackgammonBoard flip() {
		BackgammonBoard b = new BackgammonBoard();
		Arrays.fill(b.board, 0);

		for(int idx = 0; idx < 26; idx++) {
			if(board[idx]>=PLAYER_COLOR) {
				b.board[25-idx] = Math.abs(board[idx])*OPPONENT_COLOR;
			} else if(board[idx]<=OPPONENT_COLOR) {
				b.board[25-idx] = Math.abs(board[idx])*PLAYER_COLOR;
			}
		}
		b.board[PLAYER_OFF] = Math.abs(board[OPPONENT_OFF])*PLAYER_COLOR;
		b.board[OPPONENT_OFF] = Math.abs(board[PLAYER_OFF])*OPPONENT_COLOR;
		b.board[PLAYER_BAR] = Math.abs(board[OPPONENT_BAR])*PLAYER_COLOR;
		b.board[OPPONENT_BAR] = Math.abs(board[PLAYER_BAR])*OPPONENT_COLOR;
		b.mPlayerName = mOpponentName;
		b.mOpponentName = mPlayerName;
		return b;
	}
	
	public boolean isRace() {
		if(getNumBarMen(Player.OPPONENT)<=OPPONENT_COLOR) {
			return false;
		}
		int sum = getNumMenOff(Player.PLAYER);
		for(int idx=1; idx <= 24; idx++) {
			if(board[idx]<=OPPONENT_COLOR) {
				break;
			}
			sum+=board[idx];
		}
		return sum==15;
	}

	public int getNumMenOff(Player p) {
		if(p==Player.PLAYER) {
			return board[PLAYER_OFF];
		} else {
			return board[OPPONENT_OFF];
		}
	}
	
	public int getNumBarMen(Player p) {
		if(p==Player.PLAYER) {
			return board[PLAYER_BAR];
		} else {
			return board[OPPONENT_BAR];
		}
	}
	
	// Board position 1 <= idx <= 24
	public int get(int idx) {
		return board[idx];
	}
	
	public void applyMove(int iSrc, int nRoll ) {
		int iDest = iSrc - nRoll;
		if( iDest < 1 ) {
			iDest = PLAYER_OFF;
		}
		board[ iSrc ] -= PLAYER_COLOR;

		if( board[ iDest ] < 0 ) {
			board[ iDest ] = PLAYER_COLOR;
			board[ OPPONENT_BAR ] += OPPONENT_COLOR;
		} else {
			board[ iDest ] += PLAYER_COLOR;
		}
	}

	public boolean canMove( int iSrc, int nPips ) {
		int i, nBack = 0, iDest = iSrc - nPips;
		if( iDest > 0 ) {
			return ( board[ iDest ] >= -1 );
		}

		for( i = 1; i < 26; i++ ) {
			if( board[ i ] > 0 ) {
				nBack = i;
			}
		}
		return ( nBack <= 6 && ( iSrc == nBack || iDest==0 ) );
	}

	
	private String getChecker(int pos, int row) {
		int val = Math.abs(board[pos]);
		if((val>=row && row <= 4) || (val==row && row == 5)) {
			if(board[pos]<0) {
				return " X ";
			} else {
				return " O ";
			}
		} else if(val>5 && row == 5) {
			if(val < 10) {
				return " "+Integer.toString(val)+" ";
			} else {
				return Integer.toString(val)+" ";
			}
		} else {
			return "   ";
		}
	}
	
	public boolean equals(Object obj) {
//		System.out.println("equals start");
		if(obj instanceof BackgammonBoard) {
			BackgammonBoard b = (BackgammonBoard)obj;
			for(int idx = 0; idx < 28; idx++) {
//				System.out.println("idx:"+idx+" "+b.board[idx]+":"+board[idx]);
				if(b.board[idx]!=board[idx]) {
					return false;
				}
			}
//			System.out.println("is equal");
			return true;
		}
		return false;
	}
	
	public void set(BackgammonBoard b) {
		System.arraycopy(b.board, 0, board, 0, 28);
		mPlayerName = b.mPlayerName;
		mOpponentName = b.mOpponentName;
	}
	
	private int generateMoves( BackgammonBoard anBoard, int nMoveDepth, int iPip, int cPip, BackgammonRoll anRoll, List<BackgammonBoard> configs) {
		int i, fUsed = 0;
		BackgammonBoard anBoardNew = new BackgammonBoard();


		if( nMoveDepth > 3 || (nMoveDepth>1 && !anRoll.isDouble()))
			return -1;

		if( anBoard.get( 25 )!=0 ) {
			if( anBoard.get( 25 - anRoll.get(nMoveDepth) ) <= -2 )
				return -1;
			anBoardNew.set(anBoard);
			anBoardNew.applyMove(25, anRoll.get( nMoveDepth ));

			if( generateMoves( anBoardNew, nMoveDepth + 1, 24, cPip + anRoll.get( nMoveDepth ), anRoll, configs)!=0 ) {
				BackgammonBoard move = new BackgammonBoard(anBoardNew);
				configs.add(move);
			}

			return 0;
		} else {
			for( i = iPip; i>0; i-- ) {
				if( anBoard.get( i ) > 0 && anBoard.canMove(i, anRoll.get( nMoveDepth )) ) {
					int sec = i - anRoll.get( nMoveDepth );
					if( sec < 1 )
						sec = 26;
					anBoardNew.set(anBoard);
					anBoardNew.applyMove(i, anRoll.get( nMoveDepth ));

					if( generateMoves( anBoardNew, nMoveDepth + 1, anRoll.get( 0 ) == anRoll.get( 1 ) ? i : 24, cPip + anRoll.get( nMoveDepth ), anRoll, configs) != 0) {
						BackgammonBoard move = new BackgammonBoard(anBoardNew);
						configs.add(move);
					}

					fUsed = 1;
				}
			}
		}

		return fUsed==1 ? 0 : -1;
	}

	
	public String toString() {
		/*
		 * MDK Backgammon  
                   
   +12-11-10--9--8--7-------6--5--4--3--2--1-+  O: Human
   | X           O    |   | O              X |  Rolled 31
   | X           O    |   | O              X |
   | X           O    |   | O                |
   | X                |   | O                |
   | X                |   | O                |
   |                  |BAR|                  |  (Cube: 1)
   | O                |   | X                |
   | O                |   | X                |
   | O           X    |   | X                |
   | O           X    |   | X              O |  
   | O           X    |   | X              O |
   +13-14-15-16-17-18------19-20-21-22-23-24-+  X: Pubeval
		 */
		StringBuilder buf = new StringBuilder();
		buf.append("   MDK Backgammon\n\n");
		buf.append("   +12-11-10--9--8--7-+---+-6--5--4--3--2--1-+  O:").append(mPlayerName).append("\n");
		for(int row = 1; row <= 5; row++) {
			for(int pos = 12; pos > 0; pos--) {
				if(pos==12) {
					buf.append("   |");
				}
				buf.append(getChecker(pos, row));
				if(pos==7) {
					buf.append("|");
					buf.append(getChecker(PLAYER_BAR, row));
					buf.append("|");
				} else if(pos==1){
					buf.append("|");
					buf.append(getChecker(PLAYER_OFF, row));
					buf.append("\n");
				}
			}
		}
		buf.append("   |                  |BAR|                  |    \n");
		for(int row = 5; row >= 1; row--) {
			for(int pos = 13; pos < 25; pos++) {
				if(pos==13) {
					buf.append("   |");
				}
				buf.append(getChecker(pos, row));
				if(pos==18) {
					buf.append("|");
					buf.append(getChecker(OPPONENT_BAR, row));
					buf.append("|");
				} else if(pos==24){
					buf.append("|");
					buf.append(getChecker(OPPONENT_OFF, row));
					buf.append("\n");

				}
			}
		}

		buf.append("   +13-14-15-16-17-18-+---+19-20-21-22-23-24-+  X:").append(mOpponentName).append("\n");
		return buf.toString();
	}

	@Override
	public boolean hasWinner() {
		return getNumMenOff(Player.PLAYER)==15 || getNumMenOff(Player.OPPONENT)==15; 
	}

	@Override
	public Board.Player getWinner() {
		if(hasWinner()) {
			if(getNumMenOff(Player.PLAYER)==15) {
				return Player.PLAYER;
			} else if(getNumMenOff(Player.OPPONENT)==15) {
				return Player.OPPONENT;
			}
		}
		return Player.UNKNOWN;
	}

	@Override
	public List<BackgammonBoard> getBoards(BackgammonRoll roll) {
		List<BackgammonBoard> boards = new ArrayList<BackgammonBoard>();
		generateMoves(this, 0, 24, 0, roll, boards);
		return boards;
	}

	@Override
	public void setPlayerName(String name) {
		mPlayerName = name;
		if(mPlayerName.equals(mOpponentName)) {
			mPlayerName += "01";
			mOpponentName += "02";
		}
	}

	@Override
	public void setOpponenName(String name) {
		mOpponentName = name;
		if(mOpponentName.equals(mPlayerName)) {
			mPlayerName += "01";
			mOpponentName += "02";
		}
	}
}
