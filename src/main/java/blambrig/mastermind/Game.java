package blambrig.mastermind;

public class Game {
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
		table.addRow(row);
		if (matchedPositions == numCols) {
			finished = true;
		}
		return row;
	}
	
	public String secretToString() {
		return secretGuess.toString();
	}
	
	public boolean isFinished() {
		return finished;
	}
}
