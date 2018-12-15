package org.mdk.Genetic.Mutator;

import org.mdk.Genetic.Population.Population;

public interface PopulationMutator {
    void mutate(Population pop);
    String getConfiguration();
}

