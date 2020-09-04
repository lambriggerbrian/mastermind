package blambrig.mastermind.guessers;

import java.util.HashMap;

import blambrig.mastermind.Guess;

public class MinimaxTree {	
	private final HashMap<Guess, MinimaxNode> nodes;
	
	public MinimaxTree() {
		this.nodes = new HashMap<>();
		this.nodes.put(Guess.none, MinimaxNode.none);
	}
	
	public boolean addNode(Guess parent, Guess guess, Integer value) {
		if (nodes.containsKey(guess)) {
			return false;
		}
		MinimaxNode parentNode = nodes.get(parent);
		MinimaxNode guessNode = new MinimaxNode(parentNode, guess, value);
		nodes.put(guess, guessNode);
		parentNode.children.add(guessNode);
		Guess parentGuess = parentNode.getGuess();
		while (!parentNode.equals(MinimaxNode.none) && !parentGuess.equals(Guess.none)) {
			parentNode.value = value;
			parentNode = nodes.get(parentGuess);
			parentGuess = parentNode.getGuess();
		}
		return true;
	}
	
	public Guess getNextGuess(Guess guess) {
		if (!nodes.containsKey(guess)) {
			throw new IllegalArgumentException("Guess is not in tree");
		}
		MinimaxNode node = nodes.get(guess);
		return node.children.peekLast().getGuess();
	}
}
