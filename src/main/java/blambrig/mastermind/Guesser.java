package blambrig.mastermind;

import java.util.Arrays;

public class Guesser {
	protected final Table table;
	protected final ColorManager colorManager;
	protected Guess lastGuess = Guess.none;
	
	public Guesser(Table table) {
		this.table = table;
		this.colorManager = table.colorManager;
	}
	
	public Guess guess() throws Exception {
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
	
	protected Guess getFirstGuess() {
		//Color[] colors = colorManager.slice(table.numCols);
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
