package blambrig.mastermind.guessers;

import com.google.common.collect.MinMaxPriorityQueue;

import blambrig.mastermind.Guess;

public class MinimaxNode implements Comparable<MinimaxNode> {
	public final static MinimaxNode none = new MinimaxNode(null, Guess.none, 0);
	protected Integer value;
	protected final MinimaxNode parent;
	protected final Guess guess;
	protected final MinMaxPriorityQueue<MinimaxNode> children = MinMaxPriorityQueue.create();
	
	public MinimaxNode(MinimaxNode parent, Guess guess, Integer value) {
		this.value = value;
		this.parent = parent;
		this.guess = guess;
	}
	
	public Guess getGuess() {
		return this.guess;
	}
	
	@Override
	public int compareTo(MinimaxNode o) {
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
		if (guess == null || guess.equals(Guess.none)) {
			if (other.guess != null || other.guess.equals(Guess.none))
				return false;
		} else if (!guess.equals(other.guess))
			return false;
		return true;
	}
}
