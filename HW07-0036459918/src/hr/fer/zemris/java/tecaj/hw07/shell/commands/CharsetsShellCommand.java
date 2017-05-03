package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Class used as command charsets. Lists names of supported charsets for your
 * Java platform
 * 
 * @author Marko Gudelj
 *
 */
public class CharsetsShellCommand implements ShellCommand {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		for (Charset charset : Charset.availableCharsets().values()) {
			System.out.println(charset);
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "charsets";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> lista = new ArrayList<>();
		lista.add("Command charsets takes no arguments and lists"
				+ " names of supported charsets for your Java platform");
		lista = Collections.unmodifiableList(lista);
		return lista;
	}

}
