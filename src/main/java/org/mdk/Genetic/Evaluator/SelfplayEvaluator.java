package org.mdk.Genetic.Evaluator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.mdk.BoardGame.SessionResult;
import org.mdk.BoardGame.Backgammon.Player.MDKHiddenEvalPlayer;
import org.mdk.BoardGame.Backgammon.Session.BackgammonSession;
import org.mdk.Genetic.Chromosome.Chromosome;
import org.mdk.Genetic.Population.Population;
import org.mdk.commons.Pair;

public class SelfplayEvaluator extends Evaluator {
	private BackgammonSession mSession;
	private int mNumGames;
	private LinkedBlockingQueue<Pair<Integer, Chromosome<?>>> mInputQueue;
	private LinkedBlockingQueue<Pair<Integer, Double>> mOutputQueue;
	private int mNumThreads;

	public SelfplayEvaluator(boolean verbose, int games) {
		mSession = new BackgammonSession(verbose);
		mNumGames = games;
		mInputQueue = new LinkedBlockingQueue<>();
		mOutputQueue = new LinkedBlockingQueue<>();
		mNumThreads = 4;
	}
	
	private class EvaluatorRunnable implements Runnable {
		private boolean mTerminate;
		private Chromosome<Double> mPlayer;
		public EvaluatorRunnable() {
			mTerminate = false;
		}
				
		public void setPlayer(Chromosome<Double> p) {
			mPlayer = p;
		}
		
		@Override
		public void run() {
			while(!mTerminate) {
				try {
					Pair<Integer,Chromosome<?>> elem;
					elem = mInputQueue.poll(2000, TimeUnit.MILLISECONDS);
					if(elem!=null) {
						if(elem.getFirst()==-1) {
							mTerminate = true;
							continue;
						}

						MDKHiddenEvalPlayer p = new MDKHiddenEvalPlayer(15, mPlayer.getValues());
						@SuppressWarnings("unchecked")
						MDKHiddenEvalPlayer o = new MDKHiddenEvalPlayer(15, (List<Double>)elem.getSecond().getValues());
						SessionResult res = mSession.play(p, o, mNumGames);
						mOutputQueue.put(new Pair<>(elem.getFirst(),res.getEquity()));
					}
				} catch(InterruptedException e) {
					e.printStackTrace();
					// Ignore
				}
			}
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
		int popSize = pop.size();
		double ppg[][] = new double[popSize][popSize];
		for(int idx = 0; idx < popSize; idx++) {
			Arrays.fill(ppg[idx], Double.NEGATIVE_INFINITY);
		}
		double sum = 0.0;
		for(int outer=0; outer < popSize; outer++) {
			double score = 0;
			@SuppressWarnings("unchecked")
			Chromosome<Double> pc = (Chromosome<Double>) pop.get(outer);
			for(EvaluatorRunnable r : runners) {
				r.setPlayer(pc);
			}

			for(int pre=0; pre < outer; pre++) {
				score += ppg[outer][pre];
			}
			// Add jobs to queue
			for(int inner=outer+1; inner < popSize; inner++) {
				Chromosome<?> oc = pop.get(inner);
				mInputQueue.offer(new Pair<>(inner, oc));
			}
			// Wait for jobs to be processed
			for(int inner=outer+1; inner < popSize; inner++) {
				try {
					Pair<Integer, Double> result = mOutputQueue.take();
					int pos = result.getFirst();
					score += ppg[outer][pos] = result.getSecond();
					ppg[pos][outer] = 0-ppg[outer][pos];
				} catch(InterruptedException e) {
					e.printStackTrace();
					// Ignore
				}
			}
			score = score / popSize;
			sum += score;
			pc.setFitness(score);
			if(score > stat.mMaxFitness) {
				stat.mMaxFitness = score;
				stat.mBest = pc;
			}
		}
		
		// Stop Threads
		for(int idx=0; idx <mNumThreads; idx++) {
			mInputQueue.offer(new Pair<>(-1, null));
		}
		for(Thread t : threads) {
			try {
				t.join();
			} catch(InterruptedException e) {
				e.printStackTrace();
				// Ignore
			}
		}

		stat.mAvgFitness = sum / popSize;
		return stat;
	}

	@Override
	public double getGoalFitness() {
		return 1.0;
	}

	@Override
	public String getConfiguration() {
		return this.getClass().getName()+"[games="+mNumGames+"]";
	}

}
