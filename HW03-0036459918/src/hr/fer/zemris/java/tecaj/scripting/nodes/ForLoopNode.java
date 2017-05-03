package hr.fer.zemris.java.tecaj.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * Class that represents For Loop Node
 * 
 * @author Marko
 *
 */
public class ForLoopNode extends Node {
	/** Variable */
	private ElementVariable variable;
	/** element of starting expression */
	private Element startExpression;
	/** element of ending expression */
	private Element endExpression;
	/** element of step expression */
	private Element stepExpression;

	/**
	 * Constructor
	 * 
	 * @param variable
	 *            variable
	 * @param startExpression
	 *            start
	 * @param endExpression
	 *            end
	 * @param stepExpression
	 *            step
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	/**
	 * Getter
	 * 
	 * @return variable
	 */
	public ElementVariable getVariable() {
		return this.variable;
	}

	/**
	 * Getter
	 * 
	 * @return StartExpression
	 */
	public Element getStartExpression() {
		return this.startExpression;
	}

	/**
	 * Getter
	 * 
	 * @return EndExpression
	 */
	public Element getEndExpression() {
		return this.endExpression;
	}

	/**
	 * Getter
	 * 
	 * @return StepExpression
	 */
	public Element getStepExpression() {
		return this.stepExpression;
	}
}

