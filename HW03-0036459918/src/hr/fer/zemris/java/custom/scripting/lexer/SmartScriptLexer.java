package hr.fer.zemris.java.custom.scripting.lexer;

import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * Class that represents implementation for a parser for described structured
 * document format.
 * 
 * @author Marko
 *
 */
public class SmartScriptLexer {
	/** Input text */
	private char[] data;
	/** Current token */
	private Token token;
	/** index of first unprocessed sign */
	private int currentIndex;
	/** State of Lexer */
	private SmartScriptLexerState state;

	/**
	 * Constructor for Lexer
	 * 
	 * @param document
	 *            a DocumentBody
	 */
	public SmartScriptLexer(String document) {
		if (document == null) {
			throw new IllegalArgumentException();
		}

		this.data = document.toCharArray();
		this.currentIndex = 0;
		state = SmartScriptLexerState.Document;
	}

	/**
	 * Implementation of Token processing method Token consists of TokenType and
	 * string
	 * 
	 * @return Token
	 */
	public Token nextToken() {

		token = new Token(TokenType.EOF, null);

		if (currentIndex > data.length) {
			throw new SmartScriptParserException();
		}

		StringBuilder tokenString = new StringBuilder();

		if (this.state == SmartScriptLexerState.Document) {
			token = documentLexer(tokenString);
		} else if (state == SmartScriptLexerState.Tag) {
			token = tagLexer(tokenString);
		} else if (state == SmartScriptLexerState.Element) {
			token = elementLexer(tokenString);
		}
		return token;
	}

	/**
	 * Lexer for Document state of lexer(out of tags)
	 * 
	 * @param tokenString
	 *            input string
	 * @return Token
	 */
	public Token documentLexer(StringBuilder tokenString) {
		if (currentIndex == data.length) {
			token = new Token(TokenType.EOF);
			currentIndex++;
			return token;
		}

		while (data[currentIndex] != '{') {
			tokenString.append(data[currentIndex]);
			currentIndex++;
			if (currentIndex == data.length) {
				break;
			}
		}
		if (tokenString.length() == 0) {
			state = SmartScriptLexerState.Tag;
			token = tagLexer(tokenString);
			return token;
		}
		state = SmartScriptLexerState.Tag;
		token = new Token(TokenType.TEXT, tokenString.toString());

		return token;
	}

	/**
	 * Lexer for Tag state of lexer(Determining which are the tags)
	 * 
	 * @param tokenString
	 *            input string
	 * @return Token
	 */
	public Token tagLexer(StringBuilder tokenString) {
		boolean empty_lines = false;

		if (currentIndex == data.length) {
			token = new Token(TokenType.EOF);
			currentIndex++;
			return token;
		}
		currentIndex++;
		if (data[currentIndex] != '$') {
			throw new SmartScriptParserException();
		}
		currentIndex++;

		for (; currentIndex < data.length; currentIndex++) {
			if (Character.isWhitespace(data[currentIndex]) == true) {
				empty_lines = true;
				continue;
			}
			if (Character.toUpperCase(data[currentIndex]) == 'F') {
				tokenString.append(data, currentIndex, 3);
				if (tokenString.toString().toUpperCase().equals("FOR")
						&& Character.isWhitespace(data[currentIndex + 3])) {
					token = new Token(TokenType.FORTAG, tokenString.toString());
					state = SmartScriptLexerState.Element;
					currentIndex += 3;
					break;
				} else if (empty_lines) {
					throw new SmartScriptParserException();
				}
			}
			if (Character.toUpperCase(data[currentIndex]) == 'E') {
				tokenString.append(data, currentIndex, 3);
				if (tokenString.toString().toUpperCase().equals("END")
						&& (Character.isWhitespace(data[currentIndex + 3]) || data[currentIndex + 3] == '$')) {
					token = new Token(TokenType.ENDTAG, tokenString.toString());
					state = SmartScriptLexerState.Document;
					currentIndex += 3;
					while (Character.isWhitespace(data[currentIndex])) {
						currentIndex++;
					}
					currentIndex += 2;
					break;
				} else if (empty_lines) {
					throw new SmartScriptParserException();
				}
			}
			if (Character.isLetter(data[currentIndex]) == true) {
				while (Character.isWhitespace(data[currentIndex]) != true) {
					tokenString.append(data[currentIndex]);
					currentIndex++;
				}
				token = new Token(TokenType.EMPTYTAG, tokenString.toString());
				state = SmartScriptLexerState.Element;
				break;
			}
			if (data[currentIndex] == '=') {
				tokenString.append(data[currentIndex]);
				token = new Token(TokenType.EMPTYTAG, tokenString.toString());
				state = SmartScriptLexerState.Element;
				currentIndex++;
				break;
			}

		}

		return token;
	}

	/**
	 * Lexer for element state of lexer(Determining elements of the tag)
	 * 
	 * @param tokenString
	 *            input string
	 * @return Token
	 */
	public Token elementLexer(StringBuilder tokenString) {
		while (data[currentIndex] != '$') {

			if (Character.isWhitespace(data[currentIndex]) == true) {
				currentIndex++;
				continue;
			}

			if (data[currentIndex] == '+' || data[currentIndex] == '-' || data[currentIndex] == '*'
					|| data[currentIndex] == '/' || data[currentIndex] == '^') {
				tokenString.append(data[currentIndex]);
				currentIndex++;
				token = new Token(TokenType.OPERATOR, tokenString.toString());
				break;
			}

			if (data[currentIndex] == '"') {
				currentIndex++;
				while (data[currentIndex] != '"') {
					if (data[currentIndex] == '\\') {
						currentIndex++;
						if (data[currentIndex] == '\\') {
							tokenString.append(data[currentIndex]);
							currentIndex++;
							continue;
						} else if (data[currentIndex] == '"') {
							tokenString.append(data[currentIndex]);
							currentIndex++;
							continue;
						} else if (data[currentIndex] == 'n') {
							tokenString.append("\n");
							currentIndex++;
							continue;
						} else if (data[currentIndex] == 'r') {
							tokenString.append("\r");
							currentIndex++;
							continue;
						} else if (data[currentIndex] == 't') {
							tokenString.append("\t");
							currentIndex++;
							continue;
						} else {
							throw new SmartScriptParserException();
						}
					}

					tokenString.append(data[currentIndex]);
					currentIndex++;
				}
				currentIndex++;
				token = new Token(TokenType.STRING, tokenString.toString());
				break;
			}

			if (data[currentIndex] == '@') {
				currentIndex++;
				if (Character.isLetter(data[currentIndex])) {
					while (Character.isWhitespace(data[currentIndex]) == false) {
						tokenString.append(data[currentIndex]);
						currentIndex++;
					}
					token = new Token(TokenType.FUNC, tokenString.toString());
					break;
				}
			}
			if (Character.isLetter(data[currentIndex]) == true) {
				while (Character.isWhitespace(data[currentIndex]) != true && data[currentIndex] != '$') {
					tokenString.append(data[currentIndex]);
					currentIndex++;
				}
				token = new Token(TokenType.VAR, tokenString.toString());
				break;
			}

			if (data[currentIndex] == '-') {
				if (Character.isDigit(data[currentIndex + 1]) == true) {
					tokenString.append(Integer.parseInt(Character.toString(data[currentIndex + 1])) * (-1));
					currentIndex += 2;
					while (Character.isDigit(data[currentIndex]) == true) {
						tokenString.append(data[currentIndex]);
						currentIndex++;
					}
					token = new Token(TokenType.CON_INT, tokenString.toString());
					break;
				} else {
					tokenString.append(data[currentIndex]);
					token = new Token(TokenType.OPERATOR, tokenString.toString());
					currentIndex++;
					break;
				}
			}

			if (Character.isDigit(data[currentIndex]) == true) {
				while (Character.isDigit(data[currentIndex]) == true) {
					tokenString.append(data[currentIndex]);
					currentIndex++;
				}
				token = new Token(TokenType.CON_INT, tokenString.toString());
				break;
			}
		}
		if (tokenString.length() == 0) {
			currentIndex += 2;
			state = SmartScriptLexerState.Document;
			token = documentLexer(tokenString);
		}
		return token;
	}

	/**
	 * Getter for Token
	 * 
	 * @return Token
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * Getter for State of Token
	 * 
	 * @return Token
	 */
	public SmartScriptLexerState getState() {
		return state;
	}
}
