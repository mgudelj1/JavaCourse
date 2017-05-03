package hr.fer.zemris.java.tecaj.hw5.db.Getters;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Strategy for getting jmbag field
 * 
 * @author Marko
 *
 */
public class JmbagFieldGetter implements IFieldValueGetter {
	/**
	 * Getter method for jmbag field
	 */
	@Override
	public String get(StudentRecord record) {
		return record.getJmbag();
	}

}
