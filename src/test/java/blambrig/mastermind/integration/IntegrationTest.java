package blambrig.mastermind.integration;

import static org.junit.Assert.*;

import org.junit.Test;

import blambrig.mastermind.*;
import blambrig.mastermind.guessers.UniqueGuesser;

public class IntegrationTest {
	final int numColors = 6;
	final int numCols = 4;
	final ColorManager colorManager = new ColorManager(numColors, new LetteredColorFactory());
	
	private Color[] createSecret() {
		return colorManager.reverseSlice(numCols);
	}
	
	private Game createGame() {
		Table table = new Table(numCols, colorManager);
		Color[] secret = createSecret();
		Game game = new Game(table, secret);
		System.out.println(String.format("Secret is: %s", game.secretToString()));
		return game;
	}
	
	@Test
	public void testSimpleGame() {
		Game game = createGame();
		SimpleGuesser guesser = new SimpleGuesser(game.getTable());
		Player player = new SimpleGamePlayer(game, guesser);
		player.play();
		while (!game.isFinished()) {
			player.play();
		}
		assert(game.isFinished());
	}
	
	@Test
	public void testUniqueGame() {
		Game game = createGame();
		SimpleGuesser guesser = new UniqueGuesser(game.getTable());
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
		Player player = new ParallelGamePlayer(game);
		final int MAX_TRIES = 2000;
		int count = 0;
		while (!game.isFinished() && count < MAX_TRIES) {
			count++;
			player.play();
		}
		assert(game.isFinished());
	}
}
