package hr.fer.zemris.java.tecaj.hw5.db.Filters;

import hr.fer.zemris.java.tecaj.hw5.db.Getters.*;
import hr.fer.zemris.java.tecaj.hw5.db.Operators.*;

/**
 * Lexer for making expressions based on query
 * 
 * @author Marko
 *
 */
public class Lexer {
	/** Input query */
	private String[] string;
	/** Index of first unprocessed sign */
	private int currentIndex;
	/** Helper integer that defines end of the field getter */
	private int beginIndex;
	/** Number of queries */
	public int length;

	/**
	 * Constructor for lexer
	 * 
	 * @param query
	 */
	public Lexer(String query) {
		if (query == null) {
			throw new IllegalArgumentException();
		}
		string = query.trim().split("(?i)\\s+and\\s+(?i)");
		currentIndex = 0;
		beginIndex = 0;
		length = string.length;
	}

	/**
	 * Method like nextToken, but instead of Tokens returns expressions
	 * 
	 * @return Conditional expression
	 */
	public ConditionalExpression nextExpression() {
		beginIndex = 0;
		IFieldValueGetter field;
		IComparisonOperator operator;
		String literal;
		if (currentIndex < string.length) {
			field = getField(string[currentIndex]);
			if (field == null) {
				throw new IllegalArgumentException("That is not valid query");
			}
			operator = getOperator(string[currentIndex].substring(this.beginIndex, string[currentIndex].length()));
			if (operator == null) {
				throw new IllegalArgumentException("That is not valid operator");
			}

			literal = getLiteral(string[currentIndex]);
			if (literal.isEmpty()) {
				throw new IllegalArgumentException("Literal should not be empty");
			}

			currentIndex++;
			return new ConditionalExpression(field, literal, operator);
		}

		return null;
	}

	/**
	 * Helper method for getting FieldValueGetter
	 * 
	 * @param string
	 * @return fieldGetter
	 */
	private IFieldValueGetter getField(String string) {
		if (string.trim().startsWith("lastName")) {
			this.beginIndex = 8;
			return new LastNameFieldGetter();
		} else if (string.trim().startsWith("firstName")) {
			this.beginIndex = 9;
			return new FirstNameFieldGetter();
		} else if (string.trim().startsWith("jmbag")) {
			this.beginIndex = 5;
			return new JmbagFieldGetter();
		}
		return null;
	}

	/**
	 * Helper method for getting operator from string
	 * 
	 * @param string
	 * @return operator
	 */
	private IComparisonOperator getOperator(String string) {
		char[] pomdata = string.toCharArray();
		
		if (string.trim().startsWith("LIKE")) {
			return new LIKEOperator();
		}
		
		for (int i = 0; i < pomdata.length; i++) {
			if (Character.isWhitespace(pomdata[i])) {
				continue;
			}
			if (pomdata[i] == '<') {
				if (pomdata[i + 1] == '=') {
					return new LessEqualOperator();
				}
				return new LessOperator();
			}
			if (pomdata[i] == '>') {
				if (pomdata[i + 1] == '=') {
					return new GreaterEqualOperator();
				}
				return new GreaterOperator();
			}
			if (pomdata[i] == '!') {
				if (pomdata[i + 1] == '=') {
					return new NotEqualOperator();
				}
				return null;
			}
			if (pomdata[i] == '=') {
				return new EqualOperator();
			}

		}

		return null;
	}

	/**
	 * Helper method for getting literal from string
	 * 
	 * @param string
	 * @return literal
	 */
	private String getLiteral(String string) {
		char[] pomdata = string.toCharArray();
		StringBuilder sb = new StringBuilder();
		int i = beginIndex;

		for (; i < string.length(); i++) {
			if (pomdata[i] != '"') {
				continue;
			}
			break;
		}
		if (i == string.length()) {
			throw new IllegalArgumentException("There is no literal");
		}
		i++;
		for (; i < string.length(); i++) {
			if (pomdata[i] != '"') {
				sb.append(pomdata[i]);
				continue;
			}
			break;
		}
		return sb.toString();
	}
}
