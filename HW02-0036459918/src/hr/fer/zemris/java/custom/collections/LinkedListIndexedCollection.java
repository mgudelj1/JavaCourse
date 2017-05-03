package hr.fer.zemris.java.custom.collections;

/**
 * Implementacija kolekcije objekata kao liste povezanih elemenata. Nasljeđuje
 * razred Collection.
 * 
 * @author Marko Gudelj
 * @version 1.0
 */
public class LinkedListIndexedCollection extends Collection {
	/** Veličina liste (broj elemenata u listi) */
	int size;
	/** Referenca na prvi element liste */
	ListNode first;
	/** Referenca na zadnji element liste */
	ListNode last;

	/**
	 * Razred koji predstavlja element liste.
	 * 
	 * @author Marko
	 * @version 1.0
	 */
	private static class ListNode {
		/** Referenca na prethodni element liste */
		ListNode previous;
		/** Referenca na idući element liste */
		ListNode next;
		/** Objekt u elementu liste (podatak) */
		Object value;
	}

	/**
	 * Konstruktor koji dodaje sve elemente dane kolekcije u listu.
	 * 
	 * @param other kolekcija koja se dodaje
	 */
	public LinkedListIndexedCollection(Collection other) {
		if (other == null) {
			this.size = 0;
			first = null;
			last = null;
		} else {
			addAll(other);
		}
	}

	/**
	 * Uobičajeni konstruktor.
	 */
	public LinkedListIndexedCollection() {
		this(null);
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
	 * Dodaje objekt u listu na zadnje mjesto. Ako je predana vrijednost null
	 * vraća iznimku. Prosječna složenost O(1).
	 * 
	 * @param value objekt koji treba dodati
	 * 
	 * @throws IllegalArgumentException u slučaju zadane vrijednosti null
	 */
	@Override
	public void add(Object value) {
		if (value == null) {
			throw new IllegalArgumentException();
		}
		ListNode pomCvor = new ListNode();

		if (size() == 0) {
			first = pomCvor;
			last = pomCvor;
			pomCvor.next = null;
			pomCvor.previous = null;
			pomCvor.value = value;
			size++;
		} else {
			last.next = pomCvor;
			pomCvor.next = null;
			pomCvor.previous = last;
			last = pomCvor;
			pomCvor.value = value;
			size++;
		}
	}

	/**
	 * Provjerava sadrži li lista neki objekt.
	 * 
	 * @param value objekt koji treba provjeriti
	 * @return true ako postoji, false inače
	 */
	@Override
	public boolean contains(Object value) {
		ListNode pomCvor = new ListNode();
		pomCvor = first;
		for (int i = 0; i < size - 1; i++) {
			if (pomCvor.value.equals(value)) {
				return true;
			}
			pomCvor = pomCvor.next;
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
		ListNode pomCvor = new ListNode();
		pomCvor = first;

		for (int i = 0; i < size; i++) {
			if (pomCvor.value.equals(value)) {
				if (i == 0) {
					deleteFirst();
					return true;
				} else if (i == (size - 1)) {
					deleteLast();
					return true;
				} else {
					izbaciIzListe(pomCvor);
					return true;
				}
			}
			pomCvor = pomCvor.next;
		}
		return false;
	}

	/**
	 * Stvara novu listu veličine pozivajuće kolekcije, elementi liste su
	 * elementi kolekcije.
	 * 
	 * @return novu listu
	 */
	@Override
	public Object[] toArray() {
		Object[] array = new Object[size()];
		ListNode pomCvor = new ListNode();
		pomCvor = first;

		for (int i = 0; i < size; i++) {
			array[i] = pomCvor.value;
			pomCvor = pomCvor.next;
		}
		return array;
	}

	/**
	 * Metoda koja poziva processor.process za svaki element liste.
	 * 
	 * @param processor Processor
	 */
	@Override
	public void forEach(Processor processor) {
		ListNode pomCvor = new ListNode();
		pomCvor = first;

		for (int i = 0; i < size; i++) {
			processor.process(pomCvor.value);
			pomCvor = pomCvor.next;
		}
	}

	/**
	 * Dohvaća objekt koji se nalaza u listi na zadanom indexu. Ispravni indexi
	 * su od nula do (veličine_liste - 1).
	 * 
	 * @param index index traženog objekta
	 * @return traženi objekt
	 * 
	 * @throws IndexOutOfBoundsException ako je zadani index neispravan
	 */
	public Object get(int index) {
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		ListNode pomCvor = new ListNode();

		if (index > (float) size / 2) {
			pomCvor = last;
			for (int i = 0; i < size - index - 1; i++) {
				pomCvor = pomCvor.previous;
			}
			return pomCvor.value;
		} else {
			pomCvor = first;
			for (int i = 0; i < index; i++) {
				pomCvor = pomCvor.next;
			}
			return pomCvor.value;
		}
	}

	/**
	 * Metoda koja uklanja sve elemente liste.
	 */
	@Override
	public void clear() {
		size = 0;
		first = last = null;
	}

	/**
	 * Dodaje objekt na zadanu poziciju, ne briše element na poziciji nego ih
	 * pomiče za jedno mjesto prema kraju. U slučaju neispravne pozicije baca
	 * iznimku. Prosječna složenost je O(n).
	 * 
	 * @param value objekt koji se dodaje
	 * @param position pozicija na koju se dodaje
	 * 
	 * @throws IllegalArgumentException u slučaju neispravne pozicije ili null vrijednosti
	 */
	public void insert(Object value, int position) {
		if (position < 0 || position > size || value == null) {
			throw new IllegalArgumentException();
		}

		ListNode noviCvor = new ListNode();
		ListNode pomCvor = new ListNode();
		noviCvor.value = value;

		if (position == 0) {
			noviCvor.next = first;
			first.previous = noviCvor;
			first = noviCvor;
			size++;
			return;
		}
		if (position == size) {
			noviCvor.previous = last;
			last.next = noviCvor;
			last = noviCvor;
			size++;
			return;
		}
		if (position > (float) size / 2) {
			pomCvor = last;
			for (int i = 0; i < size - position + 1; i++) {
				pomCvor = pomCvor.previous;
			}
			noviCvor.next = pomCvor.next;
			pomCvor.next.previous = noviCvor;
			pomCvor.next = noviCvor;
			noviCvor.previous = pomCvor;
			size++;
		} else {
			pomCvor = first;
			for (int i = 0; i < position - 2; i++) {
				pomCvor = pomCvor.next;
			}
			noviCvor.next = pomCvor.next;
			pomCvor.next.previous = noviCvor;
			pomCvor.next = noviCvor;
			noviCvor.previous = pomCvor;
			size++;
		}
	}

	/**
	 * Pretraživa kolekciju za zadanim objektom. Prosječna složenost je O(n)
	 * 
	 * @param value objekt koji se traži
	 * @return index prvog nađenog objekta, inače -1
	 */
	public int indexOf(Object value) {
		ListNode pomCvor = new ListNode();
		pomCvor = first;
		for (int i = 0; i < size; i++) {
			if (pomCvor.value.equals(value)) {
				return i;
			}
			pomCvor = pomCvor.next;
		}
		return -1;
	}

	/**
	 * Uklanja element na zadanom indexu iz kolekcije. Nakon izvršenja element
	 * sa index+1 je na index.
	 * 
	 * @param index index elementa
	 * 
	 * @throws IllegalArgumentException ako je index neispravan
	 */
	public void remove(int index) {
		if (index < 0 || index > size) {
			throw new IllegalArgumentException();
		}
		if (index == 0) {
			deleteFirst();
			return;
		} else if (index == (size - 1)) {
			deleteLast();
			return;
		}

		ListNode pomCvor = new ListNode();
		pomCvor = first;

		for (int i = 0; i < index; i++) {
			pomCvor = pomCvor.next;
		}
		izbaciIzListe(pomCvor);
	}

	/**
	 * Briše zadnji element liste i postavlja referencu na zadnjeg
	 */
	public void deleteLast() {
		last = last.previous;
		last.next = null;
		size--;
	}

	/**
	 * Briše prvi element liste i postavlja referencu na prvog
	 */
	public void deleteFirst() {
		first = first.next;
		first.previous = null;
		size--;
	}

	/**
	 * Izbaciva iz liste zadani element
	 * 
	 * @param cvor element koji se izbaciva
	 */
	public void izbaciIzListe(ListNode cvor) {
		cvor.previous.next = cvor.next;
		cvor.next.previous = cvor.previous;
		size--;
	}

}



