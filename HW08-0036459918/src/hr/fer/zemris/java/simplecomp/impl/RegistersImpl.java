package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Razred koji predstavlja sve registre u procesoru.
 * 
 * Postoje registri opće namjene za čiji rad služe metode getRegisterValue(int)
 * i setRegisterValue(int, Object), programsko brojilo koje predstavlja adresu
 * sljedeće instrukcije koju treba izvesti, (metode getProgramCounter(),
 * incrementProgramCounter(),setProgramCounter(int)), te registar zastavice
 * (metode getFlag() setFlag(boolean)). Za razliku od klasičnih procesorskih
 * registara koji pohranjuju samo brojeve i fiksne su širine, registri opće
 * namjene koje ovdje koristimo mogu pohranjivati proizvoljno velike objekte
 * različitih tipova String, Integer.
 * 
 * Razred sadrži i konstantu STACK_REGISTER_INDEX koja predstavlja indeks
 * registra koji će se koristiti za pohranu kazaljke stoga. Prilikom obrade
 * asemblerskog programa, parser će automatski napuniti opći registar čiji je to
 * indeks adresom memorijske lokacije koja je označena labelom "@stackTop" ako
 * takva postoji. Ako ne postoji, taj će registar ostati neinicijaliziran!
 * 
 * @author Marko Gudelj
 *
 */
public class RegistersImpl implements Registers {
	/** Polje koje predstavlja registre opće namjene */
	Object[] registri;
	/** Vrijednost programskog brojila */
	Integer cntr;
	/** Vrijednost zastavice */
	boolean flag;

	/**
	 * Konstruktor za RegistersImpl. Postavlja broj registara na zadanu veličinu
	 * 
	 * @param regsLen
	 *            broj registara
	 */
	public RegistersImpl(int regsLen) {
		if (regsLen < 16) {
			throw new IllegalArgumentException("Broj registara procesora ne može biti manji od 16");
		}
		registri = new Object[regsLen];
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getRegisterValue(int index) {
		indexLegit(index);
		return registri[index];
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setRegisterValue(int index, Object value) {
		indexLegit(index);
		registri[index] = value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getProgramCounter() {
		return cntr;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setProgramCounter(int value) {
		cntr = value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void incrementProgramCounter() {
		cntr++;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean getFlag() {
		return flag;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setFlag(boolean value) {
		flag = value;
	}

	/**
	 * Pomoćna metoda za određivanje ispravnosti indexa
	 * 
	 * @param index
	 *            of register
	 * @return true if valid,else throw exception
	 * 
	 * @throws IndexOutOfBoundsException
	 *             if register is not valid
	 */
	private boolean indexLegit(Integer index) {
		if (index < 0 || index >= registri.length) {
			throw new IndexOutOfBoundsException("Wanted register is not available");
		}
		return true;
	}
}
