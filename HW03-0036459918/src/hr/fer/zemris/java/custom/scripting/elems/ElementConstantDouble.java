package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class that inherits element, represents Double Constant
 * 
 * @author Marko
 *
 */
public class ElementConstantDouble extends Element {
	/** Value of Constant */
	private double value;

	/**
	 * Constructor
	 * 
	 * @param value
	 *            Value of Constant
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}

	/**
	 * Getter method
	 * 
	 * @return Value of Constant
	 */
	public double getValue() {
		return this.value;
	}

	/**
	 * Method that represents Constant as string
	 * 
	 * @return string
	 */
	@Override
	public String asText() {
		return Double.toString(this.value);
	}
}
