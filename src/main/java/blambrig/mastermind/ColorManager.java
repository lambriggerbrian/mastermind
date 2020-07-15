package blambrig.mastermind;

import java.util.HashMap;
import java.util.Map;

public class ColorManager {
	final protected int numColors;
	final protected Map<Color, Color> nextColors = new HashMap<Color, Color>();
	private Color first;
	
	public ColorManager(int numColors) {
		this.numColors = numColors;
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

	public boolean isThereNextColor(Color color) {
		if (color == Color.none) {
			return false;
		}
		return nextColors.containsKey(color);
	}
	
	public Color[] slice(int size) {
		if (size < 0) {
			throw new IllegalArgumentException("Slice size much be >= 0");
		}
		if (size > this.numColors) {
			throw new IllegalArgumentException("Slice size cannot exceet total num of colors");
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

	protected Color newColor() {
		return new Color();
	}
	
	private Color[] createColors() {
		Color[] colors = new Color[numColors];
		for (int i = 0; i < numColors; i++) {
			colors[i] = newColor();
		}
		return colors;
	}
	
	private void createOrdering() {
		Color[] colors = createColors();
		first = colors[0];
		for (int i = 0; i < numColors - 1; i++) {
			nextColors.put(colors[i], colors[i + 1]);
		}
	}
	
	
}
