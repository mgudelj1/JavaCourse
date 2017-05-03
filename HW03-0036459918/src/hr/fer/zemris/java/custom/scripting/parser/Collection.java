package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Najviši razred, predstavlja kolekciju.
 * 
 * @author Marko Gudelj
 * @version 1.0
 */
public class Collection {
	/**
	 * Uobičajeni Konstruktor
	 */
	protected Collection() {

	}

	/**
	 * Provjerava je li određena kolekcija prazna.
	 * 
	 * @return true ako je prazna false inače
	 */
	public boolean isEmpty() {
		if (size() != 0) {
			return false;
		} else {
			return true;
		}

	}

	/**
	 * Izračunava veličinu kolekcije Ovdje implementirano da vraća 0.
	 * 
	 * @return vraća nula
	 */
	public int size() {
		return 0;
	}

	/**
	 * Dodaje objekt u kolekciju. Ovdje implementirano kao prazna metoda.
	 * 
	 * @param value objekt koji treba dodati
	 */
	public void add(Object value) {

	}

	/**
	 * Provjerava sadrži li kolekcija neki objekt. Ovdje implementirano da vraća
	 * false
	 * 
	 * @param value objekt koji treba provjeriti
	 * @return false
	 */
	public boolean contains(Object value) {
		return false;
	}

	/**
	 * Uklanja objekt iz kolekcije. Ovdje implementirano da vraća false.
	 * 
	 * @param value
	 *            objekt koji treba ukloniti
	 * @return false
	 */
	public boolean remove(Object value) {
		return false;
	}

	/**
	 * Stvara novo polje veličine pozivajuće kolekcije, puni ih sa sadržajem
	 * kolekcije i vraća polje. Ovdje implementirano da baca iznimku
	 * UnsupportedOperationException.
	 * 
	 * @return ne vraća ništa
	 * 
	 * @throws UnsupportedOperationException ovdje uvijek baca tu iznimku
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Metoda koja poziva processor.process za svaki element kolekcije. Ovdje
	 * implementirano kao prazna metoda.
	 * 
	 * @param processor Processor
	 */
	public void forEach(Processor processor) {

	}

	/**
	 * Metoda koja dodaje sve elemente dane kolekcije u pozivajuću. Sadrži
	 * lokalni razred Processor sa metodom process koja poziva add metodu.
	 * 
	 * @param other Kolekcija koju treba dodati
	 */
	public void addAll(Collection other) {

		class InnerProcessor extends Processor {

			@Override
			public void process(Object value) {

				add(value);
			}
		}
		InnerProcessor pomProc = new InnerProcessor();

		other.forEach(pomProc);
	}

	/**
	 * Metoda koja uklanja sve elemente kolekcije. Ovdje implementirana kao
	 * prazna metoda.
	 */
	public void clear() {

	}
}


