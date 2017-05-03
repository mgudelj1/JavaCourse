package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Memory;

/**
 * Razred koji predstavlja memoriju računala. Za razliku od klasične memorije
 * računala koja je bajtno orijentirana, ova implementacija memorija na svakoj
 * lokaciji može pohraniti proizvoljno veliki (jedan) objekt, a koristit ćemo
 * Stringove i Integere. Prazna memorija na svim lokacijama ima vrijednost null.
 * 
 * @author Marko Gudelj
 *
 */
public class MemoryImpl implements Memory {
	/** Polje koje predstavlja memoriju računala */
	Object[] memorija;

	/**
	 * Konstruktor za MemoryImpl. Inicijalizira polje veličine size
	 * 
	 * @param size broj memorijskih lokacija
	 */
	public MemoryImpl(int size) {
		if (size < 1) {
			throw new IllegalArgumentException("Memorija računala ne može biti manja od 1");
		}
		memorija = new Object[size];

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setLocation(int location, Object value) {
		if (location < 0 || location >= memorija.length) {
			throw new IndexOutOfBoundsException("Wanted location is not available");
		}
		memorija[location] = value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getLocation(int location) {
		if (location < 0 || location >= memorija.length) {
			throw new IndexOutOfBoundsException("Wanted location is not available");
		}
		return memorija[location];
	}
}
