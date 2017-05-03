package hr.fer.zemris.java.tecaj.hw3.prob1;

/**
 * Class that represents lexer.
 * 
 * @author Marko
 *
 */
public class Lexer {
	/** Input text */
	private char[] data;
	/** Current Token */
	private Token token;
	/** Index of first unprocessed sign */
	private int currentIndex;
	/** State of lexer */
	private LexerState state;

	/**
	 * Constructor for lexer,accepts document
	 * 
	 * @param text
	 *            string
	 */
	public Lexer(String text) {
		if (text == null) {
			throw new IllegalArgumentException();
		}

		this.data = text.toCharArray();
		this.currentIndex = 0;
		this.state = LexerState.BASIC;

	}

	/**
	 * Method that generates and return next Token
	 * 
	 * @return Token
	 * 
	 * @throws LexerException
	 *             if it comes to error
	 */
	public Token nextToken() {

		token = new Token(TokenType.EOF, null);
		if (currentIndex > data.length) {
			throw new LexerException();
		}

		StringBuilder tokenString = new StringBuilder();

		if (this.state == LexerState.BASIC) {
			token = basicLexer(tokenString);
		} else {
			token = extendedLexer(tokenString);
		}

		return token;
	}

	/**
	 * Returns last generated Token, can be called multiple times
	 * 
	 * @return Token
	 */
	public Token getToken() {
		return token;

	}

	/**
	 * Helper method for constructing string
	 * 
	 * @param tokenString
	 *            string builder
	 * @param character
	 *            character for building
	 */
	public void addToString(StringBuilder tokenString, char character) {
		tokenString.append(character);
		currentIndex++;

	}

	/**
	 * Helper method
	 * 
	 * @return boolean
	 */
	public boolean isEscapeSequence() {
		if (data[currentIndex] == '\\') {
			return true;
		}
		return false;
	}

	/**
	 * Method for setting state of lexer
	 * 
	 * @param state
	 *            desired state
	 */
	public void setState(LexerState state) {
		if (state == null) {
			throw new IllegalArgumentException();
		}
		this.state = state;
	}

	/**
	 * Lexer for extended state of lexer
	 * 
	 * @param tokenString
	 *            string builder
	 * @return Token
	 */
	public Token extendedLexer(StringBuilder tokenString) {
		if (data.length == 0) {
			this.currentIndex++;
			return token;
		}
		for (; currentIndex < data.length; currentIndex++) {
			if (data[currentIndex] == '#' && tokenString.length() == 0) {
				token = new Token(TokenType.SYMBOL, '#');
				this.state = LexerState.BASIC;
				currentIndex++;
				return token;
			}
			if (data[currentIndex] == '#') {
				break;
			}
			if (Character.isWhitespace(data[currentIndex])) {
				if (tokenString.length() != 0) {
					this.currentIndex++;
					return new Token(TokenType.WORD, tokenString.toString());
				}
				continue;
			}

			addToString(tokenString, data[currentIndex]);
			this.currentIndex--;
		}
		if (tokenString.length() == 0) {
			return token;
		}
		token = new Token(TokenType.WORD, tokenString.toString());
		return token;
	}

	/**
	 * Lexer for basic state of lexer
	 * 
	 * @param tokenString
	 *            string builder
	 * @return Token
	 */
	public Token basicLexer(StringBuilder tokenString) {

		for (; currentIndex < data.length; currentIndex++) {
			if (data[currentIndex] == '#') {
				if (tokenString.length() != 0) {
					return new Token(TokenType.WORD, tokenString.toString());
				} else {
					this.state = LexerState.EXTENDED;
					this.currentIndex++;
					return new Token(TokenType.SYMBOL, '#');
				}
			}

			if (Character.isWhitespace(data[currentIndex])) {
				if (tokenString.length() != 0) {
					this.currentIndex++;
					return new Token(TokenType.WORD, tokenString.toString());
				}
				continue;
			}

			if (isEscapeSequence() == true) {
				currentIndex++;
				try {
					if (Character.isDigit(data[currentIndex]) == true || data[currentIndex] == '\\') {
						addToString(tokenString, data[currentIndex]);
						currentIndex--;
						continue;
					} else {
						throw new LexerException();
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					throw new LexerException();
				}
			}

			if (Character.isDigit(data[currentIndex]) == true) {
				while (Character.isDigit(data[currentIndex]) == true) {
					tokenString.append(data[currentIndex]);
					currentIndex++;
					if (currentIndex == data.length) {
						break;
					}
				}
				try {
					Long.parseLong(tokenString.toString());
				} catch (NumberFormatException e) {
					throw new LexerException();
				}
				token = new Token(TokenType.NUMBER, Long.parseLong(tokenString.toString()));
				break;
			}

			if (Character.isLetter(data[currentIndex]) == true) {
				while (Character.isLetter(data[currentIndex]) == true) {
					addToString(tokenString, data[currentIndex]);
					if (currentIndex == data.length) {
						break;
					}
					while (isEscapeSequence() == true) {
						currentIndex++;
						addToString(tokenString, data[currentIndex]);
					}
				}
				token = new Token(TokenType.WORD, tokenString.toString());
				break;
			}

			if (Character.isDefined(data[currentIndex]) == true) {
				token = new Token(TokenType.SYMBOL, data[currentIndex]);
				currentIndex++;
				break;
			}
		}

		if (token.getType() == TokenType.EOF) {
			currentIndex++;
		}

		return token;
	}
}