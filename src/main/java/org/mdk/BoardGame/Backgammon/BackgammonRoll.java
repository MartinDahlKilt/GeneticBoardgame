package org.mdk.BoardGame.Backgammon;

import java.util.Random;

import org.mdk.BoardGame.Roll;

public class BackgammonRoll implements Roll {
	private int[] mDice;
	private boolean mIsDouble;

	public BackgammonRoll(int d1, int d2) {
		mDice = new int[4];
		mDice[0] = d1;
		mDice[1] = d2;
		if(mDice[0]==mDice[1]) {
			mIsDouble = true;
			mDice[2] = mDice[3] = mDice[0];
		} else {
			mIsDouble = false;
		}
	}

	
	public BackgammonRoll(Random rand) {
		mDice = new int[4];
		mDice[0] = rand.nextInt(6) + 1;
		mDice[1] = rand.nextInt(6) + 1;
		if(mDice[0]==mDice[1]) {
			mIsDouble = true;
			mDice[2] = mDice[3] = mDice[0];
		} else {
			mIsDouble = false;
		}
	}
	
	@Override
	public int get(int idx) {
		return mDice[idx];
	}
	
	public boolean isDouble() {
		return mIsDouble;
	}
	
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append(mDice[0]);
		buf.append("x");
		buf.append(mDice[1]);
		if(mIsDouble) {
			buf.append(" (Double)");
		}
		return buf.toString();
	}

}
