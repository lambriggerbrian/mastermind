package blambrig.mastermind.guessers;

import org.junit.Test;

import blambrig.mastermind.Color;
import blambrig.mastermind.ColorManager;
import blambrig.mastermind.Game;
import blambrig.mastermind.LetteredColorFactory;
import blambrig.mastermind.ParallelGamePlayer;
import blambrig.mastermind.Partitioner;
import blambrig.mastermind.Player;
import blambrig.mastermind.SimpleGamePlayer;
import blambrig.mastermind.SimpleGuesser;
import blambrig.mastermind.SimplePartitioner;
import blambrig.mastermind.Table;

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
	public void testUniqueGame() {
		Game game = createGame();
		SimpleGuesser guesser = new UniqueGuesser(game, new SimplePartitioner(colorManager, NUM_COLUMNS));
		Player player = new SimpleGamePlayer(game, guesser);
		player.play();
		while (!game.isFinished()) {
			player.play();
		}
		assert(game.isFinished());
	}
	
	@Test
	public void testSimpleParallel() {
		Game game = createGame();
		Integer NUM_THREADS = 4;
		Partitioner partitioner = new ParallelPartitioner(colorManager, NUM_COLUMNS, NUM_THREADS);
		Player player = new ParallelGamePlayer(game, new ParallelGuesser(game, partitioner, NUM_THREADS));
		final int MAX_TRIES = 2000;
		int count = 0;
		while (!game.isFinished() && count < MAX_TRIES) {
			count++;
			player.play();
		}
		assert(game.isFinished());
	}
}
