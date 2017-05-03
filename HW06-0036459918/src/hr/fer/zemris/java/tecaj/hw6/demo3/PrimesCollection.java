package hr.fer.zemris.java.tecaj.hw6.demo3;

import java.util.Iterator;

/**
 * Class that represents collection of primes calculated only when needed to
 * 
 * @author Marko Gudelj
 *
 */
public class PrimesCollection implements Iterable<Integer> {
	/** number of primes wanted to calculate */
	private int nmbOfPrimes;

	/**
	 * Constructor
	 * 
	 * @param number
	 *            of primes
	 */
	public PrimesCollection(int number) {
		nmbOfPrimes = number;
	}

	/**
	 * Iterator for PrimesCollection.Each iteration calculates new prime number.
	 */
	@Override
	public Iterator<Integer> iterator() {

		Iterator<Integer> it = new Iterator<Integer>() {
			private int currentPrime = 2;
			private int newPrime;
			private int currentIndex = 0;

			@Override
			public boolean hasNext() {
				return currentIndex < nmbOfPrimes;
			}

			@Override
			public Integer next() {
				if (currentPrime == 2) {
					currentIndex++;
					currentPrime++;
					return 2;
				}

				newPrime = findNextPrime();
				currentPrime = newPrime + 2;
				return newPrime;
			}

			private int findNextPrime() {

				boolean prime = true;
				for (int i = 3; i < currentPrime; i++)
					if (currentPrime % i == 0) {
						prime = false;
					}
				if (prime) {
					currentIndex++;
					return currentPrime;
				} else {
					currentPrime += 2;
					return findNextPrime();
				}
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException("There is no need for using this method");
			}
		};
		return it;
	}

}
