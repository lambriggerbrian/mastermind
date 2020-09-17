package blambrig.mastermind.guessers;

import org.junit.Test;

import blambrig.mastermind.Color;
import blambrig.mastermind.ColorManager;
import blambrig.mastermind.Game;
import blambrig.mastermind.Guess;
import blambrig.mastermind.LetteredColorFactory;
import blambrig.mastermind.SimpleGamePlayer;
import blambrig.mastermind.SimplePartitioner;
import blambrig.mastermind.Table;

public class MinimaxTreeIntegrationTest {
	final int NUM_COLORS = 6;
	final int NUM_COLUMNS = 4;
	final ColorManager colorManager = new ColorManager(NUM_COLORS, new LetteredColorFactory());
	
	private Color[] createSecret() {
		return colorManager.reverseSlice(NUM_COLUMNS);
	}
	
	private Game createGame(Color[] secret) {
		Table table = new Table(NUM_COLUMNS, colorManager);
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
		Color[] secret = colorManager.fill(NUM_COLUMNS, NUM_COLORS-1);
		Game game = createGame(secret);
		MinimaxTree tree = new MinimaxTree(game, colorManager, NUM_COLUMNS);
		Guess guess = tree.getNextGuess();
		assert(!guess.equals(Guess.none));
	}
	
	@Test
	public void testTreeGame() {
		Game game = createGame(createSecret());
		MinimaxGuesser guesser = new MinimaxGuesser(game, new SimplePartitioner(colorManager, NUM_COLUMNS));
		SimpleGamePlayer player = new SimpleGamePlayer(game, guesser, true);
		final int MAX_TRIES = 2000;
		int count = 0;
		while (!game.isFinished() && count < MAX_TRIES) {
			count++;
			player.play();
		}
		assert(game.isFinished());
	}
}
