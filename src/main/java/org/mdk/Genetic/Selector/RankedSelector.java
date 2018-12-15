package org.mdk.Genetic.Selector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.mdk.Genetic.Chromosome.Chromosome;
import org.mdk.Genetic.Population.Population;
import org.mdk.Genetic.Random.Random;

public class RankedSelector implements Selector {
	
	public class FitnessRanker implements Comparator<Chromosome<?>>{
		 public int a = 0;
		 
		@Override
		public int compare(Chromosome<?> arg0, Chromosome<?> arg1) {
			if(arg0.getFitness()>arg1.getFitness()) {
				return 1;
			} else if(arg0.getFitness()<arg1.getFitness()) {
				return -1;
			} 
			return 0;
		}
	}

	@Override
	public void select(Population pop, Population result) {
		List<Chromosome<?>> tmp = new ArrayList<>();
		double sum = 0.0;
		for(int idx = 0; idx < pop.size(); idx++) {
			tmp.add(pop.get(idx));
			sum += idx+1;
		}
		Collections.sort(tmp,new FitnessRanker());
		for(int idx=0; idx < tmp.size(); idx++) {
			double roll = Random.getInstance().getRandom().nextDouble()*sum;
			double val = 0.0;
			for(int j=0; j < tmp.size(); j++) {
				val += j+1;
				if(val >= roll) {
					result.add(tmp.get(j).copy());
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
