package blambrig.mastermind;

import java.util.Arrays;

public class SimplePartitioner extends Partitioner {
	public SimplePartitioner(ColorManager colorManager, Integer numCols) {
		super(colorManager, numCols);
	}
	@Override
	public Partition getNext() {
		Color[] start = new Color[numCols], end = new Color[numCols];
		Arrays.fill(start, colorManager.firstColor());
		Arrays.fill(end, colorManager.lastColor());
		isDone = true;
		return new Partition(colorManager, new Guess(start), new Guess(end));
	}
}
