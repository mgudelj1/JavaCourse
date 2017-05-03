package hr.fer.zemris.java.custom.collections;

/**
 * Implementacija kolekcije kao stoga
 * 
 * @author Marko Gudelj
 * @version 1.0
 */
public class ObjectStack {
	/** Konstruktor delegata posla ArrayIndexedCollection */
	private ArrayIndexedCollection delegate = new ArrayIndexedCollection();

	/**
	 * Konstruktor koji kopira sve elemente kolekcije
	 * 
	 * @param other kolekcija koja se kopira
	 * @param initialCapacity početni kapacitet
	 */
	public ObjectStack(Collection other, int initialCapacity) {
		this.delegate = new ArrayIndexedCollection(other, initialCapacity);
	}

	/**
	 * Konstruktor, kopira sve elemente kolekcije
	 * 
	 * @param other kolekcija
	 */
	public ObjectStack(Collection other) {
		this(other, 16);
	}

	/**
	 * Konstruktor koji postavlja stog na zadani kapacitet
	 * 
	 * @param initialCapacity kapacitet
	 */
	public ObjectStack(int initialCapacity) {
		this(null, initialCapacity);
	}

	/**
	 * Uobičajeni konstruktor, postavlja kapacitet stoga na 16
	 */
	public ObjectStack() {
		this(16);
	}

	/**
	 * Provjerava je li stog prazan.
	 * 
	 * @return true ako je prazan, false inače
	 */
	public boolean isEmpty() {
		return delegate.isEmpty();
	}

	/**
	 * Izračunava veličinu stoga.
	 * 
	 * @return vraća veličinu stoga
	 */
	public int size() {
		return delegate.size();
	}

	/**
	 * Dodaje zadani objekt na stog.
	 * 
	 * @param value objekt
	 */
	public void push(Object value) {
		delegate.add(value);
	}

	/**
	 * Skida i vraća zadnji element stoga.
	 * 
	 * @return zadnji element stoga
	 * 
	 * @throws EmptyStackException u slučaju praznog stoga
	 */
	public Object pop() {
		if (delegate.size() == 0) {
			throw new EmptyStackException();
		}
		Object pom;
		pom = delegate.get(size() - 1);
		delegate.remove(size() - 1);
		return pom;
	}

	/**
	 * Vraća zadnji element stoga, ali ga ne skida
	 * 
	 * @return zadnji element stoga
	 * 
	 * @throws EmptyStackException u slučaju praznog stoga
	 */
	public Object peek() {
		if (size() == 0) {
			throw new EmptyStackException();
		}
		return delegate.get(size() - 1);
	}

	/**
	 * Uklanja sve elemente stoga
	 */
	public void clear() {
		delegate.clear();
	}
}