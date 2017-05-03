package hr.fer.zemris.java.tecaj.hw07.shell;

import java.io.IOException;
/**
 * Interface that defines what should every environment
 * of the shell implement.
 * 
 * @author Marko Gudelj
 *
 */
public interface Environment {
	/**
	 * Reads line from the System input
	 * 
	 * @return string of input
	 * @throws IOException
	 *             if reading was not possible
	 */
	String readLine() throws IOException;

	/**
	 * Writes text to the standard output
	 * 
	 * @param text
	 *            for writing
	 * @throws IOException
	 *             if writing was not possible
	 */
	void write(String text) throws IOException;

	/**
	 * Writes text to the standard output and adds new line feed.
	 * 
	 * @param text
	 *            for writing
	 * @throws IOException
	 *             if writing was not possible
	 */
	void writeln(String text) throws IOException;

	/**
	 * Makes iterable element that contains all commands that is implemented by
	 * shell
	 * 
	 * @return iterable element
	 */
	Iterable<ShellCommand> commands();

	/**
	 * Getter for MultilineSymbol
	 * 
	 * @return MultilineSymbol
	 */
	Character getMultilineSymbol();

	/**
	 * Setter for MultilineSymbol
	 * 
	 * @param symbol
	 *            new MultilineSymbol
	 */
	void setMultilineSymbol(Character symbol);

	/**
	 * Getter for promptSymbol
	 * 
	 * @return promptSymbol
	 */
	Character getPromptSymbol();

	/**
	 * Setter for promptSymbol
	 * 
	 * @param symbol
	 *            new promptSymbol
	 */
	void setPromptSymbol(Character symbol);

	/**
	 * Getter for MorelinesSymbol
	 * 
	 * @return MorelinesSymbol
	 */
	Character getMorelinesSymbol();

	/**
	 * Setter for MorelinesSymbol
	 * 
	 * @param symbol
	 *            new MorelinesSymbol
	 */
	void setMorelinesSymbol(Character symbol);
}
