package blambrig.mastermind;

import java.util.LinkedList;
import java.util.List;

public class Table {
	final int numCols;
	final ColorManager colorManager;
	final List<Row> rows;
	
	public Table(int numCols, ColorManager colorManager) {
		this.numCols = numCols;
		this.colorManager = colorManager;
		this.rows = new LinkedList<>();
	}
	
	public void addRow(Row row) {
		rows.add(row);
	}
}
