package blambrig.mastermind.guessers;

import java.util.PriorityQueue;

import blambrig.mastermind.Guess;

public class MinimaxNode implements Comparable<MinimaxNode> {
	public final static MinimaxNode none = new MinimaxNode(null, Guess.none, 0, Integer.MIN_VALUE);
	public final int id;
	protected Integer value;
	protected Integer depth;
	protected final MinimaxNode parent;
	protected final Guess guess;
	protected final PriorityQueue<MinimaxNode> children = new PriorityQueue<>();
	protected final boolean maximizing = true;
	
	public MinimaxNode(MinimaxNode parent, Guess guess, Integer value, int id) {
		this.value = value;
		this.parent = parent;
		this.guess = guess;
		this.depth = (parent == null) ? 0 : parent.depth + 1;
		this.id = id;
	}
		
	public Guess getGuess() {
		return guess;
	}
	
	public MinimaxNode getParent() {
		return parent;
	}
	
	public int getDepth() {
		return depth;
	}
	
	@Override
	public String toString() {
		String parentString = (parent == null) ? "None" : parent.guess.toString();
		return String.format("%s: ID(%d), Depth(%d), Parent(%s), Value(%d)", 
				guess.toString(), id, depth, parentString, value);
	}
	
	@Override
	public int compareTo(MinimaxNode o) {
		if (maximizing) return o.value.compareTo(value);
		return value.compareTo(o.value);
	}

	@Override
	public int hashCode() {
		return guess.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MinimaxNode other = (MinimaxNode) obj;
		if (id == other.id) return true; 
		if (guess == null || guess.equals(Guess.none)) {
			if (other.guess != null || other.guess.equals(Guess.none))
				return false;
		} else if (!guess.equals(other.guess))
			return false;
		return true;
	}
}
