package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class that inherits Element, represents operator
 * 
 * @author Marko
 *
 */
public class ElementOperator extends Element {
	/** Operator name */
	private String symbol;

	/**
	 * Constructor
	 * 
	 * @param symbol
	 *            operator symbol
	 */
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * Getter method
	 * 
	 * @return symbol
	 */
	public String getValue() {
		return this.symbol;
	}

	/**
	 * Method that represents symbol as string
	 * 
	 * @return string
	 */
	@Override
	public String asText() {
		return this.symbol;

	}
}
