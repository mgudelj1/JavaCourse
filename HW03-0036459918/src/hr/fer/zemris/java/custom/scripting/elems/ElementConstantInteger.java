package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class that inherits element, represents Integer Constant
 * 
 * @author Marko
 *
 */
public class ElementConstantInteger extends Element {
	/** Value of Constant */
	private int value;

	/**
	 * Constructor
	 * 
	 * @param value
	 *            Value of Constant
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}

	/**
	 * Getter method
	 * 
	 * @return Value of Constant
	 */
	public int getValue() {
		return this.value;
	}

	/**
	 * Method that represents Constant as string
	 * 
	 * @return string
	 */
	@Override
	public String asText() {
		return Integer.toString(this.value);
	}
}
