package org.mdk.BoardGame;

public class SessionResult {
	private int mTies;
	private int mWins;
	private int mLosses;
	
	public int getWins() {
		return mWins;
	}

	public int getLosses() {
		return mLosses;
	}

	public int getTies() {
		return mTies;
	}

	public int getPlays() {
		return mTies+mWins+mLosses;
	}

	public double getEquity() {
		return (mWins-mLosses)/(double)getPlays();
	}
	
	public void addWin() {
		mWins++;
	}
	
	public void addLoss() {
		mLosses++;
	}
	
	public void addTie() {
		mTies++;
	}
	
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append("Equity:"+getEquity()+" Wins:"+getWins()+" Losses:"+getLosses()+" Ties:"+getTies());
		return buf.toString();
	}
}
