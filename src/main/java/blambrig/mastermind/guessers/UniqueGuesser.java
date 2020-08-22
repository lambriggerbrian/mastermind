package blambrig.mastermind.guessers;

import blambrig.mastermind.Guess;
import blambrig.mastermind.SimpleGuesser;
import blambrig.mastermind.Table;

public class UniqueGuesser extends SimpleGuesser {
	
	public UniqueGuesser(Table table) {
		super(table);
	}
	
	@Override
	protected Guess nextGuess() {
		Guess guess = super.nextGuess();
		while (!guess.isUnique()) {
			guess = super.nextGuess();
		}
		return guess;
	}
}
