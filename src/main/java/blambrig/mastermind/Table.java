package blambrig.mastermind;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Table {
	public final ColorManager colorManager;
	public final int numCols;
	public final HashSet<Guess> guesses = new HashSet<Guess>();
	private final List<Row> rows;
	
	public Table(int numCols, ColorManager colorManager) {
		this.numCols = numCols;
		this.colorManager = colorManager;
		this.rows = new LinkedList<>();
	}
	
	public Table(Table table) {
		this.colorManager = table.colorManager;
		this.numCols = table.numCols;
		this.rows = new LinkedList<>();
	}
	
	public boolean addRow(Row row) {
		boolean unique = true;
		if (guesses.contains(row.guess)) {
			unique = false;
		}
		guesses.add(row.guess);
		rows.add(row);
		return unique;
	}
}
