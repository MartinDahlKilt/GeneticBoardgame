package org.mdk.Genetic.Crossover;

import org.mdk.Genetic.Chromosome.Chromosome;

import java.util.Random;

public class UniformCrossover<T> extends Crossover<T> {
	private double mRatio;
	public UniformCrossover(Random random, double ratio) {
		super(random);
		mRatio = ratio;
	}

    public UniformCrossover(double ratio) {
        mRatio = ratio;
    }
	
	@Override
	protected void combinePair(Chromosome<T> first, Chromosome<T> second) {
		for(int idx=0; idx < first.size(); idx++) {
			if(mRandom.nextDouble() < mRatio) {
				first.swap(second, idx, idx+1);
			}
		}
	}

	@Override
	public String getConfiguration() {
		return this.getClass().getName()+"[ratio="+mRatio+"]";
	}

}
