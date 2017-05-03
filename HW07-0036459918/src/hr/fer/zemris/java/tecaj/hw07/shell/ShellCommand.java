package hr.fer.zemris.java.tecaj.hw07.shell;

import java.util.List;

/**
 * Defines what should every shell command implement.
 * 
 * @author Marko Gudelj
 *
 */
public interface ShellCommand {
	/**
	 * Main method for every shell command. This method is executed if this
	 * command is called
	 * 
	 * @param env
	 *            Environment
	 * @param arguments
	 *            arguments for the command
	 * @return TERMINATE if command is exit,CONTINUE otherwise
	 */
	ShellStatus executeCommand(Environment env, String arguments);

	/**
	 * Returns command name
	 * 
	 * @return command name
	 */
	String getCommandName();

	/**
	 * Returns user instructions for command
	 * 
	 * @return user instructions
	 */
	List<String> getCommandDescription();
}
