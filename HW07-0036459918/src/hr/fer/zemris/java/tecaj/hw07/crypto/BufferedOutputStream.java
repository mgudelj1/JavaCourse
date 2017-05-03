package hr.fer.zemris.java.tecaj.hw07.crypto;

import java.io.IOException;
import java.io.OutputStream;

/**
 * BufferedOutputStream used for buffering output.
 * 
 * @author Marko Gudelj
 *
 */
public class BufferedOutputStream extends OutputStream {
	/** Container represented as byte array of 4K */
	private byte[] spremnik = new byte[4096];
	/** Number of data */
	private int podataka = 0;
	/** Stream */
	private OutputStream osnovni;

	/**
	 * Constructor
	 * 
	 * @param osnovni stream
	 */
	public BufferedOutputStream(OutputStream osnovni) {
		this.osnovni = osnovni;
	}

	/**
	 * Method used for emptying rest of container. If there is anything left, it
	 * will be written, then discarded
	 * 
	 * @throws IOException
	 *             if writing not possible
	 */
	private void isprazni() throws IOException {
		if (podataka > 0) {
			osnovni.write(spremnik, 0, podataka);
		}
		podataka = 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write(int b) throws IOException {
		if (podataka < spremnik.length) {
			spremnik[podataka++] = (byte) b;
			return;
		}
		isprazni();
		spremnik[podataka++] = (byte) b;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void close() throws IOException {
		isprazni();
		osnovni.close();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void flush() throws IOException {
		isprazni();
		osnovni.flush();
	}

}