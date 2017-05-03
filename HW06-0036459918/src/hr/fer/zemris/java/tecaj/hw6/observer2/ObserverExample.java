package hr.fer.zemris.java.tecaj.hw6.observer2;

/**
 * Demonstration class for the Observers implementation for the Integer storage
 * 
 * @author Marko Gudelj
 *
 */
public class ObserverExample {
	/**
	 * Entry point to the program
	 * 
	 * @param args of the command line
	 */
	public static void main(String[] args) {
		IntegerStorage istorage = new IntegerStorage(20);
		
		istorage.addObserver(new ChangeCounter());
		IntegerStorageObserver observer = new SquareValue();
		istorage.addObserver(observer);
		istorage.addObserver(new DoubleValue(1));
		istorage.addObserver(new DoubleValue(2));
		istorage.addObserver(new DoubleValue(2));
		
		
		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);
		
		istorage.removeObserver(observer);
		
		
		istorage.setValue(13);
		istorage.setValue(22);
		istorage.setValue(15);
	}
}
