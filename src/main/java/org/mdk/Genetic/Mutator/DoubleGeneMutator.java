package org.mdk.Genetic.Mutator;

import org.mdk.Genetic.Random.Random;
import org.mdk.commons.Sigmoid;

public class DoubleGeneMutator implements GeneMutator<Double> {
    private double mMin;
    private double mMax;

    public DoubleGeneMutator(double min, double max) {
        mMin = min;
        mMax = max;
    }


    @Override
    public Double mutate(Double gene, int gen) {
        double val;
    	if(gene == 0) {
    	    val = Random.getInstance().getRandom().nextDouble();
    	    if(Random.getInstance().getRandom().nextBoolean()) {
    	        val = -val;
            }
        } else {
            double sdiff = Sigmoid.value(gen, 0.1);

            double diff = gene * (1 - sdiff); //((Random.getInstance().getRandom().nextDouble()*mMaxValue)+mMinValue)*(1-sdiff);

            if (Random.getInstance().getRandom().nextBoolean()) {
                val = gene + diff;
            } else {
                val = gene - diff;
            }
        }
		if(val>mMax) {
			val = mMax;
		} else if(val<mMin) {
			val = mMin;
		}
		return val;
    }
}
