package hr.fer.zemris.java.tecaj.hw07.crypto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * This class represents program used for checking SHA-256 digest with entered
 * checksum, and it is used for encrypting and decrypting files using AES
 * algorithm Accepts three arguments, first should be encrypt, decrypt or
 * checksha. In case of encryption next two arguments should be input file name
 * and output file name. If checksha is used next argument should be input file
 * name.
 * 
 * @author Marko Gudelj
 *
 */
public class Crypto {
	/**
	 * Main method used as entry point to program.
	 * 
	 * @param args
	 *            method and input and output file names
	 */
	public static void main(String[] args) {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		try {
			if (args.length < 2 || args.length > 3) {
				throw new IllegalArgumentException("There has to be 2 or 3 arguments");
			}
			if (args[0].equals("checksha")) {
				System.out.println("Please provide expected sha-256 digest for hw07part2.pdf:");
				String checksum = in.readLine();
				checkDigest(args[1], checksum);
				in.close();
				System.exit(0);
			}
			if (args[0].equals("encrypt")) {
				cryptIt(true, args[1], args[2]);
			} else if (args[0].equals("decrypt")) {
				cryptIt(false, args[1], args[2]);
			} else {
				System.err.println("First argument should be checksha, encrypt or decrypt");
			}
		} catch (IOException e) {
			System.out.println("Reading was not possible");
		}
	}

	/**
	 * Helper method for converting hex string to byte arrays
	 * 
	 * @param keyText
	 *            input string
	 * @return byte array
	 */
	public static byte[] hextobyte(String keyText) {
		String s2;
		byte[] b = new byte[keyText.length() / 2];
		int i;
		for (i = 0; i < keyText.length() / 2; i++) {
			s2 = keyText.substring(i * 2, i * 2 + 2);
			b[i] = (byte) (Integer.parseInt(s2, 16) & 0xff);
		}
		return b;
	}

	/**
	 * Helper method for crypting or decrypting given data. Method uses AES
	 * algorithm for encryption. Whole method is buffered with 4K buffer.
	 * 
	 * @param encrypt
	 *            if true method is used for encryption, decryption otherwise
	 * @param inputFileName
	 *            file for crypting
	 * @param outputFileName
	 *            new file
	 */
	private static void cryptIt(boolean encrypt, String inputFileName, String outputFileName) {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String keyText = null;
		String ivText = null;
		File dir = new File("./" + inputFileName);
		InputStream is = null;

		try {
			System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
			keyText = in.readLine();
			System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
			ivText = in.readLine();

			SecretKeySpec keySpec = new SecretKeySpec(hextobyte(keyText), "AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(hextobyte(ivText));
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);

			try {
				OutputStream os = new BufferedOutputStream(new FileOutputStream("./" + outputFileName));
				is = new FileInputStream(dir);
				byte[] buff = new byte[4096];

				while (true) {
					int r = is.read(buff);
					if (r < 1)
						break;
					os.write(cipher.update(Arrays.copyOf(buff, r)));
				}
				os.write(cipher.doFinal());
				os.close();
			} catch (IOException ex) {
				System.err.println("Something went wrong during reading of file");
				System.exit(0);
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException ignorable) {
					}
				}
			}

			if (encrypt) {
				System.out.printf("Encryption completed. Generated file %s based on file %s", outputFileName,
						inputFileName);
			} else {
				System.out.printf("Decryption completed. Generated file %s based on file %s", outputFileName,
						inputFileName);
			}

			in.close();
		} catch (Exception e) {
			System.err.println("Text could not be de(en)crypted");
		}
	}

	/**
	 * Helper method used for calculation and checking message digest. Method
	 * uses SHA-256 algorithm for calculation of digest. Result of calculation
	 * and check is printed to standard output.
	 * 
	 * @param fileName
	 *            file name for digest
	 * @param checksum
	 *            for checking with digest
	 */
	private static void checkDigest(String fileName, String checksum) {
		File dir = new File("./" + fileName);
		InputStream is = null;

		MessageDigest sha = null;
		try {
			sha = MessageDigest.getInstance("SHA-256");
			is = new FileInputStream(dir);
			byte[] buff = new byte[4096];

			while (true) {
				int r = is.read(buff);
				if (r < 1)
					break;
				sha.update(Arrays.copyOf(buff, r));
			}
		} catch (Exception ex) {
			System.err.println("Something went wrong during reading of file");
			System.exit(0);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException ignorable) {
				}
			}
		}
		byte[] digest = sha.digest();
		if (Arrays.equals(digest, hextobyte(checksum))) {
			System.out.printf("Digesting completed. Digest of %s matches expected digest.", fileName);
		} else {
			System.out.printf(
					"Digesting completed. Digest of %s does not match the expected digest. " + "\nDigest was: %s",
					fileName, String.format("%064x", new java.math.BigInteger(1, digest)));
		}
	}
}
