package hr.fer.zemris.java.tecaj.hw5.db.Filters;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * This class is query filter for indexquery. It is support for implementing
 * O(1) search for student with given JMBAG
 * 
 * @author Marko
 *
 */
public class IndexQueryFilter implements IFilter {
	/** Query String (it is actually jmbag) */
	private String query;

	/**
	 * Constructor for IndexQueryFilter
	 * 
	 * @param query
	 */
	public IndexQueryFilter(String query) {
		this.query = query;
	}

	/**
	 * dummy accepts method, never meant to be implemented
	 * 
	 * @param query
	 */
	@Override
	public boolean accepts(StudentRecord record) {
		this.query = record.getJmbag();
		return false;
	}

	/**
	 * Getter for query
	 * 
	 * @return query
	 */
	public String getQuery() {
		return query;
	}

}
