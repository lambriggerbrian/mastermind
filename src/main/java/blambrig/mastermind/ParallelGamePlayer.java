package blambrig.mastermind;

public class ParallelGamePlayer extends SimpleGamePlayer {
	
	public ParallelGamePlayer(Game game, Guesser guesser) {
		super(game, guesser);
	}
	
	@Override
	public void shutdown() {
		guesser.shutdown();
	}
}
