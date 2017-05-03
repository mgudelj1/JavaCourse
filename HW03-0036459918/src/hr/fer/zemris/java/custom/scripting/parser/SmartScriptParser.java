package hr.fer.zemris.java.custom.scripting.parser;

import java.util.EmptyStackException;

import hr.fer.zemris.java.custom.scripting.elems.*;
import hr.fer.zemris.java.custom.scripting.lexer.*;
import hr.fer.zemris.java.tecaj.scripting.nodes.*;

/**
 * Class that represents parser. Implements a parser for described structured
 * document format.
 * 
 * @author Marko
 *
 */
public class SmartScriptParser {
	/** Lexer */
	private SmartScriptLexer lexer;
	/** StackLike parser */
	private ObjectStack parser;
	/** Assisting Token */
	private Token pomToken;
	/** Node */
	private Node čvor;

	/**
	 * Constructor of Parser,accepts document body for processing
	 * 
	 * @param document
	 *            DocumentBody
	 */
	public SmartScriptParser(String document) {
		lexer = new SmartScriptLexer(document);
		parser = new ObjectStack(null);
		čvor = new DocumentNode();
		try {
			parser.push(čvor);
			parse();
		} catch (Exception e) {
			throw new SmartScriptParserException();

		}
	}

	/**
	 * Parsing method, Receives tokens and based on them makes desicions how to
	 * build document model tree
	 */
	private void parse() {

		while ((pomToken = lexer.nextToken()).getType() != TokenType.EOF) {
			if (pomToken.getType() == TokenType.TEXT) {
				čvor = new TextNode(pomToken.getValue().toString());
				((Node) parser.peek()).addChildNode(čvor);
				continue;
			}

			if (pomToken.getType() == TokenType.FORTAG) {
				Element elements[] = new Element[4];
				int i = 0;
				pomToken = lexer.nextToken();
				if (pomToken.getType() != TokenType.VAR) {
					throw new SmartScriptParserException();
				} else {
					elements[i] = new ElementVariable(pomToken.getValue().toString());
					i++;
				}
				for (; i < 4; i++) {
					pomToken = lexer.nextToken();
					if (pomToken.getType() != TokenType.TEXT) {
						switch (pomToken.getType()) {
						case VAR:
							elements[i] = new ElementVariable(pomToken.getValue().toString());
							break;
						case STRING:
							elements[i] = new ElementString(pomToken.getValue().toString());
							break;
						case CON_INT:
							elements[i] = new ElementConstantInteger(Integer.parseInt(pomToken.getValue().toString()));
							break;
						case CON_DOUBLE:
							elements[i] = new ElementConstantDouble(Double.parseDouble(pomToken.getValue().toString()));
							break;
						default:
							throw new SmartScriptParserException();
						}
					} else if (i < 2) {
						throw new SmartScriptParserException();
					}
				}
				čvor = new ForLoopNode((ElementVariable) elements[0], elements[1], elements[2], elements[3]);
				((Node) parser.peek()).addChildNode(čvor);
				parser.push(čvor);
				if (i < 3) {
					čvor = new TextNode(pomToken.getValue().toString());
					((Node) parser.peek()).addChildNode(čvor);
				}
				continue;
			}

			if (pomToken.getType() == TokenType.ENDTAG) {
				try {
					parser.pop();
				} catch (EmptyStackException e) {
					throw new SmartScriptParserException();
				}
			}

			if (pomToken.getType() == TokenType.EMPTYTAG) {
				Element elements[] = new Element[20];

				elements[0] = new ElementString(pomToken.getValue().toString());
				int i = 1;
				while (((pomToken = lexer.nextToken()).getType() != TokenType.TEXT)
						&& (pomToken.getType() != TokenType.EOF) && (pomToken.getType() != TokenType.FORTAG)
						&& (pomToken.getType() != TokenType.ENDTAG)) {
					switch (pomToken.getType()) {
					case VAR:
						elements[i] = new ElementVariable(pomToken.getValue().toString());
						break;
					case STRING:
						elements[i] = new ElementString(pomToken.getValue().toString());
						break;
					case OPERATOR:
						elements[i] = new ElementOperator(pomToken.getValue().toString());
						break;
					case FUNC:
						elements[i] = new ElementFunction(pomToken.getValue().toString());
						break;
					case CON_INT:
						elements[i] = new ElementConstantInteger(Integer.parseInt(pomToken.getValue().toString()));
						break;
					case CON_DOUBLE:
						elements[i] = new ElementConstantDouble(Double.parseDouble(pomToken.getValue().toString()));
						break;
					default:
						throw new SmartScriptParserException();
					}
					i++;
				}

				čvor = new EchoNode(elements);
				((Node) parser.peek()).addChildNode(čvor);

				if (pomToken.getType() == TokenType.EOF) {
					break;
				}
				if (pomToken.getType() == TokenType.ENDTAG) {
					try {
						parser.pop();
					} catch (EmptyStackException e) {
						throw new SmartScriptParserException();
					}
				}
				if (pomToken.getType() == TokenType.TEXT) {
					čvor = new TextNode(pomToken.getValue().toString());
					((Node) parser.peek()).addChildNode(čvor);
				}
			}
		}
	}

	/**
	 * Getter for DocumentNode
	 * 
	 * @return DocumentNode
	 */
	public DocumentNode getDocumentNode() {
		return (DocumentNode) parser.pop();
	}
}
