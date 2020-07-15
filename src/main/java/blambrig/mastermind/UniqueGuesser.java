package blambrig.mastermind;

import java.util.HashSet;

public class UniqueGuesser extends Guesser {
	
	public UniqueGuesser(Table table) {
		super(table);
	}
	
	@Override
	protected void setFirstGuess() {
		int i = lastGuess.length - 1;
		for (var color = table.colorManager.firstColor(); i >= 0; color = table.colorManager.nextColor(color)) {
			lastGuess[i--] = color;
		}
	}
	
	@Override
	protected Color[] nextGuess() {
		Color[] guess = super.nextGuess();
		while (isNotUnique(guess)) {
			guess = super.nextGuess();
		}
		return guess;
	}
	
	private boolean isNotUnique(Color[] guess) {
		final var alreadyPresent = new HashSet<Color>();
		for (final var color : guess) {
			if (alreadyPresent.contains(color)) {
				return true;
			}
			alreadyPresent.add(color);
		}
		return false;
	}
}
