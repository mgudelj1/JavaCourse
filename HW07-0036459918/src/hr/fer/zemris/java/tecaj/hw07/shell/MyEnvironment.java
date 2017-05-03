package hr.fer.zemris.java.tecaj.hw07.shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Implementation of environment made for MyShell
 * 
 * @author Marko Gudelj
 *
 */
public class MyEnvironment implements Environment {
	/** Default promptSymbol for this Environment */
	private static char promptSymbol = '>';
	/** Default multiLineSymbol for this Environment */
	private static char multiLineSymbol = '|';
	/** Default moreLinesSymbol for this Environment */
	private static char moreLinesSymbol = '\\';

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String readLine() throws IOException {
		BufferedReader in = null;
		System.out.printf("%c ", promptSymbol);
		String input = null;

		try {
			in = new BufferedReader(new InputStreamReader(System.in));
			input = in.readLine();
		} catch (IOException e) {
			System.out.println("Reading was not completed correctly");
			return null;
		}
		return input;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write(String text) throws IOException {
		System.out.printf(text);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeln(String text) throws IOException {
		System.out.println(text);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterable<ShellCommand> commands() {
		return MyShell.initializeMap().values();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Character getMultilineSymbol() {
		return multiLineSymbol;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setMultilineSymbol(Character symbol) {
		multiLineSymbol = symbol;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Character getPromptSymbol() {
		return promptSymbol;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setPromptSymbol(Character symbol) {
		promptSymbol = symbol;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Character getMorelinesSymbol() {
		return moreLinesSymbol;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setMorelinesSymbol(Character symbol) {
		moreLinesSymbol = symbol;
	}

}