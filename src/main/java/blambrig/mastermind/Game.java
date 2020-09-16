package blambrig.mastermind;

public class Game {
	public final static Game none = new Game(null, null);
	private final Table table;
	private final Guess secretGuess;
	private boolean finished = false;
	final int numCols;
	
	public Game(Table table, Color[] secret) {
		this.table = table;
		this.secretGuess = new Guess(secret);
		this.numCols = secretGuess.numCols();
	}
	
	public Row addNewGuess(Guess guess) {
		if (isFinished()) {
			throw new IllegalArgumentException("Game is finished. No more guesses allowed");
		}
		final int matchedPositions = secretGuess.numMatchingPositions(guess);
		final int matchedColors = secretGuess.numMatchingColors(guess);
		Row row = new Row(guess, matchedPositions, matchedColors);
		if (!table.addRow(row)) {
				return Row.none;
		}
		if (matchedPositions == numCols) {
			finished = true;
		}
		return row;
	}
	
	public int getGuessValue(Guess guess) {
		final int matchedPositions = secretGuess.numMatchingPositions(guess);
		final int matchedColors = secretGuess.numMatchingColors(guess);
		if (matchedPositions == numCols) return Integer.MAX_VALUE;
		return matchedPositions + matchedColors;
	}
	
	public String secretToString() {
		return secretGuess.toString();
	}
	
	public Table getTable() {
		return this.table;
	}
	
	public boolean isFinished() {
		return finished;
	}
}
