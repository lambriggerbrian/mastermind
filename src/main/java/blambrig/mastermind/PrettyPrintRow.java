package blambrig.mastermind;

import java.util.HashMap;
import java.util.Map;

public class PrettyPrintRow {
	private static final Map<Color, Character> letterMapping = new HashMap<>();
	private static final String letters = "RGBYWb";
	private static int counter = 0;
	
	public static String print (Row row) throws IllegalArgumentException {
		if (row == Row.none || row.numCols() <= 0) {
			return "EMPTY ROW";
		}
		var colorString = "";
		final var pRow = new PrintableRow(row);
		for (int i=0; i < pRow.numCols(); i++) {
			colorString += colorToChar(pRow.positions(i));
		}
		var matchString = String.format(" %d / %d", pRow.matchedPositions(), pRow.matchedColors());
		return colorString + matchString;
	}
	
	private static char colorToChar (Color color) throws IllegalArgumentException {
		if (color == Color.none || color == null) {
			throw new IllegalArgumentException("Color cannot be none or null");
		}
		if (!letterMapping.containsKey(color) && !mapColor(color)) {
			throw new IllegalArgumentException("Number of colors cannot exceed mappable letters");
		}
		return letterMapping.get(color);
	}
	
	private static boolean mapColor(Color color) {
		if (!letterMapping.containsKey(color) && letterMapping.size() >= letters.length()) {
			return false;
		}
		letterMapping.put(color, letters.charAt(counter));
		counter++;
		return true;
	}
}
