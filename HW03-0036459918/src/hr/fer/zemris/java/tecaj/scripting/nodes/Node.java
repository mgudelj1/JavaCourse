package hr.fer.zemris.java.tecaj.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.parser.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * Class that represents node
 * 
 * @author Marko
 *
 */
public class Node {
	/** Instance of collection */
	private ArrayIndexedCollection childrens = null;
	/** number of calls for method addchildNode */
	private int poziv = 0;

	/**
	 * adds given child to an internally managed collection of children; uses an
	 * instance of ArrayIndexedCollection class for this.
	 * 
	 * @param child
	 *            node child
	 */
	public void addChildNode(Node child) {
		if (poziv == 0) {
			childrens = new ArrayIndexedCollection();
			poziv++;
		}
		childrens.add(child);
	}

	/**
	 * returns a number of (direct) children
	 * 
	 * @return number of children
	 */
	public int numberOfChildren() {
		if (poziv == 0) {
			return 0;
		}
		return childrens.size();
	}

	/**
	 * returns selected child or throws an appropriate exception if the index is
	 * invalid.
	 * 
	 * @param index
	 *            index of child
	 * @return child node
	 */
	public Node getChild(int index) {
		if (index < 0 || index > numberOfChildren() - 1) {
			throw new SmartScriptParserException();
		}
		return (Node) childrens.get(index);

	}
}
