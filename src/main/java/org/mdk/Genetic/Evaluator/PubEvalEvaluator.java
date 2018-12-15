package org.mdk.Genetic.Evaluator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.mdk.BoardGame.SessionResult;
import org.mdk.BoardGame.Backgammon.Player.MDKEvalPlayer;
import org.mdk.BoardGame.Backgammon.Session.PubEvalSession;
import org.mdk.Genetic.Chromosome.Chromosome;
import org.mdk.Genetic.Population.Population;
import org.mdk.commons.Pair;

public class PubEvalEvaluator extends Evaluator {
	private PubEvalSession mSession;
	private int mNumGames;
	private LinkedBlockingQueue<Pair<Integer, Chromosome<?>>> mInputQueue;
	private LinkedBlockingQueue<Pair<Integer, Double>> mOutputQueue;
	private int mNumThreads;

	
	public PubEvalEvaluator(int numGames) {
		mSession = new PubEvalSession(false);
		mNumGames = numGames;
		mInputQueue = new LinkedBlockingQueue<>();
		mOutputQueue = new LinkedBlockingQueue<>();
		mNumThreads = 4;		
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
					Pair<Integer,Chromosome<?>> elem;
					elem = mInputQueue.poll(2000, TimeUnit.MILLISECONDS);
					if(elem!=null) {
						if(elem.getFirst()==-1) {
							mTerminate = true;
							continue;
						}
						@SuppressWarnings("unchecked")
						MDKEvalPlayer p = new MDKEvalPlayer((List<Double>)elem.getSecond().getValues());
						SessionResult res = mSession.play(p, mNumGames);
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
		int popSize = pop.size();
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

		// Add jobs to queue
		for(int idx=0; idx < popSize; idx++) {
			Chromosome<?> oc = pop.get(idx);
			mInputQueue.offer(new Pair<>(idx, oc));
		}

		// Wait for jobs to be processed
		Statistics stat = new Statistics();
		double sum = 0;
		for(int inner=0; inner < popSize; inner++) {
			try {
				Pair<Integer, Double> result = mOutputQueue.take();
				int pos = result.getFirst();
				double score = result.getSecond();
				pop.get(pos).setFitness(score);
				sum+=score;
				if(score > stat.mMaxFitness) {
					stat.mMaxFitness = score;
					stat.mBest = pop.get(pos);
				}
			} catch(InterruptedException e) {
				e.printStackTrace();
				// Ignore
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

		
		stat.mAvgFitness = sum / pop.size();
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
