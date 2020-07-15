package blambrig.mastermind.integration;

import static org.junit.Assert.*;

import org.junit.Test;

import blambrig.mastermind.*;

public class IntegrationTest {
	final int numColors = 6;
	final int numCols = 4;
	final ColorManager colorManager = new ColorManager(numColors);
	
	private Color[] createSecret() {
		Color[] secret = new Color[numCols];
		int count = 0;
		Color color = colorManager.firstColor();
		while (count < numColors - numCols) {
			color = colorManager.nextColor(color);
			count++;
		}
		for (int i = 0; i < numCols; i++) {
			secret[i] = color;
			color = colorManager.nextColor(color);
		}
		return secret;
	}
	
	@Test
	public void testSimpleGame() {
		Table table = new Table(numCols, colorManager);
		Color[] secret = createSecret();
		System.out.println(PrettyPrintRow.print(new Row(secret)));
		System.out.println();
		Game game = new Game(table, secret);
		
		Guesser guesser = new UniqueGuesser(table);
		while (!game.isFinished()) {
			Row guess = guesser.guess();
			if (guess == Row.none) {
				fail();
			}
			game.addNewGuess(guess);
			System.out.println(PrettyPrintRow.print(guess));
		}
	}
}
