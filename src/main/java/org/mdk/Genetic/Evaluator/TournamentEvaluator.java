package org.mdk.Genetic.Evaluator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.mdk.BoardGame.SessionResult;
import org.mdk.BoardGame.Backgammon.Player.MDKEvalPlayer;
import org.mdk.BoardGame.Backgammon.Session.BackgammonSession;
import org.mdk.Genetic.BackgammonRunner;
import org.mdk.Genetic.Chromosome.Chromosome;
import org.mdk.Genetic.Population.Population;
import org.mdk.commons.Pair;

public class TournamentEvaluator extends Evaluator {
    private BackgammonRunner mRunner;
	private BackgammonSession mSession;
	private int mNumGames;
	private LinkedBlockingQueue<Pair<Chromosome<?>, Chromosome<?>>> mInputQueue;
	private LinkedBlockingQueue<Pair<Double,Pair<Chromosome<?>,Chromosome<?>>>> mOutputQueue;
	private int mNumThreads;

	public TournamentEvaluator(BackgammonRunner runner, boolean verbose, int games) {
	    mRunner = runner;
		mSession = new BackgammonSession(verbose);
		mNumGames = games;
		mInputQueue = new LinkedBlockingQueue<>();
		mOutputQueue = new LinkedBlockingQueue<>();
		mNumThreads = 1;
	}
	
	private class EvaluatorRunnable implements Runnable {
		private boolean mTerminate;
		public EvaluatorRunnable() {
			mTerminate = false;
		}
				
		@Override
		public void run() {
			while(!mTerminate) {
				try {
					Pair<Chromosome<?>,Chromosome<?>> elem;
					elem = mInputQueue.poll(2000, TimeUnit.MILLISECONDS);
					if(elem!=null) {
						if(elem.getFirst()==null) {
							mTerminate = true;
							continue;
						}
						@SuppressWarnings("unchecked")
						MDKEvalPlayer p = new MDKEvalPlayer((List<Double>)elem.getFirst().getValues());
                        @SuppressWarnings("unchecked")
						MDKEvalPlayer o = new MDKEvalPlayer((List<Double>)elem.getSecond().getValues());
//						MDK2PlyEvalPlayer p = new MDK2PlyEvalPlayer(elem.getFirst().getDoubles());
//						MDK2PlyEvalPlayer o = new MDK2PlyEvalPlayer(elem.getSecond().getDoubles());

//						MDKHiddenEvalPlayer p = new MDKHiddenEvalPlayer(15, elem.getFirst().getDoubles());
//						MDKHiddenEvalPlayer o = new MDKHiddenEvalPlayer(15, elem.getSecond().getDoubles());

						SessionResult res = mSession.play(p, o, mNumGames); 
						mOutputQueue.put(new Pair<>(res.getEquity(),elem));
					}
				} catch(InterruptedException e) {
					e.printStackTrace();
					// Ignore
				}
			}
		}
		
	}
	
	
	private void playTournament(Population pop, Statistics stat) {
		int popSize = pop.size();
		Population winners = mRunner.createPopulation(false);
		for(int idx=0; idx < popSize; idx+=2) {
			// Add jobs to queue
			mInputQueue.offer(new Pair<>(pop.get(idx), pop.get(idx+1)));
		}
		// Wait for jobs to be processed
		int processed = 0;
		do {
			try {
				Pair<Double,Pair<Chromosome<?>,Chromosome<?>>> result = mOutputQueue.take();
				if(result.getFirst()>=0) {
					winners.add(result.getSecond().getFirst());
					result.getSecond().getSecond().setFitness(1/(double)popSize);
				} else {
					result.getSecond().getFirst().setFitness(1/(double)popSize);
					winners.add(result.getSecond().getSecond());
				}
				processed++;
			} catch(InterruptedException e) {
				e.printStackTrace();
				// Ignore
			}
		} while(processed<popSize/2);
		
		if(winners.size()>1) {
			playTournament(winners,stat);
		} else {
			winners.get(0).setFitness(1);
			stat.mBest = winners.get(0);
			stat.mMaxFitness = 1;
			double sum = 0;
			for(int idx = 0; idx < pop.size(); idx++) {
				sum += pop.get(idx).getFitness();
			}
			stat.mAvgFitness = sum / pop.size();
		}
	}
	
	@Override
	public Statistics evaluate(Population pop) {
		// Start Threads
		List<EvaluatorRunnable> runners = new ArrayList<>();
		List<Thread> threads = new ArrayList<>();
		for(int idx=0; idx < mNumThreads; idx++) {
			EvaluatorRunnable r = new EvaluatorRunnable();
			runners.add(r);
			Thread t = new Thread(r);
			threads.add(t);
			t.start();
		}

		
		
		Statistics stat = new Statistics();
		playTournament(pop,stat);
		
		// Stop Threads
		for(int idx=0; idx <mNumThreads; idx++) {
			mInputQueue.offer(new Pair<>(null, null));
		}
		for(Thread t : threads) {
			try {
				t.join();
			} catch(InterruptedException e) {
				e.printStackTrace();
				// Ignore
			}
		}

		return stat;
	}

	@Override
	public double getGoalFitness() {
		return 2.0;
	}

	@Override
	public String getConfiguration() {
		return this.getClass().getName()+"[games="+mNumGames+"]";
	}

}
