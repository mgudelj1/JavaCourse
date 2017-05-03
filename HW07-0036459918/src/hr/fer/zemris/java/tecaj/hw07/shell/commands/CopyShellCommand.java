package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import hr.fer.zemris.java.tecaj.hw07.crypto.BufferedOutputStream;
import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * The copy command expects two arguments: source file name and destination file
 * name (i.e. paths and names). User is asked is it allowed to overwrite it.
 * Copy command works only with files (no directories). If the second argument
 * is directory, assumed is that user wants to copy the original file into that
 * directory using the original file name.
 * 
 * @author Marko Gudelj
 *
 */
public class CopyShellCommand implements ShellCommand {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			if (arguments.split("\\s+").length == 0) {
				env.writeln("There has to be two arguments");
				return ShellStatus.CONTINUE;
			}
			Path srcPath = Paths.get(UtilFinder.getPath(arguments));

			if (!srcPath.toFile().isFile()) {
				env.writeln("Given argument is not a path to a file");
				return ShellStatus.CONTINUE;
			}

			if (!Files.isReadable(srcPath)) {
				env.writeln("Given file is not readable");
				return ShellStatus.CONTINUE;
			}

			Path destPath = Paths.get(UtilFinder.getSecondPath(arguments));

			if (Files.isDirectory(destPath)) {
				destPath = destPath.resolve(srcPath.getFileName());
			}

			if (Files.isRegularFile(destPath)) {
				env.writeln("File exists, do you wish to overwrite it? y/n");
				String input = env.readLine();
				if (input.toLowerCase().trim().compareTo("y") != 0) {
					return ShellStatus.CONTINUE;
				}
			}

			boolean status = copy(srcPath, destPath);
			if (!status) {
				env.writeln("Copy failed");
			}
		} catch (IOException ignorable) {
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * Method used for copying file. Copy uses buffered streams
	 * 
	 * @param src
	 *            path
	 * @param dest
	 *            path
	 * @return true if copied, false otherwise
	 */
	public boolean copy(Path src, Path dest) {
		InputStream is = null;

		try {
			is = new FileInputStream(src.toString());
			OutputStream os = new BufferedOutputStream(new FileOutputStream(dest.toString()));
			byte[] buff = new byte[4096];

			while (true) {
				int r = is.read(buff);
				if (r < 1)
					break;
				os.write(Arrays.copyOf(buff, r));
			}
			is.close();
			os.close();
		} catch (Exception ex) {
			return false;
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "copy";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> lista = new ArrayList<>();
		lista.add("The copy command expects two arguments: source file name and destination file"
				+ "name (i.e. paths and names).\n");
		lista.add("Copy command works only with files (no directories)\n");
		lista.add("If the second argument is directory, assumed is that\n"
				+ "user wants to copy the original file into that directory\n" + " using the original file name.");
		lista.add("Example >copy \"C:\\Program Files\\test.txt\" C:\\Users\\Marko\\Desktop");
		lista = Collections.unmodifiableList(lista);
		return lista;
	}

}
