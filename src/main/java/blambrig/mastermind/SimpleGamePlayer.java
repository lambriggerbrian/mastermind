package blambrig.mastermind;

public class SimpleGamePlayer implements Player{
	private final Game game;
	private final Guesser guesser;
	
	public SimpleGamePlayer(Game game, Guesser guesser) {
		this.game = game;
		this.guesser = guesser;
	}
	
	@Override
	public void play() {
		Guess guess = guesser.nextGuess();
		Row row = game.addNewGuess(guess);
		System.out.println(row.guess);
	}
}
