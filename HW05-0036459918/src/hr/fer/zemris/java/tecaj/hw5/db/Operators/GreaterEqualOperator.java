package hr.fer.zemris.java.tecaj.hw5.db.Operators;

import java.text.Collator;
import java.util.Locale;

/**
 * Implementation of greater equal operator for the interface IComparisonOperator
 * 
 * @author Marko
 *
 */
public class GreaterEqualOperator implements IComparisonOperator{
	/** Collator */
	Collator hrCollator = Collator.getInstance(new Locale("hr", "HR"));

	/**
	 * Determines satisfaction of given values. Satisfied returns true if value1
	 * is greater or equal to the value2
	 */
	@Override
	public boolean satisfied(String value1, String value2) {
		if (hrCollator.compare(value1, value2) >= 0) {
			return true;
		}
		return false;
	}

}
