package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Class represents mkdir shell command.
 * The mkdir command takes a single argument: directory name, and creates the
 * appropriate directory structure.
 * 
 * @author Marko Gudelj
 *
 */
public class MkdirShellCommand implements ShellCommand {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			if (arguments.split("\\s+").length != 1) {
				env.writeln("Command mkdir accepts exactly one argument");
				return ShellStatus.CONTINUE;
			}
			File directory = new File(UtilFinder.getPath(arguments));
			if (!directory.mkdirs()) {
				env.writeln("Making directories was unsucessful ");
			}
		} catch (IOException ignorable) {
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "mkdir";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> lista = new ArrayList<>();
		lista.add("The mkdir command takes a single argument: \n"
				+ "directory name, and creates the appropriate directory structure.");
		lista.add("Example >mkdir \"C:\\Program Files\\Newfile\" ");
		lista = Collections.unmodifiableList(lista);
		return lista;
	}

}
