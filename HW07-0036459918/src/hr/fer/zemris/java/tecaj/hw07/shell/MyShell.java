package hr.fer.zemris.java.tecaj.hw07.shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import hr.fer.zemris.java.tecaj.hw07.shell.commands.*;

/**
 * Class that represents implementation of shell. MyShell has built-in commands:
 * charsets, cat, ls, tree, copy, mkdir, hexdump, exit, help, symbol. MyShell is
 * case sensitive.
 * 
 * @author Marko Gudelj
 *
 */
public class MyShell {
	
	/**
	 * Entry point for the MyShell program
	 * 
	 * @param args accepts no arguments
	 */
	public static void main(String[] args) {
		Map<String, ShellCommand> commands = new HashMap<>();
		commands = initializeMap();
		MyEnvironment myEnvironment = new MyEnvironment();
		ShellCommand command;
		ShellStatus status = ShellStatus.CONTINUE;
		String line;
		System.out.println("Welcome to MyShell v 1.0");
		try {
			do {
				line = myEnvironment.readLine();
				line = readMoreLines(myEnvironment, line);
				if (line == null) {
					continue;
				}
				String commandName = line.split("\\s+")[0];
				if (commands.get(commandName) == null) {
					myEnvironment.writeln("There is no such command, please try again or type help");
				}
				String arguments = line.substring(commandName.length(), line.length()).trim();
				command = commands.get(commandName);

				status = command.executeCommand(myEnvironment, arguments);
			} while (status != ShellStatus.TERMINATE);
		} catch (IOException e) {
			System.err.println("Reading was not possible!");
		}
	}

	/**
	 * Helper method that determines if more lines are needed for reading. If it
	 * is needed it reads more lines.
	 * 
	 * @param env
	 *            Environment
	 * @param line
	 *            typed line
	 * @return full command
	 */
	private static String readMoreLines(Environment env, String line) {
		StringBuilder command = new StringBuilder();
		BufferedReader in = null;
		String input[] = null;

		if (moreLines(env, line)) {
			input = line.split("\\s+");
			command = makeCommand(command, 1, input);
		} else {
			return line;
		}
		while (true) {
			try {
				System.out.printf("%c ", env.getMultilineSymbol());
				in = new BufferedReader(new InputStreamReader(System.in));
				input = in.readLine().split("\\s+");
				if (moreLines(env, input.toString())) {
					command = makeCommand(command, 1, input);
					continue;
				}
				command = makeCommand(command, 0, input);
				break;
			} catch (Exception e) {
				System.out.println("Reading was not completed correctly");
				return null;
			}
		}

		return command.toString();
	}

	/**
	 * Helper method for making command.
	 * 
	 * @param command
	 *            that is built so far
	 * @param moreLines
	 *            if needed
	 * @param input
	 *            to build command from
	 * @return command
	 */
	private static StringBuilder makeCommand(StringBuilder command, Integer moreLines, String[] input) {
		for (int i = 0; i < input.length - moreLines; i++) {
			command.append(input[i] + " ");
		}
		return command;
	}

	/**
	 * Helper method for determining is there moreLine character at the end of
	 * line
	 * 
	 * @param env
	 *            Environment
	 * @param line
	 *            input
	 * @return true if there is moreLines character,false otherwise
	 */

	private static boolean moreLines(Environment env, String line) {
		String input[] = line.split("\\s+");
		if (input[input.length - 1].charAt(0) == env.getMorelinesSymbol()) {
			return true;
		}
		return false;
	}

	/**
	 * Helper method for initializing map with built-in commands
	 * 
	 * @return map with commands
	 */
	public static Map<String, ShellCommand> initializeMap() {
		Map<String, ShellCommand> commands = new HashMap<>();
		commands.put("charsets", new CharsetsShellCommand());
		commands.put("cat", new CatShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("tree", new TreeShellCommand());
		commands.put("copy", new CopyShellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		commands.put("hexdump", new HexdumpShellCommand());
		commands.put("exit", new ExitShellCommand());
		commands.put("symbol", new SymbolShellCommand());
		commands.put("help", new HelpShellCommand());
		return commands;
	}
}