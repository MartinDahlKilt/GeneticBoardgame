package org.mdk.Genetic.Crossover;

import org.mdk.Genetic.Chromosome.Chromosome;
import org.mdk.Genetic.Random.Random;

public class OnePointCrossover<T> extends Crossover<T> {
	@Override
	protected void combinePair(Chromosome<T> first, Chromosome<T> second) {
		int size = first.size();
		int point = mRandom.nextInt(size);
		first.swap(second, point, size);
	}

	@Override
	public String getConfiguration() {
		return this.getClass().getName();
	}

}
