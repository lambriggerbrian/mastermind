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
		System.out.println(game.secretToString());
		return game;
	}
	
	@Test
	public void testSimpleGame() {
		Game game = createGame();
		Guesser guesser = new Guesser(game.getTable());
		Player player = new SimpleGamePlayer(game, guesser);
		player.play();
		while (!game.isFinished()) {
			player.play();
		}
	}
	
	@Test
	public void testUniqueGame() {
		Game game = createGame();
		Guesser guesser = new UniqueGuesser(game.getTable());
		Player player = new SimpleGamePlayer(game, guesser);
		player.play();
		while (!game.isFinished()) {
			player.play();
		}
	}
}
