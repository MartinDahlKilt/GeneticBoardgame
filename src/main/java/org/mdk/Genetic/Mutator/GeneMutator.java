package org.mdk.Genetic.Mutator;

public interface GeneMutator<T> {
	T mutate(T gene, int gen);
}
