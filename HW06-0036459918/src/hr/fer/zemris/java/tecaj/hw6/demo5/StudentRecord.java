package hr.fer.zemris.java.tecaj.hw6.demo5;

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
	private String prezime;
	/** First name of the student */
	private String ime;
	/** Number of points at MI */
	private double bodMI;
	/** Number of points at ZI */
	private double bodZI;
	/** Number of points at LAB */
	private double bodLAB;
	/** Final grade */
	private int ocjena;

	/**
	 * Constructor for StudentRecord
	 * 
	 * @param jmbag
	 *            jmbag
	 * @param prezime
	 *            Last name
	 * @param ime
	 *            First name
	 * @param bodMI
	 *            points at first exam
	 * @param bodZI
	 *            points at last exam
	 * @param bodLAB
	 *            points at labs
	 * @param ocjena
	 *            final grade
	 */
	public StudentRecord(String jmbag, String prezime, String ime, double bodMI, double bodZI, double bodLAB,
			int ocjena) {
		super();
		this.jmbag = jmbag;
		this.prezime = prezime;
		this.ime = ime;
		this.bodMI = bodMI;
		this.bodZI = bodZI;
		this.bodLAB = bodLAB;
		this.ocjena = ocjena;
	}

	/**
	 * Getter for jmbag
	 * 
	 * @return jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Setter for jmbag
	 * 
	 * @param jmbag
	 *            new jmbag
	 */
	public void setJmbag(String jmbag) {
		this.jmbag = jmbag;
	}

	/**
	 * Getter for last name
	 * 
	 * @return last name
	 */
	public String getPrezime() {
		return prezime;
	}

	/**
	 * Setter for last name
	 * 
	 * @param prezime
	 *            last name
	 */
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	/**
	 * Getter for name
	 * 
	 * @return name
	 */
	public String getIme() {
		return ime;
	}

	/**
	 * Setter for name
	 * 
	 * @param ime name
	 */
	public void setIme(String ime) {
		this.ime = ime;
	}

	/**
	 * Getter for points at MI
	 * 
	 * @return grade
	 */
	public double getBodMI() {
		return bodMI;
	}

	/**
	 * Setter for points at MI
	 * 
	 * @param bodMI
	 *            points
	 */
	public void setBodMI(double bodMI) {
		this.bodMI = bodMI;
	}

	/**
	 * Getter for points at ZI
	 * 
	 * @return grade
	 */
	public double getBodZI() {
		return bodZI;
	}

	/**
	 * Setter for points at ZI
	 * 
	 * @param bodZI
	 *            points
	 */
	public void setBodZI(double bodZI) {
		this.bodZI = bodZI;
	}

	/**
	 * Getter for points at lab
	 * 
	 * @return points
	 */
	public double getBodLAB() {
		return bodLAB;
	}

	/**
	 * Setter for points at lab
	 * 
	 * @param bodLAB
	 *            points
	 */
	public void setBodLAB(double bodLAB) {
		this.bodLAB = bodLAB;
	}

	/**
	 * Getter for grade
	 * 
	 * @return grade
	 */
	public int getOcjena() {
		return ocjena;
	}

	/**
	 * Setter for grade
	 * 
	 * @param ocjena
	 *            grade
	 */
	public void setOcjena(int ocjena) {
		this.ocjena = ocjena;
	}

	/**
	 * New implementation of toString for helping in printing student record to
	 * the output
	 */
	@Override
	public String toString() {
		return String.format("(%s) %s, %s ocjena:%d", jmbag, prezime, ime, ocjena);
	}
}
