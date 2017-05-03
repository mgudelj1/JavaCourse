package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Class that represents symbol shell command. Symbol is used for changing
 * environment symbols such as promptSymbol
 * 
 * @author Marko Gudelj
 *
 */
public class SymbolShellCommand implements ShellCommand {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String argumenti[];
		argumenti = arguments.split("\\s+");
		if (argumenti[0].equals("PROMPT")) {
			if (argumenti.length == 1) {
				System.out.printf("Symbol for PROMPT is '%c'\n", env.getPromptSymbol());
			} else {
				System.out.printf("Symbol for PROMPT changed from '%c' to '%c'\n", env.getPromptSymbol(),
						argumenti[1].charAt(0));
				env.setPromptSymbol(argumenti[1].charAt(0));
			}
		}
		if (argumenti[0].equals("MORELINES")) {
			if (argumenti.length == 1) {
				System.out.printf("Symbol for MORELINES is '%c'\n", env.getMorelinesSymbol());
			} else {
				System.out.printf("Symbol for MORELINES changed from '%c' to '%c'\n", env.getMorelinesSymbol(),
						argumenti[1].charAt(0));
				env.setMorelinesSymbol(argumenti[1].charAt(0));
			}
		}
		if (argumenti[0].equals("MULTILINE")) {
			if (argumenti.length == 1) {
				System.out.printf("Symbol for MULTILINE is '%c'\n", env.getMultilineSymbol());
			} else {
				System.out.printf("Symbol for MULTILINE changed from '%c' to '%c'\n", env.getMultilineSymbol(),
						argumenti[1].charAt(0));
				env.setMultilineSymbol(argumenti[1].charAt(0));
			}
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "symbol";
	}

	/**
	 * {@inheritDoc}
	 */

	@Override
	public List<String> getCommandDescription() {
		List<String> lista = new ArrayList<>();
		lista.add("Symbol is used for changing environment symbols such as promptSymbol");
		lista.add("Example >symbol PROMPT !");
		lista = Collections.unmodifiableList(lista);
		return lista;
	}

}
