package blambrig.mastermind;

public class Partition {
	private final ColorManager colorManager;
	private final Guess start;
	private final Guess end;
	private Guess lastGuess = Guess.none;
	private boolean finished = false;
	
	public Partition(ColorManager colorManager, Guess start, Guess end) {
		this.colorManager = colorManager;
		this.start = start;
		this.end = end;
	}
	
	public Guess getStart() {
		return start;
	}
	
	public Guess getEnd() {
		return end;
	}
	
	public boolean isFinished() {
		return finished;
	}
	
	public Guess getNext() {
		if (finished == true) return Guess.none;
		if (finished == false && lastGuess.equals(Guess.none)) {
			lastGuess = start;
			return lastGuess;
		}
		Guess guess = lastGuess.nextGuess(colorManager);
		if (guess.equals(end)) finished = true;
		return guess;
	}
}
