package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Enumeration that represents token type
 * 
 * @author Marko
 *
 */
public enum TokenType {
	/**
	 * Text token Type
	 */
	TEXT,
	/**
	 * Var token Type
	 */
	VAR,
	/**
	 * String token Type
	 */
	STRING,
	/**
	 * Operator token Type
	 */
	OPERATOR,
	/**
	 * Function token Type
	 */
	FUNC,
	/**
	 * Constant Integer token Type
	 */
	CON_INT,
	/**
	 * Constant Double token Type
	 */
	CON_DOUBLE,
	/**
	 * End of File token Type
	 */
	EOF,
	/**
	 * FOR token Type
	 */
	FORTAG,
	/**
	 * END token Type
	 */
	ENDTAG,
	/**
	 * EmptyTag token Type
	 */
	EMPTYTAG;
}
