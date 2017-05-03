package hr.fer.zemris.java.tecaj.hw6.observer2;

/**
 * Implementation of the IntegerStorageObserver interface. Instances of
 * DoubleValue class write to the standard output double of the current value
 * which is stored in subject, but only first n times since its registration
 * with subject
 * 
 * @author Marko Gudelj
 *
 */
public class DoubleValue implements IntegerStorageObserver {
	/** Number of the times method valueChanged has been called */
	private int timeCalled;

	/**
	 * Constructor for DoubleValue.
	 * 
	 * @param timeCalled number of times to call
	 */
	public DoubleValue(int timeCalled) {
		if(timeCalled < 1){
			throw new IllegalArgumentException("Should not be constructed with number less than 1");
		}
		this.timeCalled = timeCalled;
	}

	/**
	 * Each time value in IntegerStorageChange is changed this method is called.
	 * Prints to the standard output double of the storage value, but only first
	 * timeCalled times provided in the constructor
	 */
	@Override
	public void valueChanged(IntegerStorageChange istoragechange) {
		timeCalled--;
		System.out.printf("Double value: %d\n", istoragechange.getAfterValue() * 2);
		if (timeCalled == 0) {
			istoragechange.getIntegerStorage().removeObserver(this);
		}
	}

}
