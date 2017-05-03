package hr.fer.zemris.java.tecaj.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * Class that represents Echo node
 * 
 * @author Marko
 *
 */
public class EchoNode extends Node {
	/** Elements */
	private Element[] elements;

	/**
	 * Constructor
	 * 
	 * @param elements
	 *            elements of echo node
	 */
	public EchoNode(Element[] elements) {
		this.elements = elements;
	}

	/**
	 * Getter of echo node
	 * 
	 * @return array of elements
	 */
	public Element[] getElements() {
		return this.elements;
	}
}

