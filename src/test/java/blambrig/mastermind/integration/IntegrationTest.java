package blambrig.mastermind.integration;

import static org.junit.Assert.*;

import org.junit.Test;

import blambrig.mastermind.*;

public class IntegrationTest {
	final int numColors = 6;
	final int numCols = 4;
	final ColorManager colorManager = new ColorManager(numColors, new LetteredColorFactory());
	
	private Color[] createSecret() {
		return colorManager.reverseSlice(numCols);
	}
	
	@Test
	public void testSimpleGame() {
		Table table = new Table(numCols, colorManager);
		Color[] secret = createSecret();
		Game game = new Game(table, secret);
		System.out.println(game.secretToString());
		
		Guesser guesser = new Guesser(table);
		Player player = new SimpleGamePlayer(game, guesser);
		while (!game.isFinished()) {
			player.play();
		}
	}
}
