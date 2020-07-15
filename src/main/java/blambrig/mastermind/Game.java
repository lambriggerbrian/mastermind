package blambrig.mastermind;

public class Game {
	final Table table;
	final private Row secretRow;
	private boolean finished = false;
	final int numCols;
	
	public Game(Table table, Color[] secret) {
		this.table = table;
		this.secretRow = new Row(secret);
		this.numCols = secretRow.numCols();
	}
	
	public void addNewGuess(Row row) {
		if (isFinished()) {
			throw new IllegalArgumentException("Game is finished. No more guesses allowed");
		}
		final int matchedPositions = secretRow.numMatchingPositions(row.positions);
		final int matchedColors = secretRow.numMatchingColors(row.positions);
		row.setMatch(matchedPositions, matchedColors);
		table.addRow(row);
		if (matchedPositions == numCols) {
			finished = true;
		}
	}
	
	public boolean isFinished() {
		return finished;
	}
}
