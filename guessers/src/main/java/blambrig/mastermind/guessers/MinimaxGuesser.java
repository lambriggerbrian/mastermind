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
	
	@Override
	public Guess guess() {
		if (tree.isFinished()) return Guess.none;
		Guess guess = tree.getNextGuess();
		return guess;
	}
	
	@Override
	protected Guess getFirstGuess() {
		return guess();
	}
	
	@Override
	protected Guess nextGuess() {
		return guess();
	}
}
