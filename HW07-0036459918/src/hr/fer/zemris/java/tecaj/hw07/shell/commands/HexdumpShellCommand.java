package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Class represents hexdump shell command. Hexdump command expects a single
 * argument: file name, and produces hex-output
 * 
 * @author Marko Gudelj
 *
 */
public class HexdumpShellCommand implements ShellCommand {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			if (arguments.split("\\s+").length == 0) {
				env.writeln("Command hexdump accepts only one argument");
				return ShellStatus.CONTINUE;
			}
			Path file = Paths.get(UtilFinder.getPath(arguments));

			if (Files.isDirectory(file)) {
				env.writeln("File should be provided instead of directory");
				return ShellStatus.CONTINUE;
			}

			if (!Files.isReadable(file)) {
				env.writeln("File is not readable");
				return ShellStatus.CONTINUE;
			}
			hexOutput(env, file);
		} catch (IOException ignorable) {
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * Helper method for formatting output
	 * 
	 * @param env
	 *            environment
	 * @param file
	 *            path
	 */
	public static void hexOutput(Environment env, Path file) {
		FileInputStream is = null;
		try {
			is = new FileInputStream(file.toFile());
			int r = 0;
			int br = 0;
			String first = null;
			String second = null;
			while (true) {
				byte[] buffer = new byte[16];
				r = is.read(buffer);
				if (r < 1) {
					break;
				}
				if (r < 8) {
					first = bytesToHex(Arrays.copyOf(buffer, r), r);
				} else {
					first = bytesToHex(Arrays.copyOf(buffer, 8), 8);
					second = bytesToHex(Arrays.copyOfRange(buffer, 8, r), r - 8);
				}

				String text = ToString(buffer, r);
				String output = String.format("%08x: %-23s|%-24s| %s%n", br, first, second, text);
				env.writeln(output);
				br += r;
			}
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException ignorable) {
				}
			}
		}
	}

	/**
	 * Helper method for building string
	 * 
	 * @param buffer
	 *            for building string
	 * @param r
	 *            size of buffer(not capacity!)
	 * @return string
	 */
	private static String ToString(byte[] buffer, int r) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < r; i++) {
			int value = Byte.valueOf(buffer[i]).intValue();
			if (value < 32 || value > 127) {
				builder.append(".");
			} else {
				builder.append(Character.toChars(value));
			}
		}
		return builder.toString();
	}

	/**
	 * Helper method for converting bytes to hex
	 * 
	 * @param bytes
	 *            array
	 * @param r
	 *            size
	 * @return hex string representation
	 */
	public static String bytesToHex(byte[] bytes, int r) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < r; i++) {
			builder.append(String.format("%02X", bytes[i]));
			if (i < r - 1) {
				builder.append(" ");
			}
		}
		return builder.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "hexdump";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> lista = new ArrayList<>();
		lista.add("hexdump command expects a single argument: file name, and produces hex-output\n"
				+ "Each byte whose value is less than 32 or greater than 127 is replaced with '.' in output");
		lista.add("Example >hexdump \"C:\\Program Files\\test.txt\" ");
		lista = Collections.unmodifiableList(lista);
		return lista;

	}

}
