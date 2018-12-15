package org.mdk.Genetic.Crossover;

import org.mdk.Genetic.Chromosome.Chromosome;
import org.mdk.Genetic.Random.Random;

public class OnePointSplitCrossover<T> extends Crossover<T> {
	private int mSplitPos;
	public OnePointSplitCrossover(int splitPos) {
		mSplitPos = splitPos;
	}
	
	@Override
	protected void combinePair(Chromosome<T> first, Chromosome<T> second) {
		// First 
		int point = mRandom.nextInt(mSplitPos);
		first.swap(second, point, mSplitPos);
		// Second
		point = mSplitPos + mRandom.nextInt(first.size()-mSplitPos);
		first.swap(second, point, first.size());
	}

	@Override
	public String getConfiguration() {
		return this.getClass().getName()+"[split="+mSplitPos+"]";
	}

}
