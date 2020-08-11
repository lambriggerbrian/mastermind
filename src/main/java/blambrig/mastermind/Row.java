package blambrig.mastermind;

public class Row {
	public final Guess guess;
	protected final int matchedPositions;
	protected final int matchedColors;
	
	public static final Row none = new Row(Guess.none, -1, -1);
	
	public Row(Guess guess, int matchedPositions, int matchedColors) {
		this.guess = guess;
		this.matchedPositions = matchedPositions;
		this.matchedColors = matchedColors;
	}
}
