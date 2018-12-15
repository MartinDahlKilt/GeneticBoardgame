package org.mdk.BoardGame.Backgammon.Player;

import java.util.Arrays;
import java.util.List;

import org.mdk.BoardGame.Board;
import org.mdk.BoardGame.Backgammon.BackgammonBoard;
import org.mdk.BoardGame.Backgammon.BackgammonRoll;

public class MDKHiddenEvalPlayer extends BackgammonPlayer {
	private double wr[];
	private double wc[][];
	
	private double x[];
	private double h[];
	private double wh[];
	int mNumHidden;
	
	public MDKHiddenEvalPlayer(int numHidden, List<Double> weights) {
		mNumHidden = numHidden;
		x = new double[122];
		h = new double[mNumHidden];
		wh = new double[mNumHidden];
		
		wr = new double[122];
		int pos=0;
		for(int idx=0; idx < 122; idx++) {
			wr[idx]=weights.get(pos++);
		}
		wc = new double[mNumHidden][122];
		for(int outer = 0; outer < mNumHidden; outer++) {
			for(int idx=0; idx < 122; idx++) {
				wc[outer][idx]=weights.get(pos++);
			}
		}
		for(int idx = 0; idx < mNumHidden; idx++) {
			wh[idx] = weights.get(pos++);
		}
	}
	
	private double pubeval(BackgammonBoard b) {
	        /* Backgammon move-selection evaluation function
	           for benchmark comparisons.  Computes a linear
	           evaluation function:  Score = W * X, where X is
	           an input vector encoding the board state (using
	           a raw encoding of the number of men at each location),
	           and W is a weight vector.  Separate weight vectors
	           are used for racing positions and contact positions.
	           Makes lots of obvious mistakes, but provides a
	           decent level of play for benchmarking purposes. */

	        /* Provided as a public service to the backgammon
	           programming community by Gerry Tesauro, IBM Research.
	           (e-mail: tesauro@watson.ibm.com)                     */

	        /* The following inputs are needed for this routine:

	           race   is an integer variable which should be set
	           based on the INITIAL position BEFORE the move.
	           Set race=1 if the position is a race (i.e. no contact)
	           and 0 if the position is a contact position.

	           pos[]  is an integer array of dimension 28 which
	           should represent a legal final board state after
	           the move. Elements 1-24 correspond to board locations
	           1-24 from computer's point of view, i.e. computer's
	           men move in the negative direction from 24 to 1, and
	           opponent's men move in the positive direction from
	           1 to 24. Computer's men are represented by positive
	           integers, and opponent's men are represented by negative
	           integers. Element 25 represents computer's men on the
	           bar (positive integer), and element 0 represents opponent's
	           men on the bar (negative integer). Element 26 represents
	           computer's men off the board (positive integer), and
	           element 27 represents opponent's men off the board
	           (negative integer).                                  */

	        /* Also, be sure to call rdwts() at the start of your
	           program to read in the weight values. Happy hacking] */

	        int i;
	        double score;

	        if(b.getNumMenOff(Board.Player.PLAYER)==15) return(99999999.);
	        /* all men off, best possible move */

	        setx(b); /* sets input array x[] */
	        score = 0.0;
	        if(b.isRace()) {  /* use race weights */
	            for(i=0;i<122;++i) score += wr[i]*x[i];
	        } else {  /* use contact weights */
	        	for(int idx = 0; idx < mNumHidden; idx++) {
	        		for(i=0;i<122;++i) {
	        			h[idx] += wc[idx][i]*x[i];
	        		}
	        		score+=h[idx]*wh[idx];
	        	}
	        }
	        return(score);
	}
	private void setx(BackgammonBoard b) {
	        /* sets input vector x[] given board position pos[] */
	        int j, n;
	        /* initialize */
	        Arrays.fill(x, 0);

	        /* first encode board locations 24-1 */
	        for(j=1;j<=24;++j) {
	        	int jm1 = j - 1;
	            n = b.get(25-j);
	            if(n!=0) {
	                if(n==-1) x[5*jm1+0] = 1.0;
	                if(n==1) x[5*jm1+1] = 1.0;
	                if(n>=2) x[5*jm1+2] = 1.0;
	                if(n==3) x[5*jm1+3] = 1.0;
	                if(n>=4) x[5*jm1+4] = (float)(n-3)/2.0;
	            }
	        }
	        /* encode opponent barmen */
	        x[120] = -(float)(b.getNumBarMen(Board.Player.OPPONENT))/2.0;
	        /* encode computer's menoff */
	        x[121] = (float)(b.getNumMenOff(Board.Player.PLAYER))/15.0;
	}

	@Override
	public BackgammonBoard move(BackgammonBoard board, BackgammonRoll roll) {
		BackgammonBoard m = board;
		List<BackgammonBoard> configs = board.getBoards(roll);
		double bestScore = Double.NEGATIVE_INFINITY;
		for(BackgammonBoard b: configs) {
			double newScore = pubeval(b);
			if(newScore>bestScore) {
				m = b;
				bestScore = newScore;
			}
		}
		return m;
	}
	
//	public void setContactWeights(double[] weights) {
//		for(int idx = 0; idx < wc.length; idx++) {
//			wc[idx]=weights[idx];
//		}
//	}
//	
//	public void setRaceWeights(double[] weights) {
//		for(int idx = 0; idx < wr.length; idx++) {
//			wr[idx]=weights[idx];
//		}
//	}

	@Override
	public String getName() {
		return "MDKHidden80Player";
	}
}
