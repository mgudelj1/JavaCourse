package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Class represents cat shell command.
 * Command cat takes one or two arguments. The first argument is path to some
 * file and is mandatory. The second argument is charset name that should be
 * used to interpret chars from bytes. If not provided, a default platform
 * charset should be used (see java.nio.charset.Charset class for details). This
 * command opens given file and writes its content to console.
 * 
 * @author Marko Gudelj
 *
 */
public class CatShellCommand implements ShellCommand {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Charset c = Charset.defaultCharset();
		String path;
		try {

			path = UtilFinder.getPath(arguments);

			try {
				if (Charset.isSupported(UtilFinder.getCharset(arguments))) {
					c = Charset.forName(UtilFinder.getCharset(arguments));
				}
			} catch (Exception charset) {
				System.err.println("Given Charset is not supported");
				return ShellStatus.CONTINUE;
			}

			if (path == null) {
				env.writeln("Shell command cat takes one or two arguments");
				return ShellStatus.CONTINUE;
			}

			File dir = new File(path);
			InputStream is = null;
			byte[] buff = new byte[4096];
			is = new FileInputStream(dir);

			while (true) {
				int r = is.read(buff);
				if (r < 1)
					break;
				String tekst = new String(Arrays.copyOf(buff, r), c);
				env.write(tekst);
			}

			env.writeln("");
			is.close();
		} catch (FileNotFoundException e) {
			System.out.println("Pathname for the given cat command is invalid");
			return ShellStatus.CONTINUE;
		} catch (IOException ex) {
			System.err.println("Something went wrong during reading of file");
			return ShellStatus.CONTINUE;
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "cat";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> lista = new ArrayList<>();
		lista.add("1.) Cat command accepts one argument, that argument is a path to the file\n"
				+ "i.e. cat C:\\Users\\Marko\\Desktop\\DzJava\\DZ7\\HW07-0036459918\\test.txt\n");
		lista.add("2.) Cat command accepts two arguments, first one is path to the file, second\n"
				+ "one is Charset that needs to be used, if there is no second argument, default\n"
				+ "charset will be used. Example >cat C:\\Users..test.txt ISO-8859-2");
		lista = Collections.unmodifiableList(lista);
		return lista;
	}

}
