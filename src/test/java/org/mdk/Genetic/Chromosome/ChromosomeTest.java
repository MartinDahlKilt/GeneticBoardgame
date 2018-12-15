package org.mdk.Genetic.Chromosome;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.junit.jupiter.api.Assertions.*;

class ChromosomeTest {
    static int SIZE = 10;
    Chromosome<Boolean> c;

    @BeforeEach
    void init() {
        c = new Chromosome<>(SIZE, (Boolean gene, int gen) -> false, () -> false);
        c.setFitness(5);
    }

    @Test
    void get() {
        assertEquals(false, c.get(0));
    }

    @Test
    void set() {
        c.set(5,true);
        assertThat(c.getValues(), contains(false, false, false, false, false, true, false, false, false, false));
    }

    @Test
    void mutate() {
        for(int idx = 0; idx < SIZE; idx++) {
            c.set(idx, true);
        }
        c.mutate(5, 0);
        assertEquals(false, c.get(5));
    }

    @Test
    void size() {
        assertEquals(SIZE, c.size());
    }

    @Test
    void swap() {
        Chromosome<Boolean> d = new Chromosome<>(10, (Boolean gene, int gen) -> true, () -> true);
        c.swap(d, 5, 8);

        assertThat(c.getValues(), contains(false, false, false, false, false, true, true, true, false, false));
        assertThat(d.getValues(), contains(true, true, true, true, true, false, false, false, true, true));
    }

    @Test
    void setFitness() {
        c.setFitness(10);
        assertEquals(10, c.getFitness());
    }

    @Test
    void getFitness() {
        assertEquals(5, c.getFitness());
    }

    @Test
    void copy() {
        Chromosome<Boolean> d = c.copy();
        d.set(5, true);
        assertThat(c.getValues(), contains(false, false, false, false, false, false, false, false, false, false));
        assertThat(d.getValues(), contains(false, false, false, false, false, true, false, false, false, false));
    }

    @Test
    void getValues() {
        assertThat(c.getValues(), contains(false, false, false, false, false, false, false, false, false, false));
    }
}