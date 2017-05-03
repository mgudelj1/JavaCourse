package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class that inherits Element, represents function
 * 
 * @author Marko
 *
 */
public class ElementFunction extends Element {
	/** Function name */
	private String name;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            name of function
	 */
	public ElementFunction(String name) {
		this.name = name;
	}

	/**
	 * Getter method
	 * 
	 * @return name of function
	 */
	public String getValue() {
		return this.name;
	}

	/**
	 * Method that represents name as string
	 * 
	 * @return string
	 */
	@Override
	public String asText() {
		return this.name;

	}
}
