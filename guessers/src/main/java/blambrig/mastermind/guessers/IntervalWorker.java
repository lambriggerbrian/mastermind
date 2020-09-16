package blambrig.mastermind.guessers;

import java.util.concurrent.BlockingQueue;

import blambrig.mastermind.Game;
import blambrig.mastermind.Guess;
import blambrig.mastermind.Partitioner;
import blambrig.mastermind.SimpleGuesser;

public class IntervalWorker extends SimpleGuesser implements Runnable {
	private Guess lastGuess;
	protected Guess nextGuess;
	private final BlockingQueue<Guess> guessQueue;
	
	public IntervalWorker(Game game, Partitioner partitioner, BlockingQueue<Guess> guessQueue) {
		super(game, partitioner);
		this.guessQueue = guessQueue;
		nextGuess = partition.getStart();
	}

	@Override
	public void run() {
		Thread.currentThread().setName(String.format("Guesser [%s,%s]", partition.getStart(), partition.getEnd()));
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
		if (guess.equals(partition.getEnd())) {
			guess = Guess.none;
		}
		lastGuess = guess;
		return guess;
	}
	
	public String toString() {
		return String.format("[%s,%s]", partition.getStart(), partition.getEnd());
	}
}
