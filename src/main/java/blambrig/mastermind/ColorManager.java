package blambrig.mastermind;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ColorManager {
	protected final int numColors;
	protected final Map<Color, Color> nextColors = new HashMap<Color, Color>();
	protected final Map<Color, Color> prevColors = new HashMap<Color, Color>();
	protected final Map<Color, String> colorsToBase = new HashMap<Color, String>();
	protected final Map<Integer, Color[]> spaceIDToColors = new HashMap<Integer, Color[]>();
	protected final Color[] colors;
	private Color first;
	private Color last;
	private final ColorFactory colorFactory;
	private static Random random = new Random();
	
	public ColorManager(int numColors, ColorFactory colorFactory) {
		this.numColors = numColors;
		this.colorFactory = colorFactory;
		this.colors = new Color[numColors];
		createOrdering();
	}
	
	public Color firstColor() {
		return first;
	}
	
	public Color nextColor(Color color) {
		if (color == Color.none) {
			return firstColor();
		}
		return nextColors.get(color);
	}
	
	public Color lastColor() {
		return last;
	}
	
	public Color prevColor(Color color) {
		if (color == Color.none) {
			return lastColor();
		}
		return prevColors.get(color);
	}
	
	public int numColors() {
		return numColors;
	}

	public boolean isThereNextColor(Color color) {
		if (color == Color.none) {
			return false;
		}
		return nextColors.containsKey(color);
	}
	
	public Color[] slice(int size) {
		if (size < 0) {
			throw new IllegalArgumentException("Slice size must be >= 0");
		}
		if (size > this.numColors) {
			throw new IllegalArgumentException("Slice size cannot exceed total num of colors");
		}
		Color[] slice = new Color[size];
		Color tmp = Color.none;
		int count = 0;
		while (count < size) {
			tmp = nextColor(tmp);
			slice[count] = tmp;
			count++;
		}
		return slice;
	}
	
	public Color[] reverseSlice(int size) {
		if (size < 0) {
			throw new IllegalArgumentException("Slice size must be >= 0");
		}
		if (size > this.numColors) {
			throw new IllegalArgumentException("Slice size cannot exceed total num of colors");
		}
		Color[] slice = new Color[size];
		Color tmp = Color.none;
		int count = 0;
		while (count < size) {
			tmp = prevColor(tmp);
			slice[count] = tmp;
			count++;
		}
		return slice;
	}
	
	public Color[] fill(int size, int index) {
		if (index < 0 || index >= numColors) {
			throw new IllegalArgumentException("Index must be > 0 and <= number of colors");
		}
		Color[] colors = new Color[size];
		Arrays.fill(colors, colors[index]);
		return colors;
	}
	
	public Color getRandomColor() {
		return this.colors[random.nextInt(this.numColors)];
	}
	
	public int getSpaceID(Color[] colors) {
		String id = "";
		for (Color color : colors) {
			id += colorsToBase.get(color);
		}
		return Integer.parseInt(id, numColors);
	}

	private void createColors() {
		for (int i = 0; i < numColors; i++) {
			this.colors[i] = colorFactory.newColor();
		}
	}
	
	private void createOrdering() {
		createColors();
		first = colors[0];
		last = colors[numColors-1];
		prevColors.put(Color.none, first);
		for (int i = 0; i < numColors - 1; i++) {
			nextColors.put(colors[i], colors[i + 1]);
			prevColors.put(colors[i + 1], colors[i]);
			colorsToBase.put(colors[i], Integer.toString(i, numColors));
		}
		nextColors.put(last, Color.none);
		colorsToBase.put(last, Integer.toString(numColors - 1, numColors));
	}

	public Color[] getColorsFromSpaceID(int id, int numCols) {
		if (spaceIDToColors.containsKey(id)) return spaceIDToColors.get(id);
		return createSpaceIDToColorMapping(id, numCols);
	}
	
	private Color[] createSpaceIDToColorMapping(int id, int numCols) {
		String baseString = Integer.toString(id, numColors);
		while (baseString.length() < numCols) baseString = "0" + baseString;
		char[] chars = baseString.toCharArray();
		Color[] colorMapping = new Color[numCols];
		for (int i = 0; i < numCols; i++) {
			colorMapping[i] = colors[Integer.parseInt(String.valueOf(chars[i]), numColors)];
		}
		spaceIDToColors.put(id, colors);
		return colorMapping;
	}
	
	
}
