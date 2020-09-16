package blambrig.mastermind.integration;

import org.junit.Test;

import blambrig.mastermind.*;

public class IntegrationTest {
	final int NUM_COLORS = 6;
	final int NUM_COLUMNS = 4;
	final ColorManager colorManager = new ColorManager(NUM_COLORS, new LetteredColorFactory());
	
	private Color[] createSecret() {
		return colorManager.reverseSlice(NUM_COLUMNS);
	}
	
	private Game createGame() {
		Table table = new Table(NUM_COLUMNS, colorManager);
		Color[] secret = createSecret();
		Game game = new Game(table, secret);
		System.out.println(String.format("Secret is: %s", game.secretToString()));
		return game;
	}
	
	@Test
	public void testSimpleGame() {
		Game game = createGame();
		Partitioner partitioner = new SimplePartitioner(colorManager, NUM_COLUMNS);
		SimpleGuesser guesser = new SimpleGuesser(game, partitioner);
		Player player = new SimpleGamePlayer(game, guesser);
		player.play();
		while (!game.isFinished()) {
			player.play();
		}
		assert(game.isFinished());
	}
}
