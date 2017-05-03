package hr.fer.zemris.java.tecaj.hw6.observer2;

/**
 * Implementation of the IntegerStorageObserver interface. This class instances
 * write a square of the integer stored in the IntegerStorage to the standard
 * output
 * 
 * @author Marko Gudelj
 *
 */
public class SquareValue implements IntegerStorageObserver {
	/**
	 * Each time value in IntegerStorageChange is changed this method is called.
	 * Prints to the standard output square value of the value in storage
	 */
	@Override
	public void valueChanged(IntegerStorageChange istoragechange) {
		System.out.printf("Provided new value: %d, square is %.0f\n", istoragechange.getAfterValue(),
				(Math.pow(istoragechange.getAfterValue(), 2)));
	}

}
