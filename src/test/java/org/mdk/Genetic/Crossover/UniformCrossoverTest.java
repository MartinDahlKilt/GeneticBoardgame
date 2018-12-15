package org.mdk.Genetic.Crossover;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mdk.Genetic.Chromosome.Chromosome;

import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

class UniformCrossoverTest {
    static int SIZE = 10;
    Chromosome<Boolean> a;
    Chromosome<Boolean> b;
    Crossover<Boolean> c;

    class TestRandom extends Random {
        int cnt = 0;

        @Override
        public double nextDouble() {
            double val  = 0;
            if(cnt < SIZE/2) {
                val = 0.1;
            } else {
                val = 1.0;
            }
            cnt++;
            return val;
        }

        @Override
        public int nextInt() {
            return SIZE/2;
        }
    }

    @BeforeEach
    void init() {
        a = new Chromosome<>(SIZE, (Boolean gene, int gen) -> false, () -> false);
        a.setFitness(5);

        b = new Chromosome<>(SIZE, (Boolean gene, int gen) -> true, () -> true);
        b.setFitness(2);

        c = new UniformCrossover<>(new TestRandom(), 0.5);
    }

    @Test
    void combinePair() {
        c.combinePair(a, b);
        assertThat(a.getValues(), contains(true, true, true, true, true, false, false, false, false, false));
        assertThat(b.getValues(), contains(false, false, false, false, false, true, true, true, true, true));
    }
}