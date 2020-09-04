package blambrig.mastermind.guessers;

import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import blambrig.mastermind.Guess;
import blambrig.mastermind.Partitioner;
import blambrig.mastermind.SimpleGuesser;
import blambrig.mastermind.Table;

public class ParallelGuesser extends SimpleGuesser {
	protected final int QUEUE_SIZE = 500;
	protected final int numThreads;
	protected final IntervalWorker[] guessers;
	protected final BlockingQueue<Guess> guessQueue = new ArrayBlockingQueue<Guess>(QUEUE_SIZE);
	private ExecutorService executorService;
	
	public ParallelGuesser(Table table, Partitioner partitioner, Integer numThreads) {
		super(table, partitioner);
		this.numThreads = numThreads;
		this.guessers = createGuessers();
		startAsyncGuessers(guessers);
	}
	
	public int getCurNumGuesses() {
		return guessQueue.size();
	}
	
	@Override
	public void shutdown() {
		stopAsyncGuessers(guessers);
	}
	
	@Override
	protected Guess getFirstGuess() {
		return nextGuess();
	}
	
	@Override
	protected Guess nextGuess() {
		Guess guess = Guess.none;
		try {
			guess = guessQueue.poll(5, TimeUnit.SECONDS);
		} catch (InterruptedException ignored) {
			
		}
		return guess;
	}
	
	protected IntervalWorker[] createGuessers() {
		final var guessers = new IntervalWorker[numThreads];
		for (int i = 0; i < numThreads; i++) {
			guessers[i] = new IntervalWorker(table, partitioner, guessQueue);
		}
		return guessers;
	}

	private void startAsyncGuessers(IntervalWorker[] guessers) {
		executorService = Executors.newFixedThreadPool(numThreads);
		for (IntervalWorker guesser : guessers) {
			executorService.execute(guesser);
		}
	}
	
	private void stopAsyncGuessers(IntervalWorker[] guessers) {
		executorService.shutdown();
		guessQueue.drainTo(new LinkedList<>());
	}
}
