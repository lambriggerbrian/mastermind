package blambrig.mastermind;

import java.util.Arrays;
import java.util.HashSet;

public class Guess {
	public final static Guess none = new Guess(new Color[0]);
	private final Color[] colors;
	private boolean uniquenessCalculated = false;
	private boolean unique;
	
	public Guess(Color[] colors) {
		if (colors == null || colors.length < 1) this.colors = new Color[] {Color.none};
		else this.colors = Arrays.copyOf(colors, colors.length);
	}
	
	public Color getColor(int i) {
		return colors[i];
	}
	
	public Color[] getColors() {
		return colors;
	}
	
	public int numCols() {
		return colors.length;
	}
	
	public Guess nextGuess(ColorManager colorManager) {
		final var newcolors = Arrays.copyOf(this.colors, numCols());
		
		int i = 0;
		var guessFound = false;
		while (i < newcolors.length && !guessFound) {
			if (colorManager.isThereNextColor(getColor(i))) {
				newcolors[i] = colorManager.nextColor(newcolors[i]);
				guessFound = true;
			} else {
				newcolors[i] = colorManager.firstColor();
				i++;
			}
		}
		if (guessFound) {
			return new Guess(newcolors);
		} else {
			return Guess.none;
		}
	}
	
	public int numMatchingColors(Guess guess) {
		assertCompatibility(guess);
		int count = 0;
		for (int i = 0; i < numCols(); i++) {
			for (int j = 0; j < numCols(); j++) {
				if (i != j && guess.getColor(i) == this.getColor(j)) {
					count++;
				}
			}
		}
		return count;
	}
	
	public int numMatchingPositions(Guess guess) {
		assertCompatibility(guess);
		int count = 0;
		for (int i = 0; i < numCols(); i++) {
			if (guess.getColor(i) == this.getColor(i)) {
				count++;
			}
		}
		return count;
	}
	
	public boolean isUnique() {
		if (!uniquenessCalculated) {
			final var alreadyPresent = new HashSet<Color>();
			unique = true;
			for (final var color : colors) {
				if (alreadyPresent.contains(color)) {
					unique = false;
					break;
				}
				alreadyPresent.add(color);
			}
			uniquenessCalculated = true;
		}
		return unique;
	}
	
	private void assertCompatibility(Guess guess) {
		if (guess.equals(Guess.none)) {
			throw new IllegalArgumentException("Guess cannot be none");
		}
		if (numCols() != guess.numCols()) {
			throw new IllegalArgumentException("Cannot compare guesses of different lengths");
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || !(obj instanceof Guess))
			return false;
		Guess other = (Guess) obj;
		return Arrays.equals(colors, other.colors);
	}
	
	@Override
	public int hashCode() {
		return Arrays.hashCode(colors);
	}
	
	@Override
	public String toString() {
		if (this == none) {
			return "none";
		} else {
			String s = "";
			for (int i = 0; i < colors.length; i++) {
				s += colors[i];
			}
			return s;
		}
	}
}
