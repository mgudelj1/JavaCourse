package hr.fer.zemris.java.tecaj.hw5.db.Filters;

import hr.fer.zemris.java.tecaj.hw5.db.Getters.IFieldValueGetter;
import hr.fer.zemris.java.tecaj.hw5.db.Operators.IComparisonOperator;

/**
 * Class that represents single conditional expression from the given query.
 * Consists of FieldValueGetter, literal, ComparisonOperator.
 * 
 * @author Marko
 *
 */
public class ConditionalExpression {
	/** Field getter */
	IFieldValueGetter fieldGetter;
	/** query literal */
	String stringLiteral;
	/** Comparison operator */
	IComparisonOperator comparisonOperator;

	/**
	 * Constructor for ConditionalExpression.
	 * 
	 * @param fieldGetter
	 * @param literal
	 * @param operator
	 */
	public ConditionalExpression(IFieldValueGetter fieldGetter, String literal, IComparisonOperator operator) {
		this.fieldGetter = fieldGetter;
		this.stringLiteral = literal;
		this.comparisonOperator = operator;
	}

	/**
	 * Getter for fieldGetter
	 * 
	 * @return field value getter
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}

	/**
	 * Getter for literal
	 * 
	 * @return literal
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}

	/**
	 * Getter for comparison operator
	 * 
	 * @return operator
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}

}