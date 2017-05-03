package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Class that represents help shell command. If started with no arguments, it
 * lists names of all supported commands. If started with single argument, it
 * prints name and the description of selected command. If there is no such
 * command prints error.
 * 
 * @author Marko Gudelj
 *
 */
public class HelpShellCommand implements ShellCommand {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			boolean contains = false;
			String[] argumenti = arguments.split("\\s+");
			if (argumenti[0].equals("")) {
				for (ShellCommand sc : env.commands()) {
					print(env, sc, false);
				}
			} else if (argumenti.length == 1) {
				for (ShellCommand sc : env.commands()) {
					if (sc.getCommandName().equals(argumenti[0])) {
						print(env, sc, true);
						contains = true;
						break;
					}
				}
			} else {
				env.writeln("Command help accepts at maximum one argument");
			}
			if (!contains) {
				env.writeln("Given command is not implemented");
			}
		} catch (IOException ignorable) {
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * Helper method for formatted print
	 * 
	 * @param env
	 *            Environment
	 * @param sc
	 *            shell command
	 * @param both
	 *            determines the output
	 */
	private static void print(Environment env, ShellCommand sc, boolean both) {
		try {
			env.writeln(sc.getCommandName());
			if (both) {
				for (String opis : sc.getCommandDescription()) {
					env.writeln(opis);
				}
			}
		} catch (IOException ignorable) {
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "help";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> lista = new ArrayList<>();
		lista.add("If started with no arguments, it must list names of all supported commands.");
		lista.add("If started with single argument, it must print name and the description of selected command");
		lista.add("Example >help cat");
		lista = Collections.unmodifiableList(lista);
		return lista;
	}

}
