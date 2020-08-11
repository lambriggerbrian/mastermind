package blambrig.mastermind;

public class Guesser {
	protected final Table table;
	protected final ColorManager colorManager;
	protected Guess lastGuess = Guess.none;
	
	public Guesser(Table table) {
		this.table = table;
		this.colorManager = table.colorManager;
	}
	
	public Guess guess() {
		Guess guess = nextGuess();
		while (!guess.equals(Guess.none) && notGuessedBefore(guess)) {
			guess = nextGuess();
		}
		if (guess.equals(Guess.none)) {
			return Guess.none;
		} else {
			return guess;
		}
	}
	
	protected Guess getFirstGuess() {
		Color[] colors = colorManager.slice(table.numCols);
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
			return newGuess;
		}
	}
	
	private boolean guessedBefore(Guess guess) {
		for (Row row : table.rows) {
			if (row.guess.equals(guess)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean notGuessedBefore(Guess guess) {
		return !guessedBefore(guess);
	}
}
