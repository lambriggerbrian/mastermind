package blambrig.mastermind;

public class GeneralGuesser extends Guesser {

	public GeneralGuesser(Table table) {
		super(table);
	}
	
	@Override
	protected void setFirstGuess() {
		int i = 0;
		for (Color color = table.colorManager.firstColor(); i < lastGuess.length;) {
			lastGuess[i++] = color;
		}
	}
}
