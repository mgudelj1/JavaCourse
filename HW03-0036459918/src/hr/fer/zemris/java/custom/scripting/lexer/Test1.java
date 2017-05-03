package hr.fer.zemris.java.custom.scripting.lexer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;


import org.junit.Test;

import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

@SuppressWarnings("javadoc")
public class Test1 {
	
	@Test
	public void testNotNull() {
		SmartScriptLexer lexer = new SmartScriptLexer("");

		assertNotNull("Token was expected but null was returned.", lexer.nextToken());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullInput() {
		// must throw!
		new SmartScriptLexer(null);
	}

	@Test
	public void testEmpty() {
		SmartScriptLexer lexer = new SmartScriptLexer("");

		assertEquals("Empty input must generate only EOF token.", TokenType.EOF, lexer.nextToken().getType());
	}
	
	@Test
	public void testGetReturnsLastNext() {
		// Calling getToken once or several times after calling nextToken must return each time what nextToken returned...
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		Token token = lexer.nextToken();
		assertEquals("getToken returned different token than nextToken.", token, lexer.getToken());
		assertEquals("getToken returned different token than nextToken.", token, lexer.getToken());
	}

	
	@Test(expected=SmartScriptParserException.class)
	public void testRadAfterEOF() {
		SmartScriptLexer lexer = new SmartScriptLexer("");

		// will obtain EOF
		lexer.nextToken();
		// will throw!
		lexer.nextToken();
	}
	
	
	@Test
	public void testNoActualContent() {
		// When input is only of spaces, tabs, newlines, etc...
		SmartScriptLexer lexer = new SmartScriptLexer("   \r\n\t    ");
		
		assertEquals("Input had empty tags. Lexer should generate text token.", TokenType.TEXT, lexer.nextToken().getType());
	}

	
	@Test
	public void text() throws IOException {
		// Lets check for several words...
		String docBody = new String(Files.readAllBytes(Paths.get("examples/doc1.txt")), StandardCharsets.UTF_8);
		SmartScriptLexer lexer = new SmartScriptLexer(docBody);

		// We expect the following stream of tokens
		Token correctData[] = {
			new Token(TokenType.TEXT, "This is sample text.\r\n"),
			new Token(TokenType.FORTAG, "FOR"),
			new Token(TokenType.VAR, "i"),
			new Token(TokenType.CON_INT, "1"),
			new Token(TokenType.CON_INT, "10"),
			new Token(TokenType.CON_INT, "1"),
			new Token(TokenType.TEXT, "\r\n This is"),
			new Token(TokenType.EMPTYTAG, "="),
			new Token(TokenType.VAR, "i"),
			new Token(TokenType.TEXT,"-th time this message is generated.\r\n"),
			new Token(TokenType.ENDTAG,"END"),
			new Token(TokenType.TEXT, "\r\n"),
			new Token(TokenType.FORTAG, "FOR"),
			new Token(TokenType.VAR, "i"),
			new Token(TokenType.CON_INT, "0"),
			new Token(TokenType.CON_INT, "10"),
			new Token(TokenType.CON_INT, "2"),
			new Token(TokenType.TEXT,"\r\n sin("),
			new Token(TokenType.EMPTYTAG, "="),
			new Token(TokenType.VAR, "i"),
			new Token(TokenType.TEXT,"^2) = "),
			new Token(TokenType.EMPTYTAG, "="),
			new Token(TokenType.VAR, "i"),
			new Token(TokenType.VAR, "i"),
			new Token(TokenType.OPERATOR, "*"),
			new Token(TokenType.FUNC, "sin"),
			new Token(TokenType.STRING, "0.000"),
			new Token(TokenType.FUNC, "decfmt"),
			new Token(TokenType.TEXT,"\r\n"),
			new Token(TokenType.ENDTAG,"END"),
			new Token(TokenType.EOF,null)
			
		};

		checkTokenStream(lexer, correctData);
	}
	
	
	
	
	
	private void checkTokenStream(SmartScriptLexer lexer, Token[] correctData) {
		int counter = 0;
		for(Token expected : correctData) {
			Token actual = lexer.nextToken();
			String msg = "Checking token "+counter + ":";
			assertEquals(msg, expected.getType(), actual.getType());
			assertEquals(msg, expected.getValue(), actual.getValue());
			counter++;
		}
	}
}
