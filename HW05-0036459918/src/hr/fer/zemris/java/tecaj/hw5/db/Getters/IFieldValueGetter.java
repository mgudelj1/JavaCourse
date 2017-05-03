package hr.fer.zemris.java.tecaj.hw5.db.Getters;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Interface for the field value getter. This interface will be implemented by
 * various field getters, such as FirstNameFieldGetter etc.
 * 
 * @author Marko
 *
 */
public interface IFieldValueGetter {
	/**
	 * This method should provide getter for named field
	 * 
	 * @param record
	 * @return field
	 */
	public String get(StudentRecord record);
}
