package org.mdk.Genetic;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.mdk.Genetic.Chromosome.Chromosome;
import org.mdk.Genetic.Crossover.Crossover;
import org.mdk.Genetic.Evaluator.Evaluator;
import org.mdk.Genetic.Mutator.PopulationMutator;
import org.mdk.Genetic.Population.Population;
import org.mdk.Genetic.Selector.Selector;

public abstract class Runner {

	public abstract Population createPopulation(boolean populated);
	public abstract Evaluator createEvaluator();
	public abstract Selector createSelector();
	public abstract Crossover createCrossover();
	public abstract PopulationMutator createMutator();
	public abstract int getNumElites();
	public abstract double externalEvaluate(Chromosome<?> best);
	public abstract double getCrossoverProbability();
	public abstract String getLogPath();
	
	
	private FileWriter initExternalLog(File root, Population p, Evaluator e, Selector s, Crossover c, int elites, double cprob) throws IOException {
		FileWriter writer = new FileWriter(new File(root, "external.log"),false);
		writer.write("#"+p.getConfiguration()+"\n");
		writer.write("#"+e.getConfiguration()+"\n");
		writer.write("#"+s.getConfiguration()+"\n");
		writer.write("#"+c.getConfiguration()+"\n");
		writer.write("#Crossover prob="+cprob+"\n");
		writer.write("#Elites="+elites+"\n");
		writer.write("Generation,%Wins,Avg,Min\n");
		return writer;
	}

	private FileWriter initChromeLog(File root) throws IOException {
		FileWriter writer = new FileWriter(new File(root, "chrome.log"),false);
		writer.write("Generation,Chromosome\n");
		return writer;
	}

	
	private void writeExternalLogEntry(FileWriter writer, int gen, Population p, Evaluator.Statistics s, boolean complete) throws IOException {
		double externalScore = externalEvaluate(s.mBest);
		StringBuilder buf = new StringBuilder();
		buf.append(gen).append(",").append(externalScore);
		if(complete) {
			double sum = 0;
			double min = Double.POSITIVE_INFINITY;
			for(int idx=0; idx <p.size(); idx++) {
				double sc = externalEvaluate(p.get(idx));
				sum+=sc;
				if(sc < min) {
					min = sc;
				}
			}
			buf.append(","+(sum/p.size())+","+min);
		} else {
			buf.append(",0,0");
		}
		buf.append("\n");
		writer.write(buf.toString());
		writer.flush();
	}
	
	private void writeChromeLog(FileWriter writer, int gen, Evaluator.Statistics s) throws IOException {
		StringBuilder buf = new StringBuilder();
		buf.append(gen).append(",").append(s.mBest.toString()).append("\n");
		writer.write(buf.toString());
		writer.flush();
	}
	
	public void run() throws IOException {
		Population population = createPopulation(true);
		Evaluator evaluator = createEvaluator();
		Selector selector = createSelector();
		Crossover crossover = createCrossover();
		PopulationMutator mutator = createMutator();
		int numElites = getNumElites();
		double crossover_probability = getCrossoverProbability();
		Chromosome<?> winner = null;
		File root = new File(getLogPath());
		if(!root.exists()) {
			root.mkdir();
		}
		FileWriter externalLog = initExternalLog(root, population, evaluator, selector, crossover, numElites, crossover_probability);
		FileWriter chromeLog = initChromeLog(root);
		Evaluator.Statistics stat = null;
		double currentMaxScore = Double.NEGATIVE_INFINITY;
		int generation = 0;
		while(currentMaxScore < evaluator.getGoalFitness()) {
			generation++;
			stat = evaluator.evaluate(population);
			System.out.println("Generation:"+generation+" Score  Max: "+stat.mMaxFitness+" Avg:"+stat.mAvgFitness);
			if(stat.mMaxFitness > currentMaxScore) {
				winner = stat.mBest;
				currentMaxScore = stat.mMaxFitness;
			}
			if(generation%10==0||generation==1) {
				writeExternalLogEntry(externalLog, generation, population, stat, false);
				writeChromeLog(chromeLog, generation, stat);
			}
			List<Chromosome<?>> elites = null;
			if(numElites>0) {
				elites = population.extractElite(numElites);
			}
			Population selected = createPopulation(false);
			Population mated = createPopulation(false);
			selector.select(population, selected);
			crossover.combine(selected, mated, crossover_probability);
			mated.setGenerations(generation);
			mutator.mutate(mated);
			if(numElites>0) {
				mated.injectElite(elites);
			}
			population = mated;
		}
		externalLog.close();
		// Save winner
	}
}
