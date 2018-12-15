package org.mdk.Genetic.Selector;

import org.mdk.Genetic.Population.Population;

public interface Selector {
	void select(Population pop, Population result);
	String getConfiguration();
}
