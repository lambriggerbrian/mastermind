package blambrig.mastermind.guessers;

import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Queue;

import blambrig.mastermind.Color;
import blambrig.mastermind.ColorManager;
import blambrig.mastermind.Game;
import blambrig.mastermind.Guess;

public class MinimaxTree {	
	public final MinimaxNode root;
	protected final static int NUM_SLICES = 4;
	protected final static int MAX_DEPTH = 4;
	protected Game simGame;
	protected final ColorManager colorManager;
	protected boolean finished = false;
	protected MinimaxNode bestCandidate = MinimaxNode.none;
	protected final int numPossiblities;
	protected final int stepSize;
	protected final int numCols;
	private final HashMap<Guess, MinimaxNode> nodes;
	private final Queue<MinimaxNode> leafNodes;

	public MinimaxTree(Game simGame, ColorManager colorManager, int numCols) {
		this.simGame = simGame;
		this.colorManager = colorManager;
		this.numCols = numCols;
		this.numPossiblities = colorManager.getSpaceSize(this.numCols);
		this.stepSize = ((numPossiblities / NUM_SLICES) > 1) ? numPossiblities / NUM_SLICES : 1;
		this.root = new MinimaxNode(MinimaxNode.none, Guess.none, 0, -1);
		this.nodes = new HashMap<>();
		this.nodes.put(Guess.none, root);
		this.leafNodes = new PriorityQueue<>();
		this.leafNodes.add(root);
	}
	
	
	public Guess getNextGuess() {
		if (finished) return Guess.none;
		if (bestCandidate.equals(MinimaxNode.none)) growTree();
		return bestCandidate.getGuess();
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
	
	protected void growTree() {
		for (int i = 0; i < MAX_DEPTH; i++) {
			MinimaxNode node = leafNodes.remove();
			int id = node.id;
			int step = stepSize / node.getDepth();
			if (step < 1) step = 1;
			for (int j = 0; i < NUM_SLICES; i++) {
				id += step;
				Color[] colors = colorManager.getColorsFromSpaceID(id, numCols);
				Guess guess = new Guess(colors);
				int value = simGame.getGuessValue(guess);
				addNode(node, guess, value);				
			}
		}
	}
	
	protected boolean addNode(MinimaxNode parent, Guess guess, Integer value) {
		if (nodes.containsKey(guess)) return false;
		if (value == Integer.MAX_VALUE) finished = true;
		MinimaxNode guessNode = new MinimaxNode(parent, guess, value, colorManager.getSpaceID(guess.getColors()));
		if (value > bestCandidate.value) bestCandidate = guessNode;
		nodes.put(guess, guessNode);
		leafNodes.add(guessNode);
		parent.children.add(guessNode);
		MinimaxNode cur = parent;
		while (!cur.getGuess().equals(Guess.none) && !cur.equals(MinimaxNode.none)) {
			cur.value = Integer.max(parent.value, value);
			cur = cur.getParent();
		}
		return true;
	}

	protected boolean addNode(Guess parent, Guess guess, Integer value) {
		return addNode(nodes.get(parent), guess, value);
	}

	protected int minimax(MinimaxNode node) {
		int depth = node.depth;
		return -1;
	}
}
