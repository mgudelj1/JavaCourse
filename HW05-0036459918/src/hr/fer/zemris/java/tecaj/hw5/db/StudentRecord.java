package hr.fer.zemris.java.tecaj.hw5.db;

/**
 * Class representation for the student record in the database.Instance of this
 * class represents one student input in database
 * 
 * @author Marko
 *
 */
public class StudentRecord {
	/** Jmbag of the student */
	private String jmbag;
	/** Last name of the student */
	private String lastName;
	/** First name of the student */
	private String firstName;
	/** Grade for the student */
	private String finalGrade;

	/**
	 * Constructor for the StudentRecord
	 * 
	 * @param jmbag
	 * @param lastName
	 * @param firstName
	 * @param finalGrade
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, String finalGrade) {
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}

	/**
	 * Getter for Jmbag
	 * 
	 * @return jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Getter for the last name
	 * 
	 * @return last name
	 */
	public String getlastName() {
		return lastName;
	}

	/**
	 * Getter for the first name
	 * 
	 * @return first name
	 */
	public String getfirstName() {
		return firstName;
	}

	/**
	 * Getter for the grade
	 * 
	 * @return grade
	 */
	public String getfinalGrade() {
		return finalGrade;
	}

	/**
	 * hash Code
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	/**
	 * Equals method that compares two students based on theirs jmbag. Two
	 * students can not have same jmbag.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}

}
