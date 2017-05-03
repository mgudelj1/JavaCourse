package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Class that represents tree shell command. The tree command expects a single
 * argument: directory name and prints a tree (each directory level shifts
 * output two characters to the right).
 * 
 * @author Marko Gudelj
 *
 */
public class TreeShellCommand implements ShellCommand {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			if (arguments.split("\\s+").length == 0) {
				env.writeln("Command tree accepts one argument");
				return ShellStatus.CONTINUE;
			}
			Path directory = Paths.get(UtilFinder.getPath(arguments));

			if (!Files.isDirectory(directory)) {
				env.writeln("Given argument is not a path to the directory");
				return ShellStatus.CONTINUE;
			}

			Files.walkFileTree(directory, new Ispis());
		} catch (Exception ignorable) {
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * Static class that represents Lister
	 * 
	 * @author Marko Gudelj
	 *
	 */
	private static class Ispis implements FileVisitor<Path> {
		/** Depth of directory */
		private int level;

		/**
		 * Method for printing to standard output
		 * 
		 * @param f
		 *            path
		 */
		private void ispisi(Path f) {
			if (level == 0) {
				System.out.println(f.normalize().toAbsolutePath());
			} else {
				System.out.printf("%" + (2 * level) + "s%s%n", "", f.getFileName());
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			ispisi(dir);
			level++;
			return FileVisitResult.CONTINUE;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			ispisi(file);
			return FileVisitResult.CONTINUE;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
			return FileVisitResult.CONTINUE;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			level--;
			return FileVisitResult.CONTINUE;
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "tree";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> lista = new ArrayList<>();
		lista.add("The tree command expects a single argument: directory name\n"
				+ "Example >tree C:\\Users\\Marko\\Desktop\\");
		lista = Collections.unmodifiableList(lista);
		return lista;
	}

}
