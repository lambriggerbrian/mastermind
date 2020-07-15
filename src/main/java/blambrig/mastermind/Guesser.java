package blambrig.mastermind;

public abstract class Guesser {
	protected final Table table;
	private final ColorManager colorManager;
	protected final Color[] lastGuess;
	public static final Color[] none = new Color[] {Color.none};
	
	public Guesser(Table table) {
		this.table = table;
		this.lastGuess = new Color[table.numCols];
		this.colorManager = table.colorManager;
	}
	
	public Row guess() {
		Color[] guess = nextGuess();
		while (guess != none && !notGuessedBefore(guess)) {
			guess = nextGuess();
		}
		if (guess == none) {
			return Row.none;
		} else {
			return new Row(guess);
		}
	}
	
	abstract protected void setFirstGuess();
	
	protected Color[] nextGuess() {
		if (lastGuess[0] == null) {
			setFirstGuess();
			return lastGuess;
		} else {
			return nextNonFirstGuess();
		}
	}
	
	private Color[] nextNonFirstGuess() {
		int i = 0;
		boolean guessFound = false;
		while (i < table.numCols && !guessFound) {
			if (colorManager.isThereNextColor(lastGuess[i])) {
				lastGuess[i] = colorManager.nextColor(lastGuess[i]);
				guessFound = true;
			} else {
				lastGuess[i] = colorManager.firstColor();
				i++;
			}
		}
		if (guessFound) {
			return lastGuess;
		} else {
			return none;
		}
	}
	
	private boolean guessedBefore(Color[] guess) {
		for (Row row : table.rows) {
			if (!row.isMatch(guess)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean notGuessedBefore(Color[] guess) {
		return !guessedBefore(guess);
	}
}
