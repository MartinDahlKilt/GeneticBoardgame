package org.mdk.Genetic.Selector;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mdk.Genetic.Chromosome.Chromosome;
import org.mdk.Genetic.Population.Population;

import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

class RankedSelectorTest {
    static int CHROMOSOME_SIZE = 10;
    static int POPULATION_SIZE = 100;

    RankedSelector selector;
    Population pop;
    Supplier<Chromosome<?>> supplier;

    @BeforeEach
    void init() {
        supplier = ()-> (new Chromosome<>(CHROMOSOME_SIZE, (Boolean gene, int gen) -> false, () -> false));
        pop = new Population(POPULATION_SIZE, supplier);
        selector = new RankedSelector();
    }

    @Test
    void select() {
        Population res = new Population(supplier);
        for(int idx = 0; idx < pop.size(); idx++) {
            pop.get(idx).setFitness(idx);
        }
        selector.select(pop, res);

        assertEquals(POPULATION_SIZE, res.size());

        for(int r = 0; r < res.size(); r++) {
            boolean found = false;
            Chromosome<?> rc = res.get(r);
            for(int p = 0; p < pop.size(); p++) {
                if(rc == pop.get(p)) {
                    found = true;
                    break;
                }
            }
            assertFalse(found);
        }
    }
}