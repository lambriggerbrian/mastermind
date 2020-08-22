package blambrig.mastermind;

public interface Guesser {
	public Guess guess();
	public void shutdown();
}
