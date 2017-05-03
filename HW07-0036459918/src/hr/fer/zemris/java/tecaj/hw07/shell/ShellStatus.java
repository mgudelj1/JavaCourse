package hr.fer.zemris.java.tecaj.hw07.shell;
/**
 * Shell status. Depending on status shell is terminated
 * or continues to run.
 * @author Marko Gudelj
 *
 */
public enum ShellStatus {
	/**
	 * Every command gives this status except exit. Shell continues to run until
	 * TERMINATE is received.
	 */
	CONTINUE,
	/**
	 * Only exit gives this status. If given shell is terminated
	 */
	TERMINATE;

}
