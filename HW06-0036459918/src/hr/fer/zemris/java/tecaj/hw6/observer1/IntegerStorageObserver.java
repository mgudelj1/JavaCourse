package hr.fer.zemris.java.tecaj.hw6.observer1;

/**
 * Interface for the Integer storage observer. Every class implementing this
 * interface should provide implementation of the method valueChanged.
 * 
 * @author Marko Gudelj
 *
 */
public interface IntegerStorageObserver {
	/**
	 * This method should be invoked every time when storage value is changed
	 * 
	 * @param istorage Integer storage
	 */
	public void valueChanged(IntegerStorage istorage);
}
