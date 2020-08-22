package blambrig.mastermind.guessers;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import blambrig.mastermind.Color;
import blambrig.mastermind.Guess;
import blambrig.mastermind.SimpleGuesser;
import blambrig.mastermind.Table;

public class ParallelGuesser extends SimpleGuesser {
	protected final IntervalGuesser[] guessers;
	protected final int QUEUE_SIZE = 500;
	protected final int NUM_THREADS = 4;
	protected final int NUM_COLORS;
	protected final BlockingQueue<Guess> guessQueue = new ArrayBlockingQueue<Guess>(QUEUE_SIZE);
	private ExecutorService executorService;
	
	public ParallelGuesser(Table table) {
		super(table);
		this.NUM_COLORS = colorManager.numColors();
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
	
	protected IntervalGuesser[] createGuessers() {
		final var guessers = new IntervalGuesser[NUM_THREADS];
		Color[] colors = new Color[NUM_COLORS];
		Guess start = firstIntervalStart(colors);
		for (int i = 0; i < NUM_THREADS; i++) {
			Guess end = nextIntervalStart(colors);
			guessers[i] = new IntervalGuesser(this.table, start, end, guessQueue);
			start = end;
		}
		return guessers;
	}
	
	protected Guess firstIntervalStart(Color[] colors) {
		Arrays.fill(colors, colorManager.firstColor());
		return new Guess(colors);
	}
	
	protected Guess nextIntervalStart (Color[] colors) {
		final int index = colors.length - 1;
		int step = NUM_COLORS / NUM_THREADS;
		if (step == 0) {
			step = 1;
		}
		while (step > 0) {
			if (colorManager.isThereNextColor(colors[index])) {
				Arrays.fill(colors, colorManager.nextColor(colors[index]));
				step--;
			} else {
				return Guess.none;
			}
		}
		Guess guess = new Guess(colors);
		return guess;
	}
	
	private void startAsyncGuessers(IntervalGuesser[] guessers) {
		executorService = Executors.newFixedThreadPool(NUM_THREADS);
		for (IntervalGuesser guesser : guessers) {
			executorService.execute(guesser);
		}
	}
	
	private void stopAsyncGuessers(IntervalGuesser[] guessers) {
		executorService.shutdown();
		guessQueue.drainTo(new LinkedList<>());
	}
}
