package blambrig.mastermind.guessers;

import java.util.Arrays;

import blambrig.mastermind.Color;
import blambrig.mastermind.ColorManager;
import blambrig.mastermind.Guess;
import blambrig.mastermind.SimplePartitioner;

public class ParallelPartitioner extends SimplePartitioner {

	protected Color[] start;
	protected Color[] end;
	protected final int numParts;
	protected final int numColors;
	
	public ParallelPartitioner(ColorManager colorManager, Integer numCols, Integer numParts) {
		super(colorManager, numCols);
		this.numColors = colorManager.numColors();
		this.start = new Color[numCols];
		this.end = new Color[numCols];
		this.numParts = numParts;
	}
	
	protected Guess firstIntervalStart(Color[] colors) {
		Arrays.fill(colors, colorManager.firstColor());
		return new Guess(colors);
	}
	
	protected Guess nextIntervalStart (Color[] colors) {
		final int index = colors.length - 1;
		int step = numColors / numParts;
		if (step == 0) {
			step = 1;
		}
		while (step > 0) {
			if (colorManager.isThereNextColor(colors[index])) {
				Arrays.fill(colors, colorManager.nextColor(colors[index]));
				step--;
			} else {
				return Guess.none;
			}
		}
		Guess guess = new Guess(colors);
		return guess;
	}

}
