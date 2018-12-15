package org.mdk.Genetic.Population;

import org.mdk.Genetic.Chromosome.Chromosome;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;

public class Population /*implements IPopulation<T>*/ {
	private int mGenerations;
	protected List<Chromosome<?>> mPopulation;
	/*protected abstract Chromosome<T> createInstance();
	protected abstract Chromosome<T> createInstance(Chromosome<T> src);*/
	//public abstract String getConfiguration();

	protected Supplier<Chromosome<?>> mSupplier;

    public Population(Supplier<Chromosome<?>> supplier){
        mSupplier = supplier;
		mPopulation = new ArrayList<>();
    }

	public Population(int size, Supplier<Chromosome<?>> supplier){
		mSupplier = supplier;
		initialize(size);
	}


	public class PopulationSorter implements Comparator<Chromosome<?>>{
		@Override
		public int compare(Chromosome<?> arg0, Chromosome<?> arg1) {
			if(arg0.getFitness()>arg1.getFitness()) {
				return -1;
			} else if(arg0.getFitness()<arg1.getFitness()) {
				return 1;
			} 
			return 0;
		}
	}
	
	protected void initialize(int size) {
		setGenerations(1);
		mPopulation = new ArrayList<>();
		for(int idx = 0; idx < size; idx++) {
			mPopulation.add(mSupplier.get());
		}
	}
	
	public int size() {
		return mPopulation.size();
	}
	
	public Chromosome<?> get(int idx) {
		return mPopulation.get(idx);
	}
	
	public Chromosome<?> copy(int idx) {
		return get(idx).copy();
	}
	
	public void add(Chromosome<?> c) {
		mPopulation.add(c);
	}
	
	public String toString() {
		StringBuilder buf = new StringBuilder();
		for(Chromosome<?> c : mPopulation) {
			buf.append(c).append("\n");
		}
		return buf.toString();
	}
		
	public List<Chromosome<?>> extractElite(int numElites) {
		List<Chromosome<?>> l = new ArrayList<>();
		mPopulation.sort(new PopulationSorter());
		for(int idx = 0; idx < numElites; idx++) {
            Chromosome<?> e = mPopulation.get(idx).copy();
			l.add(e);
		}
		for(int idx = size()-numElites; idx < size(); idx++) {
			mPopulation.remove(idx);
		}
		return l;
	}
	
	public void injectElite(List<Chromosome<?>> elites) {
		mPopulation.addAll(elites);
	}
	public int getGenerations() {
		return mGenerations;
	}
	public void setGenerations(int gen) {
		mGenerations = gen;
	}


    public String getConfiguration() {
        return this.getClass().getName()+"[psize="+size()+"]";
    }

}
