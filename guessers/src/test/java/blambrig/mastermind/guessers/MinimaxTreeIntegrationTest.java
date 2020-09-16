package blambrig.mastermind.guessers;

import org.junit.Test;

import blambrig.mastermind.ColorManager;
import blambrig.mastermind.Game;
import blambrig.mastermind.Guess;
import blambrig.mastermind.LetteredColorFactory;

public class MinimaxTreeIntegrationTest {
	int NUM_COLORS = 4;
	ColorManager colorManager = new ColorManager(NUM_COLORS, new LetteredColorFactory());
	
	@Test
	public void treeAddTest() {
		MinimaxTree tree = new MinimaxTree(Game.none, colorManager, 3);
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
		MinimaxTree tree = new MinimaxTree(Game.none, colorManager, 3);
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

}
