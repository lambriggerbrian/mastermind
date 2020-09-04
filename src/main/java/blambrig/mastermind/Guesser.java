package blambrig.mastermind;

public interface Guesser {
	Guess guess();
	void shutdown();
}
