package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Razred koji predstavlja podatkovni dio računala
 * 
 * @author Marko Gudelj
 *
 */
public class ComputerImpl implements Computer {
	/** Vrijednost koja predstavlja skup registara ovog računala */
	Registers registri;
	/** Vrijednost koja prestavlja memoriju ovog računala */
	Memory memorija;

	/**
	 * Konstruktor za razred ComputerImpl. Inicijalizira podatkovni dio računala
	 * sa zadanom size veličinom memorije i regsLen brojem registara
	 * 
	 * @param size
	 *            veličina memorije
	 * @param regsLen
	 *            broj registara
	 */
	public ComputerImpl(Integer size, Integer regsLen) {
		registri = new RegistersImpl(regsLen);
		memorija = new MemoryImpl(size);
	}

	/**
	 * Metoda koja vraća registre računala.
	 * 
	 * @return registri
	 */
	@Override
	public Registers getRegisters() {
		return registri;
	}

	/**
	 * Metoda koja vraća memoriju računala.
	 * 
	 * @return memorija računala
	 */
	@Override
	public Memory getMemory() {
		return memorija;
	}

}
