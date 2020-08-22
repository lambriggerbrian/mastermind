package blambrig.mastermind;

import blambrig.mastermind.guessers.ParallelGuesser;

public class ParallelGamePlayer extends SimpleGamePlayer {
	public ParallelGamePlayer(Game game) {
		super(game, new ParallelGuesser(game.getTable()));
	}
	
	@Override
	public void shutdown() {
		guesser.shutdown();
	}
}
