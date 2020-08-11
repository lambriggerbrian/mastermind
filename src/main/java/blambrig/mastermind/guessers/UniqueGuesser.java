package blambrig.mastermind.guessers;

import blambrig.mastermind.Guess;
import blambrig.mastermind.Guesser;
import blambrig.mastermind.Table;

public class UniqueGuesser extends Guesser {
	
	public UniqueGuesser(Table table) {
		super(table);
	}
	
	@Override
	protected Guess getFirstGuess() {
		return new Guess(colorManager.slice(table.numCols));
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
