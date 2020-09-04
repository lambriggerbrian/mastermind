package blambrig.mastermind.guessers;

import blambrig.mastermind.Guess;
import blambrig.mastermind.Partitioner;
import blambrig.mastermind.SimpleGuesser;
import blambrig.mastermind.Table;

public class UniqueGuesser extends SimpleGuesser {
	
	public UniqueGuesser(Table table, Partitioner partitioner) {
		super(table, partitioner);
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
