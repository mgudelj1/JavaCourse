import static org.junit.Assert.assertEquals;


import hr.fer.zemris.java.tecaj.hw07.crypto.*;
import org.junit.Test;


@SuppressWarnings("javadoc")
public class hexToByteTests {

	
	@Test
	public void hexToByteTest1() {
		byte[] array = new byte[] {10, 11, 12};
		assertEquals(array[2], Crypto.hextobyte("0a0b0c")[2]);
	}

	@Test
	public void hexToByteTest2() {
		byte[] array = new byte[] {12,13,14};
		assertEquals(array[0], Crypto.hextobyte("0c0d0e")[0]);
	}
}
