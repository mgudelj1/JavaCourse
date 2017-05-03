package hr.fer.zemris.java.tecaj.hw5.db.Filters;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Interface that represents filters such as QueryFilter
 * 
 * @author Marko
 *
 */
public interface IFilter {
	/**
	 * Method for processing single record. Result is true in case that record
	 * satisfies given filter, false otherwise
	 * 
	 * @param record
	 * @return true if satisfies, otherwise false
	 */
	public boolean accepts(StudentRecord record);
}
