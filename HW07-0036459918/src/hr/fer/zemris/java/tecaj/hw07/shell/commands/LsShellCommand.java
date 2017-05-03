package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Class represents ls shell command.
 * Command ls takes a single argument – directory – and writes a directory
 * listing (not recursive). The output consists of 4 columns. First column
 * indicates if current object is directory (d), readable (r), writable (w) and
 * executable (x). Second column contains object size in bytes that is right
 * aligned and occupies 10 characters. Follows file creation date/time and
 * finally file name.
 * 
 * @author Marko Gudelj
 *
 */
public class LsShellCommand implements ShellCommand {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			if (arguments.split("\\s+").length == 0) {
				env.writeln("Command ls accepts one argument");
				return ShellStatus.CONTINUE;
			}
			File datoteka = new File(UtilFinder.getPath(arguments));
			if (!datoteka.isDirectory()) {
				env.writeln("Given path is not a directory");
				return ShellStatus.CONTINUE;
			}
			printDirectoryListing(env, datoteka);

		} catch (IOException ignorable) {
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * Helper method for printing directory listing to the standard output
	 * 
	 * @param env
	 *            Environment
	 * @param datoteka
	 *            file
	 * @throws IOException
	 *             when writing not possible
	 */
	private static void printDirectoryListing(Environment env, File datoteka) throws IOException {
		File[] children = datoteka.listFiles();
		if (children != null) {
			List<File> files = Arrays.asList(children);
			files.sort((z1, z2) -> z1.compareTo(z2));
		}
		StringBuilder output;
		for (File f : children) {
			output = new StringBuilder();
			output.append(getDrwx(f) + " " + getSize(f) + " " + getDate(f) + " " + f.getName());
			env.writeln(output.toString());
		}

	}

	/**
	 * Method used for getting information bits.
	 * 
	 * @param f
	 *            file
	 * @return bits for given file
	 */
	private static String getDrwx(File f) {
		StringBuilder string = new StringBuilder();
		string.append(f.isDirectory() ? "d" : "-");
		string.append(f.canRead() ? "r" : "-");
		string.append(f.canWrite() ? "w" : "-");
		string.append(f.canExecute() ? "x" : "-");
		return string.toString();
	}

	/**
	 * Helper method for getting file size.
	 * 
	 * @param f
	 *            file
	 * @return size
	 */
	private static String getSize(File f) {
		long size = f.length();
		return String.format("%10d", size);
	}

	/**
	 * Helper method for getting creation date for file
	 * 
	 * @param f
	 *            file
	 * @return string that represents creation date
	 */
	private static String getDate(File f) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Path path = f.toPath();
		BasicFileAttributeView faView = Files.getFileAttributeView(path, BasicFileAttributeView.class,
				LinkOption.NOFOLLOW_LINKS);
		BasicFileAttributes attributes = null;
		try {
			attributes = faView.readAttributes();
		} catch (IOException e) {
			return null;
		}
		FileTime fileTime = attributes.creationTime();
		String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
		return formattedDateTime;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> lista = new ArrayList<>();
		lista.add("Command ls takes single argument, directory.\n"
				+ "If directory is not given command will not be executed");
		lista.add("Example >ls C:\\Users\\Marko\\Desktop\\ will print listing for Dekstop");
		lista = Collections.unmodifiableList(lista);
		return lista;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "ls";
	}

}
