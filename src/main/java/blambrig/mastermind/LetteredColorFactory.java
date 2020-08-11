package blambrig.mastermind;

public class LetteredColorFactory implements ColorFactory {
	private static String letters =
			"ABCDEFHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	private int counter = 0;
	
	@Override
	public Color newColor() {
		Color color = new LetteredColor(letters.substring(counter, counter+1));
		counter++;
		return color;
	}

}
