package org.mdk.Genetic;


import org.mdk.BoardGame.SessionResult;
import org.mdk.BoardGame.Backgammon.Player.MDKEvalPlayer;
import org.mdk.BoardGame.Backgammon.Session.PubEvalSession;
import org.mdk.Genetic.Chromosome.Chromosome;
import org.mdk.Genetic.Crossover.Crossover;
import org.mdk.Genetic.Crossover.UniformCrossover;
import org.mdk.Genetic.Evaluator.Evaluator;
import org.mdk.Genetic.Evaluator.TournamentEvaluator;
import org.mdk.Genetic.Mutator.DoubleGeneMutator;
import org.mdk.Genetic.Mutator.PopulationMutator;
import org.mdk.Genetic.Mutator.UniformMutator;
import org.mdk.Genetic.Population.Population;
import org.mdk.Genetic.Selector.RankedSelector;
import org.mdk.Genetic.Selector.Selector;

import java.util.function.Supplier;

public class BackgammonRunner extends Runner {

	public static void main(String[] args) throws Exception {
		BackgammonRunner runner = new BackgammonRunner();
		runner.run();
	}

	@Override
    public Population createPopulation(boolean populated) {
//		int numDoubles = 15*122+15+122;
		Supplier<Chromosome<?>> supplier = ()-> (new Chromosome<>(244,new DoubleGeneMutator(-8, 8), () -> (0.0)));
		if(populated) {
			return new Population(256, supplier);
		} else {
			return new Population(supplier);
		}
	}

	@Override
    public Evaluator createEvaluator() {
		return new TournamentEvaluator(this, false, 50);
//		return new SelfplayEvaluator(false, 100);
//		return new PubEvalEvaluator(300);
	}

	@Override
    public Selector createSelector() {
		return new RankedSelector();
	}

	@Override
    public Crossover createCrossover() {
		return new UniformCrossover(0.5);
	}

	@Override
    public PopulationMutator createMutator() {
		double rate = 1/(double)244;
		return new UniformMutator(rate);
	}

	@Override
    public int getNumElites() {
		return 1;
	}

	@Override
    public double externalEvaluate(Chromosome<?> best) {
		return  evaluate((Chromosome<Double>)best);
	}

	protected double evaluate(Chromosome<Double> c) {
		MDKEvalPlayer p = new MDKEvalPlayer(c.getValues());
		PubEvalSession game = new PubEvalSession(false);
		SessionResult sess = game.play(p, 10000);
		System.out.println(sess);
		return sess.getEquity();
	}

	@Override
    public double getCrossoverProbability() {
		return 0.8;
	}

	@Override
    public String getLogPath() {
		return "BackgammonRunner."+System.currentTimeMillis();
	}
}
