package blambrig.mastermind.print;

import blambrig.mastermind.Color;
import blambrig.mastermind.Row;

public class PrintableRow extends Row {
	public PrintableRow(Row source) {
		super(source);
	}
	
	public Color positions(int i) {
		return positions[i];
	}
	
	public int matchedPositions() {
		return matchedPositions;
	}
	
	public int matchedColors() {
		return matchedColors;
	}
	
}
