package hr.fer.zemris.java.tecaj.hw6.demo3;

/**
 * Demonstration class 2
 * 
 * @author Marko
 *
 */
public class PrimesDemo2 {
	/**
	 * Entry point
	 * 
	 * @param args
	 *            not used
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(2);
		for (Integer prime : primesCollection) {
			for (Integer prime2 : primesCollection) {
				System.out.println("Got prime pair: " + prime + ", " + prime2);
			}
		}
	}
}
