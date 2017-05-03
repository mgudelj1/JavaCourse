package hr.fer.zemris.java.tecaj.hw6.observer2;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents integer storage with a list of observers for the
 * storage. Each time value is changed, observers are notified.
 * 
 * @author Marko Gudelj
 *
 */
public class IntegerStorage {
	/** Value of the storage */
	private int value;
	/** List of observers for the storage */
	private List<IntegerStorageObserver> observers;

	/**
	 * Constructor for the IntegerStorage. Initiates value of the storage at the
	 * given parameter
	 * 
	 * @param initialValue for storage value
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
		observers = new ArrayList<>();
	}
	
	/**
	 * Method for adding observer to the list of observers Every observer that
	 * is registered to this storage is located in observers list
	 * 
	 * @param observer for the storage
	 */
	public void addObserver(IntegerStorageObserver observer) {
		if(!observers.contains(observer)){
			observers.add(observer);
		}
		
	}

	/**
	 * Method for removing observer from the list of observers
	 * 
	 * @param observer for the storage
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		observers.remove(observer);
	}

	/**
	 * Method for clearing all the observers from the list of observers
	 */
	public void clearObservers() {
		observers.clear();
	}

	/**
	 * Getter for the value
	 * 
	 * @return value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Setter for the value. Each time value of the storage is changed, all the
	 * observers from the observers list are notified
	 * 
	 * @param value of the storage
	 */
	public void setValue(int value) {
		List <IntegerStorageObserver> pomLista = new ArrayList<>();
		pomLista.addAll(observers);
		IntegerStorageChange iStorageChange = new IntegerStorageChange(this, value);
		// Only if new value is different than the current value:
		if (this.value != value) {
			// Update current value
			this.value = value;
			// Notify all registered observers
			if (observers != null) {
				for (IntegerStorageObserver observer : pomLista) {
					observer.valueChanged(iStorageChange);
				}
			}
		}
	}
}
