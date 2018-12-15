package org.mdk.Genetic.Crossover;

import org.mdk.Genetic.Chromosome.Chromosome;
import org.mdk.Genetic.Population.Population;
import org.mdk.Genetic.Random.Random;

public abstract class Crossover<T>  {
    protected java.util.Random mRandom;
	public Crossover(java.util.Random random) {
	    mRandom = random;
    }

    public Crossover() {
	    mRandom = Random.getInstance().getRandom();
    }

	public abstract String getConfiguration();
	protected abstract void combinePair(Chromosome<T> first, Chromosome<T> second);

	public void combine(Population src, Population dst, double probability) {
		int srcSize = src.size();
		for(int idx = 0; idx < srcSize; idx++) {
			if(mRandom.nextDouble()<=probability) {
				int mate = mRandom.nextInt(srcSize);
				Chromosome<T> child = (Chromosome<T>)src.get(idx).copy();
				combinePair(child, (Chromosome<T>)src.get(mate).copy());
				dst.add(child);
			} else {
				dst.add(src.get(idx).copy());
			}
		}
	}
}
