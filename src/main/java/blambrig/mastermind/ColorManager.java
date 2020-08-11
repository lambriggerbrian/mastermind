package blambrig.mastermind;

import java.util.HashMap;
import java.util.Map;

public class ColorManager {
	final protected int numColors;
	final protected Map<Color, Color> nextColors = new HashMap<Color, Color>();
	final protected Map<Color, Color> prevColors = new HashMap<Color, Color>();
	private Color first;
	private Color last;
	private final ColorFactory colorFactory;
	
	public ColorManager(int numColors, ColorFactory colorFactory) {
		this.numColors = numColors;
		this.colorFactory = colorFactory;
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

	private Color[] createColors() {
		Color[] colors = new Color[numColors];
		for (int i = 0; i < numColors; i++) {
			colors[i] = colorFactory.newColor();
		}
		return colors;
	}
	
	private void createOrdering() {
		Color[] colors = createColors();
		first = colors[0];
		last = colors[numColors-1];
		for (int i = 0; i < numColors - 1; i++) {
			nextColors.put(colors[i], colors[i + 1]);
			prevColors.put(colors[i + 1], colors[i]);
		}
	}
	
	
}
