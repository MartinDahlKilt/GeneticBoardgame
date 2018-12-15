package org.mdk.Genetic.Chromosome;

import org.mdk.Genetic.Mutator.GeneMutator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class Chromosome<T>  {
    private GeneMutator<T> mMutator;
    private Supplier<T> mSupplier;
	private List<T> mGenes;
	private double mFitness;

	public Chromosome(int size, GeneMutator<T> mutator, Supplier<T> supplier) {
	    mMutator = mutator;
	    mSupplier = supplier;
		mFitness = 0;
		mGenes = new ArrayList<>(size);
		for(int idx = 0; idx < size; idx++) {
			mGenes.add(mMutator.mutate(createGene(), 1));
		}
	}

	private T createGene() {
	    return mSupplier.get();
    }

	public Chromosome(Chromosome<T> c) {
		mGenes = new ArrayList<>(c.mGenes);
		mMutator = c.mMutator;
		mSupplier = c.mSupplier;
		mFitness= c.mFitness;
	}

	
	public T get(int pos) {
		return mGenes.get(pos);
	}

	public void set(int pos, T value) {
		mGenes.set(pos, value);
	}

	public void mutate(int pos, int gen) {
		mGenes.set(pos, mMutator.mutate(mGenes.get(pos), gen));
	}

	public int size() {
		return mGenes.size();
	}

	public void swap(Chromosome<T> other, int start, int end) {
		for(int idx = start; idx < end; idx++) {
			T tmp = get(idx);
			set(idx, other.get(idx));
			other.set(idx, tmp);
		}
	}

	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append("{ [");
		for(T g : mGenes) {
			buf.append(g).append("|");
		}
		buf.append("] ").append("fitness=").append(mFitness).append("}"); 
		return buf.toString();
	}

	public void setFitness(double fitness) {
		mFitness = fitness;
	}

	public double getFitness() {
	    return mFitness;
	}

	public Chromosome<T> copy() {
		return new Chromosome<>(this);
	}

	/*public T[] getValues() {
	    return (T[])mGenes.toArray();
    }*/

	public List<T> getValues() {
	    return Collections.unmodifiableList(mGenes);
    }

}
