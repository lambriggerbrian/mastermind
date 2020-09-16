package blambrig.mastermind.guessers;

import org.junit.Test;

import blambrig.mastermind.Color;
import blambrig.mastermind.ColorManager;
import blambrig.mastermind.Game;
import blambrig.mastermind.Guess;
import blambrig.mastermind.LetteredColorFactory;
import blambrig.mastermind.Table;

public class MinimaxTreeIntegrationTest {
	int NUM_COLORS = 4;
	int NUM_COLUMNS = 4;
	ColorManager colorManager = new ColorManager(NUM_COLORS, new LetteredColorFactory());
	
	private Color[] createSecret() {
		return colorManager.fill(NUM_COLUMNS, NUM_COLORS-1);
	}
	
	private Game createGame() {
		Table table = new Table(NUM_COLUMNS, colorManager);
		Color[] secret = createSecret();
		Game game = new Game(table, secret);
		System.out.println(String.format("Secret is: %s", game.secretToString()));
		return game;
	}
			
	@Test
	public void treeAddTest() {
		MinimaxTree tree = new MinimaxTree(Game.none, colorManager, NUM_COLUMNS);
		assert(tree.getNextGuess(Guess.none).equals(Guess.none));
		Guess guess1 = new Guess(colorManager.slice(4));
		Guess guess2 = new Guess(colorManager.reverseSlice(4));
		Guess guess3 = new Guess(colorManager.fill(4, 1));
		tree.addNode(Guess.none, guess1, 1);
		assert(tree.getNextGuess(Guess.none).equals(guess1));
		tree.addNode(Guess.none, guess2, 2);
		assert(tree.getNextGuess(Guess.none).equals(guess2));
		assert(tree.getNextGuess(guess1).equals(Guess.none));
		tree.addNode(guess1, guess3, 3);
		assert(tree.getNextGuess(guess1).equals(guess3));
	}
	
	@Test
	public void treePropagateTest() {
		MinimaxTree tree = new MinimaxTree(Game.none, colorManager, NUM_COLUMNS);
		Guess guess1 = new Guess(colorManager.reverseSlice(4));
		Guess guess2 = new Guess(colorManager.slice(4));
		Guess guess3 = new Guess(colorManager.fill(4, 1));
		tree.addNode(Guess.none, guess1, 1);
		assert(tree.getValue(guess1) == 1);
		tree.addNode(guess1, guess2, 2);
		assert(tree.getValue(guess1) == 2);
		tree.addNode(guess2, guess3, 3);
		assert(tree.getValue(guess1) == 3);
	}
	
	@Test
	public void testTreeGrow() {
		System.out.println(MinimaxNode.none);
		Game game = createGame();
		MinimaxTree tree = new MinimaxTree(game, colorManager, NUM_COLUMNS);
		Guess guess = tree.getNextGuess();
		Guess secret = new Guess(colorManager.fill(NUM_COLUMNS, NUM_COLORS-1));
		assert(guess.equals(secret));
	}
}
