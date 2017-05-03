package hr.fer.zemris.java.tecaj.hw5.db.Operators;

import java.text.Collator;
import java.util.Locale;

/**
 * Implementation of LIKE operator for the interface
 * IComparisonOperator
 * 
 * @author Marko
 *
 */
public class LIKEOperator implements IComparisonOperator{
	/** Collator */
	Collator hrCollator = Collator.getInstance(new Locale("hr", "HR"));

	/**
	 * Determines satisfaction of given values. Satisfied returns true if value1
	 * is LIKE the value2
	 */
	@Override
	public boolean satisfied(String value1, String value2) {
		char data2[] = value2.toCharArray();
		int i=0;
		
		for(; i < data2.length; i++){
			if(data2[i] == '*'){
				break;
			}
		}
		if(value1.startsWith(value2.substring(0, i))){
			System.out.println(value2.substring(0, i));
			if(value1.endsWith(value2.substring(i+1, value2.length()))){
				return true;
			}
		}
		return false;
	}

}
