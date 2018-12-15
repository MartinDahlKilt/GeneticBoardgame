package org.mdk.Genetic.Mutator;

import org.mdk.Genetic.Chromosome.Chromosome;
import org.mdk.Genetic.Population.Population;
import org.mdk.Genetic.Random.Random;

public class NonUniformMutator implements PopulationMutator {
	private double mProbability;
	public NonUniformMutator(double probability) {
		mProbability = probability;
	}

	@Override
	public void mutate(Population pop) {
		for(int idx=0; idx < pop.size(); idx++) {
			Chromosome<?> c = pop.get(idx);
			for(int g=0; g < c.size(); g++) {
				if(Random.getInstance().getRandom().nextDouble()<(mProbability/pop.getGenerations())) {
					c.mutate(g, pop.getGenerations());
				}
			}
		}
	}

	@Override
	public String getConfiguration() {
		return this.getClass().getName()+"[prob="+mProbability+"]";
	}

}
