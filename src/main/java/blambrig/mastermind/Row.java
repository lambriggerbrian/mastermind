package blambrig.mastermind;

import java.util.Arrays;

public class Row {
	protected final Color[] positions;
	protected int matchedPositions;
	protected int matchedColors;
	
	public static final Row none = new Row(new Color[0]);
	
	public Row(Color[] positions) {
		this.positions = Arrays.copyOf(positions, positions.length);
	}
	
	protected Row(Row source) {
		this(source.positions);
		setMatch(source.matchedPositions, source.matchedColors);
	}
	
	public void setMatch(int matchedPositions, int matchedColors) {
		if (matchedColors + matchedPositions > this.positions.length) {
			throw new IllegalArgumentException ("Number of matches cannot exceed positions");
		}
		this.matchedColors = matchedColors;
		this.matchedPositions = matchedPositions;
	}
	
	public boolean isMatch(Color[] guess) {
		return numMatchingColors(guess) == matchedColors &&  numMatchingPositions(guess) == matchedPositions;
	}
	
	public boolean isNotMatch(Color[] guess) {
		return !isMatch(guess);
	}
	
	public int numMatchingColors(Color[] guess) {
		if (guess.length != positions.length) {
			throw new IllegalArgumentException ("Guess length not equal to Row length");
		}
		int count = 0;
		for (int i=0; i < guess.length; i++) {
			for (int j=0; j < positions.length; j++) {
				if (i != j && guess[i] == positions[j]) {
					count++;
				}
			}
		}
		return count;
	}
	
	public int numMatchingPositions(Color[] guess) {
		if (guess.length != positions.length) {
			throw new IllegalArgumentException ("Guess length not equal to Row length");
		}
		int count = 0;
		for (int i=0; i < guess.length; i++) {
			if (guess[i] == positions[i]) {
				count++;
			}
		}
		return count;
	}
	
	public int numCols() {
		return positions.length;
	}
	
	public int matchedPositions() {
		return matchedPositions;
	}
	
	public int matchedColors() {
		return matchedColors;
	}
}
