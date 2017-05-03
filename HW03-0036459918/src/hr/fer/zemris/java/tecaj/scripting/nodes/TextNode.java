package hr.fer.zemris.java.tecaj.scripting.nodes;

/**
 * Class that represents Text node
 * 
 * @author Marko
 *
 */
public class TextNode extends Node {
	/** Text string of node */
	private String text;

	/**
	 * Constructor
	 * 
	 * @param text
	 *            text
	 */
	public TextNode(String text) {
		this.text = text;
	}

	/**
	 * Getter
	 * 
	 * @return string text
	 */
	public String getText() {
		return this.text;
	}
}
