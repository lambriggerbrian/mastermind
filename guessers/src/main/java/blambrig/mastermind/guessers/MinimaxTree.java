package blambrig.mastermind.guessers;

import java.util.HashMap;

import blambrig.mastermind.Color;
import blambrig.mastermind.ColorManager;
import blambrig.mastermind.Game;
import blambrig.mastermind.Guess;
import blambrig.mastermind.Table;

public class MinimaxTree {	
	public final MinimaxNode root;
	protected Game simGame;
	protected final ColorManager colorManager;
	protected int maxDepth = 3;
	protected boolean finished = false;
	protected MinimaxNode bestCandidate = MinimaxNode.none;
	private final HashMap<Guess, MinimaxNode> nodes;
	protected final static int NUM_SLICES = 4;

	public MinimaxTree(Game simGame, ColorManager colorManager, int maxDepth) {
		this.simGame = simGame;
		this.colorManager = colorManager;
		this.maxDepth = maxDepth;
		this.root = new MinimaxNode(MinimaxNode.none, Guess.none, 0);
		this.nodes = new HashMap<>();
		this.nodes.put(Guess.none, root);
	}
	
	
	public Guess getNextGuess() {
		if (finished) return Guess.none;
		if (!bestCandidate.equals(MinimaxNode.none)) return bestCandidate.getGuess();
		return Guess.none;
	}
	
	public Guess getNextGuess(Guess guess) {
		if (!nodes.containsKey(guess)) {
			throw new IllegalArgumentException("Guess is not in tree");
		}
		MinimaxNode node = nodes.get(guess);
		
		if (node.children.size() <= 0) {
			return Guess.none;
		}
		while (node.children.size() > 0) {
			node = node.children.peek();
		}
		return node.getGuess();
	}

	public int getValue(Guess guess) {
		if (!nodes.containsKey(guess)) {
			throw new IllegalArgumentException("Guess is not in tree");
		}
		MinimaxNode node = nodes.get(guess);
		return node.value;
	}
	
	public int size() {
		return nodes.size()-1;
	}
	
	public boolean isFinished() {
		return finished;
	}
	
	protected void initTree() {
		Table table = simGame.getTable();
		int cols = table.numCols;
		Color cur = colorManager.firstColor();
		
	}
	
	protected boolean addNode(MinimaxNode parent, Guess guess, Integer value) {
		return addNode(parent.guess, guess, value);
	}

	protected boolean addNode(Guess parent, Guess guess, Integer value) {
		if (nodes.containsKey(guess)) return false;
		if (value == Integer.MAX_VALUE) finished = true;
		MinimaxNode parentNode = nodes.get(parent);
		MinimaxNode guessNode = new MinimaxNode(parentNode, guess, value);
		nodes.put(guess, guessNode);
		parentNode.children.add(guessNode);
		Guess parentGuess = parentNode.getGuess(); 
		while (!parentGuess.equals(Guess.none) && !parentNode.equals(MinimaxNode.none)) {
			parentNode.value = Integer.max(parentNode.value, value);
			parentNode = parentNode.getParent();
			parentGuess = parentNode.getGuess();
		}
		return true;
	}

	protected int minimax(MinimaxNode node) {
		int depth = node.depth;
		return -1;
	}
}
