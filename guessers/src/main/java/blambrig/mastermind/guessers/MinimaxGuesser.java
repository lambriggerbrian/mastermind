package blambrig.mastermind.guessers;

import java.util.Arrays;

import blambrig.mastermind.Color;
import blambrig.mastermind.Game;
import blambrig.mastermind.Guess;
import blambrig.mastermind.Partitioner;
import blambrig.mastermind.SimpleGuesser;

public class MinimaxGuesser extends SimpleGuesser {
	public final int MAX_LOOKAHEAD = 3;	
	protected final Game simGame;
	protected final MinimaxTree tree;
	
	public MinimaxGuesser(Game game, Partitioner partitioner) {
		super(game, partitioner);
		this.simGame = game;
		this.tree = new MinimaxTree(game, colorManager, table.numCols);
	}
	
	public void initTree() {
		int cols = table.numCols;
		Color cur = colorManager.firstColor();
		while (!cur.equals(Color.none)) {
			Color[] colors = new Color[cols];
			Arrays.fill(colors, colorManager.firstColor());
			colors[0] = cur;
			Guess guess = new Guess(colors);
			int value = game.getGuessValue(guess);
			tree.addNode(tree.root, guess, value);
		}
	}
	
	@Override
	public Guess guess() {
		if (lastGuess.equals(Guess.none) && !tree.isFinished()) initTree();
		Guess guess = tree.getNextGuess();
		return null;
	}
}
