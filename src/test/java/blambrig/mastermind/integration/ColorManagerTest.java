package blambrig.mastermind.integration;

import java.util.Arrays;

import org.junit.Test;

import blambrig.mastermind.Color;
import blambrig.mastermind.ColorManager;
import blambrig.mastermind.LetteredColorFactory;

public class ColorManagerTest {
	final int NUM_COLORS = 4;
	final int NUM_COLUMNS = 4;
	final ColorManager colorManager = new ColorManager(NUM_COLORS, new LetteredColorFactory());
	
	@Test
	public void testEncoding() {
		Color[] c0 = new Color[NUM_COLUMNS];
		Arrays.fill(c0, colorManager.firstColor());
		assert(colorManager.getSpaceID(c0) == 0);
		Color[] c1 = new Color[NUM_COLUMNS];
		Arrays.fill(c1, colorManager.lastColor());
		assert(colorManager.getSpaceID(c1) == Math.pow(NUM_COLORS, NUM_COLUMNS)-1);
	}
	
	@Test
	public void testDecoding() {
		Color[] c0 = new Color[NUM_COLUMNS];
		Arrays.fill(c0, colorManager.firstColor());
		assert(Arrays.equals(c0, colorManager.getColorsFromSpaceID(0, NUM_COLUMNS)));
		Color[] c1 = new Color[NUM_COLUMNS];
		Arrays.fill(c1,  colorManager.lastColor());
		assert(Arrays.equals(c1, colorManager.getColorsFromSpaceID(255, NUM_COLUMNS)));
	}

}
