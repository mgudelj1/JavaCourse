package hr.fer.zemris.java.tecaj.hw07.shell.commands;

/**
 * Helper Class that is used for parsing through arguments
 * 
 * @author Marko Gudelj
 *
 */
public class UtilFinder {
	/**
	 * Method that returns path from first argument
	 * 
	 * @param argument
	 *            argument
	 * @return path string
	 */
	public static String getPath(String argument) {
		String[] arguments;

		if (argument.split("\\s+").length == 0) {
			return null;
		}
		if (argument.contains("\"")) {
			arguments = argument.replaceFirst("\"", "").split("\"");
		} else {
			arguments = argument.split("\\s+");
		}
		if (arguments.length > 2) {
			return null;
		}
		return arguments[0];
	}

	/**
	 * Method that returns path from second argument
	 * 
	 * @param argument
	 *            argument
	 * @return path string
	 */
	public static String getSecondPath(String argument) {

		String[] arguments;

		if (argument.split("\\s+").length == 0) {
			return null;
		}
		if (argument.contains("\"")) {
			arguments = argument.replaceFirst("\"", "").split("\"");
		} else {
			arguments = argument.split("\\s+");
		}
		return arguments[1].trim();
	}

	/**
	 * Method that returns Charset from second argument
	 * 
	 * @param argument
	 *            argument
	 * @return charset string
	 */
	public static String getCharset(String argument) {
		String[] arguments;

		if (argument.split("\\s+").length == 0) {
			return null;
		}
		if (argument.contains("\"")) {
			arguments = argument.replaceFirst("\"", "").split("\"");
		} else {
			arguments = argument.split("\\s+");
		}
		if (arguments.length == 1) {
			return null;
		}
		return arguments[1];
	}
}
