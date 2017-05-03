package hr.fer.zemris.java.fractals;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Class used as factory for creating daemonic threads
 * 
 * @author Marko Gudelj
 *
 */
public class DaemonicThreadFactory implements ThreadFactory {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Thread newThread(Runnable r) {
		Thread thread = Executors.defaultThreadFactory().newThread(r);
		thread.setDaemon(true);
		return thread;
	}

}
