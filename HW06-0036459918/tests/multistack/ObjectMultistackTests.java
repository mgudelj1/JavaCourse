package multistack;

import static org.junit.Assert.assertEquals;

import java.util.EmptyStackException;

import org.junit.Test;

import hr.fer.zemris.java.custom.scripting.exec.*;

@SuppressWarnings("javadoc")
public class ObjectMultistackTests {

	@Test
	public void testPushAndPeek() {
		ObjectMultistack multistack = new ObjectMultistack();

		ValueWrapper value = new ValueWrapper("4.2");
		multistack.push("value", value);
		assertEquals(4.2, multistack.peek("value").getValue());
	}

	@Test
	public void testPushAndPeek2() {
		ObjectMultistack multistack = new ObjectMultistack();

		ValueWrapper value1 = new ValueWrapper(4.2);
		ValueWrapper value2 = new ValueWrapper(4);

		multistack.push("value", value1);
		multistack.push("value", value2);

		assertEquals(4, multistack.peek("value").getValue());
	}

	@Test(expected = EmptyStackException.class)
	public void testEmptyStack() {
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.peek("value").getValue();
	}
	
	@Test
	public void testisEmpty() {
		ObjectMultistack multistack = new ObjectMultistack();
		assertEquals(true,multistack.isEmpty("ime"));
	}

}