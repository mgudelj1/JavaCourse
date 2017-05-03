package hr.fer.zemris.java.tecaj.hw6.observer2;

/**
 * Implementation of IntegerStorageChange that stores new and old value of the
 * storage
 * 
 * @author Marko Gudelj
 *
 */
public class IntegerStorageChange {
	/** Integer storage */
	private IntegerStorage iStorage;
	/** Value of storage before change */
	private int beforeValue;
	/** Value of storage after change */
	private int afterValue;

	/**
	 * Constructor for IntegerStorageChange
	 * 
	 * @param iStorage Integer Storage to change
	 * @param value to change
	 */
	public IntegerStorageChange(IntegerStorage iStorage, int value) {
		this.iStorage = iStorage;
		beforeValue = iStorage.getValue();
		afterValue = value;
	}

	/**
	 * Getter for IntegerStorage
	 * 
	 * @return iStorage
	 */
	public IntegerStorage getIntegerStorage() {
		return iStorage;
	}

	/**
	 * Getter for value before changing
	 * 
	 * @return unchanged value
	 */
	public int getBeforeValue() {
		return beforeValue;
	}

	/**
	 * Getter for value after changing
	 * 
	 * @return value after change
	 */
	public int getAfterValue() {
		return afterValue;
	}
}