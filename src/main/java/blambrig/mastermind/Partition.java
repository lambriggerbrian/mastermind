package blambrig.mastermind;

public class Partition {
	private final Guess start;
	private final Guess end;
	
	public Partition(Guess start, Guess end) {
		this.start = start;
		this.end = end;
	}
	
	public Guess getStart() {
		return start;
	}
	
	public Guess getEnd() {
		return end;
	}
}
