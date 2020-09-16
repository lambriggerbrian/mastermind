package blambrig.mastermind.guessers;

import blambrig.mastermind.Game;
import blambrig.mastermind.Guess;
import blambrig.mastermind.Partitioner;
import blambrig.mastermind.SimpleGuesser;

public class UniqueGuesser extends SimpleGuesser {
	
	public UniqueGuesser(Game game, Partitioner partitioner) {
		super(game, partitioner);
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
