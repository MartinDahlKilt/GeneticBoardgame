package org.mdk.Genetic.Evaluator;

import org.mdk.Genetic.Chromosome.Chromosome;
import org.mdk.Genetic.Population.Population;

public abstract class Evaluator {
	public class Statistics {
		public Statistics() {
			mMaxFitness = Double.NEGATIVE_INFINITY;
			mAvgFitness = Double.NEGATIVE_INFINITY;
			mBest = null;
		}
		public double mMaxFitness;
		public double mAvgFitness;
		public Chromosome<?> mBest;
	}

	
	public abstract double getGoalFitness();
	public abstract Statistics evaluate(Population pop);
	public abstract String getConfiguration();
}
