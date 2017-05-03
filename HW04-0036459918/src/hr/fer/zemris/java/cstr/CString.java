package hr.fer.zemris.java.cstr;

import java.util.Arrays;

/**
 * Base class which offers similar functionality as the old official (before
 * Java 7 update 6) implementation of the String class. This class represents
 * unmodifiable strings on which substring methods are executed in O(1)
 * complexity, which is achieved by sharing the character array.
 * 
 * @author Marko Gudelj
 *
 */
public class CString {
	/** Value of the string stored as array of characters */
	private char[] data;
	/** Offset of the stored string */
	private int offset;
	/** length of the character array */
	private int length;

	/**
	 * Public Constructor for class CString that creates new array consisting of
	 * characters from given data,offset and length.
	 * 
	 * @param data
	 *            Array of characters
	 * @param offset
	 *            initial offset
	 * @param length
	 *            length of new array
	 * 
	 * @throws IndexOutOfBoundsException
	 *             if the given offset or length are invalid
	 */
	public CString(char[] data, int offset, int length) {
		if (data == null) {
			throw new IllegalArgumentException();
		}
		if (offset < 0 || length < 0 || offset + length > data.length) {
			throw new IndexOutOfBoundsException();
		}
		this.data = makeDeepCopy(data, 0, data.length);

		this.offset = offset;
		this.length = length;

	}

	/**
	 * Public Constructor for class CString that makes a reference to the given
	 * array of characters
	 * 
	 * @param data
	 *            array of characters
	 */
	public CString(char[] data) {
		if (data == null) {
			throw new NullPointerException();
		}
		this.data = data;
		this.offset = 0;
		this.length = data.length;
	}

	/**
	 * Public Constructor for class CString that makes a deep copy if there is a
	 * subset of characters in original CString, if not makes a reference to the
	 * original CString
	 * 
	 * @param original
	 *            given CString
	 */
	public CString(CString original) {
		if(original == null){
			throw new IllegalArgumentException();
		}
		if (original.length < original.data.length) {
			this.data = makeDeepCopy(original.data, original.offset, original.length);
			
		} else {
			this.data = original.data;
		}
		this.length = original.length;
		this.offset = 0;
	}

	/**
	 * Helper method used in Constructors for making deep copy of the argument
	 * array
	 * 
	 * @param data2
	 *            source array of characters
	 * @param offset
	 *            given offset
	 * @param length
	 *            length of inner array
	 * @return reference to new array
	 */
	private char[] makeDeepCopy(char[] data2, int offset, int length) {
		int j = 0;
		int duljina = length + offset;
		char[] data1 = new char[length];
		for (int i = offset; i < duljina; i++) {
			data1[j] = data2[i];
			j++;
		}
		return data1;
	}

	/**
	 * Factory method that returns new CString object which has the same
	 * character data as given Java's String object
	 * 
	 * @param s
	 *            Java String object
	 * @return CString
	 */
	public static CString fromString(String s) {
		if (s == null) {
			throw new IllegalArgumentException();
		}
		char[] pomdata = s.toCharArray();
		return new CString(pomdata);
	}

	/**
	 * Method that returns value length
	 * 
	 * @return length value
	 */
	public int length() {
		return length;
	}

	/**
	 * Allocates a new array of length equals to length of this CString object
	 * (not its internal array which may be larger!), copies string content into
	 * it and returns it
	 * 
	 * @return array of characters
	 */
	public char[] toCharArray() {
		return makeDeepCopy(this.data, this.offset, this.length);
	}

	/**
	 * Returns character at given index
	 * 
	 * @param index
	 *            of character
	 * @return character at index
	 * 
	 * @throws IndexOutOfBoundsException
	 *             if index is invalid
	 */
	public char charAt(int index) {
		if (index + offset >= this.length || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		return this.data[offset + index];
	}

	/**
	 * Method used for conversion of CString into String
	 * 
	 * @return String representation of CString
	 */
	public String toString() {
		return String.valueOf(makeDeepCopy(this.data, this.offset, this.length));
	}

	/**
	 * Method for finding first occurrence of character c
	 * 
	 * @param c
	 *            character to find
	 * @return index of first occurrence
	 */
	public int indexOf(char c) {
		for (int i = offset; i < length; i++) {
			if (data[i] == c) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Transforms given CString to array and compares it to current array. If
	 * first s.length characters of current array are equal to the given array
	 * returns true, else return false
	 * 
	 * @param s
	 *            CString to compare
	 * @return true if starts with, else false
	 */
	public boolean startsWith(CString s) {
		if (s == null) {
			throw new IllegalArgumentException();
		}
		if (s.length > this.length) {
			return false;
		}
		return Arrays.equals(s.toCharArray(), makeDeepCopy(this.data, this.offset, s.length));
	}

	/**
	 * Transforms given CString to array and compares it to current array. If
	 * last s.length characters of current array are equal to the given array
	 * returns true, else return false
	 * 
	 * @param s
	 *            CString to compare
	 * @return true if ends with, else false
	 */
	public boolean endsWith(CString s) {
		if (s == null) {
			throw new IllegalArgumentException();
		}
		if (s.length > this.length) {
			return false;
		}
		return Arrays.equals(s.toCharArray(), makeDeepCopy(this.data, this.length - s.length, s.length));
	}

	/**
	 * Transforms given CString to array and compares it to current array. If
	 * characters of given array are contained in current array returns true,
	 * else return false
	 * 
	 * @param s
	 *            CString to check
	 * @return true if contains, else false
	 */
	public boolean contains(CString s) {
		if (s == null) {
			throw new IllegalArgumentException();
		}
		char[] pomdata = s.toCharArray();
		if (pomdata.length > this.length) {
			return false;
		}
		for (int i = offset; i < length - pomdata.length + 1; i++) {
			if (Arrays.equals(pomdata, makeDeepCopy(this.data, i, s.length))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * returns new CString which represents a part of original string; position
	 * endIndex does not belong to the substring; it holds: startIndex>=0,
	 * endIndex>=startIndex; its complexity is O(1)
	 * 
	 * @param startIndex
	 *            starting index
	 * @param endIndex
	 *            ending index
	 * @return CString
	 */
	public CString substring(int startIndex, int endIndex) {
		if (startIndex < 0 || endIndex < startIndex || endIndex > this.length) {
			throw new IndexOutOfBoundsException();
		}

		return new CString(offset + startIndex, endIndex - startIndex, this.data);
	}

	/**
	 * Private Constructor for CString
	 * 
	 * @param offset
	 *            offset
	 * @param length
	 *            length
	 * @param data
	 *            array of characters
	 */
	private CString(int offset, int length, char[] data) {
		this.data = data;
		this.offset = offset;
		this.length = length;
	}

	/**
	 * Factory method that returns new CString which represents starting part of
	 * original string and is of length n Throws an exception if this can not be
	 * constructed
	 * 
	 * @param n
	 *            length of substring
	 * @return CString
	 */
	public CString left(int n) {
		return substring(0, n);
	}

	/**
	 * Factory method that returns new CString which represents ending part of
	 * original string and is of length n Throws an exception if this can not be
	 * constructed
	 * 
	 * @param n
	 *            length of substring
	 * @return CString
	 */
	public CString right(int n) {
		return substring(this.length - n, length);
	}

	/**
	 * Factory method that returns new CString which represents concatenation of
	 * original string and given string Throws an exception if this can not be
	 * constructed
	 * 
	 * @param s
	 *            CString to concatenate
	 * @return new CString
	 */
	public CString add(CString s) {
		if (s == null) {
			throw new IllegalArgumentException();
		}
		char[] pomdata = new char[length + s.length];
		for (int i = 0; i < length; i++) {
			pomdata[i] = this.data[i];
		}
		int j = 0;
		for (int i = length; i < length + s.length; i++) {
			pomdata[i] = s.data[j];
			j++;
		}

		return new CString(pomdata, 0, this.length + s.length);
	}

	/**
	 * Factory method that creates a new CString in which each occurrence of old
	 * character is replaced with new character
	 * 
	 * @param oldChar
	 *            character that needs to be replaced
	 * @param newChar
	 *            character to be replaced with
	 * @return new CString
	 */
	public CString replaceAll(char oldChar, char newChar) {
		if (!Character.isDefined(oldChar) || !Character.isDefined(newChar)) {
			throw new IllegalArgumentException();
		}
		if (oldChar == newChar) {
			return new CString(0, length, data);
		}
		char[] pomdata = makeDeepCopy(this.data, this.offset, this.length);

		for (int i = offset; i < length; i++) {
			if (pomdata[i] == oldChar) {
				pomdata[i] = newChar;
			}
		}
		return new CString(0, this.length, pomdata);
	}

	/**
	 * Factory method that creates a new CString in which each occurrence of old
	 * subString is replaced with new subString
	 * 
	 * @param oldStr
	 *            substring to be replaced
	 * @param newStr
	 *            substring to replace with
	 * @return new CString
	 */
	public CString replaceAll(CString oldStr, CString newStr) {
		if (oldStr == null || newStr == null) {
			throw new IllegalArgumentException();
		}
		if (this.contains(oldStr)) {
			char[] pomdata;
			char[] pomdata2 = makeDeepCopy(this.data, offset, length);

			CString pom1 = new CString(new char[0], 0, 0);
			CString pom2;

			for (int i = offset; i < pomdata2.length; i++) {
				if (pomdata2.length - i < oldStr.length) {
					pomdata = new char[1];
					pomdata[0] = pomdata2[i];
					pom2 = new CString(pomdata, 0, 1);
					pom1 = pom1.add(pom2);
					continue;
				}
				pomdata = makeDeepCopy(pomdata2, i, oldStr.length);
				if (Arrays.equals(pomdata, oldStr.data)) {
					pom1 = pom1.add(newStr);
					i += oldStr.length - 1;
				} else if (i == pomdata2.length - oldStr.length) {

					pom2 = new CString(pomdata, 0, oldStr.length);
					pom1 = pom1.add(pom2);
					return pom1;
				} else {
					pomdata = new char[1];
					pomdata[0] = pomdata2[i];
					pom2 = new CString(pomdata, 0, 1);
					pom1 = pom1.add(pom2);
				}
			}
			return pom1;
		} else {
			return new CString(this.offset, this.length, this.data);
		}
	}

}