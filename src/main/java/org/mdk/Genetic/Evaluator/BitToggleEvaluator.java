package org.mdk.Genetic.Evaluator;

import org.mdk.Genetic.Chromosome.Chromosome;
import org.mdk.Genetic.Population.Population;

public class BitToggleEvaluator extends Evaluator {
	private int mNumBits;
	public BitToggleEvaluator(int numBits) {
		mNumBits = numBits;
	}
	
	@Override
	public Statistics evaluate(Population pop) {
		Statistics stat = new Statistics();
		double sum = 0.0;
		for(int idx=0; idx < pop.size(); idx++) {
			double score = 0;
			Chromosome<?> c = pop.get(idx);
			boolean expected = true;
			for(int cc=0; cc < c.size(); cc++) {
				boolean val=false;
				if((Byte)c.get(cc)==1) {
					val=true;
				}
				if(val==expected) {
					score += 1;
				}
				expected = !expected;
			}
			c.setFitness(score);
			sum+=score;
			if(score > stat.mMaxFitness) {
				stat.mMaxFitness = score;
				stat.mBest=c;
			}
		}
		stat.mAvgFitness=sum/pop.size();
		return stat;
	}

	@Override
	public double getGoalFitness() {
		return mNumBits;
	}

	@Override
	public String getConfiguration() {
		return this.getClass().getName()+"[bits="+mNumBits+"]";
	}

}
