package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Class that represents Token, Token consists of TokenType and Value it is
 * produced by lexer and used by parser
 * 
 * @author Marko
 *
 */
public class Token {
	/** Value of Token */
	private Object value;
	/** Type of Token */
	private TokenType type;

	/**
	 * Constructor for Token
	 * 
	 * @param type
	 *            type of token
	 * @param value
	 *            value of token
	 */
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
	}

	/**
	 * Constructor for Token
	 * 
	 * @param type
	 *            type of token
	 */
	public Token(TokenType type) {
		this.type = type;
	}

	/**
	 * Getter for value of Token
	 * 
	 * @return value
	 */
	public Object getValue() {
		return this.value;
	}

	/**
	 * Getter for type of Token
	 * 
	 * @return type
	 */
	public TokenType getType() {
		return this.type;
	}

}
