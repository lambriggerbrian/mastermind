package blambrig.mastermind;

import static org.junit.Assert.*;

import org.junit.Test;

public class PrettyPrintRowTest {
	final int numColors = 6;
	final int numCols = 4;
	final ColorManager colorManager = new ColorManager(numColors);
	final Table table = new Table(numCols, colorManager);
	final Color[] secret = colorManager.slice(numCols);
	
	@Test
	public void printTest() {
		String expected = "RGBY 0 / 0";
		String actual = PrettyPrintRow.print(new Row(secret));
		assertEquals(expected, actual);
	}
	
	@Test
	public void printEmptyTest() {
		Color[] empty = new Color[0];
		String expected = "EMPTY ROW";
		String actual = PrettyPrintRow.print(new Row(empty));
		assertEquals(expected, actual);
		actual = PrettyPrintRow.print(Row.none);
		assertEquals(expected, actual);
	}
	
	@Test(expected = RuntimeException.class)
	public void printRuntimeExceptionOnOverflowTest() {
		Color[] tooBig = new Color[8];
		PrettyPrintRow.print(new Row(tooBig));
	}

}
