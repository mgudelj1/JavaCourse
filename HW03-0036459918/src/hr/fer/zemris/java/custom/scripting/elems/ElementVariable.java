package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class that inherits Element, represents Variable
 * 
 * @author Marko
 *
 */
public class ElementVariable extends Element {
	/** Variable name */
	private String name;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            variable name
	 */
	public ElementVariable(String name) {
		this.name = name;
	}

	/**
	 * Getter method
	 * 
	 * @return name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Method that represents variable as string
	 * 
	 * @return string
	 */
	@Override
	public String asText() {
		return this.name;
	}
}
