package org.mdk.Genetic.Population;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mdk.Genetic.Chromosome.Chromosome;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;

class PopulationTest {
    static int CHROMOSOME_SIZE = 10;
    static int POPULATION_SIZE = 100;

    Population pop;
    Supplier<Chromosome<?>> supplier;

    @BeforeEach
    void init() {
        supplier = ()-> (new Chromosome<>(CHROMOSOME_SIZE, (Boolean gene, int gen) -> false, () -> false));
        pop = new Population(supplier);
        pop.initialize(POPULATION_SIZE);
    }


    @Test
    void initialize() {
        assertEquals(POPULATION_SIZE, pop.size());
        assertEquals(CHROMOSOME_SIZE, pop.get(0).size());
    }

    @Test
    void size() {
        assertEquals(POPULATION_SIZE, pop.size());
    }

    @Test
    void get() {
        Chromosome<?> c = pop.get(0);
        assertNotNull(c);
        assertEquals(CHROMOSOME_SIZE, pop.get(0).size());
    }

    @Test
    void copy() {
        Chromosome<?> c = pop.copy(0);
        assertNotNull(c);
        assertTrue(c != pop.get(0));
        assertThat(c.getValues(), is(pop.get(0).getValues()));
    }

    @Test
    void add() {
        pop.add(supplier.get());
        assertEquals(POPULATION_SIZE + 1, pop.size());
    }

    @Test
    void extractElite() {
        pop.get(0).setFitness(10.0);
        List<Chromosome<?>> l = pop.extractElite(1);
        assertEquals(1, l.size());
        assertEquals(10, l.get(0).getFitness());
    }

    @Test
    void injectElite() {
        Chromosome<?> c = supplier.get();
        c.setFitness(10);
        pop.injectElite(Collections.singletonList(c));
        assertEquals(POPULATION_SIZE + 1, pop.size());

        List<Chromosome<?>> l = pop.extractElite(1);
        assertEquals(1, l.size());
        assertEquals(10, l.get(0).getFitness());
    }

    @Test
    void getGenerations() {
        assertEquals(1, pop.getGenerations());
    }

    @Test
    void setGenerations() {
        pop.setGenerations(2);
        assertEquals(2, pop.getGenerations());
    }

    @Test
    void getConfiguration() {
    }
}