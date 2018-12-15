package org.mdk.Genetic.Selector;

import org.mdk.Genetic.Population.Population;
import org.mdk.Genetic.Random.Random;

public class RouletteWheelSelector implements Selector {
	@Override
	public void select(Population pop, Population result) {
		double sum=0;
		for(int idx=0; idx < pop.size(); idx++) {
			sum += pop.get(idx).getFitness();
		}
		if(sum==0.0) {
			result = pop;
		}
		for(int idx=0; idx < pop.size(); idx++) {
			double roll = Random.getInstance().getRandom().nextDouble()*sum;
			double val = 0.0;
			for(int j=0; j < pop.size(); j++) {
				val += pop.get(j).getFitness();
				if(val >= roll) {
					result.add(pop.get(j).copy());
					break;
				}
			}
		}
	}

	@Override
	public String getConfiguration() {
		return this.getClass().getName();
	}
}
