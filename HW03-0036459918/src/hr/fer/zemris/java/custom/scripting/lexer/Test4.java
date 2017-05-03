package hr.fer.zemris.java.custom.scripting.lexer;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;



@SuppressWarnings("javadoc")
public class Test4 {

	@Test
	public void text() throws IOException {
		// Lets check for several words...
		String docBody = new String(Files.readAllBytes(Paths.get("examples/doc4.txt")), StandardCharsets.UTF_8);
		SmartScriptLexer lexer = new SmartScriptLexer(docBody);

		// We expect the following stream of tokens
		Token correctData[] = {
				new Token(TokenType.EMPTYTAG, "="),
				new Token(TokenType.STRING, "text/plain"),
				new Token(TokenType.FUNC, "setMimeType"),
				new Token(TokenType.TEXT, "Prvih 10 fibonaccijevih brojeva je:\r\n"),
				new Token(TokenType.EMPTYTAG, "="),
				new Token(TokenType.STRING, "0"),
				new Token(TokenType.STRING, "a"),
				new Token(TokenType.FUNC, "tparamSet"),
				new Token(TokenType.STRING, "1"),
				new Token(TokenType.STRING, "b"),
				new Token(TokenType.FUNC, "tparamSet"),
				new Token(TokenType.STRING, "0\r\n1\r\n"),
				new Token(TokenType.FORTAG,"FOR"),
				new Token(TokenType.VAR,"i"),
				new Token(TokenType.CON_INT, "3"),
				new Token(TokenType.CON_INT, "10"),
				new Token(TokenType.CON_INT, "1"),
				new Token(TokenType.EMPTYTAG, "="),
				new Token(TokenType.STRING, "b"),
				new Token(TokenType.STRING, "0"),
				new Token(TokenType.FUNC, "tparamGet"),
				new Token(TokenType.FUNC, "dup"),
				new Token(TokenType.STRING, "a"),
				new Token(TokenType.STRING, "0"),
				new Token(TokenType.FUNC, "tparamGet"),
				new Token(TokenType.OPERATOR, "+"),
				new Token(TokenType.STRING, "b"),
				new Token(TokenType.FUNC, "tparamSet"),
				new Token(TokenType.STRING, "a"),
				new Token(TokenType.FUNC, "tparamSet"),
				new Token(TokenType.STRING, "b"),
				new Token(TokenType.STRING, "0"),
				new Token(TokenType.FUNC, "tparamGet"),
				new Token(TokenType.STRING, "\r\n"),
				new Token(TokenType.ENDTAG,"END")
			

		};

		checkTokenStream(lexer, correctData);
	}

	private void checkTokenStream(SmartScriptLexer lexer, Token[] correctData) {
		int counter = 0;
		for (Token expected : correctData) {
			Token actual = lexer.nextToken();
			String msg = "Checking token " + counter + ":";
			assertEquals(msg, expected.getType(), actual.getType());
			assertEquals(msg, expected.getValue(), actual.getValue());
			counter++;
		}
	}
}
