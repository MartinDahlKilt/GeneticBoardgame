package org.mdk.Genetic.Random;


public class Random {
	private static Random mInstance = null;
	protected Random() {
		// Exists only to defeat instantiation.
	}
	public static Random getInstance() {
		if(mInstance == null) {
			mInstance = new Random();
		}
		return mInstance;
	}
	
	private java.util.Random mRandom = new java.util.Random();
	public java.util.Random getRandom() {
		return mRandom;
	}
}
