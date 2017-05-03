package hr.fer.zemris.java.tecaj.hw6.observer2;

/**
 * Implementation of the IntegerStorageObserver interface. This class instances
 * write a number of the times the value in storage has been changed to the
 * standard output
 * 
 * @author Marko Gudelj
 *
 */
public class ChangeCounter implements IntegerStorageObserver {
	/** Counter for the number of changes */
	private int counter;

	/**
	 * Each time value in IntegerStorageChange is changed this method is called.
	 * Prints to the standard output number of the times the value in storage
	 * has been changed
	 */
	@Override
	public void valueChanged(IntegerStorageChange istoragechange) {
		counter++;
		System.out.printf("Number of value changes since tracking: %d\n", counter);

	}

}
