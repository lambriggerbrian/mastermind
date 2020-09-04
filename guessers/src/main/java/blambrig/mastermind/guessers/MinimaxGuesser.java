package blambrig.mastermind.guessers;

import blambrig.mastermind.Guess;
import blambrig.mastermind.Partitioner;
import blambrig.mastermind.SimpleGuesser;
import blambrig.mastermind.Table;

public class MinimaxGuesser extends SimpleGuesser {
	public final int MAX_LOOK_AHEAD = 3;	
	protected final Table searchTable;
	
	public MinimaxGuesser(Table table, Partitioner partitioner) {
		super(table, partitioner);
		this.searchTable = new Table(table);
	}
	
	protected int minimax(Guess guess, int depth) {
		return 0;
	}

}
