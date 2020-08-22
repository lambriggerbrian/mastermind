package blambrig.mastermind;

import java.util.Arrays;

public class SimpleGuesser implements Guesser {
	protected final Table table;
	protected final ColorManager colorManager;
	protected Guess lastGuess = Guess.none;
	
	public SimpleGuesser(Table table) {
		this.table = table;
		this.colorManager = table.colorManager;
	}
	
	public Guess guess() {
		Guess guess = nextGuess();
		while (!guess.equals(Guess.none) && guessedBefore(guess)) {
			guess = nextGuess();
		}
		if (guess.equals(Guess.none)) {
			return Guess.none;
		} else {
			return guess;
		}
	}
	
	public void shutdown() {}
	
	protected Guess getFirstGuess() {
		Color[] colors = new Color[table.numCols];
		Arrays.fill(colors, colorManager.firstColor());
		return new Guess(colors);
	}
	
	protected Guess nextGuess() {
		if (lastGuess.equals(Guess.none)) {
			lastGuess = getFirstGuess();
			return lastGuess;
		} else {
			return nextNonFirstGuess();
		}
	}
	
	private Guess nextNonFirstGuess() {
		Guess newGuess = lastGuess.nextGuess(colorManager);
		if (newGuess.equals(Guess.none)) {
			return Guess.none;
		} else {
			lastGuess = newGuess;
			return newGuess;
		}
	}
	
	private boolean guessedBefore(Guess guess) {
		if (table.guesses.contains(guess)) {
			return true;
		}
		return false;
	}
}
