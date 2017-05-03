package hr.fer.zemris.java.custom.collections;

/**
 * Implementacija kolekcije objekata kao polja sa promjenjivom veličinom.
 * Nasljeđuje metode iz razreda Collection.
 * 
 * @author Marko
 * @version 1.0
 */
public class ArrayIndexedCollection extends Collection {

	/** Trenutna veličina polja (broj elemenata u polju)*/
	private int size;
	/** Kapacitet polja */
	private int capacity;
	/** Polje referenca na objekte */
	private Object[] elements;

	/**
	 * Konstruktor sa dva argumenta, ako je zadana kolekcija dodaje cijelu
	 * kolekciju u polje. Kapacitet ne smije biti manji od 1.
	 * 
	 * @param other kolekcija koja se dodaje
	 * @param initialCapacity kapacitet polja
	 */
	public ArrayIndexedCollection(Collection other, int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException();
		}
		this.capacity = initialCapacity;
		this.elements = new Object[this.capacity];
		this.size = 0;
		if (other != null) {
			addAll(other);
		}

	}

	/**
	 * Konstruktor sa jednim argumentom kolekcije, poziva konstruktor sa dva
	 * argumenta.
	 * 
	 * @param other Kolekcija koja se dodaje
	 */
	public ArrayIndexedCollection(Collection other) {
		this(other, 16);
	}

	/**
	 * Konstruktor sa jednim argumentom kapaciteta, poziva konstruktor sa dva
	 * argumenta, predajući praznu kolekciju.
	 * 
	 * @param initialCapacity kapacitet polja
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		this(null, initialCapacity);
	}

	/**
	 * Uobičajeni konstruktor bez argumenata. Poziva konstruktor sa argumentom
	 * kapaciteta 16.
	 */
	public ArrayIndexedCollection() {
		this(16);
	}

	/**
	 * Izračunava veličinu kolekcije.
	 * 
	 * @return vraća veličinu kolekciju
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Dodaje objekt u kolekciju na prvo prazno mjesto. Ako je predana
	 * vrijednost null vraća iznimku. U slučaju da je kolekcija popunjena poziva
	 * metodu needToResize(); Prosječna složenost O(1).
	 * 
	 * @param value objekt koji treba dodati
	 * 
	 * @throws IllegalArgumentException ako je zadana null vrijednost
	 */
	@Override
	public void add(Object value) {
		if (value == null) {
			throw new IllegalArgumentException();
		}

		needToResize(this);

		elements[size] = value;
		size++;
	}

	/**
	 * Provjerava sadrži li kolekcija neki objekt.
	 * 
	 * @param value objekt koji treba provjeriti
	 * @return true ako postoji, false inače
	 */
	@Override
	public boolean contains(Object value) {
		if(value == null){
			throw new IllegalArgumentException();
		}
		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Uklanja objekt iz kolekcije.
	 * 
	 * @param value objekt koji treba ukloniti
	 * @return true ako je uklonjen, false inače
	 */
	@Override
	public boolean remove(Object value) {
		if( value == null){
			throw new IllegalArgumentException();
		}

		for (int i = 0; i < size-1; i++) {
			if (elements[i].equals(value)) {
				elements[i] = null;
				
				for (; i < (size - 1); i++) {
					elements[i] = elements[i + 1];
				}
				size--;
				return true;
			}
		}
		return false;
	}

	/**
	 * Stvara novo polje veličine pozivajuće kolekcije, puni ih sa sadržajem
	 * kolekcije.
	 * 
	 * @return polje
	 */
	@Override
	public Object[] toArray() {
		Object[] array = new Object[size()];
		for (int i = 0; i < size; i++) {
			array[i] = elements[i];
		}
		return array;
	}

	/**
	 * Metoda koja poziva processor.process za svaki element kolekcije.
	 * 
	 * @param processor Processor
	 */
	@Override
	public void forEach(Processor processor) {
		for (int i = 0; i < size; i++) {
			processor.process(elements[i]);
		}
	}

	/**
	 * Vraća objekt na traženom indexu, ako je unesen neispravan index, baca
	 * iznimku
	 * 
	 * @param index index objekta
	 * @return objekt ako postoji
	 */
	public Object get(int index) {
		if (legalIndex(index)) {
			return elements[index];
		}
		return null;
	}

	/**
	 * Metoda koja uklanja sve elemente kolekcije.
	 */
	@Override
	public void clear() {
		size = 0;
		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}
	}
	
	/**
	 * Dodaje objekt na zadanu poziciju, ne briše element na poziciji nego ih
	 * pomiče za jedno mjesto prema kraju. Ako je polje popunjeno poziva se
	 * metoda needToResize(). Prosječna složenost je O(n).
	 * 
	 * @param value objekt koji se dodaje
	 * @param position pozicija na koju se dodaje
	 * 
	 * @throws IndexOutOfBoundsException ako je index neispravan
	 * 
	 */
	public void insert(Object value, int position) {
		if (position < 0 || position > size) {
			throw new IndexOutOfBoundsException();
		}

		needToResize(this);

		for (int i = 0; i < (size - position); i++) {
			elements[size - i] = elements[size - 1 - i];
		}
		elements[position] = value;
		size++;
	}

	/**
	 * Pretraživa kolekciju za zadanim objektom. Prosječna složenost je O(n)
	 * 
	 * @param value objekt koji se traži
	 * @return index prvog nađenog objekta, inače -1
	 */
	public int indexOf(Object value) {
		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Uklanja element na zadanom indexu iz kolekcije. Nakon izvršenja element
	 * sa index+1 je na index.
	 * 
	 * @param index index elementa
	 */
	public void remove(int index) {
		if (legalIndex(index)) {
			for (; index < (size - 1); index++) {
				elements[index] = elements[index + 1];
			}
			elements[size - 1] = null;
			size--;
		}
	}

	/**
	 * Provjerava je li potrebno povećati kapacitet polja, ako da udupla ga.
	 * 
	 * @param other kolekcija za koju se provjerava
	 */
	private void needToResize(Collection other) {
		if (size() == capacity) {
			Object[] temp = new Object[2 * capacity];
			for (int i = 0; i < size; i++) {
				temp[i] = elements[i];
			}
			elements = temp;
		}
	}

	/**
	 * Provjerava je li zadan ispravni index polja
	 * Ako je unesen neispravan index baca iznimku
	 * IndexOutOfBoundsException
	 * 
	 * @param index index koji se provjerava
	 * @return true ako je ispravan
	 * 
	 * @throws IndexOutOfBoundsException ako je neispravan index
	 */
	private boolean legalIndex(int index) {
		if (index < 0 || index > size() - 1) {
			throw new IndexOutOfBoundsException();
		}
		return true;
	}

}
