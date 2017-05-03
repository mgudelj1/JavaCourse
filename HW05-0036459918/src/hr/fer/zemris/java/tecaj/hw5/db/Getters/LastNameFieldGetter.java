package hr.fer.zemris.java.tecaj.hw5.db.Getters;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Strategy for getting last name
 * 
 * @author Marko
 *
 */
public class LastNameFieldGetter implements IFieldValueGetter {
	/**
	 * Getter method for last name field
	 */
	@Override
	public String get(StudentRecord record) {
		return record.getlastName();
	}

}
