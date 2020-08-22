package blambrig.mastermind.guessers;

import java.util.concurrent.BlockingQueue;

import blambrig.mastermind.Guess;
import blambrig.mastermind.SimpleGuesser;
import blambrig.mastermind.Table;

public class IntervalGuesser extends SimpleGuesser implements Runnable {
	private final Guess start;
	private final Guess end;
	
	private Guess lastGuess;
	protected Guess nextGuess;
	private final BlockingQueue<Guess> guessQueue;
	
	public IntervalGuesser(Table table, Guess start, Guess end, BlockingQueue<Guess> guessQueue) {
		super(table);
		this.start = start;
		this.end = end;
		this.guessQueue = guessQueue;
		nextGuess = start;
	}

	@Override
	public void run() {
		Thread.currentThread().setName(String.format("Guesser [%s,%s]", start, end));
		var guess = guess();
		try {
			while(guess != Guess.none) {
				guessQueue.put(guess);
				guess = guess();
			}
		} catch (InterruptedException ignored) {}
	}
	
	@Override
	protected Guess nextGuess() {
		var guess = super.nextGuess();
		if (guess.equals(end)) {
			guess = Guess.none;
		}
		lastGuess = guess;
		return guess;
	}
	
	public String toString() {
		return String.format("[%s,%s]", start, end);
	}
}
