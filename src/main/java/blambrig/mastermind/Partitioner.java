package blambrig.mastermind;

public abstract class Partitioner {
	protected ColorManager colorManager;
	protected final Integer numCols;
	protected boolean isDone = false;
	
	public Partitioner(ColorManager colorManager, Integer numCols) {
		this.colorManager = colorManager;
		this.numCols = numCols;
	}

	public abstract Partition getNext();
	
	public boolean isDone() {
		return isDone;
	}
}
