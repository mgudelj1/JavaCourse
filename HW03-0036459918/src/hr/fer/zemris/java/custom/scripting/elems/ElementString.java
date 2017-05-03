package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class that inherits Element, represents string
 * 
 * @author Marko
 *
 */
public class ElementString extends Element {
	/** String value */
	private String value;

	/**
	 * Constructor
	 * 
	 * @param value
	 *            string value
	 */
	public ElementString(String value) {
		this.value = value;
	}

	/**
	 * Getter method
	 * 
	 * @return value
	 */
	public String getValue() {
		return this.value;
	}

	/**
	 * Method that represents value as string
	 * 
	 * @return string
	 */
	@Override
	public String asText() {
		return this.value;

	}
}