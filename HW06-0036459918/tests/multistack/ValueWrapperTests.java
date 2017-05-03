package multistack;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;

@SuppressWarnings("javadoc")
public class ValueWrapperTests {
	@Test
	public void testDoubleString1() {
		ValueWrapper value = new ValueWrapper("2.2");
		assertEquals(2.2, value.getValue());
	}
	
	@Test(expected = RuntimeException.class)
	public void testRunTimeException() {
		new ValueWrapper("2.š");
	}
	
	@Test
	public void testDoubleString2() {
		ValueWrapper value = new ValueWrapper("2e1"); //will become Double
		assertEquals(20.0, value.getValue());
	}
	
	@Test
	public void testIncrement1() {
		ValueWrapper value1 = new ValueWrapper("2e1");
		ValueWrapper value2 = new ValueWrapper("2");
		value1.increment(value2.getValue());
		
		assertEquals(22.0, value1.getValue());
	}
	
	@Test
	public void testIncrement2() {
		ValueWrapper value1 = new ValueWrapper(5);
		ValueWrapper value2 = new ValueWrapper("6");
		value1.increment(value2.getValue());
		
		assertEquals(11.0, value1.getValue());
	}
	@Test
	public void testIncrement3() {
		ValueWrapper value1 = new ValueWrapper("2");
		ValueWrapper value2 = new ValueWrapper("2e-1");
		value1.increment(value2.getValue());
		
		assertEquals(2.2, value1.getValue());
	}
	
	@Test
	public void testDecrement() {
		ValueWrapper value1 = new ValueWrapper("2e1");
		ValueWrapper value2 = new ValueWrapper("2");
		value1.decrement(value2.getValue());
		
		assertEquals(18.0, value1.getValue());
	}
	
	@Test
	public void testDecrement2() {
		ValueWrapper value1 = new ValueWrapper(15);
		ValueWrapper value2 = new ValueWrapper(7);
		value1.decrement(value2.getValue());
		
		assertEquals(8, value1.getValue());
	}
	
	@Test
	public void testMultiply() {
		ValueWrapper value1 = new ValueWrapper(4);
		ValueWrapper value2 = new ValueWrapper(7);
		value1.multiply(value2.getValue());
		
		assertEquals(28, value1.getValue());
	}
	
	@Test
	public void testDivide() {
		ValueWrapper value1 = new ValueWrapper(15);
		ValueWrapper value2 = new ValueWrapper(5);
		value1.divide(value2.getValue());
		
		assertEquals(3, value1.getValue());
	}
	
	@Test
	public void testDivide2() {
		ValueWrapper value1 = new ValueWrapper("24.0");
		ValueWrapper value2 = new ValueWrapper(8);
		value1.divide(value2.getValue());
		
		assertEquals(3.0, value1.getValue());
	}
	
	@Test(expected = ArithmeticException.class)
	public void testDivideException() {
		ValueWrapper value1 = new ValueWrapper("24.0");
		ValueWrapper value2 = new ValueWrapper(0);
		value1.divide(value2.getValue());
	}
	
	@Test
	public void testNumCompare() {
		ValueWrapper value1 = new ValueWrapper("24.0");
		ValueWrapper value2 = new ValueWrapper(24);
		
		assertEquals(0, value1.numCompare(value2.getValue()));
	}
	
	@Test
	public void testNumCompare2() {
		ValueWrapper value1 = new ValueWrapper("13");
		ValueWrapper value2 = new ValueWrapper(24);
		
		assertEquals(-1, value1.numCompare(value2.getValue()));
	}
	
	@Test
	public void testNumCompare3() {
		ValueWrapper value1 = new ValueWrapper(null);
		ValueWrapper value2 = new ValueWrapper(null);
		
		assertEquals(0, value1.numCompare(value2.getValue()));
	}
	
	@Test
	public void testNumCompare4() {
		ValueWrapper value1 = new ValueWrapper(0);
		ValueWrapper value2 = new ValueWrapper(null);
		
		assertEquals(0, value1.numCompare(value2.getValue()));
	}
}
