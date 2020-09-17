package blambrig.mastermind;

public class SimpleGamePlayer implements Player {
	protected final Game game;
	protected final Guesser guesser;
	protected int guesses = 0;
	protected boolean isVerbose = false;
	
	public SimpleGamePlayer(Game game, Guesser guesser) {
		this.game = game;
		this.guesser = guesser;
	}
	
	public SimpleGamePlayer(Game game, Guesser guesser, boolean isVerbose) {
		this(game, guesser);
		this.isVerbose = isVerbose;
	}
	
	@Override
	public void play() {
		var guess = Guess.none;
		var row = Row.none;
		while (guess.equals(Guess.none) || row.equals(Row.none)) {
			guess = guesser.guess();	
			row = game.addNewGuess(guess);
		}
		guesses++;
		if (isVerbose) {
			System.out.println(row.toString());
		}
		if (game.isFinished()) {
			System.out.println(
					String.format("Secret = Guess! Game finished in %d guesses.", guesses));
			shutdown();
		}
	}

	@Override
	public void shutdown() {
	}
}
