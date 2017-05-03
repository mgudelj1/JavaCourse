package hr.fer.zemris.java.tecaj.hw5.db.Operators;

/**
 * Interface that demands implementation of the method satisfied.Various
 * comparison operators will be implementing this interface
 * 
 * @author Marko
 *
 */
public interface IComparisonOperator {
	/**
	 * should return true if satisfies equation
	 * 
	 * @param value1
	 * @param value2
	 * @return true if satisfies, false otherwise
	 */
	public boolean satisfied(String value1, String value2);
}
