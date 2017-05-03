package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;

/**
 * Testni razred
 * 
 * @author Marko Čupić
 * @verion 1.0
 */
public class Demo1 {
	/**
	 * Ulazna metoda
	 * 
	 * @param args
	 *            ne prihvaća argumente
	 */
	public static void main(String[] args) {

		ArrayIndexedCollection c = new ArrayIndexedCollection();
		c.add("New York");
		c.add("Atlanta");
		c.add("Phoenix");
		c.remove("Atlanta");
		System.out.printf("%d%n",c.size());//2
		System.out.printf("%d",c.indexOf("Phoenix"));//1
		

	}
}
