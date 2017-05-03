package hr.fer.zemris.java.custom.collections;

/**
 * Novi razred koji predstavlja iznimku, nasljeđuje RuntimeException
 * 
 * @author Marko Gudelj
 * @version 1.0
 */
public class EmptyStackException extends RuntimeException {
	/**
	 * Konstruktor iznimke
	 */
	public EmptyStackException() {
		super("Stack is empty");
	}
}
